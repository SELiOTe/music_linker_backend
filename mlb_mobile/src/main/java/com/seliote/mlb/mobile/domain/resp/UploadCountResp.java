package com.seliote.mlb.mobile.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.music.CatalogMusicCount;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 获取用户上传音乐总数 Resp
 *
 * @author seliote
 * @version 2021-09-17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadCountResp {

    // 音乐数
    @JsonProperty("count")
    @CatalogMusicCount
    private Long count;
}
