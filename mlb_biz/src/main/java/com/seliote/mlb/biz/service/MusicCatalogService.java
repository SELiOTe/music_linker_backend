package com.seliote.mlb.biz.service;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
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
    @Min(0)
    long uploadCount(@NotNull Long userId);
}
