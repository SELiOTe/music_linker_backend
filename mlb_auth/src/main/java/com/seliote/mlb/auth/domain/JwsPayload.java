package com.seliote.mlb.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.jws.Aud;
import com.seliote.mlb.common.jsr303.jws.Iss;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.time.Instant;
import java.util.Collection;

/**
 * JWS 负载部分
 *
 * @author seliote
 * @version 2021-07-04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwsPayload implements Principal {

    // JWS 签发者
    @JsonProperty("iss")
    @Iss
    private String iss;

    // JWS 受众
    @JsonProperty("aud")
    @Aud
    private Long aud;

    // JWS 过期时间
    @JsonProperty("exp")
    @NotNull
    private Instant exp;

    // 账户是否是启用状态
    @JsonProperty("ebl")
    @NotNull
    private Boolean ebl;

    // 账户权限
    @JsonProperty("roles")
    @NotEmpty
    private Collection<Role> roles;

    @JsonIgnore
    @Override
    public String getName() {
        return aud.toString();
    }

    /**
     * 检查当前 JWS 是否可用
     *
     * @return 可用返回 true，否则返回 false
     */
    @NonNull
    public Boolean available() {
        return ebl && exp.isAfter(Instant.now());
    }
}
