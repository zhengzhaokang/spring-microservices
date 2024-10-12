package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;

import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigMapper接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsPersonalQuickLinkConfigMapper
{
    /**
     * 查询BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return BizSdmsPersonalQuickLinkConfig
     */
    public BizSdmsPersonalQuickLinkConfigDO selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId);

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return BizSdmsPersonalQuickLinkConfig集合
     */
    public List<BizSdmsPersonalQuickLinkConfigDO> selectBizSdmsPersonalQuickLinkConfigList(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig);

    /**
     * 新增BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    public int insertBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig);

    /**
     * 修改BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    public int updateBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfig);

    /**
     * 删除BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId);

    /**
     * 批量删除BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(Long[] quickLinkConfigIds);
}
