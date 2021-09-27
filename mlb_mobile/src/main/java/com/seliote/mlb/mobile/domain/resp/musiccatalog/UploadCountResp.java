package com.seliote.mlb.mobile.domain.resp.musiccatalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

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
    @Min(0)
    private Long count;
}
