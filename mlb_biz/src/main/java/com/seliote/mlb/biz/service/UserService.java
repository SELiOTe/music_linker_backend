package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.si.user.*;
import com.seliote.mlb.biz.domain.so.user.FindUserSo;
import com.seliote.mlb.biz.domain.so.user.GetUserInfoSo;
import com.seliote.mlb.biz.domain.so.user.SignUpSo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

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
     * @return 已注册返回对应 SO
     */
    @Valid
    Optional<FindUserSo> findUser(@NotNull @Valid FindUserSi si);

    /**
     * 普通用户注册
     *
     * @param si 请求 SI
     * @return 注册成功返回用户注册信息 SO
     */
    Optional<SignUpSo> signUp(@NotNull @Valid SignUpSi si);

    /**
     * 用户添加受信任设备
     *
     * @param si 请求 SI
     * @return 添加成功返回 true，否则返回 false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean addTrustDevice(@NotNull @Valid AddTrustDeviceSi si);

    /**
     * 创建用户 Token 并写入至缓存，存在旧 Token 时将会刷新旧 Token
     *
     * @param userId 用户 ID
     * @return 创建成功返回 Token
     */
    Optional<String> createToken(@NotNull Long userId);

    /**
     * 判断是否为用户受信任设备
     *
     * @param si 请求 SI
     * @return 信任时返回 true，否则返回 false
     */
    boolean isTrustDevice(@NotNull @Valid IsTrustDeviceSi si);

    /**
     * 校验用户密码
     *
     * @param si 请求 SI
     * @return 校验成功返回 true，否则返回 false
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    boolean checkUserPassword(@NotNull @Valid CheckUserPasswordSi si);

    /**
     * 重置用户密码
     *
     * @param si 请求 SI
     */
    void resetPassword(@NotNull @Valid ResetPasswordSi si);

    /**
     * 获取当前用户 ID
     *
     * @return 当前用户 ID，无法获取时返回空
     */
    Optional<Long> currentUserId();

    /**
     * 获取用户信息
     *
     * @param userId 用户 ID
     * @return 用户信息 SO
     */
    Optional<GetUserInfoSo> getUserInfo(@NotNull Long userId);
}
