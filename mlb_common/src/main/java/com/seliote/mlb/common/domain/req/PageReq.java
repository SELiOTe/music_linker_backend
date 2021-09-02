package com.seliote.mlb.common.domain.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.page.PageNo;
import com.seliote.mlb.common.jsr303.page.PageSize;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 分页请求请求体基类
 *
 * @author seliote
 * @version 2021-08-04
 */
@Slf4j
@Data
public class PageReq {

    // 页码，从 0 开始
    @JsonProperty("page_no")
    @PageNo
    private Integer pageNo;

    // 页大小，最大值为 10000
    @JsonProperty("page_size")
    @PageSize
    private Integer pageSize;
}
