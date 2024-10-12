package com.microservices.otmp.masterdata.mapper;


import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;

import java.util.List;


/**
 * bwBuBgMappingMapper接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface BizBaseBwBuBgMappingMapper
{
    /**
     * 查询bwBuBgMapping
     * 
     * @param id bwBuBgMapping主键
     * @return bwBuBgMapping
     */
    public BizBaseBwBuBgMappingDO selectBizBaseBwBuBgMappingById(Long id);

    /**
     * 查询bwBuBgMapping列表
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return bwBuBgMapping集合
     */
    public List<BizBaseBwBuBgMappingDO> selectBizBaseBwBuBgMappingList(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping);

    /**
     * 新增bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    public int insertBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDO bizBaseBwBuBgMapping);

    /**
     * 修改bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    public int updateBizBaseBwBuBgMapping (BizBaseBwBuBgMappingDO bizBaseBwBuBgMapping);

    /**
     * 删除bwBuBgMapping
     * 
     * @param id bwBuBgMapping主键
     * @return 结果
     */
    public int deleteBizBaseBwBuBgMappingById(Long id);

    /**
     * 批量删除bwBuBgMapping
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizBaseBwBuBgMappingByIds(Long[] ids);

    int updateByKey(BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO);
}
