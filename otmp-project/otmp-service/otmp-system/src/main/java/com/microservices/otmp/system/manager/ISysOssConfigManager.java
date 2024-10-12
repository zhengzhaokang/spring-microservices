package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.dto.SysOssConfigDTO;
import com.microservices.otmp.system.domain.entity.SysOssConfigDO;

import java.util.List;

/**
 * SysOssConfigManager接口
 * 
 * @author lovefamily
 * @date 2022-08-04
 */
public interface ISysOssConfigManager
{
    /**
     * 查询SysOssConfig
     * 
     * @param ossConfigId SysOssConfig主键
     * @return SysOssConfig
     */
    public SysOssConfigDO selectSysOssConfigByOssConfigId(Long ossConfigId);

    /**
     * 查询SysOssConfig列表
     * 
     * @param sysOssConfigDTO SysOssConfig
     * @return SysOssConfig集合
     */
    public List<SysOssConfigDO> selectSysOssConfigList(SysOssConfigDTO sysOssConfig);

    /**
     * 新增SysOssConfig
     * 
     * @param sysOssConfigDO SysOssConfig
     * @return 结果
     */
    public int insertSysOssConfig(SysOssConfigDO sysOssConfig);

    /**
     * 修改SysOssConfig
     * 
     * @param sysOssConfigDO SysOssConfig
     * @return 结果
     */
    public int updateSysOssConfig(SysOssConfigDO sysOssConfig);

    /**
     * 批量删除SysOssConfig
     * 
     * @param ossConfigIds 需要删除的SysOssConfig主键集合
     * @return 结果
     */
    public int deleteSysOssConfigByOssConfigIds(Long[] ossConfigIds);

    /**
     * 删除SysOssConfig信息
     * 
     * @param ossConfigId SysOssConfig主键
     * @return 结果
     */
    public int deleteSysOssConfigByOssConfigId(Long ossConfigId);
}
