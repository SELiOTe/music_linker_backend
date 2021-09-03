package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.si.user.IsSignedUpSi;
import com.seliote.mlb.biz.domain.si.user.SignUpSi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户信息 Service
 *
 * @author seliote
 * @version 2021-08-22
 */
@Validated
public interface UserService {

    /**
     * 判断用户是否已注册
     *
     * @param si 请求 SI
     * @return 已注册返回 true，否则返回 false
     */
    boolean isSignedUp(@NotNull @Valid IsSignedUpSi si);

    /**
     * 普通用户注册
     *
     * @param si 请求 SI
     */
    void signUp(@NotNull @Valid SignUpSi si);
}
