package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.si.user.AddTrustDeviceSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import com.seliote.mlb.biz.domain.si.user.IsTrustDeviceSi;
import com.seliote.mlb.biz.domain.si.user.SignUpSi;
import com.seliote.mlb.biz.domain.so.user.FindUserSo;
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
    boolean addTrustDevice(@NotNull @Valid AddTrustDeviceSi si);

    /**
     * 创建用户 Token
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
}
