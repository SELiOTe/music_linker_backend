package com.seliote.mlb.dao.entity;

import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * 用户角色实体
 *
 * @author seliote
 * @version 2021-07-10
 */
@SuppressWarnings("unused")
@Entity(name = "user_role")
public class UserRoleEntity extends AuditingEntity {

    // 用户 ID
    private Long userId;

    // 用户 ID
    private Long roleId;

    @Column(name = "user_id")
    @NotNull
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = "role_id")
    @NotNull
    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
