package com.seliote.mlb.fs.service;

import com.seliote.mlb.common.jsr303.minio.Catalog;
import com.seliote.mlb.common.jsr303.minio.Extension;
import com.seliote.mlb.common.jsr303.minio.Filename;
import com.seliote.mlb.fs.exception.MinioException;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.InputStream;

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
     * <em>上传完成后需调用方关闭参数 InputStream 流，否则会造成句柄泄漏</em>
     *
     * @param catalog     文件分类
     * @param extension   文件类型
     * @param inputStream 输入流，上传完成后需调用方负责关闭
     * @return 上传文件返回的名称
     * @throws MinioException 上传失败时抛出
     */
    @Filename
    String upload(@Catalog String catalog, @Extension String extension, @NotNull InputStream inputStream)
            throws MinioException;

    /**
     * 下载文件
     * <em>下载完成后需调用方关闭返回的 InputStream 流，否则会造成句柄泄漏</em>
     *
     * @param catalog  文件分类
     * @param filename 上传文件返回的名称
     * @return 文件流
     * @throws MinioException 下载失败时抛出
     */
    @NotNull
    InputStream download(@Catalog String catalog, @Filename String filename) throws MinioException;
}
