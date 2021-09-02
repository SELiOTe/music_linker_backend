package com.seliote.mlb.api;

import com.seliote.mlb.api.domain.req.auth.IsSignedUpReq;
import com.seliote.mlb.api.domain.req.auth.SignUpSmsReq;
import com.seliote.mlb.api.domain.req.auth.mapper.IsSignedUpReqMapper;
import com.seliote.mlb.api.domain.req.auth.mapper.SignUpSmsReqMapper;
import com.seliote.mlb.api.domain.resp.auth.CaptchaResp;
import com.seliote.mlb.api.domain.resp.auth.CountryListResp;
import com.seliote.mlb.api.domain.resp.auth.mapper.CaptchaRespMapper;
import com.seliote.mlb.api.domain.resp.auth.mapper.CountryListRespMapper;
import com.seliote.mlb.biz.service.CommonService;
import com.seliote.mlb.biz.service.CountryService;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.config.api.ApiFreq;
import com.seliote.mlb.common.config.api.ApiFreqType;
import com.seliote.mlb.common.domain.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 认证与授权相关 Controller
 * 该 Controller 内方法无需鉴权
 *
 * @author seliote
 * @version 2021-08-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final CountryService countryService;
    private final CommonService commonService;
    private final UserService userService;

    @Autowired
    public AuthController(CountryService countryService,
                          CommonService commonService,
                          UserService userService) {
        this.countryService = countryService;
        this.commonService = commonService;
        this.userService = userService;
    }

    /**
     * 用户是否注册
     *
     * @param req 请求实体
     * @return 响应实体
     */
    @Valid
    @PostMapping("/is_signed_up")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo")
    public Resp<Void> isSignedUp(@RequestBody @NotNull @Valid IsSignedUpReq req) {
        if (userService.isSignedUp(IsSignedUpReqMapper.INSTANCE.toSi(req))) {
            log.info("User {} had signed up", req);
            return Resp.resp(0, "had signed up");
        } else {
            log.info("User {} not signed up", req);
            return Resp.resp(1, "not signed up");
        }
    }

    /**
     * 获取国家列表
     *
     * @return 国家列表响应
     */
    @Valid
    @PostMapping("/country_list")
    public Resp<List<CountryListResp>> countryList() {
        var soList = countryService.listCountryInfo();
        return Resp.resp(CountryListRespMapper.INSTANCE.fromSoList(soList));
    }

    /**
     * 获取图片验证码
     *
     * @return 图片验证码响应
     */
    @Valid
    @PostMapping("/captcha")
    public Resp<CaptchaResp> captcha() {
        var so = commonService.createCaptcha();
        return Resp.resp(CaptchaRespMapper.INSTANCE.fromSo(so));
    }

    /**
     * 发送注册短信验证码
     *
     * @param req 请求实体
     * @return 响应实体，0 为发送成功，1 为图形验证码错误，2 为用户已注册，3 为短信发送失败
     */
    @Valid
    @PostMapping("/sign_up_sms")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 3)
    public Resp<Void> signUpSms(@RequestBody @NotNull @Valid SignUpSmsReq req) {
        if (!commonService.checkCaptcha(SignUpSmsReqMapper.INSTANCE.toCheckCaptchaSi(req))) {
            commonService.removeCaptcha(req.getUuid());
            return Resp.resp(1, "captcha code incorrect");
        }
        // 无论成功与否都删除验证码，防止验证码重放
        commonService.removeCaptcha(req.getUuid());
        if (userService.isSignedUp(SignUpSmsReqMapper.INSTANCE.toIsSignedUpSi(req))) {
            return Resp.resp(2, "user had signed up");
        }
        if (!commonService.sendSms(SignUpSmsReqMapper.INSTANCE.toSendSmsSi(req))) {
            return Resp.resp(3, "send sign up verify code sms failed");
        }
        return Resp.resp();
    }
}
