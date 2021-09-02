package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.so.country.ListCountryInfoSo;
import com.seliote.mlb.biz.domain.so.country.mapper.ListCountryInfoSoMapper;
import com.seliote.mlb.biz.service.CountryService;
import com.seliote.mlb.dao.repo.CountryRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 国家相关 Service 实现
 *
 * @author seliote
 * @version 2021-06-27
 */
@Slf4j
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepo countryRepo;

    @Autowired
    public CountryServiceImpl(CountryRepo countryRepo) {
        this.countryRepo = countryRepo;
    }

    @Override
    public List<ListCountryInfoSo> listCountryInfo() {
        var countryEntities = countryRepo.findAll();
        return ListCountryInfoSoMapper.INSTANCE.fromEntityList(countryEntities);
    }
}
