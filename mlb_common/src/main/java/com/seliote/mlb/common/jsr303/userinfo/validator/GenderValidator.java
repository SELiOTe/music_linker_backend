package com.seliote.mlb.common.jsr303.userinfo.validator;

import com.seliote.mlb.common.domain.eunm.GenderEnum;
import com.seliote.mlb.common.jsr303.common.Gender;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 用户性别 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-09-04
 */
@Slf4j
public class GenderValidator implements ConstraintValidator<Gender, Integer> {

    private final Set<Integer> genderValueSet = new CopyOnWriteArraySet<>();

    @Override
    public void initialize(Gender constraintAnnotation) {
        for (GenderEnum value : GenderEnum.values()) {
            var genderValue = value.getGenderValue();
            log.info("Add gender value {} to JSR 303", genderValue);
            genderValueSet.add(genderValue);
        }
    }

    @Override
    public boolean isValid(Integer i, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Validate {} of gender value", i);
        return genderValueSet.contains(i);
    }
}
