package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.SignUpReq;
import com.seliote.mlb.biz.domain.si.common.CheckSignUpSmsSi;
import com.seliote.mlb.biz.domain.si.common.RemoveSignUpSmsSi;
import com.seliote.mlb.biz.domain.si.user.SignUpSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 注册接口 Req MapStruct
 *
 * @author seliote
 * @version 2021-09-03
 */
@Mapper
public interface SignUpReqMapper {

    // 单例引用
    SignUpReqMapper INSTANCE = Mappers.getMapper(SignUpReqMapper.class);

    /**
     * 注册接口 Req 转为校验短信验证码 SI
     *
     * @param req 注册接口 Req
     * @return 校验短信验证码 SI
     */
    CheckSignUpSmsSi toCheckSignUpSmsSi(SignUpReq req);

    /**
     * 注册接口 Req 转为普通用户注册 SI
     *
     * @param req 注册接口 Req
     * @return 普通用户注册 SI
     */
    SignUpSi toSignUpSi(SignUpReq req);

    /**
     * 注册接口 Req 转为移除注册短信验证码 SI
     *
     * @param req 注册接口 Req
     * @return 移除注册短信验证码 SI
     */
    RemoveSignUpSmsSi toRemoveSignUpSmsSi(SignUpReq req);
}
