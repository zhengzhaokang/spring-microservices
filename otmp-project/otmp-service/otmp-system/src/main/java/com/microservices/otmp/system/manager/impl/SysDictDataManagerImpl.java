package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.entity.SysDictDataDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.mapper.SysDictDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysDictDataManagerImpl implements SysDictDataManager {

    @Autowired
    private SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictDataDO> selectDictDataList(SysDictData dictData) {
        return sysDictDataMapper.selectDictDataList(dictData);
    }

    @Override
    @Cacheable(value = "sys_dict_details", key = "#dictType")
    public List<SysDictDataDO> selectDictDataByType(String dictType) {
        return sysDictDataMapper.selectDictDataByType(dictType);
    }

    @Override
    public List<SysDictDataDO> selectDictDataByTypeCode(String dictType) {
        return sysDictDataMapper.selectDictDataByType(dictType);
    }

    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return sysDictDataMapper.selectDictLabel(dictType, dictValue);
    }

    @Override
    public SysDictDataDO selectDictDataById(Long dictCode) {
        return sysDictDataMapper.selectDictDataById(dictCode);
    }

    @Override
    public int countDictDataByType(String dictType) {
        return sysDictDataMapper.countDictDataByType(dictType);
    }

    @Override
//    @CacheEvict(value = "sys_dict_details", allEntries = true)
    public int deleteDictDataById(Long dictCode) {
        return sysDictDataMapper.deleteDictDataById(dictCode);
    }

    @Override
//    @CacheEvict(value = "sys_dict_details", allEntries = true)
    public int deleteDictDataByIds(String loginName, String[] ids) {
        return sysDictDataMapper.deleteDictDataByIds(loginName, ids);
    }

    @Override
    @CacheEvict(value = "sys_dict_details", key = "#dictData.dictType")
    public int insertDictData(SysDictDataDO dictData) {
        return sysDictDataMapper.insertDictData(dictData);
    }

    @Override
    @CacheEvict(value = "sys_dict_details", key = "#dictData.dictType")
    public int updateDictData(SysDictDataDO dictData) {
        return sysDictDataMapper.updateDictData(dictData);
    }

    @Override
    @CacheEvict(value = "sys_dict_details", key = "#oldDictType")
    public int updateDictDataType(String oldDictType, String newDictType) {
        return sysDictDataMapper.updateDictDataType(oldDictType, newDictType);
    }

    @Override
//    @CacheEvict(value = "sys_dict_details", allEntries = true)
    public int deleteByIds(Long[] ids) {
        return sysDictDataMapper.deleteByIds(ids);
    }

    @Override
    @CacheEvict(value = "sys_dict_details", key = "#dictType")
    public int deleteDictDataByDictType(String dictType) {

        return sysDictDataMapper.deleteDictDataByDictType(dictType);
    }
}
