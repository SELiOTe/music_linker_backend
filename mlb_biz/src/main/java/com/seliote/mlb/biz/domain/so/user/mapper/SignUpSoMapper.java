package com.seliote.mlb.biz.domain.so.user.mapper;

import com.seliote.mlb.biz.domain.so.user.SignUpSo;
import com.seliote.mlb.dao.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * 普通用户注册 SO Map Struct
 *
 * @author seliote
 * @version 2021-09-04
 */
@Mapper
public interface SignUpSoMapper {

    // 单例引用
    SignUpSoMapper INSTANCE = Mappers.getMapper(SignUpSoMapper.class);

    /**
     * 从用户实体转为普通用户注册 SO
     *
     * @param userEntity 用户实体
     * @return 普通用户注册 SO
     */
    @Mapping(source = "id", target = "userId")
    @Mapping(source = "countryEntity.countryCode", target = "countryCode")
    @Mapping(source = "countryEntity.phoneCode", target = "phoneCode")
    @Mapping(source = "telNo", target = "telNo")
    SignUpSo fromUserEntity(UserEntity userEntity);
}
