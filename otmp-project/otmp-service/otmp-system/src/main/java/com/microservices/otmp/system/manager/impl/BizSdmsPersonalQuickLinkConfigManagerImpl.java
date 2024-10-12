package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;
import com.microservices.otmp.system.manager.BizSdmsPersonalQuickLinkConfigManager;
import com.microservices.otmp.system.mapper.BizSdmsPersonalQuickLinkConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsPersonalQuickLinkConfigManagerImpl implements BizSdmsPersonalQuickLinkConfigManager
{
    @Autowired
    private BizSdmsPersonalQuickLinkConfigMapper bizSdmsPersonalQuickLinkConfigMapper;

    /**
     * 查询BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return BizSdmsPersonalQuickLinkConfigDO
     */
    @Override
    public BizSdmsPersonalQuickLinkConfigDO selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId)
    {
        return bizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(quickLinkConfigId);
    }

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return BizSdmsPersonalQuickLinkConfigDO
     */
    @Override
    public List<BizSdmsPersonalQuickLinkConfigDO> selectBizSdmsPersonalQuickLinkConfigList(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig)
    {
        return bizSdmsPersonalQuickLinkConfigMapper.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);
    }

    /**
     * 新增BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    @Override
    public int insertBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig)
    {
        bizSdmsPersonalQuickLinkConfig.setCreateTime(DateUtils.getNowDate());
        return bizSdmsPersonalQuickLinkConfigMapper.insertBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);
    }

    /**
     * 修改BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    @Override
    public int updateBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig)
    {
        bizSdmsPersonalQuickLinkConfig.setUpdateTime(DateUtils.getNowDate());
        return bizSdmsPersonalQuickLinkConfigMapper.updateBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig);
    }

    /**
     * 批量删除BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigIds 需要删除的BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(Long[] quickLinkConfigIds)
    {
        return bizSdmsPersonalQuickLinkConfigMapper.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(quickLinkConfigIds);
    }

    /**
     * 删除BizSdmsPersonalQuickLinkConfig信息
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId)
    {
        return bizSdmsPersonalQuickLinkConfigMapper.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(quickLinkConfigId);
    }
}
