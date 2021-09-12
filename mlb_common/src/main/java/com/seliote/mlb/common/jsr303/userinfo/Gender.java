package com.seliote.mlb.common.jsr303.userinfo;

import com.seliote.mlb.common.jsr303.userinfo.validator.GenderValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 用户性别 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GenderValidator.class})
@ReportAsSingleViolation
@NotNull
public @interface Gender {

    String message() default "Illegal gender";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Gender[] value();
    }
}
