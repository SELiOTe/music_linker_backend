package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.country.CountryCode;
import com.seliote.mlb.common.jsr303.country.EnName;
import com.seliote.mlb.common.jsr303.country.LocalName;
import com.seliote.mlb.common.jsr303.phone.PhoneCode;
import com.seliote.mlb.common.jsr303.phone.PhonePattern;
import com.seliote.mlb.dao.AuditingEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 国家码及国际电话区号实体
 *
 * @author seliote
 * @version 2021-06-27
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Entity(name = "country")
public class CountryEntity extends AuditingEntity {

    // 国家码
    @CountryCode
    @Column(name = "country_code")
    private String countryCode;

    // 英文名称
    @EnName
    @Column(name = "en_name")
    private String enName;

    // 母语中的名称
    @LocalName
    @Column(name = "local_name")
    private String localName;

    // 国际电话区号
    @PhoneCode
    @Column(name = "phone_code")
    private String phoneCode;

    // 手机号码格式正则
    @PhonePattern
    @Column(name = "phone_pattern")
    private String phonePattern;
}
