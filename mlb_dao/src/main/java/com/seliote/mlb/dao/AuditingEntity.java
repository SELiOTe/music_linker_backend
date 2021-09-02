package com.seliote.mlb.dao;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;

/**
 * 审计实体，所有项目实体均需继承自此实体
 *
 * @author seliote
 * @version 2021-06-27
 */
@Data
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AuditingEntity {

    // row id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // 行创建时间
    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    // 行最后修改时间
    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;
}
