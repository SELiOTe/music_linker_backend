package com.seliote.mlb.api.domain.resp.user.mapper;

import com.seliote.mlb.api.domain.resp.user.InfoResp;
import com.seliote.mlb.biz.domain.so.user.GetUserInfoSo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 用户个人信息 Resp Map Struct
 *
 * @author seliote
 * @version 2021-09-12
 */
@Mapper
public interface InfoRespMapper {

    // 单例引用
    InfoRespMapper INSTANCE = Mappers.getMapper(InfoRespMapper.class);

    /**
     * 获取用户信息 SO 转为用户个人信息 Resp
     *
     * @param getUserInfoSo 获取用户信息 SO
     * @return 用户个人信息 Resp
     */
    InfoResp fromGetUserInfoSo(GetUserInfoSo getUserInfoSo);
}
