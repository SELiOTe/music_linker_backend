package com.seliote.mlb.mobile.domain.req.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 播放音乐请求
 *
 * @author seliote
 * @version 2021-10-21
 */
@Data
public class PlayMusicReq {

    // 音乐 ID
    @JsonProperty("music_id")
    @NotNull
    private Long musicId;
}
