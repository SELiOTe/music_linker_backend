package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.CountryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * 国家码及国际电话区号仓库
 *
 * @author seliote
 * @version 2021-06-27
 */
@Validated
public interface CountryRepo extends JpaRepository<CountryEntity, Long> {
}
