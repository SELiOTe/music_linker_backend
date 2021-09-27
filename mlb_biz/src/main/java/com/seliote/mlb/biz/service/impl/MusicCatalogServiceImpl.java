package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.so.musiccatalog.UploadListSo;
import com.seliote.mlb.biz.domain.so.musiccatalog.mapper.UploadListSoMapper;
import com.seliote.mlb.biz.service.MusicCatalogService;
import com.seliote.mlb.common.exception.ApiException;
import com.seliote.mlb.dao.AuditingEntity;
import com.seliote.mlb.dao.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * 歌单信息 Service 实现
 *
 * @author seliote
 * @version 2021-09-16
 */
@Slf4j
@Service
public class MusicCatalogServiceImpl implements MusicCatalogService {

    private final UserRepo userRepo;

    @Autowired
    public MusicCatalogServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public long uploadCount(Long userId) {
        var user = userRepo.findById(userId).orElseThrow(() -> {
            log.error("User {} not exists", userId);
            throw new ApiException("User not found");
        });
        return user.getUploadMusic().size();
    }

    @Override
    public List<UploadListSo> uploadList(Long userId) {
        var user = userRepo.findById(userId).orElseThrow(() -> {
            log.error("User {} not exists", userId);
            throw new ApiException("User not found");
        });
        var musicList = user.getUploadMusic();
        musicList.sort(Comparator.comparing(AuditingEntity::getId));
        return UploadListSoMapper.INSTANCE.fromMusicEntityList(musicList);
    }
}
