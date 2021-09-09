package com.seliote.mlb.common.jsr303.userinfo.validator;

import com.seliote.mlb.common.domain.eunm.RoleNameEnum;
import com.seliote.mlb.common.jsr303.userinfo.RoleName;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 用户角色名 JSR 303 校验实现
 *
 * @author seliote
 * @version 2021-09-04
 */
@Slf4j
public class RoleNameValidator implements ConstraintValidator<RoleName, String> {

    private final Set<String> roleNameSet = new CopyOnWriteArraySet<>();

    @Override
    public void initialize(RoleName constraintAnnotation) {
        for (RoleNameEnum value : RoleNameEnum.values()) {
            var roleName = value.name();
            log.info("Add role name {} to JSR 303", roleName);
            roleNameSet.add(roleName);
        }
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        log.trace("Validate {} of role name", s);
        return roleNameSet.contains(s);
    }
}
