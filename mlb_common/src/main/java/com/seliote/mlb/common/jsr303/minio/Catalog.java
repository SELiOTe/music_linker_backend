package com.seliote.mlb.common.jsr303.minio;

import com.seliote.mlb.common.jsr303.minio.validator.CatalogValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Minio 文件分类 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-07-02
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {CatalogValidator.class})
@ReportAsSingleViolation
@NotNull
public @interface Catalog {

    String message() default "Illegal Minio catalog";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Catalog[] value();
    }
}
