package com.seliote.mlb.mobile.domain.req.musiccatalog;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取用户上传音乐列表 Req
 *
 * @author seliote
 * @version 2021-09-17
 */
@Data
public class UploadListReq {

    // 用户 ID
    @JsonProperty("user_id")
    @NotNull
    private Long userId;
}
