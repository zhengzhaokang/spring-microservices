package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseSalesOfficeProfitCenterMappingDO;

import java.util.List;

/**
 * salesOffice与profitMapping映射Service接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface IBizBaseSalesOfficeProfitCenterMappingService
{
    /**
     * 查询salesOffice与profitMapping映射
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return salesOffice与profitMapping映射DTO
     */
    public BizBaseSalesOfficeProfitCenterMappingDTO selectBizBaseSalesOfficeProfitCenterMappingById(Long id);

    /**
     * 查询salesOffice与profitMapping映射列表
     *
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return salesOffice与profitMapping映射DTO集合
     */
    public List<BizBaseSalesOfficeProfitCenterMappingDTO> selectBizBaseSalesOfficeProfitCenterMappingList(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 新增salesOffice与profitMapping映射
     * 
     * @param \
     * * salesOffice与profitMapping映射
     * @return 结果
     */
    public int insertBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 修改salesOffice与profitMapping映射
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    public int updateBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 批量删除salesOffice与profitMapping映射
     * 
     * @param ids 需要删除的salesOffice与profitMapping映射主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeProfitCenterMappingByIds(Long[] ids);

    /**
     * 删除salesOffice与profitMapping映射信息
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeProfitCenterMappingById(Long id);

    int updateBySalesOffice(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping);
}
