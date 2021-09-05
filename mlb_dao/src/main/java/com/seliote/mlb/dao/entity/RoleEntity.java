package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.RoleName;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户角色实体
 *
 * @author seliote
 * @version 2021-07-10
 */
@SuppressWarnings("unused")
@Entity(name = "role")
public class RoleEntity extends AuditingEntity {

    // 角色名
    private String roleName;

    @RoleName
    @Column(name = "role_name")
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
