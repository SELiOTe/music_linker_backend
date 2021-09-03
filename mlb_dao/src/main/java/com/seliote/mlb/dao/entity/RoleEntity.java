package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.RoleName;
import com.seliote.mlb.dao.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户角色实体
 *
 * @author seliote
 * @version 2021-07-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "role")
public class RoleEntity extends AuditingEntity {

    // 角色名
    @Column(name = "role_name")
    @RoleName
    private String roleName;
}
