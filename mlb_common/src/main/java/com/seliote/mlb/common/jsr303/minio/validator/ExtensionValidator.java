package com.seliote.mlb.common.jsr303.minio.validator;

import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.exception.MlbException;
import com.seliote.mlb.common.jsr303.minio.Catalog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Minio 文件类型后缀 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-07-02
 */
@Slf4j
public class ExtensionValidator implements ConstraintValidator<Catalog, String> {

    private final YmlConfig.Minio minio;

    private final Set<String> extensionSet = new CopyOnWriteArraySet<>();

    @Autowired
    public ExtensionValidator(YmlConfig ymlConfig) {
        this.minio = ymlConfig.getMinio();
    }

    @Override
    public void initialize(Catalog constraintAnnotation) {
        var properties = BeanUtils.getPropertyDescriptors(minio.getExtension().getClass());
        for (PropertyDescriptor pd : properties) {
            if (pd.getName().equals("class")) {
                continue;
            }
            try {
                var extension = pd.getReadMethod().invoke(minio.getExtension()).toString();
                log.trace("Add {} as extension to Minio JSR 303", extension);
                extensionSet.add(extension);
            } catch (IllegalAccessException | InvocationTargetException exception) {
                log.error("Failed initialization ExtensionValidator, {}", exception.getMessage());
                throw new MlbException(exception);
            }
        }
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
