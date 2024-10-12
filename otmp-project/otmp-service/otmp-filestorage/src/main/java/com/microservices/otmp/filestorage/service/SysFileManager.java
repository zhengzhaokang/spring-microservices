package com.microservices.otmp.filestorage.service;

import java.util.List;
import java.util.Map;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;

public interface SysFileManager {
    SystemFileDO selectByFileUuid(String fileUUID);

    int insertSelective(SystemFileDO sysFileDO);

    int deleteByFileUuid(String fileUuid);

    List<SystemFileDO> selectBySysFile(SystemFile sysFile);

    int updateByDataId(Map<String, String> params);

    List<String> selectByDataId(Map<String, String> params);

    int batchUpdateFileStatusByFileUuid(List<String> fileUuidBySql);

    int batchUpdateFileStatusAvilableByFileUuids(List<String> fileUuids);

    int updateByFileUuidsAndDataId(String dataId, List<String> fileUuids);
}
