package com.seliote.mlb.api.domain.resp.auth.mapper;

import com.seliote.mlb.api.domain.resp.auth.CaptchaResp;
import com.seliote.mlb.biz.domain.so.country.CaptchaSo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 图形验证码 Resp MapStruct
 *
 * @author seliote
 * @version 2021-08-29
 */
@Mapper
public interface CaptchaRespMapper {

    // 单例引用
    CaptchaRespMapper INSTANCE = Mappers.getMapper(CaptchaRespMapper.class);

    /**
     * 从 SO 转为 Resp
     *
     * @param so SO 对象
     * @return Resp 对象
     */
    CaptchaResp fromSo(CaptchaSo so);
}
