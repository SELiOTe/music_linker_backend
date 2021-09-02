package com.seliote.mlb.api.domain.resp.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.EnName;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.PhonePattern;
import lombok.Data;

/**
 * 列出所有国家信息 Resp
 *
 * @author seliote
 * @version 2021-08-04
 */
@Data
public class CountryListResp {

    // 国家码
    @JsonProperty("country_code")
    @CountryCode
    private String countryCode;

    // 英文名称
    @JsonProperty("en_name")
    @EnName
    private String enName;

    // 母语中的名称
    @JsonProperty("local_name")
    @LocalName
    private String localName;

    // 国际电话区号
    @JsonProperty("phone_code")
    @PhoneCode
    private String phoneCode;

    // 手机号码格式正则
    @JsonProperty("phone_pattern")
    @PhonePattern
    private String phonePattern;
}
