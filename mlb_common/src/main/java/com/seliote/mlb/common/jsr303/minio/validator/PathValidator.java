package com.seliote.mlb.common.jsr303.minio.validator;

import com.seliote.mlb.common.jsr303.minio.Path;
import com.seliote.mlb.common.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.regex.Pattern;

/**
 * Minio 文件路径 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
public class PathValidator implements ConstraintValidator<Path, String> {

    private final CommonService commonService;

    private final Set<String> catalogSet = new CopyOnWriteArraySet<>();
    private final Set<String> extensionSet = new CopyOnWriteArraySet<>();

    // 路径正则
    private final Pattern pathPattern = Pattern.compile("^(\\w+)/(\\w)/(\\w)/([\\w-]{36})\\.(\\w+)$");

    @Autowired
    public PathValidator(CommonService commonService) {
        this.commonService = commonService;
    }

    @Override
    public void initialize(Path constraintAnnotation) {
        catalogSet.addAll(commonService.minioSupportCatalog());
        extensionSet.addAll(commonService.minioSupportExtension());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        var matcher = pathPattern.matcher(s);
        if (!matcher.find()) {
            log.warn("Minio path {} is illegal", s);
            return false;
        }
        // catalog 部分
        var catalog = matcher.group(1);
        // extension 部分
        var extension = matcher.group(5);
        if (catalogSet.contains(catalog) && extensionSet.contains(extension)) {
            return true;
        } else {
            log.warn("Minio path {} is illegal, catalog or extension illegal", s);
            return false;
        }
    }
}
