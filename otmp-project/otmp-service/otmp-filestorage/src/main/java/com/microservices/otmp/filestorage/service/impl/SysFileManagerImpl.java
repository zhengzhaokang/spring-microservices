package com.microservices.otmp.filestorage.service.impl;

import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;
import com.microservices.otmp.filestorage.mapper.SysFileMapper;
import com.microservices.otmp.filestorage.service.SysFileManager;
import com.microservices.otmp.filestorage.dto.SystemFile;
import com.microservices.otmp.filestorage.dto.SystemFileDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysFileManagerImpl implements SysFileManager {

    @Autowired
    private SysFileMapper sysFileMapper;

    @Override
    public SystemFileDO selectByFileUuid(String fileUUID) {
        return sysFileMapper.selectByFileUuid(fileUUID);
    }

    @Override
    public int insertSelective(SystemFileDO sysFileDO) {
        return sysFileMapper.insertSelective(sysFileDO);
    }

    @Override
    public int deleteByFileUuid(String fileUuid) {
        return sysFileMapper.deleteByFileUuid(fileUuid);
    }

    @Override
    public List<SystemFileDO> selectBySysFile(SystemFile sysFile) {
        return sysFileMapper.selectBySysFile(sysFile);
    }

    @Override
    public int updateByDataId(Map<String, String> params) {
        return sysFileMapper.updateByDataIdAndFileUuid(params);
    }

    @Override
    public List<String> selectByDataId(Map<String, String> params) {
        return sysFileMapper.selectByDataId(params);
    }

    @Override
    public int batchUpdateFileStatusByFileUuid(List<String> fileUuidBySql) {
        return sysFileMapper.batchUpdateFileStatusByFileUuid(fileUuidBySql);
    }

    @Override
    public int batchUpdateFileStatusAvilableByFileUuids(List<String> fileUuids) {
        return sysFileMapper.batchUpdateFileStatusAvilableByFileUuids(fileUuids);
    }

    @Override
    public int updateByFileUuidsAndDataId(String dataId, List<String> fileUuids) {
        return sysFileMapper.updateByDataId(Long.parseLong(dataId), fileUuids);
    }
}
