package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.LyricEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * 歌词仓库
 *
 * @author seliote
 * @version 2021-09-15
 */
@Validated
public interface LyricRepo extends JpaRepository<LyricEntity, Long> {
}
