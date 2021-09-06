package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.auth.config.JwsFilter;
import com.seliote.mlb.auth.domain.JwsPayload;
import com.seliote.mlb.auth.domain.Role;
import com.seliote.mlb.auth.service.JwsService;
import com.seliote.mlb.biz.domain.si.user.*;
import com.seliote.mlb.biz.domain.so.user.FindUserSo;
import com.seliote.mlb.biz.domain.so.user.SignUpSo;
import com.seliote.mlb.biz.domain.so.user.mapper.FindUserSoMapper;
import com.seliote.mlb.biz.domain.so.user.mapper.SignUpSoMapper;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.domain.eunm.RoleNameEnum;
import com.seliote.mlb.common.service.RedisService;
import com.seliote.mlb.dao.entity.TrustDeviceEntity;
import com.seliote.mlb.dao.entity.UserEntity;
import com.seliote.mlb.dao.repo.CountryRepo;
import com.seliote.mlb.dao.repo.RoleRepo;
import com.seliote.mlb.dao.repo.TrustDeviceRepo;
import com.seliote.mlb.dao.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * 用户信息 Service 实现
 *
 * @author seliote
 * @version 2021-08-22
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final YmlConfig.Jws jwsConfig;
    private final UserRepo userRepo;
    private final CountryRepo countryRepo;
    private final RoleRepo roleRepo;
    private final TrustDeviceRepo trustDeviceRepo;
    private final JwsService jwsService;

    @Autowired
    public UserServiceImpl(RedisService redisService,
                           PasswordEncoder passwordEncoder,
                           YmlConfig ymlConfig,
                           UserRepo userRepo,
                           CountryRepo countryRepo,
                           RoleRepo roleRepo,
                           TrustDeviceRepo trustDeviceRepo,
                           JwsService jwsService) {
        this.redisService = redisService;
        this.passwordEncoder = passwordEncoder;
        this.jwsConfig = ymlConfig.getJws();
        this.userRepo = userRepo;
        this.countryRepo = countryRepo;
        this.roleRepo = roleRepo;
        this.trustDeviceRepo = trustDeviceRepo;
        this.jwsService = jwsService;
    }

    @Transactional
    @Override
    public Optional<FindUserSo> findUser(FindUserSi si) {
        var userEntity =
                userRepo.findByCountryEntity_PhoneCodeAndTelNo(si.getPhoneCode(), si.getTelNo());
        if (userEntity.isEmpty()) {
            log.debug("User {} not find", si);
            return Optional.empty();
        }
        log.debug("Find user {}", si);
        return Optional.of(FindUserSoMapper.INSTANCE.fromUserEntity(userEntity.get()));
    }

    @Transactional
    @Override
    public Optional<SignUpSo> signUp(SignUpSi si) {
        var countryEntity = countryRepo.findByPhoneCode(si.getPhoneCode());
        if (countryEntity.isEmpty() || !si.getTelNo().matches(countryEntity.get().getPhonePattern())) {
            log.error("Error sign up, because phone code {} incorrect for {}", si.getPhoneCode(), si);
            return Optional.empty();
        }
        var roleEntity = roleRepo.findByRoleName(RoleNameEnum.user.name());
        if (roleEntity.isEmpty()) {
            log.error("Error sign up, because user role name incorrect");
            return Optional.empty();
        }
        var userEntity = new UserEntity();
        userEntity.setCountryEntity(countryEntity.get());
        userEntity.setTelNo(si.getTelNo());
        userEntity.setPassword(passwordEncoder.encode(si.getPassword()));
        userEntity.setEnable(true);
        userEntity.setNickname(si.getNickname());
        userEntity.setRoles(new HashSet<>(List.of(roleEntity.get())));
        userRepo.save(userEntity);
        log.info("Sign up success, {}", si);
        return Optional.of(SignUpSoMapper.INSTANCE.fromUserEntity(userEntity));
    }

    @Transactional
    @Override
    public boolean addTrustDevice(AddTrustDeviceSi si) {
        var userEntity = userRepo.findById(si.getUserId());
        if (userEntity.isEmpty()) {
            log.info("User {} not exists, but you want to add trust device", si.getUserId());
            return false;
        }
        var trustDeviceEntity = new TrustDeviceEntity();
        trustDeviceEntity.setUserEntity(userEntity.get());
        trustDeviceEntity.setDeviceNo(si.getDeviceNo());
        trustDeviceRepo.save(trustDeviceEntity);
        log.info("User {} add trust device {}", si.getUserId(), si.getDeviceNo());
        return true;
    }

    @Override
    public Optional<String> createToken(Long userId) {
        var userEntity = userRepo.findById(userId);
        if (userEntity.isEmpty()) {
            log.info("User {} not exists, but you want to create token", userId);
            return Optional.empty();
        }
        var roles = new HashSet<Role>();
        userEntity.get().getRoles().forEach(roleEntity ->
                roles.add(Role.builder().authority(roleEntity.getRoleName()).build()));
        var jwsPayload = JwsPayload.builder()
                .iss(jwsConfig.getIss())
                .aud(userEntity.get().getId())
                .exp(Instant.now().plusSeconds(jwsConfig.getValidDays() * 24 * 60 * 60))
                .ebl(userEntity.get().getEnable())
                .roles(roles)
                .build();
        var token = jwsService.sign(jwsPayload);
        if (token.isEmpty()) {
            log.warn("Can not create token for {}", userId);
            return Optional.empty();
        }
        log.info("Create bearer token for {}", userId);
        redisService.set(Duration.ofDays(jwsConfig.getValidDays()), token.get(),
                JwsFilter.REDIS_KEY, jwsPayload.getName());
        return token;
    }

    @Override
    public boolean isTrustDevice(IsTrustDeviceSi si) {
        return trustDeviceRepo.existsByUserEntity_IdAndDeviceNo(si.getUserId(), si.getDeviceNo());
    }

    @Override
    public boolean checkUserPassword(CheckUserPasswordSi si) {
        var user = userRepo.findByCountryEntity_PhoneCodeAndTelNo(si.getPhoneCode(), si.getTelNo());
        if (user.isEmpty()) {
            return false;
        }
        var match = passwordEncoder.matches(si.getPassword(), user.get().getPassword());
        log.info("Compare password {}, result {}", si, match);
        return match;
    }
}
