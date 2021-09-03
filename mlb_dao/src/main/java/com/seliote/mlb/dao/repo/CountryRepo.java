package com.seliote.mlb.dao.repo;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.dao.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 国家码及国际电话区号仓库
 *
 * @author seliote
 * @version 2021-06-27
 */
@Validated
public interface CountryRepo extends JpaRepository<CountryEntity, Long> {

    /**
     * 根据国际电话区号查找国家实体
     *
     * @param phoneCode 国际电话区号
     * @return 国家实体
     */
    @Valid
    Optional<CountryEntity> findByPhoneCode(@PhoneCode String phoneCode);
}
