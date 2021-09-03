package com.seliote.mlb.common.jsr303.userinfo;

import com.seliote.mlb.common.jsr303.userinfo.validator.RoleNameValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

/**
 * 用户角色名 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {RoleNameValidator.class})
@ReportAsSingleViolation
public @interface RoleName {

    String message() default "Illegal role name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        RoleName[] value();
    }
}
