package com.seliote.mlb.common.jsr303.jws;

import com.seliote.mlb.common.jsr303.jws.validator.IssValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

/**
 * JWS 签发者 JSR 303 校验
 * 不可为 null
 *
 * @author seliote
 * @version 2021-07-04
 */
@SuppressWarnings("unused")
@Documented
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IssValidator.class})
@ReportAsSingleViolation
@NotBlank
public @interface Iss {

    String message() default "Illegal JWS issuer";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    @Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        Iss[] value();
    }
}
