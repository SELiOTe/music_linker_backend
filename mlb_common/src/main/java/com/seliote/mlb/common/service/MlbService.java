package com.seliote.mlb.common.service;

import org.springframework.validation.annotation.Validated;

import java.util.Set;

/**
 * JSR 303 Service，提供相关服务
 *
 * @author seliote
 * @version 2021-09-09
 */
@Validated
public interface MlbService {

    /**
     * 获取支持的 catalog
     *
     * @return 支持的 catalog 集合
     */
    Set<String> minioSupportCatalog();

    /**
     * 支持的文件类型
     *
     * @return 支持的文件类型集合
     */
    Set<String> minioSupportExtension();
}
