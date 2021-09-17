package com.seliote.mlb.biz.service;

import com.seliote.mlb.common.jsr303.music.CatalogMusicCount;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 歌单信息 Service
 *
 * @author seliote
 * @version 2021-09-16
 */
@Validated
public interface MusicCatalogService {

    /**
     * 获取指定用户上传的音乐总数
     *
     * @param userId 用户 ID
     * @return 用户上传的音乐总数
     */
    @CatalogMusicCount
    Long uploadCount(@NotNull Long userId);
}
