package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.common.Gender;
import com.seliote.mlb.common.jsr303.userinfo.Avatar;
import com.seliote.mlb.common.jsr303.userinfo.Nickname;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
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

    // 用户昵称
    private String nickname;

    // 用户性别，0 为未知，1 为男，2 为女
    private Integer gender;

    // 用户头像，Minio 路径
    private String avatar;

    // 用户角色
    private Set<RoleEntity> roles;

    // 用户上传的音乐
    private List<MusicEntity> uploadMusic;

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

    @Nickname
    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Gender
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Avatar
    @Column(name = "avatar")
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    @NotNull
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "music_upload",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"))
    public List<MusicEntity> getUploadMusic() {
        return uploadMusic;
    }

    public void setUploadMusic(List<MusicEntity> uploadMusic) {
        this.uploadMusic = uploadMusic;
    }
}
