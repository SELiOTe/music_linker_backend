package com.seliote.mlb.dao.repo;

import com.seliote.mlb.common.jsr303.userinfo.DeviceNo;
import com.seliote.mlb.dao.entity.TrustDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 用户信任设备仓库
 *
 * @author seliote
 * @version 2021-09-04
 */
@Validated
public interface TrustDeviceRepo extends JpaRepository<TrustDeviceEntity, Long> {

    /**
     * 根据用户 ID 与设备串号查询记录是否存在（是否为用户的受信任设备）
     *
     * @param userId   用户 ID
     * @param deviceNo 设备串号
     * @return 存在时返回 true，否则返回 false
     */
    Boolean existsByUserEntity_IdAndDeviceNo(@NotNull Long userId, @DeviceNo String deviceNo);
}
