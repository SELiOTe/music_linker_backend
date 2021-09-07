package com.seliote.mlb.auth.domain;

import com.seliote.mlb.common.domain.eunm.RoleNameEnum;
import com.seliote.mlb.common.exception.AuthException;
import com.seliote.mlb.common.jsr303.userinfo.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * 用户角色
 *
 * @author seliote
 * @version 2021-07-04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role implements GrantedAuthority {

    @RoleName
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public void setAuthority(String authority) {
        if (!StringUtils.hasText(authority) ||
                (!authority.equals(RoleNameEnum.admin.name()) && !authority.equals(RoleNameEnum.user.name()))) {
            throw new AuthException(authority + " is not a illegal role!");
        }
        this.authority = authority;
    }
}
