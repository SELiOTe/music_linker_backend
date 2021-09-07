package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.SignUpSmsReq;
import com.seliote.mlb.biz.domain.si.common.CheckCaptchaSi;
import com.seliote.mlb.biz.domain.si.common.SendSignUpSmsSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 发送注册验证码 Req MapStruct
 *
 * @author seliote
 * @version 2021-09-01
 */
@Mapper
public interface SignUpSmsReqMapper {

    // 单例引用
    SignUpSmsReqMapper INSTANCE = Mappers.getMapper(SignUpSmsReqMapper.class);

    /**
     * 从发送注册验证码 Req 转为校验图形验证码是否正确 SI
     *
     * @param req 发送注册验证码 Req
     * @return 校验图形验证码是否正确 SI
     */
    CheckCaptchaSi toCheckCaptchaSi(SignUpSmsReq req);

    /**
     * 从发送注册验证码 Req 转为用户是否注册 SI
     *
     * @param req 发送注册验证码 Req
     * @return 用户是否注册 SI
     */
    FindUserSi toFindUserSi(SignUpSmsReq req);

    /**
     * 从发送注册验证码 Req 转为发送短信验证码 SI
     *
     * @param req 发送注册验证码 Req
     * @return 发送短信验证码 SI
     */
    SendSignUpSmsSi toSendSmsSi(SignUpSmsReq req);
}
