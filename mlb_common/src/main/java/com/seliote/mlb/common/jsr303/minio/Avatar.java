package com.seliote.mlb.common.jsr303.minio;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

/**
 * 用户头像 JSR 303 校验
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
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Path
public @interface Avatar {

    String message() default "Illegal avatar";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD,
            ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Avatar[] value();
    }
}
