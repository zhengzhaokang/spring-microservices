package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.dto.SysOssConfigDTO;
import com.microservices.otmp.system.domain.entity.SysOssConfigDO;
import com.microservices.otmp.system.manager.ISysOssConfigManager;
import com.microservices.otmp.system.mapper.SysOssConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * SysOssConfigManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-04
 */
@Service
public class SysOssConfigManagerImpl implements ISysOssConfigManager
{
    @Autowired
    private SysOssConfigMapper sysOssConfigMapper;

    /**
     * 查询SysOssConfig
     * 
     * @param ossConfigId SysOssConfig主键
     * @return SysOssConfigDO
     */
    @Override
    public SysOssConfigDO selectSysOssConfigByOssConfigId(Long ossConfigId)
    {
        return sysOssConfigMapper.selectSysOssConfigByOssConfigId(ossConfigId);
    }

    /**
     * 查询SysOssConfig列表
     * 
     * @param sysOssConfigDTO SysOssConfig
     * @return SysOssConfigDO
     */
    @Override
    public List<SysOssConfigDO> selectSysOssConfigList(SysOssConfigDTO sysOssConfig)
    {
        return sysOssConfigMapper.selectSysOssConfigList(sysOssConfig);
    }

    /**
     * 新增SysOssConfig
     * 
     * @param sysOssConfigDO SysOssConfig
     * @return 结果
     */
    @Override
    public int insertSysOssConfig(SysOssConfigDO sysOssConfig)
    {
        sysOssConfig.setCreateTime(DateUtils.getNowDate());
        return sysOssConfigMapper.insertSysOssConfig (sysOssConfig);
    }

    /**
     * 修改SysOssConfig
     * 
     * @param sysOssConfigDO  SysOssConfig
     * @return 结果
     */
    @Override
    public int updateSysOssConfig(SysOssConfigDO sysOssConfig)
    {
        sysOssConfig.setUpdateTime(DateUtils.getNowDate());
        return sysOssConfigMapper.updateSysOssConfig (sysOssConfig);
    }

    /**
     * 批量删除SysOssConfig
     * 
     * @param ossConfigIds 需要删除的SysOssConfig主键
     * @return 结果
     */
    @Override
    public int deleteSysOssConfigByOssConfigIds(Long[] ossConfigIds)
    {
        return sysOssConfigMapper.deleteSysOssConfigByOssConfigIds(ossConfigIds);
    }

    /**
     * 删除SysOssConfig信息
     * 
     * @param ossConfigId SysOssConfig主键
     * @return 结果
     */
    @Override
    public int deleteSysOssConfigByOssConfigId(Long ossConfigId)
    {
        return sysOssConfigMapper.deleteSysOssConfigByOssConfigId(ossConfigId);
    }
}
