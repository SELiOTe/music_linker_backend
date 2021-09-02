package com.seliote.mlb.dao.repo;

import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.userinfo.TelNo;
import com.seliote.mlb.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

/**
 * 用户信息仓库
 *
 * @author seliote
 * @version 2021-07-10
 */
@Validated
public interface UserRepo extends JpaRepository<UserEntity, Long> {

    /**
     * 根据国际电话区号与手机号码查询用户信息
     *
     * @param phoneCode 国际电话区号
     * @param telNo     手机号码
     * @return 对应的用户信息实体
     */
    @Valid
    Optional<UserEntity> findByCountryEntity_PhoneCodeAndTelNo(@PhoneCode String phoneCode, @TelNo String telNo);
}
