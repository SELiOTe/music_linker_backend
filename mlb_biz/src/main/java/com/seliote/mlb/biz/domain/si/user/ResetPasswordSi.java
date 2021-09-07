package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.userinfo.PlaintextPassword;
import lombok.*;

import javax.validation.constraints.NotNull;

/**
 * 重置用户密码 SI
 *
 * @author seliote
 * @version 2021-09-07
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPasswordSi {

    // 用户 ID
    @NotNull
    private Long userId;

    // 用户密码
    @ToString.Exclude
    @PlaintextPassword
    private String password;
}
