package com.microservices.otmp.masterdata.service.impl;

import java.util.List;
import java.util.ArrayList;
import com.microservices.otmp.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;
import com.microservices.otmp.masterdata.manager.IBizBaseCompanyLocalCurrecyMappingManager;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCompanyLocalCurrecyMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseCompanyLocalCurrecyMappingDO;
import com.microservices.otmp.masterdata.service.IBizBaseCompanyLocalCurrecyMappingService;

/**
 * companyCode和localcurrency映射Service业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseCompanyLocalCurrecyMappingServiceImpl implements IBizBaseCompanyLocalCurrecyMappingService
{
    @Autowired
    private IBizBaseCompanyLocalCurrecyMappingManager bizBaseCompanyLocalCurrecyMappingManager;

    /**
     * 查询companyCode和localcurrency映射
     * 
     * @param id companyCode和localcurrency映射主键
     * @return companyCode和localcurrency映射DTO
     */
    @Override
    public BizBaseCompanyLocalCurrecyMappingDTO selectBizBaseCompanyLocalCurrecyMappingById(Long id)
    {
         BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO =  bizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingById(id);
         BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMappingDTO = new BizBaseCompanyLocalCurrecyMappingDTO();
         BeanUtils.copyProperties(bizBaseCompanyLocalCurrecyMappingDO, bizBaseCompanyLocalCurrecyMappingDTO);
        return bizBaseCompanyLocalCurrecyMappingDTO;
    }

    /**
     * 查询companyCode和localcurrency映射列表
     *
     * @param bizBaseCompanyLocalCurrecyMapping companyCode和localcurrency映射
     * @return companyCode和localcurrency映射DTO
     */
    @Override
    public List<BizBaseCompanyLocalCurrecyMappingDTO> selectBizBaseCompanyLocalCurrecyMappingList(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {

        List<BizBaseCompanyLocalCurrecyMappingDO> bizBaseCompanyLocalCurrecyMappingDOS =
                    bizBaseCompanyLocalCurrecyMappingManager.selectBizBaseCompanyLocalCurrecyMappingList(bizBaseCompanyLocalCurrecyMapping);
        List<BizBaseCompanyLocalCurrecyMappingDTO> bizBaseCompanyLocalCurrecyMappingList = new ArrayList<>(bizBaseCompanyLocalCurrecyMappingDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseCompanyLocalCurrecyMappingDOS, bizBaseCompanyLocalCurrecyMappingList, BizBaseCompanyLocalCurrecyMappingDTO.class);
        return bizBaseCompanyLocalCurrecyMappingList;

    }

    /**
     * 新增companyCode和localcurrency映射
     * 
     * @param bizBaseCompanyLocalCurrecyMappingDTO companyCode和localcurrency映射
     * @return 结果
     */
    @Override
    public int insertBizBaseCompanyLocalCurrecyMapping(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        bizBaseCompanyLocalCurrecyMapping.setCreateTime(DateUtils.getNowDate());
        BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO =new  BizBaseCompanyLocalCurrecyMappingDO();
        BeanUtils.copyProperties(bizBaseCompanyLocalCurrecyMapping, bizBaseCompanyLocalCurrecyMappingDO);
        return bizBaseCompanyLocalCurrecyMappingManager.insertBizBaseCompanyLocalCurrecyMapping(bizBaseCompanyLocalCurrecyMappingDO);
    }

    /**
     * 修改companyCode和localcurrency映射
     * 
     * @param bizBaseCompanyLocalCurrecyMappingDTO companyCode和localcurrency映射
     * @return 结果
     */
    @Override
    public int updateBizBaseCompanyLocalCurrecyMapping(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(DateUtils.getNowDate());
       BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO =new  BizBaseCompanyLocalCurrecyMappingDO();
        BeanUtils.copyProperties(bizBaseCompanyLocalCurrecyMapping, bizBaseCompanyLocalCurrecyMappingDO);
        return bizBaseCompanyLocalCurrecyMappingManager.updateBizBaseCompanyLocalCurrecyMapping(bizBaseCompanyLocalCurrecyMappingDO);
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
        return bizBaseCompanyLocalCurrecyMappingManager.deleteBizBaseCompanyLocalCurrecyMappingByIds(ids);
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
        return bizBaseCompanyLocalCurrecyMappingManager.deleteBizBaseCompanyLocalCurrecyMappingById(id);
    }

    @Override
    public int updateByCompanyCode(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping) {
        bizBaseCompanyLocalCurrecyMapping.setUpdateTime(DateUtils.getNowDate());
        BizBaseCompanyLocalCurrecyMappingDO bizBaseCompanyLocalCurrecyMappingDO =new  BizBaseCompanyLocalCurrecyMappingDO();
        BeanUtils.copyProperties(bizBaseCompanyLocalCurrecyMapping, bizBaseCompanyLocalCurrecyMappingDO);
        return bizBaseCompanyLocalCurrecyMappingManager.updateByCompanyCode(bizBaseCompanyLocalCurrecyMappingDO);
    }

}
