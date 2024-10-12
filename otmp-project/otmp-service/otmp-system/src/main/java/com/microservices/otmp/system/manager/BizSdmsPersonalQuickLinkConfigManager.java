package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;

import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigManager接口
 *
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsPersonalQuickLinkConfigManager {
    /**
     * 查询BizSdmsPersonalQuickLinkConfig
     *
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return BizSdmsPersonalQuickLinkConfigDO
     */
    public BizSdmsPersonalQuickLinkConfigDO selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId);

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     *
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return BizSdmsPersonalQuickLinkConfigDO集合
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
