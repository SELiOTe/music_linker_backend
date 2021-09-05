package com.seliote.mlb.biz.domain.si.user;

import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 判断是否为用户受信任设备 SI
 *
 * @author seliote
 * @version 2021-09-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsTrustDeviceSi {

    // 用户 ID
    @NotNull
    private Long userId;

    // 设备串号
    @DeviceNo
    private String deviceNo;
}
