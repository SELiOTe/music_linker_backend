package com.seliote.mlb.common.domain.eunm;

/**
 * 性别枚举
 *
 * @author seliote
 * @version 2021-09-12
 */
public enum GenderEnum {
    unknown(0),
    male(1),
    female(2);

    private final Integer genderValue;

    GenderEnum(Integer genderValue) {
        this.genderValue = genderValue;
    }

    @Override
    public String toString() {
        return genderValue + "";
    }

    public Integer getGenderValue() {
        return genderValue;
    }
}
