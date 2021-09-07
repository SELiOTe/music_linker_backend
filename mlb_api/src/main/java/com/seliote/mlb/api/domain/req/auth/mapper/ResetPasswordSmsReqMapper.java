package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.ResetPasswordSmsReq;
import com.seliote.mlb.biz.domain.si.common.CheckCaptchaSi;
import com.seliote.mlb.biz.domain.si.common.SendResetPasswordSmsSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 发送重置密码短信验证码 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-07
 */
@Mapper
public interface ResetPasswordSmsReqMapper {

    // 单例引用
    ResetPasswordSmsReqMapper INSTANCE = Mappers.getMapper(ResetPasswordSmsReqMapper.class);

    /**
     * 发送重置密码短信验证码 Req 转为校验图形验证码是否正确 SI
     *
     * @param req 发送重置密码短信验证码 Req
     * @return 校验图形验证码是否正确 SI
     */
    CheckCaptchaSi toCheckCaptchaSi(ResetPasswordSmsReq req);

    /**
     * 发送重置密码短信验证码 Req 转为校验图形验证码是否正确 SI
     *
     * @param req 发送重置密码短信验证码 Req
     * @return 校验图形验证码是否正确 SI
     */
    FindUserSi toFindUserSi(ResetPasswordSmsReq req);

    /**
     * 发送重置密码短信验证码 Req 转为发送重置密码短信 SI
     *
     * @param req 发送重置密码短信验证码 Req
     * @return 发送重置密码短信 SI
     */
    SendResetPasswordSmsSi toSendResetPasswordSmsSi(ResetPasswordSmsReq req);
}
