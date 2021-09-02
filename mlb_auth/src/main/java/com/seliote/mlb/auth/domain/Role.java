package com.seliote.mlb.auth.domain;

import com.seliote.mlb.common.exception.AuthException;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

/**
 * 用户角色
 *
 * @author seliote
 * @version 2021-07-04
 */
@Data
@Builder
public class Role implements GrantedAuthority {

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }

    @SuppressWarnings("unused")
    public void setAuthority(String authority) {
        if (!StringUtils.hasText(authority) || (!authority.equals(ADMIN) && !authority.equals(USER))) {
            throw new AuthException(authority + " is not a illegal role!");
        }
        this.authority = authority;
    }
}
