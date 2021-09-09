package com.seliote.mlb.common.service.impl;

import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.exception.MlbException;
import com.seliote.mlb.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * JSR 303 Service，提供相关服务实现
 *
 * @author seliote
 * @version 2021-09-09
 */
@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    private final YmlConfig.Minio minio;

    private Set<String> catalogSet;
    private Set<String> extensionSet;

    @Autowired
    public CommonServiceImpl(YmlConfig ymlConfig) {
        this.minio = ymlConfig.getMinio();
    }

    @Override
    public Set<String> minioSupportCatalog() {
        if (catalogSet != null) {
            return Collections.unmodifiableSet(catalogSet);
        }
        var tmp = new CopyOnWriteArraySet<String>();
        var properties = BeanUtils.getPropertyDescriptors(minio.getCatalog().getClass());
        for (PropertyDescriptor pd : properties) {
            if (pd.getName().equals("class")) {
                continue;
            }
            try {
                var catalog = pd.getReadMethod().invoke(minio.getCatalog()).toString();
                log.trace("Add {} as catalog to Minio JSR 303", catalog);
                tmp.add(catalog);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                log.error("Failed initialization CatalogValidator, {}", exception.getMessage());
                throw new MlbException(exception);
            }
        }
        catalogSet = tmp;
        return catalogSet;
    }

    @Override
    public Set<String> minioSupportExtension() {
        if (extensionSet != null) {
            return Collections.unmodifiableSet(extensionSet);
        }
        var tmp = new CopyOnWriteArraySet<String>();
        var properties = BeanUtils.getPropertyDescriptors(minio.getExtension().getClass());
        for (PropertyDescriptor pd : properties) {
            if (pd.getName().equals("class")) {
                continue;
            }
            try {
                var extension = pd.getReadMethod().invoke(minio.getExtension()).toString();
                log.trace("Add {} as extension to Minio JSR 303", extension);
                tmp.add(extension);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                log.error("Failed initialization ExtensionValidator, {}", exception.getMessage());
                throw new MlbException(exception);
            }
        }
        extensionSet = tmp;
        return extensionSet;
    }
}
