package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.IsTrustDeviceReq;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 是否为受信任设备 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-05
 */
@Mapper
public interface IsTrustDeviceReqMapper {

    // 单例引用
    IsTrustDeviceReqMapper INSTANCE = Mappers.getMapper(IsTrustDeviceReqMapper.class);

    /**
     * 从是否为受信任设备 Req 转为用户是否注册 SI
     *
     * @param req 是否为受信任设备 Req
     * @return 用户是否注册 SI
     */
    FindUserSi toIsSignedUpSi(IsTrustDeviceReq req);
}
