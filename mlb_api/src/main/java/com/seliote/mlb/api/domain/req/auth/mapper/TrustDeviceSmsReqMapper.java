package com.seliote.mlb.api.domain.req.auth.mapper;

import com.seliote.mlb.api.domain.req.auth.TrustDeviceSmsReq;
import com.seliote.mlb.biz.domain.si.common.CheckCaptchaSi;
import com.seliote.mlb.biz.domain.si.common.SendTrustDeviceSmsSi;
import com.seliote.mlb.biz.domain.si.user.FindUserSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 获取信任设备短信验证码 Req Map Struct
 *
 * @author seliote
 * @version 2021-09-05
 */
@Mapper
public interface TrustDeviceSmsReqMapper {

    // 单例引用
    TrustDeviceSmsReqMapper INSTANCE = Mappers.getMapper(TrustDeviceSmsReqMapper.class);

    /**
     * 从获取信任设备短信验证码 Req 转为校验图形验证码是否正确 SI
     *
     * @param req 获取信任设备短信验证码 Req
     * @return 校验图形验证码是否正确 SI
     */
    CheckCaptchaSi toCheckCaptchaSi(TrustDeviceSmsReq req);

    /**
     * 从获取信任设备短信验证码 Req 转为查找用户信息 SI
     *
     * @param req 获取信任设备短信验证码 Req
     * @return 查找用户信息 SI
     */
    FindUserSi toFindUserSi(TrustDeviceSmsReq req);

    /**
     * 从获取信任设备短信验证码 Req 转为发送信任设备短信验证码 SI
     *
     * @param req 获取信任设备短信验证码 Req
     * @return 发送信任设备短信验证码 SI
     */
    SendTrustDeviceSmsSi toSendTrustDeviceSmsSi(TrustDeviceSmsReq req);
}
