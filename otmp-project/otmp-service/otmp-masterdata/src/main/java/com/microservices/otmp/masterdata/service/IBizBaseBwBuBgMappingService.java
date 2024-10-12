package com.microservices.otmp.masterdata.service;

import java.util.List;

import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;

/**
 * bwBuBgMappingService接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface IBizBaseBwBuBgMappingService
{
    /**
     * 查询bwBuBgMapping
     * 
     * @param id bwBuBgMapping主键
     * @return bwBuBgMappingDTO
     */
    public BizBaseBwBuBgMappingDTO selectBizBaseBwBuBgMappingById(Long id);

    /**
     * 查询bwBuBgMapping列表
     *
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return bwBuBgMappingDTO集合
     */
    public List<BizBaseBwBuBgMappingDTO> selectBizBaseBwBuBgMappingList(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping);

    /**
     * 新增bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    public int insertBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping);

    /**
     * 修改bwBuBgMapping
     * 
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    public int updateBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping);

    /**
     * 批量删除bwBuBgMapping
     * 
     * @param ids 需要删除的bwBuBgMapping主键集合
     * @return 结果
     */
    public int deleteBizBaseBwBuBgMappingByIds(Long[] ids);

    /**
     * 删除bwBuBgMapping信息
     * 
     * @param id bwBuBgMapping主键
     * @return 结果
     */
    public int deleteBizBaseBwBuBgMappingById(Long id);

    int updateByKey(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMappingDO);

}
