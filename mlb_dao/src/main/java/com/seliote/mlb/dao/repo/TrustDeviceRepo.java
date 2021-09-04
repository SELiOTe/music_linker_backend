package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.TrustDeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户信任设备仓库
 *
 * @author seliote
 * @version 2021-09-04
 */
@Validated
public interface TrustDeviceRepo extends JpaRepository<TrustDeviceEntity, Long> {

    /**
     * 根据用户 ID 查询用户受信任设备列表
     *
     * @param userId 用户 ID
     * @return 用户受信任设备列表
     */
    List<TrustDeviceEntity> findByUserEntity_Id(@NotNull Long userId);
}
