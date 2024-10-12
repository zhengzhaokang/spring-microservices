package com.microservices.otmp.filestorage.mapper;

import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysFileMapper {
    /**
     * 根据file_uuid查询文件信息
     * @param fileUUID
     * @return
     */
    SystemFileDO selectByFileUuid(String fileUUID);

    int insertSelective(SystemFileDO sysFileDO);

    int deleteByFileUuid(String fileUuid);

    List<SystemFileDO> selectBySysFile(@Param("params") SystemFile sysFile);

    int updateByDataIdAndFileUuid(@Param("params")Map<String, String> params);

    List<String> selectByDataId(@Param("params")Map<String, String> params);

    int batchUpdateFileStatusByFileUuid(@Param("items") List<String> fileUuidBySql);

    int batchUpdateFileStatusAvilableByFileUuids(@Param("items") List<String> fileUuids);

    int updateByDataId(@Param("dataId") Long dataId, @Param("items") List<String> fileUuids);

    List<SystemFileDO> selectForTest();
}
