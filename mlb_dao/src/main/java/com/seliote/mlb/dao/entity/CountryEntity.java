package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.EnName;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.PhonePattern;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 国家码及国际电话区号实体
 *
 * @author seliote
 * @version 2021-06-27
 */
@SuppressWarnings("unused")
@Entity(name = "country")
public class CountryEntity extends AuditingEntity {

    // 国家码
    private String countryCode;

    // 英文名称
    private String enName;

    // 母语中的名称
    private String localName;

    // 国际电话区号
    private String phoneCode;

    // 手机号码格式正则
    private String phonePattern;

    @CountryCode
    @Column(name = "country_code")
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @EnName
    @Column(name = "en_name")
    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    @LocalName
    @Column(name = "local_name")
    public String getLocalName() {
        return localName;
    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    @PhoneCode
    @Column(name = "phone_code")
    public String getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(String phoneCode) {
        this.phoneCode = phoneCode;
    }

    @PhonePattern
    @Column(name = "phone_pattern")
    public String getPhonePattern() {
        return phonePattern;
    }

    public void setPhonePattern(String phonePattern) {
        this.phonePattern = phonePattern;
    }
}
