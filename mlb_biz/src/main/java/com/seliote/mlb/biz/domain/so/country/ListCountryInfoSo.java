package com.seliote.mlb.biz.domain.so.country;

import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.EnName;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.PhonePattern;
import lombok.Data;

/**
 * 列出所有支持的国家信息 SO
 *
 * @author seliote
 * @version 2021-06-27
 */
@Data
public class ListCountryInfoSo {

    // 国家码
    @CountryCode
    private String countryCode;

    // 英文名称
    @EnName
    private String enName;

    // 母语中的名称
    @LocalName
    private String localName;

    // 国际电话区号
    @PhoneCode
    private String phoneCode;

    // 手机号码格式正则
    @PhonePattern
    private String phonePattern;
}
