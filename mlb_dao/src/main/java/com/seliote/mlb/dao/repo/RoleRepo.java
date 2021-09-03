package com.seliote.mlb.dao.repo;

import com.seliote.mlb.common.jsr303.userinfo.RoleName;
import com.seliote.mlb.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 角色仓库
 *
 * @author seliote
 * @version 2021-07-15
 */
@Validated
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {

    /**
     * 根据角色名拉取角色实体
     *
     * @param roleName 角色名
     * @return 角色实体
     */
    @Valid
    Optional<RoleEntity> findByRoleName(@RoleName String roleName);
}
