package com.seliote.mlb.common.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.domain.so.PageSo;
import com.seliote.mlb.common.jsr303.page.PageNo;
import com.seliote.mlb.common.jsr303.page.PageSize;
import com.seliote.mlb.common.jsr303.page.TotalElements;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页响应 Resp
 *
 * @author seliote
 * @version 2021-08-07
 */
@Slf4j
@Getter
@ToString(callSuper = true)
public class PageResp<T> extends Resp<List<T>> {

    // 页码，从 0 开始
    @JsonProperty("page_no")
    @PageNo
    private final Integer pageNo;

    // 页大小
    @JsonProperty("page_size")
    @PageSize
    private final Integer pageSize;

    // 总页数
    @JsonProperty("total_pages")
    @PageNo
    private final Integer totalPages;

    // 总数据量
    @JsonProperty("total_elements")
    @TotalElements
    private final Long totalElements;

    /**
     * 私有构造函数
     *
     * @param code       响应码
     * @param msg        响应码描述
     * @param pageNo     页码
     * @param pageSize   页大小
     * @param totalPages 总页数
     * @param data       响应实际数据，可为空
     */
    private PageResp(@NonNull Integer code, @NonNull String msg, @NonNull Integer pageNo,
                     @NonNull Integer pageSize, @NonNull Integer totalPages, @NonNull Long totalElements,
                     @NonNull List<T> data) {
        super(code, msg, data);
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    /**
     * 创建空分页响应
     *
     * @return PageResp 对象
     */
    public static PageResp<Void> page() {
        return new PageResp<>(SUCCESS_CODE, SUCCESS_MSG, 0, 0,
                0, 0L, new ArrayList<>());
    }

    /**
     * 创建分页响应
     *
     * @param code   响应码
     * @param msg    响应码描述
     * @param pageSo PageSo 对象
     * @param data   分页实际响应数据
     * @param <T>    分页实际响应数据泛型
     * @param <D>    PageSo 对象 SO 数据泛型
     * @return 分页响应
     */
    public static <T, D> PageResp<T> page(@NonNull Integer code, @NonNull String msg,
                                          @NonNull PageSo<D> pageSo, @NonNull List<T> data) {
        return new PageResp<>(code, msg, pageSo.getPageNo(),
                pageSo.getPageSize(), pageSo.getTotalPages(), pageSo.getTotalElements(), data);
    }

    /**
     * 创建默认成功的分页响应
     *
     * @param pageSo PageSo 对象
     * @param data   分页实际响应数据
     * @param <T>    分页实际响应数据泛型
     * @param <D>    PageSo 对象 SO 数据泛型
     * @return 分页响应
     */
    public static <T, D> PageResp<T> page(@NonNull PageSo<D> pageSo, @NonNull List<T> data) {
        return new PageResp<>(SUCCESS_CODE, SUCCESS_MSG, pageSo.getPageNo(),
                pageSo.getPageSize(), pageSo.getTotalPages(), pageSo.getTotalElements(), data);
    }
}
