package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.music.File;
import com.seliote.mlb.common.jsr303.music.Name;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 歌曲信息实体
 *
 * @author seliote
 * @version 2021-09-15
 */
@SuppressWarnings("unused")
@Entity(name = "music")
public class MusicEntity extends AuditingEntity {

    // 音乐文件，Minio 地址
    private String file;

    // 歌曲名称
    private String name;

    // 歌手信息，多对一
    private SingerEntity singerEntity;

    // 上传者，存在一首歌曲被多人上传的情况
    private Set<UserEntity> uploadBy;

    @File
    @Column(name = "file")
    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Name
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @Valid
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "singer_id", referencedColumnName = "id")
    public SingerEntity getSingerEntity() {
        return singerEntity;
    }

    public void setSingerEntity(SingerEntity singerEntity) {
        this.singerEntity = singerEntity;
    }

    @NotEmpty
    @Valid
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "music_upload",
            joinColumns = @JoinColumn(name = "music_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    public Set<UserEntity> getUploadBy() {
        return uploadBy;
    }

    public void setUploadBy(Set<UserEntity> uploadBy) {
        this.uploadBy = uploadBy;
    }
}
