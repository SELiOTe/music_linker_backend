package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.MusicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * 歌曲信息仓库
 *
 * @author seliote
 * @version 2021-09-15
 */
@Validated
public interface MusicRepo extends JpaRepository<MusicEntity, Long> {

    /**
     * 根据上传人与音乐 ID 拉取指定的音乐实体
     *
     * @param userId  用户 ID
     * @param musicId 音乐 ID
     * @return 指定的音乐实体，当用户不存在该音乐时返回 null
     */
    Optional<MusicEntity> findByUploadBy_IdAndId(@NotNull Long userId, @NotNull Long musicId);
}
