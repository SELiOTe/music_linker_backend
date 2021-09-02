package com.seliote.mlb.common.domain.so;

import com.seliote.mlb.common.jsr303.page.PageNo;
import com.seliote.mlb.common.jsr303.page.PageSize;
import com.seliote.mlb.common.jsr303.page.TotalElements;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页 SO
 *
 * @author seliote
 * @version 2021-08-07
 */
@Slf4j
@Getter
@ToString
public class PageSo<D> {

    // 页码，从 0 开始
    @PageNo
    private final Integer pageNo;

    // 页大小
    @PageSize
    private final Integer pageSize;

    // 总页数
    @PageNo
    private final Integer totalPages;

    // 总数据量
    @TotalElements
    private final Long totalElements;

    // 分页数据
    @NotNull
    private final List<D> data;

    /**
     * 私有构造函数
     *
     * @param page 分页信息
     * @param data 分页实际数据
     */
    private PageSo(Page<?> page, List<D> data) {
        this.pageNo = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.data = data;
    }

    /**
     * 创建空分页 PageSo
     *
     * @return PageSo 对象
     */
    public static PageSo<Void> page() {
        return new PageSo<>(Page.empty(), new ArrayList<>());
    }

    /**
     * 创建分页 PageSo
     *
     * @param page Page 对象
     * @param data Service 返回的 SO 对象，即 SO 分页响应，需要由实体类手动转换为 SO 对象
     * @param <E>  Page 对象泛型类型
     * @param <D>  SO 分页响应泛型类型
     * @return PageSo 对象
     */
    public static <E, D> PageSo<D> page(Page<E> page, List<D> data) {
        return new PageSo<>(page, data);
    }
}
