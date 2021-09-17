package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.MusicEntity;
import com.seliote.mlb.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * 歌曲信息仓库
 *
 * @author seliote
 * @version 2021-09-15
 */
@Validated
public interface MusicRepo extends JpaRepository<MusicEntity, Long> {
}
