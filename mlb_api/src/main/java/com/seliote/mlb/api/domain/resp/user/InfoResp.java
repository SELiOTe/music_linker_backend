package com.seliote.mlb.api.domain.resp.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.common.Gender;
import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.userinfo.Avatar;
import com.seliote.mlb.common.jsr303.userinfo.Nickname;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户个人信息 Resp
 *
 * @author seliote
 * @version 2021-09-12
 */
@Data
public class InfoResp {

    // 用户 ID
    @JsonProperty("user_id")
    @NotNull
    private Long userId;

    // 国家码
    @JsonProperty("country_code")
    @CountryCode
    private String countryCode;

    // 母语中的名称
    @JsonProperty("local_name")
    @LocalName
    private String localName;

    // 用户昵称
    @JsonProperty("nickname")
    @Nickname
    private String nickname;

    // 用户性别
    @JsonProperty("gender")
    @Gender
    private Integer gender;

    // 用户头像，Minio 路径
    @JsonProperty("avatar")
    @Avatar
    private String avatar;
}
