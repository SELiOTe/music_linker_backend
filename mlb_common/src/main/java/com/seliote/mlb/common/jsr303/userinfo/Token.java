package com.seliote.mlb.common.jsr303.userinfo;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.lang.annotation.*;

/**
 * 用户 Token JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@NotBlank
@Pattern(regexp = "^[a-zA-Z\\d+/=-]+\\.[a-zA-Z\\d+/=-]+\\.\\S+$")
public @interface Token {

    String message() default "Illegal token";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Token[] value();
    }
}
