package com.seliote.mlb.biz.domain.so.musiccatalog;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取用户上传音乐列表 SO
 *
 * @author seliote
 * @version 2021-09-26
 */
@Data
public class UploadListSo {

    // 歌手 ID
    @NotNull
    private Long singerId;

    // 歌手名称
    @com.seliote.mlb.common.jsr303.singer.Name
    private String singerName;

    // 歌曲 ID
    @NotNull
    private Long musicId;

    // 歌曲名称
    @com.seliote.mlb.common.jsr303.music.Name
    private String musicName;
}
