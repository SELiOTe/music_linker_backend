package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户信任设备实体，只有设备串号，未采集设备型号
 *
 * @author seliote
 * @version 2021-09-04
 */
@SuppressWarnings("unused")
@Entity(name = "trust_device")
public class TrustDeviceEntity extends AuditingEntity {

    // 用户信息实体实体，映射 user_id 字段
    private UserEntity userEntity;

    // 设备串号
    private String deviceNo;

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @DeviceNo
    @Column(name = "device_no")
    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
