package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysConfig;
import com.microservices.otmp.system.domain.entity.SysConfigDO;
import com.microservices.otmp.system.manager.SysConfigManager;
import com.microservices.otmp.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 参数配置 服务层实现
 *
 * @author lovefamily
 */
@Service
public class SysConfigServiceImpl implements ISysConfigService {
    @Autowired
    private SysConfigManager sysConfigManager;

    @Autowired
    private RedisUtils redis;

    private static final String SYS_CONFIG_DATA = "SYS_CONFIG_DATA";

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    @Override
    public SysConfig selectConfigById(Long configId) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigId(configId);
        SysConfigDO configDO = sysConfigManager.selectConfig(sysConfigDO);
        SysConfig config = new SysConfig();
        org.springframework.beans.BeanUtils.copyProperties(configDO, config);
        return config;
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Override
    public String selectConfigByKey(String configKey) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        sysConfigDO.setConfigKey(configKey);
        SysConfigDO configDO = sysConfigManager.selectConfig(sysConfigDO);
        return StringUtils.isNotNull(configDO) ? configDO.getConfigValue() : "";
    }

    /**
     * 查询参数配置列表
     *
     * @param sysConfig 参数配置信息
     * @return 参数配置集合
     */
    @Override
    public List<SysConfig> selectConfigList(SysConfig sysConfig) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(sysConfig, sysConfigDO);
        List<SysConfigDO> sysConfigDOS = sysConfigManager.selectConfigList(sysConfigDO);
        List<SysConfig> sysConfigs = new ArrayList<>(sysConfigDOS.size());
        for (SysConfigDO configDO : sysConfigDOS) {
            SysConfig config = new SysConfig();
            org.springframework.beans.BeanUtils.copyProperties(configDO, config);
            sysConfigs.add(config);
        }
        return sysConfigs;
    }

    /**
     * 新增参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int insertConfig(SysConfig config) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(config, sysConfigDO);
        int ret = sysConfigManager.insertConfig(sysConfigDO);
        changeRedisConfigData();
        return ret;
    }

    /**
     * 修改参数配置
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public int updateConfig(SysConfig config) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(config, sysConfigDO);
        int ret = sysConfigManager.updateConfig(sysConfigDO);
        changeRedisConfigData();
        return ret;
    }

    /**
     * 批量删除参数配置对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteConfigByIds(String ids) {
        int ret = sysConfigManager.deleteConfigByIds(Convert.toStrArray(ids));
        changeRedisConfigData();
        return ret;
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    @Override
    public String checkConfigKeyUnique(SysConfig config) {
        Long configId = StringUtils.isNull(config.getConfigId()) ? -1L : config.getConfigId();
        SysConfigDO sysConfigDO = sysConfigManager.checkConfigKeyUnique(config.getConfigKey());
        if (StringUtils.isNotNull(sysConfigDO) && sysConfigDO.getConfigId().longValue() != configId.longValue()) {
            return UserConstants.CONFIG_KEY_NOT_UNIQUE;
        }
        return UserConstants.CONFIG_KEY_UNIQUE;
    }

    /* (non-Javadoc)
     * @see com.microservices.otmp.system.service.ISysConfigService#updateValueByKey(java.lang.String, java.lang.String)
     */
    @Override
    public int updateValueByKey(String key, String configValue) {
        SysConfigDO info = sysConfigManager.checkConfigKeyUnique(key);
        if (StringUtils.isNotNull(info)) {
            info.setConfigValue(configValue);
            int ret = sysConfigManager.updateConfig(info);
            changeRedisConfigData();
            return ret;
        }
        return 0;
    }

    @Override
    public PageInfo<SysConfig> selectConfigPage(SysConfig sysConfig) {
        SysConfigDO sysConfigDO = new SysConfigDO();
        org.springframework.beans.BeanUtils.copyProperties(sysConfig, sysConfigDO);
        List<SysConfigDO> sysConfigDOS = sysConfigManager.selectConfigList(sysConfigDO);
        PageInfo<SysConfigDO> pageInfo = new PageInfo<>(sysConfigDOS);
        List<SysConfig> sysConfigs = new ArrayList<>(sysConfigDOS.size());
        BeanUtils.copyListProperties(sysConfigDOS, sysConfigs, SysConfig.class);
        PageInfo<SysConfig> resultPageInfo = new PageInfo<>(sysConfigs);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * SYS_CONFIG_DATA存入redis
     */
    private void changeRedisConfigData() {

        List<SysConfig> configList = selectConfigList(new SysConfig());
        redis.set(SYS_CONFIG_DATA, configList);
        configList.stream().forEach(config -> redis.set(config.getConfigKey(), config.getConfigValue(), RedisConstants.REDIS_EXPIRE_TIME_FOREVER));
    }
}
