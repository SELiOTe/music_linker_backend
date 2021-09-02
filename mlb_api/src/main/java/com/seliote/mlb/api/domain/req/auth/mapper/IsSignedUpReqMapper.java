package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.IsSignedUpReq;
import com.seliote.mlb.biz.domain.si.user.IsSignedUpSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户是否注册 Req MapStruct
 *
 * @author seliote
 * @version 2021-08-22
 */
@Mapper
public interface IsSignedUpReqMapper {

    // 单例引用
    IsSignedUpReqMapper INSTANCE = Mappers.getMapper(IsSignedUpReqMapper.class);

    /**
     * 从用户是否注册 Req 转为用户是否注册 SI
     *
     * @param req 用户是否注册 Req
     * @return 用户是否注册 SI
     */
    IsSignedUpSi toSi(IsSignedUpReq req);
}
