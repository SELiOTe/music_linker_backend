package com.seliote.mlb.biz.domain.so.musiccatalog.mapper;

import com.seliote.mlb.biz.domain.so.musiccatalog.UploadListSo;
import com.seliote.mlb.dao.entity.MusicEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 获取用户上传音乐列表 SO MapStruct 映射
 *
 * @author seliote
 * @version 2021-09-26
 */
@Mapper
public interface UploadListSoMapper {

    // 单例引用
    UploadListSoMapper INSTANCE = Mappers.getMapper(UploadListSoMapper.class);

    /**
     * 从歌曲信息实体转为获取用户上传音乐列表 SO
     *
     * @param entity 歌曲信息实体
     * @return 获取用户上传音乐 SO
     */
    @Mapping(source = "singerEntity.id", target = "singerId")
    @Mapping(source = "singerEntity.name", target = "singerName")
    @Mapping(source = "id", target = "musicId")
    @Mapping(source = "name", target = "musicName")
    UploadListSo fromMusicEntity(MusicEntity entity);

    /**
     * 从歌曲信息实体列表转为获取用户上传音乐列表 SO
     *
     * @param entityList 歌曲信息实体列表
     * @return 获取用户上传音乐 SO 列表
     */
    List<UploadListSo> fromMusicEntityList(List<MusicEntity> entityList);
}
