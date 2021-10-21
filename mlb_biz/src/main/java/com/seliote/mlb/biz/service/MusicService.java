package com.seliote.mlb.biz.service;

import com.seliote.mlb.biz.domain.si.music.CanPlaySi;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 音乐 Service
 *
 * @author seliote
 * @version 2021-10-21
 */
@Validated
public interface MusicService {

    /**
     * 获取指定用户是否可播放指定音乐
     *
     * @param si 请求 SI
     * @return 能播放返回 true，否则返回 false
     */
    Boolean canPlay(@NotNull @Valid CanPlaySi si);

    /**
     * 获取指定的音乐
     *
     * @param musicId 音乐 ID
     * @return 音乐字节数组
     */
    byte[] playMusic(@NotNull Long musicId);
}
