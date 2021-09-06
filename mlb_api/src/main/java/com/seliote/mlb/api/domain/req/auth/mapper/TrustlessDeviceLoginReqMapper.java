package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.TrustlessDeviceLoginReq;
import com.seliote.mlb.biz.domain.si.common.CheckTrustDeviceSmsSi;
import com.seliote.mlb.biz.domain.si.common.RemoveTrustDeviceSmsSi;
import com.seliote.mlb.biz.domain.si.user.CheckUserPasswordSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 不受信设备登录 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-06
 */
@Mapper
public interface TrustlessDeviceLoginReqMapper {

    // 单例引用
    TrustlessDeviceLoginReqMapper INSTANCE = Mappers.getMapper(TrustlessDeviceLoginReqMapper.class);

    /**
     * 不受信设备登录 Req 转为校验信任设备短信验证码 SI
     *
     * @param req 不受信设备登录 Req
     * @return 校验信任设备短信验证码 SI
     */
    CheckTrustDeviceSmsSi toCheckTrustDeviceSmsSi(TrustlessDeviceLoginReq req);

    /**
     * 不受信设备登录 Req 转为查找用户信息 SI
     *
     * @param req 不受信设备登录 Req
     * @return 查找用户信息 SI
     */
    FindUserSi toFindUserSi(TrustlessDeviceLoginReq req);

    /**
     * 不受信设备登录 Req 转为校验用户密码 SI
     *
     * @param req 不受信设备登录 Req
     * @return 校验用户密码 SI
     */
    CheckUserPasswordSi toCheckUserPasswordSi(TrustlessDeviceLoginReq req);

    /**
     * 不受信设备登录 Req 移除信任设备短信验证码 SI
     *
     * @param req 不受信设备登录 Req
     * @return 移除信任设备短信验证码 SI
     */
    RemoveTrustDeviceSmsSi toRemoveTrustDeviceSmsSi(TrustlessDeviceLoginReq req);
}
