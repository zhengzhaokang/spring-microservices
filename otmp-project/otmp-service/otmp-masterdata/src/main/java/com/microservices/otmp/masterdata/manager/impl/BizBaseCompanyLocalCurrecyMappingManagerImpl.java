package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservices.otmp.masterdata.mapper.BizBaseCompanyLocalCurrecyMappingMapper;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCompanyLocalCurrecyMappingDO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCompanyLocalCurrecyMappingDTO;
import com.microservices.otmp.masterdata.manager.IBizBaseCompanyLocalCurrecyMappingManager;

/**
 * companyCode和localcurrency映射Manager业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseCompanyLocalCurrecyMappingManagerImpl implements IBizBaseCompanyLocalCurrecyMappingManager
{
    @Autowired
    private BizBaseCompanyLocalCurrecyMappingMapper bizBaseCompanyLocalCurrecyMappingMapper;

    /**
     * 查询companyCode和localcurrency映射
     * 
     * @param id companyCode和localcurrency映射主键
     * @return companyCode和localcurrency映射DO
     */
    @Override
    public BizBaseCompanyLocalCurrecyMappingDO selectBizBaseCompanyLocalCurrecyMappingById(Long id)
    {
        return bizBaseCompanyLocalCurrecyMappingMapper.selectBizBaseCompanyLocalCurrecyMappingById(id);
    }

    /**
     * 查询companyCode和localcurrency映射列表
     *
     * @param bizBaseCompanyLocalCurrecyMapping companyCode和localcurrency映射
     * @return companyCode和localcurrency映射DO
     */
    @Override
    public List<BizBaseCompanyLocalCurrecyMappingDO> selectBizBaseCompanyLocalCurrecyMappingList(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        return bizBaseCompanyLocalCurrecyMappingMapper.selectBizBaseCompanyLocalCurrecyMappingList(bizBaseCompanyLocalCurrecyMapping);
    }

    /**
     * 新增companyCode和localcurrency映射
     *
     * @param bizBaseCompanyLocalCurrecyMapping companyCode和localcurrency映射
     * @return 结果
     */
    @Override
    public int insertBizBaseCompanyLocalCurrecyMapping(BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping)
    {
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(DateUtils.getNowDate());
        return bizBaseCompanyLocalCurrecyMappingMapper.insertBizBaseCompanyLocalCurrecyMapping (bizBaseCompanyLocalCurrecyMapping);
    }

    /**
     * 修改companyCode和localcurrency映射
     *
     * @param bizBaseCompanyLocalCurrecyMapping  companyCode和localcurrency映射
     * @return 结果
     */
    @Override
    public int updateBizBaseCompanyLocalCurrecyMapping(BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping)
    {
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(DateUtils.getNowDate());
        return bizBaseCompanyLocalCurrecyMappingMapper.updateBizBaseCompanyLocalCurrecyMapping (bizBaseCompanyLocalCurrecyMapping);
    }

    /**
     * 批量删除companyCode和localcurrency映射
     * 
     * @param ids 需要删除的companyCode和localcurrency映射主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCompanyLocalCurrecyMappingByIds(Long[] ids)
    {
        return bizBaseCompanyLocalCurrecyMappingMapper.deleteBizBaseCompanyLocalCurrecyMappingByIds(ids);
    }

    /**
     * 删除companyCode和localcurrency映射信息
     * 
     * @param id companyCode和localcurrency映射主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseCompanyLocalCurrecyMappingById(Long id)
    {
        return bizBaseCompanyLocalCurrecyMappingMapper.deleteBizBaseCompanyLocalCurrecyMappingById(id);
    }

    @Override
    public int updateByCompanyCode(BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMapping) {
        return bizBaseCompanyLocalCurrecyMappingMapper.updateByCompanyCode(bizBaseCompanyLocalCurrecyMapping);
    }
}
