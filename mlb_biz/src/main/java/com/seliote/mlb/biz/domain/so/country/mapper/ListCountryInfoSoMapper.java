package com.seliote.mlb.biz.domain.so.country.mapper;

import com.seliote.mlb.biz.domain.so.country.ListCountryInfoSo;
import com.seliote.mlb.dao.entity.CountryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 列出所有支持的国家信息 SO MapStruct 映射
 *
 * @author seliote
 * @version 2021-06-29
 */
@Mapper
public interface ListCountryInfoSoMapper {

    // 单例引用
    ListCountryInfoSoMapper INSTANCE = Mappers.getMapper(ListCountryInfoSoMapper.class);

    /**
     * 从实体列表转换为 SO 列表
     *
     * @param entityList 实体列表
     * @return SO 列表
     */
    List<ListCountryInfoSo> fromEntityList(List<CountryEntity> entityList);
}
