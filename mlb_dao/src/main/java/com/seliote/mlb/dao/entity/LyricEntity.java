package com.seliote.mlb.dao.entity;

import com.seliote.mlb.common.jsr303.lyric.Line;
import com.seliote.mlb.common.jsr303.lyric.OffsetMilli;
import com.seliote.mlb.dao.AuditingEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * 歌词实体
 *
 * @author seliote
 * @version 2021-09-15
 */
@SuppressWarnings("unused")
@Entity(name = "lyric")
public class LyricEntity extends AuditingEntity {

    // 歌曲 ID，关联 music.id
    // 这里不会涉及歌词查歌曲，所以未做实体关联
    // 加之歌曲信息与歌词信息均为分开拉取，对方实体也未作实体关联
    private Long musicId;

    // 歌词定位，相对于歌曲开始经过的毫秒
    private Long offsetMilli;

    // 歌词行
    private String line;

    @NotNull
    @Column(name = "music_id")
    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    @OffsetMilli
    @Column(name = "offset_milli")
    public Long getOffsetMilli() {
        return offsetMilli;
    }

    public void setOffsetMilli(Long millisecond) {
        this.offsetMilli = millisecond;
    }

    @Line
    @Column(name = "line")
    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }
}
