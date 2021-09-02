package com.seliote.mlb.dao.repo;

import com.seliote.mlb.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

/**
 * 角色仓库
 *
 * @author seliote
 * @version 2021-07-15
 */
@Validated
public interface RoleRepo extends JpaRepository<RoleEntity, Long> {
}
