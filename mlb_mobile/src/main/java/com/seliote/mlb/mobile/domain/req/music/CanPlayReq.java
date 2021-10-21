package com.seliote.mlb.mobile.domain.req.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户是否能播放音乐 Req
 *
 * @author seliote
 * @version 2021-10-21
 */
@Data
public class CanPlayReq {

    // 音乐 ID
    @JsonProperty("music_id")
    @NotNull
    private Long musicId;
}
