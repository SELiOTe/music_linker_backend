package com.seliote.mlb.api.controller;

import com.seliote.mlb.api.domain.resp.user.InfoResp;
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
}
