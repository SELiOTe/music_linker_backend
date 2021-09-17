package com.seliote.mlb.mobile.controller;

import com.seliote.mlb.biz.service.MusicCatalogService;
import com.seliote.mlb.biz.service.UserService;
import com.seliote.mlb.common.config.api.ApiFreq;
import com.seliote.mlb.common.domain.resp.Resp;
import com.seliote.mlb.mobile.domain.req.UploadCountReq;
import com.seliote.mlb.mobile.domain.resp.UploadCountResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * 歌单信息 Controller
 *
 * @author seliote
 * @version 2021-09-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/music_catalog")
public class MusicCatalogController {

    private final UserService userService;
    private final MusicCatalogService musicCatalogService;

    @Autowired
    public MusicCatalogController(UserService userService,
                                  MusicCatalogService musicCatalogService) {
        this.userService = userService;
        this.musicCatalogService = musicCatalogService;
    }

    /**
     * 获取用户上传音乐总数
     *
     * @param req 请求实体
     * @return 响应实体，0 为查找成功并会返回相应数量，1 为未查找到相应用户
     */
    @Valid
    @PostMapping("/upload_count")
    @ApiFreq(freq = 20)
    public Resp<UploadCountResp> uploadCount(@RequestBody @NotNull @Valid UploadCountReq req) {
        var user = userService.getUserInfo(req.getUserId());
        if (user.isEmpty()) {
            log.info("User {} not exists", req);
            return Resp.resp(1, "user not exists");
        }
        var count = musicCatalogService.uploadCount(req.getUserId());
        log.info("User {} upload count is {}", req.getUserId(), count);
        return Resp.resp(UploadCountResp.builder().count(count).build());
    }
}
