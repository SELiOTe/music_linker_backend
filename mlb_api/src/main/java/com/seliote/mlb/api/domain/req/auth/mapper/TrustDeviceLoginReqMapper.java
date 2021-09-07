package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.TrustDeviceLoginReq;
import com.seliote.mlb.biz.domain.si.user.CheckUserPasswordSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 受信设备登录 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-07
 */
@Mapper
public interface TrustDeviceLoginReqMapper {

    // 单例引用
    TrustDeviceLoginReqMapper INSTANCE = Mappers.getMapper(TrustDeviceLoginReqMapper.class);

    /**
     * 受信设备登录 Req 转为查找用户信息 SI
     *
     * @param req 受信设备登录 Req
     * @return 查找用户信息 SI
     */
    FindUserSi toFindUserSi(TrustDeviceLoginReq req);

    /**
     * 受信设备登录 Req 转为校验用户密码 SI
     *
     * @param req 受信设备登录 Req
     * @return 校验用户密码 SI
     */
    CheckUserPasswordSi toCheckUserPasswordSi(TrustDeviceLoginReq req);
}
