package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.si.user.IsSignedUpSi;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.dao.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息 Service 实现
 *
 * @author seliote
 * @version 2021-08-22
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isSignedUp(IsSignedUpSi si) {
        return userRepo.findByCountryEntity_PhoneCodeAndTelNo(si.getPhoneCode(), si.getTelNo())
                .isPresent();
    }
}
