package com.microservices.otmp.masterdata.manager.impl;

import java.util.List;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBwBuBgMappingDO;
import com.microservices.otmp.masterdata.manager.IBizBaseBwBuBgMappingManager;
import com.microservices.otmp.masterdata.mapper.BizBaseBwBuBgMappingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * bwBuBgMappingManager业务层处理
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@Service
public class BizBaseBwBuBgMappingManagerImpl implements IBizBaseBwBuBgMappingManager
{
    @Autowired
    private BizBaseBwBuBgMappingMapper bizBaseBwBuBgMappingMapper;

    /**
     * 查询bwBuBgMapping
     * 
     * @param id bwBuBgMapping主键
     * @return bwBuBgMappingDO
     */
    @Override
    public BizBaseBwBuBgMappingDO selectBizBaseBwBuBgMappingById(Long id)
    {
        return bizBaseBwBuBgMappingMapper.selectBizBaseBwBuBgMappingById(id);
    }

    /**
     * 查询bwBuBgMapping列表
     *
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return bwBuBgMappingDO
     */
    @Override
    public List<BizBaseBwBuBgMappingDO> selectBizBaseBwBuBgMappingList(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        return bizBaseBwBuBgMappingMapper.selectBizBaseBwBuBgMappingList(bizBaseBwBuBgMapping);
    }

    /**
     * 新增bwBuBgMapping
     *
     * @param bizBaseBwBuBgMapping bwBuBgMapping
     * @return 结果
     */
    @Override
    public int insertBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDO bizBaseBwBuBgMapping)
    {
        bizBaseBwBuBgMapping.setCreateTime(DateUtils.getNowDate());
        return bizBaseBwBuBgMappingMapper.insertBizBaseBwBuBgMapping (bizBaseBwBuBgMapping);
    }

    /**
     * 修改bwBuBgMapping
     *
     * @param bizBaseBwBuBgMapping  bwBuBgMapping
     * @return 结果
     */
    @Override
    public int updateBizBaseBwBuBgMapping(BizBaseBwBuBgMappingDO bizBaseBwBuBgMapping)
    {
        bizBaseBwBuBgMapping.setUpdateTime(DateUtils.getNowDate());
        return bizBaseBwBuBgMappingMapper.updateBizBaseBwBuBgMapping (bizBaseBwBuBgMapping);
    }

    /**
     * 批量删除bwBuBgMapping
     * 
     * @param ids 需要删除的bwBuBgMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBwBuBgMappingByIds(Long[] ids)
    {
        return bizBaseBwBuBgMappingMapper.deleteBizBaseBwBuBgMappingByIds(ids);
    }

    /**
     * 删除bwBuBgMapping信息
     * 
     * @param id bwBuBgMapping主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseBwBuBgMappingById(Long id)
    {
        return bizBaseBwBuBgMappingMapper.deleteBizBaseBwBuBgMappingById(id);
    }

    @Override
    public int updateByKey(BizBaseBwBuBgMappingDO bizBaseBwBuBgMappingDO) {
        return bizBaseBwBuBgMappingMapper.updateByKey(bizBaseBwBuBgMappingDO);
    }
}
