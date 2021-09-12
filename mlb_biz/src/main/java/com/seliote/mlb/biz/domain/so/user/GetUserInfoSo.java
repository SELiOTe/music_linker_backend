package com.seliote.mlb.biz.domain.so.user;

import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.userinfo.Avatar;
import com.seliote.mlb.common.jsr303.userinfo.Nickname;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 获取用户信息 SO
 *
 * @author seliote
 * @version 2021-09-12
 */
@Data
public class GetUserInfoSo {

    // 用户 ID
    @NotNull
    private Long userId;

    // 国家码
    @CountryCode
    private String countryCode;

    // 母语中的名称
    @LocalName
    private String localName;

    // 用户昵称
    @Nickname
    private String nickname;

    // 用户头像，Minio 路径
    @Avatar
    private String avatar;
}
