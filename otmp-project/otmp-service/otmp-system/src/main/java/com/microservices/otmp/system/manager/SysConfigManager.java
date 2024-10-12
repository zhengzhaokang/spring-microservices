package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.entity.SysConfigDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:20
 */
public interface SysConfigManager {
    /**
     * 查询参数配置信息
     *
     * @param sysConfigDO 参数配置信息
     * @return 参数配置信息
     */
    public SysConfigDO selectConfig(SysConfigDO sysConfigDO);

    /**
     * 查询参数配置列表
     *
     * @param sysConfigDO 参数配置信息
     * @return 参数配置集合
     */
    public List<SysConfigDO> selectConfigList(SysConfigDO sysConfigDO);

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    public SysConfigDO checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     *
     * @param sysConfigDO 参数配置信息
     * @return 结果
     */
    public int insertConfig(SysConfigDO sysConfigDO);

    /**
     * 修改参数配置
     *
     * @param sysConfigDO 参数配置信息
     * @return 结果
     */
    public int updateConfig(SysConfigDO sysConfigDO);

    /**
     * 批量删除参数配置
     *
     * @param configIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteConfigByIds(String[] configIds);
}
