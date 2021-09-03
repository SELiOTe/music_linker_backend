package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.si.user.IsSignedUpSi;
import com.seliote.mlb.biz.domain.si.user.SignUpSi;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.domain.eunm.RoleNameEnum;
import com.seliote.mlb.common.exception.ApiException;
import com.seliote.mlb.dao.entity.UserEntity;
import com.seliote.mlb.dao.repo.CountryRepo;
import com.seliote.mlb.dao.repo.RoleRepo;
import com.seliote.mlb.dao.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

/**
 * 用户信息 Service 实现
 *
 * @author seliote
 * @version 2021-08-22
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final CountryRepo countryRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public UserServiceImpl(PasswordEncoder passwordEncoder,
                           UserRepo userRepo,
                           CountryRepo countryRepo,
                           RoleRepo roleRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
        this.countryRepo = countryRepo;
        this.roleRepo = roleRepo;
    }

    @Override
    public boolean isSignedUp(IsSignedUpSi si) {
        return userRepo.findByCountryEntity_PhoneCodeAndTelNo(si.getPhoneCode(), si.getTelNo())
                .isPresent();
    }

    @Transactional
    @Override
    public void signUp(SignUpSi si) {
        var countryEntity = countryRepo.findByPhoneCode(si.getPhoneCode());
        if (countryEntity.isEmpty()) {
            log.error("Error sign up, because phone code {} incorrect for {}", si.getPhoneCode(), si);
            throw new ApiException(2, "incorrect phone code");
        }
        var roleEntity = roleRepo.findByRoleName(RoleNameEnum.user.name());
        if (roleEntity.isEmpty()) {
            log.error("Error sign up, because user role name incorrect");
            throw new ApiException(3, "sign up failed");
        }
        var userEntity = new UserEntity();
        userEntity.setCountryEntity(countryEntity.get());
        userEntity.setTelNo(si.getTelNo());
        userEntity.setPassword(passwordEncoder.encode(si.getPassword()));
        userEntity.setEnable(true);
        userEntity.setRoles(new HashSet<>(List.of(roleEntity.get())));
        userRepo.save(userEntity);
    }
}
