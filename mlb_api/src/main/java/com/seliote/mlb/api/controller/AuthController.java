package com.seliote.mlb.api.controller;

import com.seliote.mlb.api.domain.req.auth.*;
import com.seliote.mlb.api.domain.req.auth.mapper.*;
import com.seliote.mlb.api.domain.resp.auth.CaptchaResp;
import com.seliote.mlb.api.domain.resp.auth.CountryListResp;
import com.seliote.mlb.api.domain.resp.auth.SignUpResp;
import com.seliote.mlb.api.domain.resp.auth.mapper.CaptchaRespMapper;
import com.seliote.mlb.api.domain.resp.auth.mapper.CountryListRespMapper;
import com.seliote.mlb.biz.domain.si.user.AddTrustDeviceSi;
import com.seliote.mlb.biz.domain.si.user.IsTrustDeviceSi;
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
import java.time.temporal.ChronoUnit;
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
        var so = userService.findUser(IsSignedUpReqMapper.INSTANCE.toSi(req));
        if (so.isPresent()) {
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
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<Void> signUpSms(@RequestBody @NotNull @Valid SignUpSmsReq req) {
        if (!commonService.checkCaptcha(SignUpSmsReqMapper.INSTANCE.toCheckCaptchaSi(req))) {
            log.info("Incorrect captcha in send sign up sms, {}", req);
            commonService.removeCaptcha(req.getUuid());
            return Resp.resp(1, "captcha code incorrect");
        }
        // 无论成功与否都删除验证码，防止验证码重放
        commonService.removeCaptcha(req.getUuid());
        var user = userService.findUser(SignUpSmsReqMapper.INSTANCE.toIsSignedUpSi(req));
        if (user.isPresent()) {
            log.warn("Used {} had signed up when sign up", req);
            return Resp.resp(2, "user had signed up");
        }
        if (!commonService.sendSignUpSms(SignUpSmsReqMapper.INSTANCE.toSendSmsSi(req))) {
            log.warn("Send sign up sms failed, {}", req);
            return Resp.resp(3, "send sign up verify code sms failed");
        }
        log.info("Send sign up sms to {} success", req);
        return Resp.resp();
    }

    /**
     * 注册接口
     *
     * @param req 请求实体
     * @return 响应实体，0 为注册成功会同时返回鉴权 Token，1 短信验证码不正确，2 为注册失败
     * 3 为添加受信任设备失败，4 为获取登录 Token 失败
     */
    @Valid
    @PostMapping("/sign_up")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 2)
    public Resp<SignUpResp> signUp(@RequestBody @NotNull @Valid SignUpReq req) {
        if (!commonService.checkSignUpSms(SignUpReqMapper.INSTANCE.toCheckSignUpSmsSi(req))) {
            log.info("Failed check sign up verify code, {}", req);
            return Resp.resp(1, "verify code incorrect", null);
        }
        // 成功使用后删除验证码，防止重放
        commonService.removeSignUpSms(SignUpReqMapper.INSTANCE.toRemoveSignUpSmsSi(req));
        var signUpSo = userService.signUp(SignUpReqMapper.INSTANCE.toSignUpSi(req));
        if (signUpSo.isEmpty()) {
            log.warn("Failed sign up for {}", req);
            return Resp.resp(2, "sign up failed");
        }
        if (!userService.addTrustDevice(AddTrustDeviceSi.builder()
                .userId(signUpSo.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            log.error("User sign up success, but add trust device failed");
            return Resp.resp(3, "add trust device failed");
        }
        var token = userService.createToken(signUpSo.get().getUserId());
        if (token.isEmpty()) {
            log.error("Failed create token for {} when sign up", req);
            return Resp.resp(4, "can not create token for user");
        }
        return Resp.resp(SignUpResp.builder().token(token.get()).build());
    }

    /**
     * 查询设备是否为对应帐号的受信任设备
     *
     * @param req 请求实体
     * @return 响应实体，0 为受信任设备，1 为用户未找到，2 为非受信任设备
     */
    @Valid
    @PostMapping("/is_trust_device")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo")
    public Resp<Void> isTrustDevice(@RequestBody @NotNull @Valid IsTrustDeviceReq req) {
        var user = userService.findUser(IsTrustDeviceReqMapper.INSTANCE.toIsSignedUpSi(req));
        if (user.isEmpty()) {
            log.warn("User {} not exists when query trust device", req);
            return Resp.resp(1, "user not found");
        }
        if (userService.isTrustDevice(IsTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            log.info("Trust user device {}", req);
            return Resp.resp(0, "trust device");
        } else {
            log.info("Not trust user device {}", req);
            return Resp.resp(2, "not trust device");
        }
    }

    /**
     * 发送信任设备短信验证码
     *
     * @param req 请求实体
     * @return 响应实体，0 为发送成功，1 为图形验证码错误，2 为用户未注册，3 为已是受信任设备，4 为短信发送失败
     */
    @Valid
    @PostMapping("/trust_device_sms")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<Void> trustDeviceSms(@RequestBody @NotNull @Valid TrustDeviceSmsReq req) {
        if (!commonService.checkCaptcha(TrustDeviceSmsReqMapper.INSTANCE.toCheckCaptchaSi(req))) {
            log.info("Incorrect captcha when send trust device sms, {}", req);
            commonService.removeCaptcha(req.getUuid());
            return Resp.resp(1, "captcha code incorrect");
        }
        // 无论成功与否都删除验证码，防止验证码重放
        commonService.removeCaptcha(req.getUuid());
        var user = userService.findUser(TrustDeviceSmsReqMapper.INSTANCE.toFindUserSi(req));
        if (user.isEmpty()) {
            log.warn("User {} not signed up when send trust device sms", req);
            return Resp.resp(2, "user not signed up");
        }
        if (userService.isTrustDevice(IsTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            log.info("Device had trusted, {}", req);
            return Resp.resp(3, "device had trusted");
        }
        if (!commonService.sendTrustDeviceSms(TrustDeviceSmsReqMapper.INSTANCE.toSendTrustDeviceSmsSi(req))) {
            log.warn("Send trust device sms failed, {}", req);
            return Resp.resp(4, "send trust device sms failed");
        }
        log.info("Send trust device sms to {} success", req);
        return Resp.resp();
    }
}
