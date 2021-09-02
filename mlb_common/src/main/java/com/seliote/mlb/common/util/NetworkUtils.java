package com.seliote.mlb.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 网络相关工具类
 *
 * @author seliote
 * @version 2021-07-03
 */
@Slf4j
public class NetworkUtils {

    /**
     * 获取当前请求的 HttpServletRequest
     *
     * @return HttpServletRequest 对象
     */
    public static Optional<HttpServletRequest> getServletRequest() {
        var requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            log.warn("Can not get request attributes");
            return Optional.empty();
        }
        if (!(requestAttributes instanceof ServletRequestAttributes)) {
            log.warn("Failed get ServletRequestAttributes, actually type is {}",
                    requestAttributes.getClass().getCanonicalName());
            return Optional.empty();
        }
        var servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return Optional.of(servletRequestAttributes.getRequest());
    }

    /**
     * 获取当前线程绑定请求的 URI
     *
     * @return 请求 URI，获取失败返回空
     */
    public static Optional<String> getRequestUri() {
        var request = getServletRequest();
        if (request.isEmpty()) {
            return Optional.empty();
        }
        log.trace("Get request URI by servlet");
        return Optional.ofNullable(request.get().getRequestURI());
    }

    /**
     * 获取当前线程绑定请求的源 IP 地址
     * <em>如果涉及反向代理，需要配置反向代理将 X-Real-IP 请求头填充为真实 IP，否则将获取到反向代理的地址</em>
     *
     * @return 源 IP 地址，获取失败返回空
     */
    public static Optional<String> getRequestIp() {
        var request = getServletRequest();
        if (request.isEmpty()) {
            return Optional.empty();
        }
        var realIp = request.get().getHeader("X-Real-IP");
        if (StringUtils.hasText(realIp)) {
            log.trace("Get request IP by X-Real-IP header");
            return Optional.of(realIp);
        }
        log.trace("Get request IP by servlet");
        return Optional.ofNullable(request.get().getRemoteAddr());
    }

    /**
     * 获取当前线程绑定请求的请求头
     *
     * @param header 请求头 Key
     * @return 请求头 Value
     */
    public static Optional<String> getRequestHeader(@NonNull String header) {
        var request = getServletRequest();
        if (request.isEmpty()) {
            return Optional.empty();
        }
        log.trace("Get header {} by servlet", header);
        var value = request.get().getHeader(header);
        if (!StringUtils.hasText(value)) {
            log.info("Empty header {}", header);
            return Optional.empty();
        }
        return Optional.of(value);
    }
}
