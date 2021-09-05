package com.seliote.mlb.biz.domain.so.user.mapper;

import com.seliote.mlb.biz.domain.so.user.FindUserSo;
import com.seliote.mlb.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 查找用户信息 SO MapStruct
 *
 * @author seliote
 * @version 2021-09-05
 */
@Mapper
public interface FindUserSoMapper {

    // 单例引用
    FindUserSoMapper INSTANCE = Mappers.getMapper(FindUserSoMapper.class);

    /**
     * 从用户信息实体转为查找用户信息 SO
     *
     * @param userEntity 用户信息实体
     * @return 查找用户信息 SO
     */
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "countryEntity.countryCode", target = "countryCode")
    @Mapping(source = "countryEntity.phoneCode", target = "phoneCode")
    @Mapping(source = "telNo", target = "telNo")
    FindUserSo fromUserEntity(UserEntity userEntity);
}
