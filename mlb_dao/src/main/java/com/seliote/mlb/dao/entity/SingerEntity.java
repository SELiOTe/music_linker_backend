package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.common.Gender;
import com.seliote.mlb.common.jsr303.singer.Brief;
import com.seliote.mlb.common.jsr303.singer.Img;
import com.seliote.mlb.common.jsr303.singer.Name;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 歌手信息实体
 *
 * @author seliote
 * @version 2021-09-15
 */
@SuppressWarnings("unused")
@Entity(name = "singer")
public class SingerEntity extends AuditingEntity {

    // 歌手名
    private String name;

    // 用户性别，0 为未知，1 为男，2 为女
    private Integer gender;

    // 歌手照片
    private Integer img;

    // 歌手简介
    private String brief;

    @Name
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Gender
    @Column(name = "gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @Img
    @Column(name = "img")
    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    @Brief
    @Column(name = "brief")
    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
