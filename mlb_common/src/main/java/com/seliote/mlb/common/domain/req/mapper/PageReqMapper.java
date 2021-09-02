package com.seliote.mlb.common.domain.req.mapper;

import com.seliote.mlb.common.domain.req.PageReq;
import com.seliote.mlb.common.domain.si.PageSi;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * PageReq MapStruct 映射
 *
 * @author seliote
 * @version 2021-08-07
 */
@Mapper
public interface PageReqMapper {

    // 单例引用
    PageReqMapper INSTANCE = Mappers.getMapper(PageReqMapper.class);

    /**
     * 将 PageReq 转为 PageSi
     *
     * @param pageReq PageReq 对象
     * @return PageSi 对象
     */
    PageSi toPageSi(PageReq pageReq);
}
