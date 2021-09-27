package com.seliote.mlb.mobile.domain.resp.musiccatalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取用户上传音乐列表 Resp
 *
 * @author seliote
 * @version 2021-09-25
 */
@Data
public class UploadListResp {

    // 歌手 ID
    @JsonProperty("singer_id")
    @NotNull
    private Long singerId;

    // 歌手名称
    @JsonProperty("singer_name")
    @com.seliote.mlb.common.jsr303.singer.Name
    private String singerName;

    // 歌曲 ID
    @JsonProperty("music_id")
    @NotNull
    private Long musicId;

    // 歌曲名称
    @JsonProperty("music_name")
    @com.seliote.mlb.common.jsr303.music.Name
    private String musicName;
}
