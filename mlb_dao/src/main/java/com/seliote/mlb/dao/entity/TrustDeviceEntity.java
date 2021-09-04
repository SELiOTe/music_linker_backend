package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.dao.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 用户信任设备实体，只有设备串号，未采集设备型号
 *
 * @author seliote
 * @version 2021-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "trust_device")
public class TrustDeviceEntity extends AuditingEntity {

    // 用户信息实体实体，映射 user_id 字段
    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    // 设备串号
    @DeviceNo
    @Column(name = "device_no")
    private String deviceNo;
}
