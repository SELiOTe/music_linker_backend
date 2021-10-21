package com.seliote.mlb.mobile.domain.resp.music;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 用户是否能播放音乐 Resp
 *
 * @author seliote
 * @version 2021-10-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanPlayResp {

    // 是否能够播放，能播放返回 true，否则返回 false
    @JsonProperty("can_play")
    @NotNull
    private Boolean canPlay;
}
