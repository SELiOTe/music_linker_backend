package com.seliote.mlb.auth.config;

import com.seliote.mlb.auth.service.JwsService;
import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.service.RedisService;
import com.seliote.mlb.common.util.NetworkUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWS 过滤器
 *
 * @author seliote
 * @version 2021-07-04
 */
@Slf4j
@Component
public class JwsFilter extends OncePerRequestFilter {

    // Token 值前缀
    private static final String AUTHORIZATION_VALUE_PREFIX = "Bearer";

    // Redis 缓存 key
    private static final String REDIS_KEY = "jws";

    private final RedisService redisService;
    private final YmlConfig.Jws jwsConfig;
    private final JwsService jwsService;

    @Autowired
    public JwsFilter(RedisService redisService, YmlConfig ymlConfig, JwsService jwsService) {
        this.redisService = redisService;
        this.jwsConfig = ymlConfig.getJws();
        this.jwsService = jwsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest httpServletRequest,
                                    @NonNull HttpServletResponse httpServletResponse,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        var jwsHeader = NetworkUtils.getRequestHeader(HttpHeaders.AUTHORIZATION);
        if (jwsHeader.isPresent() && jwsHeader.get().startsWith(AUTHORIZATION_VALUE_PREFIX)) {
            var jws = jwsHeader.get().substring(AUTHORIZATION_VALUE_PREFIX.length()).trim();
            var payload = jwsService.parse(jws);
            if (payload.isPresent() && payload.get().available() && jwsConfig.getIss().equals(payload.get().getIss())) {
                var cached = redisService.get(REDIS_KEY, payload.get().getName());
                if (cached.isPresent() && jws.equals(cached.get())
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var auth = new UsernamePasswordAuthenticationToken(
                            payload.get(), null, payload.get().getRoles());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.debug("Success set security context {}", payload.get());
                } else {
                    log.warn("JWS {} not match", jws);
                }
            } else {
                log.warn("Invalid JWS {}", jws);
            }
        } else {
            log.info("Empty header, skip set security context");
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
