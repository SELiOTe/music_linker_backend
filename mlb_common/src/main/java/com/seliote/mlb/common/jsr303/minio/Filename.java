package com.seliote.mlb.common.jsr303.minio;

import com.seliote.mlb.common.jsr303.minio.validator.FilenameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

/**
 * Minio 上传后返回的文件名 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-09-12
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
        ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FilenameValidator.class})
@ReportAsSingleViolation
@NotBlank
public @interface Filename {

    String message() default "Illegal filename";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
            ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Filename[] value();
    }
}
