package com.seliote.mlb.dao;

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
@SuppressWarnings("unused")
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public abstract class AuditingEntity {

    // row id
    private Long id;

    // 行创建时间
    private Instant createdDate;

    // 行最后修改时间
    private Instant lastModifiedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @CreatedDate
    @Column(name = "created_date")
    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @LastModifiedDate
    @Column(name = "last_modified_date")
    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
