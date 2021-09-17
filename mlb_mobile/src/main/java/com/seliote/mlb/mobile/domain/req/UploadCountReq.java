package com.seliote.mlb.mobile.domain.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取用户上传音乐总数 Req
 *
 * @author seliote
 * @version 2021-09-17
 */
@Data
public class UploadCountReq {

    // 用户 ID
    @JsonProperty("user_id")
    @NotNull
    private Long userId;
}
