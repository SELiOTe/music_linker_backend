package com.seliote.mlb.api.controller;

import com.seliote.mlb.api.domain.req.auth.*;
import com.seliote.mlb.api.domain.req.auth.mapper.*;
import com.seliote.mlb.api.domain.resp.auth.*;
import com.seliote.mlb.api.domain.resp.auth.mapper.CaptchaRespMapper;
import com.seliote.mlb.api.domain.resp.auth.mapper.CountryListRespMapper;
import com.seliote.mlb.biz.domain.si.user.AddTrustDeviceSi;
import com.seliote.mlb.biz.domain.si.user.IsTrustDeviceSi;
import com.seliote.mlb.biz.domain.si.user.ResetPasswordSi;
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
        var user = userService.findUser(SignUpSmsReqMapper.INSTANCE.toFindUserSi(req));
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
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
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

    /**
     * 不受信设备登录
     *
     * @param req 请求实体
     * @return 响应实体，0 为登录成功会同时返回鉴权 Token，1 为短信验证码不正确，2 为帐号或密码错误
     * 3 为添加受信任设备失败，4 为获取登录 Token 失败
     */
    @Valid
    @PostMapping("/trustless_device_login")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<TrustlessDeviceLoginResp> trustlessDeviceLogin(
            @RequestBody @NotNull @Valid TrustlessDeviceLoginReq req) {
        // 校验短信验证码
        if (!commonService.checkTrustDeviceSms(TrustlessDeviceLoginReqMapper.INSTANCE.toCheckTrustDeviceSmsSi(req))) {
            log.info("Failed check trustless device login verify code, {}", req);
            return Resp.resp(1, "verify code incorrect");
        }
        // 校验用户是否存在
        var user = userService.findUser(TrustlessDeviceLoginReqMapper.INSTANCE.toFindUserSi(req));
        if (user.isEmpty()) {
            log.warn("{} login failed, user not exists", req);
            return Resp.resp(2, "login failed");
        }
        if (!userService.checkUserPassword(TrustlessDeviceLoginReqMapper.INSTANCE.toCheckUserPasswordSi(req))) {
            log.info("{} login failed, password incorrect", req);
            return Resp.resp(2, "login failed");
        }
        // 添加受信任设备
        if (!userService.addTrustDevice(AddTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            log.error("User {} login success, but can not add trust device", user.get());
            return Resp.resp(3, "add trust device failed");
        }
        // 创建 Token
        var token = userService.createToken(user.get().getUserId());
        if (token.isEmpty()) {
            log.error("Failed create token for {} when login", user);
            return Resp.resp(4, "can not create token for user");
        }
        // 成功后删除验证码，防止重放
        commonService.removeTrustDeviceSms(TrustlessDeviceLoginReqMapper.INSTANCE.toRemoveTrustDeviceSmsSi(req));
        return Resp.resp(TrustlessDeviceLoginResp.builder().token(token.get()).build());
    }

    /**
     * 受信设备登录
     *
     * @param req 请求实体
     * @return 响应实体，0 为登录成功会同时返回鉴权 Token，1 为帐号或密码错误，2 为非受信任设备
     * 3 为获取登录 Token 失败
     */
    @Valid
    @PostMapping("/trust_device_login")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<TrustDeviceLoginResp> trustDeviceLogin(@RequestBody @NotNull @Valid TrustDeviceLoginReq req) {
        var user = userService.findUser(TrustDeviceLoginReqMapper.INSTANCE.toFindUserSi(req));
        if (user.isEmpty()) {
            log.warn("{} not exists when login", req);
            return Resp.resp(1, "login failed");
        }
        if (!userService.isTrustDevice(IsTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            return Resp.resp(2, "not in trust device");
        }
        if (!userService.checkUserPassword(TrustDeviceLoginReqMapper.INSTANCE.toCheckUserPasswordSi(req))) {
            log.info("{} password incorrect when login", req);
            return Resp.resp(1, "login failed");
        }
        var token = userService.createToken(user.get().getUserId());
        if (token.isEmpty()) {
            log.error("Failed create token for {} when login", user);
            return Resp.resp(3, "can not create token for user");
        }
        return Resp.resp(TrustDeviceLoginResp.builder().token(token.get()).build());
    }

    /**
     * 发送重置密码短信验证码
     *
     * @param req 请求实体
     * @return 响应实体，0 为发送成功，1 为图片验证码错误，2 为用户未注册，3 为发送短信验证码失败
     */
    @Valid
    @PostMapping("/reset_password_sms")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<Void> resetPasswordSms(@RequestBody @NotNull @Valid ResetPasswordSmsReq req) {
        if (!commonService.checkCaptcha(ResetPasswordSmsReqMapper.INSTANCE.toCheckCaptchaSi(req))) {
            log.info("Incorrect captcha in reset password sms, {}", req);
            commonService.removeCaptcha(req.getUuid());
            return Resp.resp(1, "captcha code incorrect");
        }
        // 无论成功与否都删除验证码，防止验证码重放
        commonService.removeCaptcha(req.getUuid());
        var user = userService.findUser(ResetPasswordSmsReqMapper.INSTANCE.toFindUserSi(req));
        if (user.isEmpty()) {
            log.warn("Used {} had not signed up when reset password", req);
            return Resp.resp(2, "user had not signed up");
        }
        if (!commonService.sendResetPasswordSms(ResetPasswordSmsReqMapper.INSTANCE.toSendResetPasswordSmsSi(req))) {
            log.warn("Send reset password sms failed, {}", req);
            return Resp.resp(3, "send reset password verify code sms failed");
        }
        log.info("Send reset password sms to {} success", req);
        return Resp.resp();
    }

    /**
     * 重置密码
     *
     * @param req 请求实体
     * @return 响应实体，0 为重置成功并会返回登录 Token，1 为短信验证码校验失败，2 为用户尚未注册，3 为添加信任设备失败，
     * 4 为创建 Token 失败
     */
    @Valid
    @PostMapping("/reset_password")
    @ApiFreq(type = ApiFreqType.BODY, keys = "phoneCode,telNo", freq = 10, unit = ChronoUnit.DAYS)
    public Resp<ResetPasswordResp> resetPassword(@RequestBody @NotNull @Valid ResetPasswordReq req) {
        if (!commonService.checkResetPasswordSms(ResetPasswordReqMapper.INSTANCE.toCheckResetPasswordSmsSi(req))) {
            log.info("Failed check reset password verify code, {}", req);
            return Resp.resp(1, "verify code incorrect");
        }
        // 校验用户是否存在
        var user = userService.findUser(ResetPasswordReqMapper.INSTANCE.toFindUserSi(req));
        if (user.isEmpty()) {
            log.warn("{} reset password failed, user not exists", req);
            return Resp.resp(2, "user had not signed up");
        }
        // 不是受信任设备就添加
        if (!userService.isTrustDevice(IsTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())
                && !userService.addTrustDevice(AddTrustDeviceSi.builder()
                .userId(user.get().getUserId()).deviceNo(req.getDeviceNo()).build())) {
            log.error("{} add trust device failed when reset password", req);
            return Resp.resp(3, "add trust device failed");
        }
        // 重置密码
        userService.resetPassword(
                ResetPasswordSi.builder().userId(user.get().getUserId()).password(req.getPassword()).build());
        commonService.removeResetPasswordSms(ResetPasswordReqMapper.INSTANCE.toRemoveResetPasswordSmsSi(req));
        // 创建一个 Token
        var token = userService.createToken(user.get().getUserId());
        if (token.isEmpty()) {
            log.error("Failed create token for {} when reset password", user);
            return Resp.resp(4, "can not create token for user");
        }
        return Resp.resp(ResetPasswordResp.builder().token(token.get()).build());
    }
}
