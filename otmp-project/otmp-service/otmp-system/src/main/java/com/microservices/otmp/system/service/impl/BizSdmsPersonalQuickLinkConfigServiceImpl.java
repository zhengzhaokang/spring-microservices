package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.BizSdmsPersonalQuickLinkConfig;
import com.microservices.otmp.system.domain.entity.BizSdmsPersonalQuickLinkConfigDO;
import com.microservices.otmp.system.manager.BizSdmsPersonalQuickLinkConfigManager;
import com.microservices.otmp.system.service.IBizSdmsPersonalQuickLinkConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigService业务层处理
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsPersonalQuickLinkConfigServiceImpl implements IBizSdmsPersonalQuickLinkConfigService {
    @Autowired
    private BizSdmsPersonalQuickLinkConfigManager bizSdmsPersonalQuickLinkConfigManager;

    /**
     * 查询BizSdmsPersonalQuickLinkConfig
     *
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return BizSdmsPersonalQuickLinkConfig
     */
    @Override
    public BizSdmsPersonalQuickLinkConfig selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId) {
        BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = bizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(quickLinkConfigId);
        BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig = new BizSdmsPersonalQuickLinkConfig();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsPersonalQuickLinkConfigDO, bizSdmsPersonalQuickLinkConfig);
        return bizSdmsPersonalQuickLinkConfig;
    }

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     *
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return BizSdmsPersonalQuickLinkConfig
     */
    @Override
    public List<BizSdmsPersonalQuickLinkConfig> selectBizSdmsPersonalQuickLinkConfigList(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {

        BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsPersonalQuickLinkConfig, bizSdmsPersonalQuickLinkConfigDO);
        List<BizSdmsPersonalQuickLinkConfigDO> bizSdmsPersonalQuickLinkConfigDOS = bizSdmsPersonalQuickLinkConfigManager.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfigDO);
        List<BizSdmsPersonalQuickLinkConfig> bizSdmsPersonalQuickLinkConfigs = new ArrayList<>(bizSdmsPersonalQuickLinkConfigDOS.size());
        BeanUtils.copyListProperties(bizSdmsPersonalQuickLinkConfigDOS, bizSdmsPersonalQuickLinkConfigs, BizSdmsPersonalQuickLinkConfig.class);
        return bizSdmsPersonalQuickLinkConfigs;
    }

    /**
     * 新增BizSdmsPersonalQuickLinkConfig
     *
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    @Override
    public int insertBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        bizSdmsPersonalQuickLinkConfig.setCreateTime(DateUtils.getNowDate());
        BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsPersonalQuickLinkConfig, bizSdmsPersonalQuickLinkConfigDO);
        return bizSdmsPersonalQuickLinkConfigManager.insertBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfigDO);
    }

    /**
     * 修改BizSdmsPersonalQuickLinkConfig
     *
     * @param bizSdmsPersonalQuickLinkConfig BizSdmsPersonalQuickLinkConfig
     * @return 结果
     */
    @Override
    public int updateBizSdmsPersonalQuickLinkConfig(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        bizSdmsPersonalQuickLinkConfig.setUpdateTime(DateUtils.getNowDate());
        BizSdmsPersonalQuickLinkConfigDO bizSdmsPersonalQuickLinkConfigDO = new BizSdmsPersonalQuickLinkConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsPersonalQuickLinkConfig, bizSdmsPersonalQuickLinkConfigDO);
        return bizSdmsPersonalQuickLinkConfigManager.updateBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfigDO);
    }

    /**
     * 批量删除BizSdmsPersonalQuickLinkConfig
     *
     * @param quickLinkConfigIds 需要删除的BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(Long[] quickLinkConfigIds) {
        return bizSdmsPersonalQuickLinkConfigManager.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(quickLinkConfigIds);
    }

    /**
     * 删除BizSdmsPersonalQuickLinkConfig信息
     *
     * @param quickLinkConfigId BizSdmsPersonalQuickLinkConfig主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(Long quickLinkConfigId) {
        return bizSdmsPersonalQuickLinkConfigManager.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(quickLinkConfigId);
    }
}
