package com.seliote.mlb.biz.service.impl;

import com.seliote.mlb.biz.domain.si.music.CanPlaySi;
import com.seliote.mlb.biz.service.MusicService;
import com.seliote.mlb.common.config.YmlConfig;
import com.seliote.mlb.common.exception.ApiException;
import com.seliote.mlb.dao.repo.MusicRepo;
import com.seliote.mlb.fs.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 音乐 Service 实现
 *
 * @author seliote
 * @version 2021-10-21
 */
@Slf4j
@Service
public class MusicServiceImpl implements MusicService {

    private final MusicRepo musicRepo;
    private final MinioService minioService;
    private final String musicCatalog;

    @Autowired
    public MusicServiceImpl(MusicRepo musicRepo,
                            MinioService minioService,
                            YmlConfig ymlConfig) {
        this.musicRepo = musicRepo;
        this.minioService = minioService;
        this.musicCatalog = ymlConfig.getMinio().getCatalog().getMusic();
    }

    @Override
    public Boolean canPlay(CanPlaySi si) {
        log.info("User {} query {} can play or not", si.getUserId(), si.getMusicId());
        var music = musicRepo.findByUploadBy_IdAndId(si.getUserId(), si.getMusicId());
        return music.isPresent();
    }

    @Override
    public byte[] playMusic(Long musicId) {
        var music = musicRepo.findById(musicId).orElseThrow(() -> {
            log.warn("Music {} not exists", musicId);
            return new ApiException("Music not exists");
        });
        log.info("Play music {}", musicId);
        return minioService.download(musicCatalog, music.getFile());
    }
}
