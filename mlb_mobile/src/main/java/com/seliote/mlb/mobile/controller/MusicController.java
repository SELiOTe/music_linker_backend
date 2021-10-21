package com.seliote.mlb.mobile.controller;

import com.seliote.mlb.biz.domain.si.music.CanPlaySi;
import com.seliote.mlb.biz.service.MusicService;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.config.api.ApiFreq;
import com.seliote.mlb.common.domain.resp.Resp;
import com.seliote.mlb.common.exception.ApiException;
import com.seliote.mlb.mobile.domain.req.music.CanPlayReq;
import com.seliote.mlb.mobile.domain.req.music.PlayMusicReq;
import com.seliote.mlb.mobile.domain.resp.music.CanPlayResp;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 音乐 Controller
 *
 * @author seliote
 * @version 2021-10-20
 */
@Slf4j
@Validated
@Controller
@RequestMapping("/music")
public class MusicController {

    private final UserService userService;
    private final MusicService musicService;

    @Autowired
    public MusicController(UserService userService,
                           MusicService musicService) {
        this.userService = userService;
        this.musicService = musicService;
    }

    /**
     * 获取当前用户是否有权限播放相应歌曲
     *
     * @param req 请求实体
     * @return 响应实体
     */
    @Valid
    @RequestMapping(value = "/can_play", method = RequestMethod.POST)
    @ApiFreq(freq = 60)
    @ResponseBody
    public Resp<CanPlayResp> canPlay(@RequestBody @NotNull @Valid CanPlayReq req) {
        var userId = userService.currentUserId().orElseThrow(() -> {
            log.warn("Can not get user id by security context");
            return new ApiException("Can not get user id by security context");
        });
        return Resp.resp(CanPlayResp.builder().canPlay(musicService.canPlay(
                CanPlaySi.builder().userId(userId).musicId(req.getMusicId()).build())).build());
    }

    /**
     * 获取播放流
     *
     * @param req 请求实体
     */
    @SneakyThrows
    @RequestMapping(value = "/play_music", method = RequestMethod.POST)
    @ApiFreq(freq = 60)
    public void playMusic(@RequestBody @NotNull @Valid PlayMusicReq req,
                          HttpServletResponse response) {
        var userId = userService.currentUserId().orElseThrow(() -> {
            log.warn("Can not get user id by security context");
            return new ApiException("Can not get user id by security context");
        });
        var canPlay = musicService.canPlay(
                CanPlaySi.builder().userId(userId).musicId(req.getMusicId()).build());
        if (!canPlay) {
            log.warn("User {} want to play music {}, permission deny", userId, req.getMusicId());
            throw new ApiException("Permission deny for play music");
        }
        var musicBytes = musicService.playMusic(req.getMusicId());
        // 直接操作响应
        response.getOutputStream().write(musicBytes);
    }
}
