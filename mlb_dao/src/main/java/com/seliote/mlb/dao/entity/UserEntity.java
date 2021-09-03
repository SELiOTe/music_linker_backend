package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import com.seliote.mlb.dao.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 用户信息实体
 *
 * @author seliote
 * @version 2021-07-08
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "user")
public class UserEntity extends AuditingEntity {

    // 国家码及国际电话区号实体，映射 country_id 字段
    // 不级联
    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private CountryEntity countryEntity;

    // 手机号码
    @TelNo
    @Column(name = "tel_no")
    private String telNo;

    // BCrypt 加密后的密码
    @NotBlank
    @Column(name = "password")
    private String password;

    // 用户账户是否启用
    @NotNull
    @Column(name = "enable")
    private Boolean enable;

    // 用户角色
    // 所有级联
    @NotEmpty
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<RoleEntity> roles;
}
