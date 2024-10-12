package com.microservices.otmp.masterdata.manager;

import java.util.List;

import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;


/**
 * bwBuBgMappingManager接口
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
public interface IBizBaseBwBuBgMappingManager
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
    public int updateBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDO bizBaseBwBuBgMapping);

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

    int updateByKey(BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO);
}
