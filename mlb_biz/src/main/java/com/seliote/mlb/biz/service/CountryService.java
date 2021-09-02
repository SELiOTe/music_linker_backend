package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.so.country.ListCountryInfoSo;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 国家相关 Service
 *
 * @author seliote
 * @version 2021-06-27
 */
@Validated
public interface CountryService {

    /**
     * 列出所有支持的国家信息
     *
     * @return 国家信息列表
     */
    @NotNull
    @Valid
    List<ListCountryInfoSo> listCountryInfo();
}
