package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseSalesOfficeProfitCenterMappingDO;

import java.util.List;



/**
 * salesOffice与profitMapping映射Mapper接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface BizBaseSalesOfficeProfitCenterMappingMapper
{
    /**
     * 查询salesOffice与profitMapping映射
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return salesOffice与profitMapping映射
     */
    public BizBaseSalesOfficeProfitCenterMappingDO selectBizBaseSalesOfficeProfitCenterMappingById(Long id);

    /**
     * 查询salesOffice与profitMapping映射列表
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return salesOffice与profitMapping映射集合
     */
    public List<BizBaseSalesOfficeProfitCenterMappingDO> selectBizBaseSalesOfficeProfitCenterMappingList(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 新增salesOffice与profitMapping映射
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    public int insertBizBaseSalesOfficeProfitCenterMapping(BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 修改salesOffice与profitMapping映射
     * 
     * @param bizBaseSalesOfficeProfitCenterMapping salesOffice与profitMapping映射
     * @return 结果
     */
    public int updateBizBaseSalesOfficeProfitCenterMapping (BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping);

    /**
     * 删除salesOffice与profitMapping映射
     * 
     * @param id salesOffice与profitMapping映射主键
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeProfitCenterMappingById(Long id);

    /**
     * 批量删除salesOffice与profitMapping映射
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseSalesOfficeProfitCenterMappingByIds(Long[] ids);

     int updateBySalesOffice(BizBaseSalesOfficeProfitCenterMappingDO bizBaseSalesOfficeProfitCenterMapping);
}
