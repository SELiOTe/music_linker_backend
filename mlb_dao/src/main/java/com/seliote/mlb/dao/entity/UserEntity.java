package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import com.seliote.mlb.dao.AuditingEntity;

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
@SuppressWarnings("unused")
@Entity(name = "user")
public class UserEntity extends AuditingEntity {

    // 国家码及国际电话区号实体，映射 country_id 字段
    // 不级联
    private CountryEntity countryEntity;

    // 手机号码
    private String telNo;

    // BCrypt 加密后的密码
    private String password;

    // 用户账户是否启用
    private Boolean enable;

    // 用户角色
    private Set<RoleEntity> roles;

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    public CountryEntity getCountryEntity() {
        return countryEntity;
    }

    public void setCountryEntity(CountryEntity countryEntity) {
        this.countryEntity = countryEntity;
    }

    @TelNo
    @Column(name = "tel_no")
    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    @NotBlank
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @NotNull
    @Column(name = "enable")
    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    @NotEmpty
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}
