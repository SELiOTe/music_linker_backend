package com.seliote.mlb.common.jsr303.page;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 分页数据总量 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-08-04
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@NotNull
@Min(0)
public @interface TotalElements {

    String message() default "Illegal page total elements, must greater then 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        TotalElements[] value();
    }
}
