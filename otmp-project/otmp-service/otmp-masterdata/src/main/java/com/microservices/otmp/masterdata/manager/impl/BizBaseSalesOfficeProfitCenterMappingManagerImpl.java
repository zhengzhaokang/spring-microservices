package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseSalesOfficeProfitCenterMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseSalesOfficeProfitCenterMappingManager;
import com.microservices.otmp.masterdata.mapper.BizBaseSalesOfficeProfitCenterMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * salesOffice与profitMapping映射Manager业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseSalesOfficeProfitCenterMappingManagerImpl implements IBizBaseSalesOfficeProfitCenterMappingManager
{
    @Autowired
    private BizBaseSalesOfficeProfitCenterMappingMapper bizBaseSalesOfficeProfitCenterMappingMapper;

    /**
     * 查询salesOffice与profitMapping映射
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return salesOffice与profitMapping映射DO
     */
    @Override
    public BizBaseSalesOfficeProfitCenterMappingDO selectBizBaseSalesOfficeProfitCenterMappingById(Long id)
    {
        return bizBaseSalesOfficeProfitCenterMappingMapper.selectBizBaseSalesOfficeProfitCenterMappingById(id);
    }

    /**
     * 查询salesOffice与profitMapping映射列表
     *
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return salesOffice与profitMapping映射DO
     */
    @Override
    public List<BizBaseSalesOfficeProfitCenterMappingDO> selectBizBaseSalesOfficeProfitCenterMappingList(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        return bizBaseSalesOfficeProfitCenterMappingMapper.selectBizBaseSalesOfficeProfitCenterMappingList(bizBaseSalesOfficeProfitCenterMapping);
    }

    /**
     * 新增salesOffice与profitMapping映射
     *
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    @Override
    public int insertBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping)
    {
        bizBaseSalesOfficeProfitCenterMapping.setCreateTime(DateUtils.getNowDate());
        return bizBaseSalesOfficeProfitCenterMappingMapper.insertBizBaseSalesOfficeProfitCenterMapping (bizBaseSalesOfficeProfitCenterMapping);
    }

    /**
     * 修改salesOffice与profitMapping映射
     *
     * @param bizBaseSalesOfficeProfitCenterMapping  salesOffice与profitMapping映射
     * @return 结果
     */
    @Override
    public int updateBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping)
    {
        bizBaseSalesOfficeProfitCenterMapping.setUpdateTime(DateUtils.getNowDate());
        return bizBaseSalesOfficeProfitCenterMappingMapper.updateBizBaseSalesOfficeProfitCenterMapping (bizBaseSalesOfficeProfitCenterMapping);
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
        return bizBaseSalesOfficeProfitCenterMappingMapper.deleteBizBaseSalesOfficeProfitCenterMappingByIds(ids);
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
        return bizBaseSalesOfficeProfitCenterMappingMapper.deleteBizBaseSalesOfficeProfitCenterMappingById(id);
    }

    @Override
    public int updateBySalesOffice(BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping) {
        return bizBaseSalesOfficeProfitCenterMappingMapper.updateBySalesOffice(bizBaseSalesOfficeProfitCenterMapping);
    }
}
