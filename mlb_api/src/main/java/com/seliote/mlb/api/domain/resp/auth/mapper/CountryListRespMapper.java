package com.seliote.mlb.api.domain.resp.auth.mapper;

import com.seliote.mlb.api.domain.resp.auth.CountryListResp;
import com.seliote.mlb.biz.domain.so.country.ListCountryInfoSo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 列出所有国家信息 Resp MapStruct
 *
 * @author seliote
 * @version 2021-08-05
 */
@Mapper
public interface CountryListRespMapper {

    // 单例引用
    CountryListRespMapper INSTANCE = Mappers.getMapper(CountryListRespMapper.class);

    /**
     * 从 SO 列表转为响应实体列表
     *
     * @param soList SO 列表
     * @return 实体列表
     */
    List<CountryListResp> fromSoList(List<ListCountryInfoSo> soList);
}
