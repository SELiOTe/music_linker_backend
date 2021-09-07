package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.ResetPasswordReq;
import com.seliote.mlb.biz.domain.si.common.CheckResetPasswordSmsSi;
import com.seliote.mlb.biz.domain.si.common.RemoveResetPasswordSmsSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 重置密码 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-07
 */
@Mapper
public interface ResetPasswordReqMapper {

    // 单例引用
    ResetPasswordReqMapper INSTANCE = Mappers.getMapper(ResetPasswordReqMapper.class);

    /**
     * 重置密码 Req 转校验重置密码短信验证码 SI
     *
     * @param req 重置密码 Req
     * @return 校验重置密码短信验证码 SI
     */
    CheckResetPasswordSmsSi toCheckResetPasswordSmsSi(ResetPasswordReq req);

    /**
     * 重置密码 Req 转移除重置密码短信验证码 SI
     *
     * @param req 重置密码 Req
     * @return 移除重置密码短信验证码 SI
     */
    RemoveResetPasswordSmsSi toRemoveResetPasswordSmsSi(ResetPasswordReq req);

    /**
     * 重置密码 Req 转查找用户信息 SI
     *
     * @param req 重置密码 Req
     * @return 查找用户信息 SI
     */
    FindUserSi toFindUserSi(ResetPasswordReq req);
}
