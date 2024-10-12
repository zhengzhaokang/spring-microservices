package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.entity.SysOperLogDO;

import java.util.List;

/**
 * @author guowb1
 * @date 2022/6/10 17:21
 */
public interface SysOperLogManager {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    public void insertOperlog(SysOperLogDO operLog);

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysOperLogDO> selectOperLogList(SysOperLog operLog);

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteOperLogByIds(String[] ids);

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysOperLogDO selectOperLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanOperLog();
}
