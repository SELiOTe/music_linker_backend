package com.seliote.mlb.common.jsr303.jws.validator;

import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.jsr303.jws.Iss;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JWS 签发者 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-07-04
 */
@Slf4j
public class IssValidator implements ConstraintValidator<Iss, String> {

    private final YmlConfig.Jws jws;
    private String iss;

    @Autowired
    public IssValidator(YmlConfig ymlConfig) {
        this.jws = ymlConfig.getJws();
    }

    @Override
    public void initialize(Iss constraintAnnotation) {
        iss = jws.getIss();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (!iss.equals(s)) {
            log.warn("Issuer {} is illegal", s);
            return false;
        }
        return true;
    }
}
