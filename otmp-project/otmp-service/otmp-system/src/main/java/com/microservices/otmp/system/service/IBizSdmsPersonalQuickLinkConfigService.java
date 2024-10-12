package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.BizSdmsPersonalQuickLinkConfig;

import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigService接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface IBizSdmsPersonalQuickLinkConfigService
{
    /**
     * 查询BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return BizSdmsPersonalQuickLinkConfig
     */
    public BizSdmsPersonalQuickLinkConfig selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId);

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return BizSdmsPersonalQuickLinkConfig集合
     */
    public List<BizSdmsPersonalQuickLinkConfig> selectBizSdmsPersonalQuickLinkConfigList(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig);

    /**
     * 新增BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    public int insertBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig);

    /**
     * 修改BizSdmsPersonalQuickLinkConfig
     * 
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    public int updateBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig);

    /**
     * 批量删除BizSdmsPersonalQuickLinkConfig
     * 
     * @param quickLinkConfigIds 需要删除的BizSdmsPersonalQuickLinkConfig主键集合
     * @return 结果
     */
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(Long[] quickLinkConfigIds);

    /**
     * 删除BizSdmsPersonalQuickLinkConfig信息
     * 
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId);
}
