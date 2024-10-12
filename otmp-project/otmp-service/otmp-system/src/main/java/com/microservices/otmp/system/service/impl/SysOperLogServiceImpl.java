package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.entity.SysOperLogDO;
import com.microservices.otmp.system.manager.SysOperLogManager;
import com.microservices.otmp.system.service.ISysOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author lovefamily
 */
@Service
public class SysOperLogServiceImpl implements ISysOperLogService {
    @Autowired
    private SysOperLogManager operLogManager;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(SysOperLog operLog) {
        SysOperLogDO sysOperLogDO = new SysOperLogDO();
        org.springframework.beans.BeanUtils.copyProperties(operLog, sysOperLogDO);
        operLogManager.insertOperlog(sysOperLogDO);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysOperLog> selectOperLogList(SysOperLog operLog) {
        List<SysOperLogDO> sysOperLogDOS = operLogManager.selectOperLogList(operLog);
        List<SysOperLog> sysOperLogs = new ArrayList<>(sysOperLogDOS.size());
        BeanUtils.copyListProperties(sysOperLogDOS, sysOperLogs, SysOperLog.class);
        return sysOperLogs;
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids) {
        return operLogManager.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysOperLog selectOperLogById(Long operId) {
        SysOperLogDO sysOperLogDO = operLogManager.selectOperLogById(operId);
        SysOperLog sysOperLog = new SysOperLog();
        org.springframework.beans.BeanUtils.copyProperties(sysOperLogDO, sysOperLog);
        return sysOperLog;
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogManager.cleanOperLog();
    }

    @Override
    public PageInfo<SysOperLog> selectOperLogPage(SysOperLog operLog) {
        List<SysOperLogDO> sysOperLogDOS = operLogManager.selectOperLogList(operLog);
        PageInfo<SysOperLogDO> pageInfo = new PageInfo<>(sysOperLogDOS);
        List<SysOperLog> sysOperLogs = new ArrayList<>(sysOperLogDOS.size());
        BeanUtils.copyListProperties(sysOperLogDOS, sysOperLogs, SysOperLog.class);
        PageInfo<SysOperLog> resultPageInfo = new PageInfo<>(sysOperLogs);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }
}
