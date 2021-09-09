package com.seliote.mlb.common.jsr303.minio.validator;

import com.seliote.mlb.common.jsr303.minio.Catalog;
import com.seliote.mlb.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Minio 文件分类 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
public class CatalogValidator implements ConstraintValidator<Catalog, String> {

    private final CommonService commonService;

    private final Set<String> catalogSet = new CopyOnWriteArraySet<>();

    @Autowired
    public CatalogValidator(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public void initialize(Catalog constraintAnnotation) {
        catalogSet.addAll(commonService.minioSupportCatalog());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (catalogSet.contains(s)) {
            return true;
        }
        log.warn("Minio catalog {} is not illegal", s);
        return false;
    }
}
