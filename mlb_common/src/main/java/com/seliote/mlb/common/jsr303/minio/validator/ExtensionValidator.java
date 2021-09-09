package com.seliote.mlb.common.jsr303.minio.validator;

import com.seliote.mlb.common.jsr303.minio.Extension;
import com.seliote.mlb.common.service.MlbService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Minio 文件类型后缀 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
public class ExtensionValidator implements ConstraintValidator<Extension, String> {

    private final MlbService mlbService;

    private final Set<String> extensionSet = new CopyOnWriteArraySet<>();

    @Autowired
    public ExtensionValidator(MlbService mlbService) {
        this.mlbService = mlbService;
    }

    @Override
    public void initialize(Extension constraintAnnotation) {
        extensionSet.addAll(mlbService.minioSupportExtension());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (extensionSet.contains(s)) {
            return true;
        }
        log.warn("Minio extension {} is not illegal", s);
        return false;
    }
}
