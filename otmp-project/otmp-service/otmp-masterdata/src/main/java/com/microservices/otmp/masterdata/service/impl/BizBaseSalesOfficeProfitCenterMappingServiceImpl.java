package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseSalesOfficeProfitCenterMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseSalesOfficeProfitCenterMappingManager;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOfficeProfitCenterMappingService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * salesOffice与profitMapping映射Service业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseSalesOfficeProfitCenterMappingServiceImpl implements IBizBaseSalesOfficeProfitCenterMappingService
{
    @Autowired
    private IBizBaseSalesOfficeProfitCenterMappingManager bizBaseSalesOfficeProfitCenterMappingManager;

    /**
     * 查询salesOffice与profitMapping映射
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return salesOffice与profitMapping映射DTO
     */
    @Override
    public BizBaseSalesOfficeProfitCenterMappingDTO selectBizBaseSalesOfficeProfitCenterMappingById(Long id)
    {
         BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO =  bizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingById(id);
         BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMappingDTO = new BizBaseSalesOfficeProfitCenterMappingDTO();
         BeanUtils.copyProperties(bizBaseSalesOfficeProfitCenterMappingDO, bizBaseSalesOfficeProfitCenterMappingDTO);
        return bizBaseSalesOfficeProfitCenterMappingDTO;
    }

    /**
     * 查询salesOffice与profitMapping映射列表
     *
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return salesOffice与profitMapping映射DTO
     */
    @Override
    public List<BizBaseSalesOfficeProfitCenterMappingDTO> selectBizBaseSalesOfficeProfitCenterMappingList(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {

        List<BizBaseSalesOfficeProfitCenterMappingDO> bizBaseSalesOfficeProfitCenterMappingDOS =
                    bizBaseSalesOfficeProfitCenterMappingManager.selectBizBaseSalesOfficeProfitCenterMappingList(bizBaseSalesOfficeProfitCenterMapping);
        List<BizBaseSalesOfficeProfitCenterMappingDTO> bizBaseSalesOfficeProfitCenterMappingList = new ArrayList<>(bizBaseSalesOfficeProfitCenterMappingDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseSalesOfficeProfitCenterMappingDOS, bizBaseSalesOfficeProfitCenterMappingList, BizBaseSalesOfficeProfitCenterMappingDTO.class);
        return bizBaseSalesOfficeProfitCenterMappingList;

    }

    /**
     * 新增salesOffice与profitMapping映射
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    @Override
    public int insertBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(DateUtils.getNowDate());
        BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO =new  BizBaseSalesOfficeProfitCenterMappingDO();
        BeanUtils.copyProperties(bizBaseSalesOfficeProfitCenterMapping, bizBaseSalesOfficeProfitCenterMappingDO);
        return bizBaseSalesOfficeProfitCenterMappingManager.insertBizBaseSalesOfficeProfitCenterMapping(bizBaseSalesOfficeProfitCenterMappingDO);
    }

    /**
     * 修改salesOffice与profitMapping映射
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    @Override
    public int updateBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(DateUtils.getNowDate());
       BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO =new  BizBaseSalesOfficeProfitCenterMappingDO();
        BeanUtils.copyProperties(bizBaseSalesOfficeProfitCenterMapping, bizBaseSalesOfficeProfitCenterMappingDO);
        return bizBaseSalesOfficeProfitCenterMappingManager.updateBizBaseSalesOfficeProfitCenterMapping(bizBaseSalesOfficeProfitCenterMappingDO);
    }

    /**
     * 批量删除salesOffice与profitMapping映射
     * 
     * @param ids 需要删除的salesOffice与profitMapping映射主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOfficeProfitCenterMappingByIds(Long[] ids)
    {
        return bizBaseSalesOfficeProfitCenterMappingManager.deleteBizBaseSalesOfficeProfitCenterMappingByIds(ids);
    }

    /**
     * 删除salesOffice与profitMapping映射信息
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseSalesOfficeProfitCenterMappingById(Long id)
    {
        return bizBaseSalesOfficeProfitCenterMappingManager.deleteBizBaseSalesOfficeProfitCenterMappingById(id);
    }

    @Override
    public int updateBySalesOffice(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping) {
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(DateUtils.getNowDate());
        BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMappingDO =new  BizBaseSalesOfficeProfitCenterMappingDO();
        BeanUtils.copyProperties(bizBaseSalesOfficeProfitCenterMapping, bizBaseSalesOfficeProfitCenterMappingDO);
        return bizBaseSalesOfficeProfitCenterMappingManager.updateBySalesOffice(bizBaseSalesOfficeProfitCenterMappingDO);
    }
}
