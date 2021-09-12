package com.seliote.mlb.biz.domain.so.user.mapper;

import com.seliote.mlb.biz.domain.so.user.GetUserInfoSo;
import com.seliote.mlb.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 获取用户信息 SO Map Struct
 *
 * @author seliote
 * @version 2021-09-12
 */
@Mapper
public interface GetUserInfoSoMapper {

    // 单例引用
    GetUserInfoSoMapper INSTANCE = Mappers.getMapper(GetUserInfoSoMapper.class);

    /**
     * 从用户信息实体转为获取用户信息 SO
     *
     * @param userEntity 用户信息实体
     * @return 获取用户信息 SO
     */
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "countryEntity.countryCode", target = "countryCode")
    @Mapping(source = "countryEntity.localName", target = "localName")
    @Mapping(source = "nickname", target = "nickname")
    @Mapping(source = "gender", target = "gender")
    @Mapping(source = "avatar", target = "avatar")
    GetUserInfoSo fromUserEntity(UserEntity userEntity);
}
