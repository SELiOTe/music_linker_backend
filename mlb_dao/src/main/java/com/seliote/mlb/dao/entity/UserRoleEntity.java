package com.seliote.mlb.dao.entity;

import com.seliote.mlb.dao.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * 用户角色实体
 *
 * @author seliote
 * @version 2021-07-10
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user_role")
public class UserRoleEntity extends AuditingEntity {

    // 用户 ID
    @Column(name = "user_id")
    @NotNull
    private Long userId;

    // 用户 ID
    @Column(name = "role_id")
    @NotNull
    private Long roleId;
}
