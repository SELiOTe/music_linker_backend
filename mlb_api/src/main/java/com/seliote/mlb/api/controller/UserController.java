package com.seliote.mlb.api.controller;

import com.seliote.mlb.api.domain.resp.user.InfoResp;
import com.seliote.mlb.api.domain.resp.user.RefreshTokenResp;
import com.seliote.mlb.api.domain.resp.user.mapper.InfoRespMapper;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.config.api.ApiFreq;
import com.seliote.mlb.common.domain.resp.Resp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户 Controller
 *
 * @author seliote
 * @version 2021-09-12
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户个人信息
     *
     * @return 响应实体，0 为获取成功，1 为无法找到用户
     */
    @Valid
    @PostMapping("/info")
    @ApiFreq(freq = 20)
    public Resp<InfoResp> info() {
        var id = userService.currentUserId();
        if (id.isEmpty()) {
            log.warn("Can not get current user id");
            return Resp.resp(1, "can not get user id");
        }
        var user = userService.getUserInfo(id.get());
        if (user.isEmpty()) {
            log.warn("Can not get user by id {}", id.get());
            return Resp.resp(1, "can not get user");
        }
        return Resp.resp(InfoRespMapper.INSTANCE.fromGetUserInfoSo(user.get()));
    }

    /**
     * 刷新当前用户的 Token 并失效当前的
     *
     * @return 新生成的 Token，0 为刷新成功并会返回相应的新 Token，1 为无法获取当前用户，2 为生成 Token 失败
     */
    @Valid
    @PostMapping("/refresh_token")
    @ApiFreq
    public Resp<RefreshTokenResp> refreshToken() {
        var id = userService.currentUserId();
        if (id.isEmpty()) {
            log.warn("Can not get current user id");
            return Resp.resp(1, "can not get user id");
        }
        var token = userService.createToken(id.get());
        if (token.isEmpty()) {
            log.warn("Can not generate token");
            return Resp.resp(2, "can not generate token");
        }
        return Resp.resp(RefreshTokenResp.builder().token(token.get()).build());
    }
}
