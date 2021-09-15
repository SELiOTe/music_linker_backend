package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.SingerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * 歌手信息仓库
 *
 * @author seliote
 * @version 2021-09-15
 */
@Validated
public interface SingerRepo extends JpaRepository<SingerEntity, Long> {
}
