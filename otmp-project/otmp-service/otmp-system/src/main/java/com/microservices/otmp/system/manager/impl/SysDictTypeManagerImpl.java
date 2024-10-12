package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.domain.entity.SysDictTypeDO;
import com.microservices.otmp.system.manager.SysDictTypeManager;
import com.microservices.otmp.system.mapper.SysDictTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */

@Service
public class SysDictTypeManagerImpl implements SysDictTypeManager {

    @Autowired
    private SysDictTypeMapper sysDictTypeMapper;

    @Override
    public List<SysDictTypeDO> selectDictTypeListLike(SysDictType dictType) {
        return sysDictTypeMapper.selectDictTypeListLike(dictType);
    }

    @Override
    public List<SysDictTypeDO> selectDictTypeList(SysDictType dictType) {
        return sysDictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public List<SysDictTypeDO> selectDictTypeAll() {
        return sysDictTypeMapper.selectDictTypeAll();
    }

    @Override
    public SysDictTypeDO selectDictTypeById(Long dictId) {
        return sysDictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public int deleteDictTypeById(Long dictId) {
        return sysDictTypeMapper.deleteDictTypeById(dictId);
    }

    @Override
    public int deleteDictTypeByIds(String loginName, Long[] ids) {
        return sysDictTypeMapper.deleteDictTypeByIds(loginName, ids);
    }

    @Override
    public int insertDictType(SysDictTypeDO dictType) {
        return sysDictTypeMapper.insertDictType(dictType);
    }

    @Override
    public int updateDictType(SysDictTypeDO dictType) {
        return sysDictTypeMapper.updateDictType(dictType);
    }

    @Override
    public SysDictTypeDO checkDictTypeUnique(String dictType) {
        return sysDictTypeMapper.checkDictTypeUnique(dictType);
    }

    @Override
    public int deleteDictTypeByDictType(String dictType) {
        return sysDictTypeMapper.deleteDictTypeByDictType(dictType);
    }
}
