package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.enums.GenerateBusinessTypeEnum;
import com.microservices.otmp.common.exception.BusinessException;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
//import com.microservices.otmp.documents.domain.dto.SysOrderGenerateResultDTO;
//import com.microservices.otmp.documents.domain.feign.RemoteDocumentsService;
import com.microservices.otmp.system.domain.SysDictType;
import com.microservices.otmp.system.domain.entity.SysDictTypeDO;
import com.microservices.otmp.system.manager.SysDictDataManager;
import com.microservices.otmp.system.manager.SysDictTypeManager;
import com.microservices.otmp.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author lovefamily
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService {
    @Autowired
    private SysDictTypeManager sysDictTypeManager;

    @Autowired
    private SysDictDataManager sysDictDataManager;


    @Override
    public List<SysDictType> selectDictTypeListLike(SysDictType dictType) {
        Long[] dictIds = Convert.toLongArray(dictType.getIds());
        dictType.setIdsArray(dictIds);
        List<SysDictTypeDO> sysDictTypeDOS = sysDictTypeManager.selectDictTypeListLike(dictType);
        List<SysDictType> sysDictTypes = new ArrayList<>(sysDictTypeDOS.size());
        BeanUtils.copyListProperties(sysDictTypeDOS, sysDictTypes, SysDictType.class);
        return sysDictTypes;
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        Long[] dictIds = Convert.toLongArray(dictType.getIds());
        dictType.setIdsArray(dictIds);
        List<SysDictTypeDO> sysDictTypeDOS = sysDictTypeManager.selectDictTypeList(dictType);
        List<SysDictType> sysDictTypes = new ArrayList<>(sysDictTypeDOS.size());
        BeanUtils.copyListProperties(sysDictTypeDOS, sysDictTypes, SysDictType.class);
        return sysDictTypes;
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll() {
        List<SysDictTypeDO> sysDictTypeDOS = sysDictTypeManager.selectDictTypeAll();
        List<SysDictType> sysDictTypes = new ArrayList<>(sysDictTypeDOS.size());
        for (SysDictTypeDO sysDictTypeDO : sysDictTypeDOS) {
            SysDictType sysDictType = new SysDictType();
            org.springframework.beans.BeanUtils.copyProperties(sysDictTypeDO, sysDictType);
            sysDictTypes.add(sysDictType);
        }
        return sysDictTypes;
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        SysDictTypeDO sysDictTypeDO = sysDictTypeManager.selectDictTypeById(dictId);
        SysDictType sysDictType = null;
        if (sysDictTypeDO != null) {
            sysDictType = new SysDictType();
            org.springframework.beans.BeanUtils.copyProperties(sysDictTypeDO, sysDictType);
        }
        return sysDictType;
    }

    /**
     * 通过字典ID删除字典信息
     *
     * @param dictId 字典ID
     * @return 结果
     */
    @Override
    public int deleteDictTypeById(Long dictId) {
        return sysDictTypeManager.deleteDictTypeById(dictId);
    }

    /**
     * 批量删除字典类型
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String loginName, String ids) throws BusinessException {
        Long[] dictIds = Convert.toLongArray(ids);
        for (Long dictId : dictIds) {
            SysDictType dictType = selectDictTypeById(dictId);
            if (sysDictDataManager.countDictDataByType(dictType.getDictType()) > 0) {
                throw new BusinessException(String.format("%1$s已分配,不能删除", dictType.getDictName()));
            }
        }

        return sysDictTypeManager.deleteDictTypeByIds(loginName, dictIds);
    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dictType) {
        SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        org.springframework.beans.BeanUtils.copyProperties(dictType, sysDictTypeDO);
        return sysDictTypeManager.insertDictType(sysDictTypeDO);
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dictType) {
        SysDictTypeDO oldDict = sysDictTypeManager.selectDictTypeById(dictType.getDictId());
        sysDictDataManager.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        SysDictTypeDO sysDictTypeDO = new SysDictTypeDO();
        org.springframework.beans.BeanUtils.copyProperties(dictType, sysDictTypeDO);
        return sysDictTypeManager.updateDictType(sysDictTypeDO);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(SysDictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        SysDictTypeDO dictType = sysDictTypeManager.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }

    @Override
    public PageInfo<SysDictType> selectDictTypePage(SysDictType dictType) {
        Long[] dictIds = Convert.toLongArray(dictType.getIds());
        dictType.setIdsArray(dictIds);
        List<SysDictTypeDO> sysDictTypeDOS = sysDictTypeManager.selectDictTypeListLike(dictType);
        PageInfo<SysDictTypeDO> pageInfo = new PageInfo<>(sysDictTypeDOS);
        List<SysDictType> sysDictTypes = new ArrayList<>(sysDictTypeDOS.size());
        BeanUtils.copyListProperties(sysDictTypeDOS, sysDictTypes, SysDictType.class);
        PageInfo<SysDictType> resultPageInfo = new PageInfo<>(sysDictTypes);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    @Override
    public int save(SysDictType sysDictType) {
        return this.insertDictType(sysDictType);
    }

    @Override
    public int deleteDictTypeByDictType(String dictType) {
        return sysDictTypeManager.deleteDictTypeByDictType(dictType);
    }
}
