package com.seliote.mlb.mobile.domain.resp.musiccatalog.mapper;

import com.seliote.mlb.biz.domain.so.musiccatalog.UploadListSo;
import com.seliote.mlb.mobile.domain.resp.musiccatalog.UploadListResp;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 获取用户上传音乐列表 Resp MapStruct
 *
 * @author seliote
 * @version 2021-09-26
 */
@Mapper
public interface UploadListRespMapper {

    // 单例引用
    UploadListRespMapper INSTANCE = Mappers.getMapper(UploadListRespMapper.class);

    /**
     * 从获取用户上传音乐列表 SO 列表转为获取用户上传音乐列表 Resp 列表
     *
     * @param soList 获取用户上传音乐列表 SO 列表
     * @return 获取用户上传音乐列表 Resp 列表
     */
    List<UploadListResp> fromUploadListSoList(List<UploadListSo> soList);
}
