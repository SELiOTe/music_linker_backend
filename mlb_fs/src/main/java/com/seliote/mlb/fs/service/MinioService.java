package com.seliote.mlb.fs.service;

import com.seliote.mlb.common.jsr303.minio.Catalog;
import com.seliote.mlb.common.jsr303.minio.Extension;
import com.seliote.mlb.common.jsr303.minio.Filename;
import com.seliote.mlb.fs.exception.MinioException;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * Minio Service
 *
 * @author seliote
 * @version 2021-07-02
 */
@Validated
public interface MinioService {

    /**
     * 上传文件
     *
     * @param catalog   文件分类
     * @param extension 文件类型
     * @param bytes     输入字节
     * @return 上传文件返回的名称
     * @throws MinioException 上传失败时抛出
     */
    @Filename
    String upload(@Catalog String catalog, @Extension String extension, @NotNull byte[] bytes);

    /**
     * 下载文件
     *
     * @param catalog  文件分类
     * @param filename 上传文件返回的名称
     * @return 文件字节数组
     * @throws MinioException 下载失败时抛出
     */
    @NotNull
    byte[] download(@Catalog String catalog, @Filename String filename);
}
