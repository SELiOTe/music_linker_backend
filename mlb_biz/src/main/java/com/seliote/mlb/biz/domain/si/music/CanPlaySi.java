package com.seliote.mlb.biz.domain.si.music;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 指定用户是否可播放指定音乐 SI
 *
 * @author seliote
 * @version 2021-10-21
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CanPlaySi {

    // 用户 ID
    @NotNull
    private Long userId;

    // 音乐 ID
    @NotNull
    private Long musicId;
}
