package com.seliote.mlb.common.jsr303.minio;

import com.seliote.mlb.common.jsr303.minio.validator.PathValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * Minio 文件路径 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-07-02
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PathValidator.class})
@ReportAsSingleViolation
@NotNull
public @interface Path {

    String message() default "Illegal Minio path";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
            ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Path[] value();
    }
}
