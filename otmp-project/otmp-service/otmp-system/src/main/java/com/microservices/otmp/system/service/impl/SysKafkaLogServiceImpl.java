package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysKafkaLog;
import com.microservices.otmp.system.domain.entity.SysKafkaLogDO;
import com.microservices.otmp.system.manager.SysKafkaLogManager;
import com.microservices.otmp.system.service.ISysKafkaLogService;
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
public class SysKafkaLogServiceImpl implements ISysKafkaLogService {
    @Autowired
    private SysKafkaLogManager kafkaLogManager;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertKafkalog(SysKafkaLog operLog) {
        SysKafkaLogDO sysKafkaLogDO = new SysKafkaLogDO();
        org.springframework.beans.BeanUtils.copyProperties(operLog, sysKafkaLogDO);
        kafkaLogManager.insertKafkalog(sysKafkaLogDO);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<SysKafkaLog> selectKafkaLogList(SysKafkaLog operLog) {
        List<SysKafkaLogDO> sysKafkaLogDOS = kafkaLogManager.selectKafkaLogList(operLog);
        List<SysKafkaLog> sysKafkaLogs = new ArrayList<>(sysKafkaLogDOS.size());
        BeanUtils.copyListProperties(sysKafkaLogDOS, sysKafkaLogs, SysKafkaLog.class);
        return sysKafkaLogs;
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteKafkaLogByIds(String ids) {
        return kafkaLogManager.deleteKafkaLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public SysKafkaLog selectKafkaLogById(Long operId) {
        SysKafkaLogDO sysKafkaLogDO = kafkaLogManager.selectKafkaLogById(operId);
        SysKafkaLog sysKafkaLog = new SysKafkaLog();
        org.springframework.beans.BeanUtils.copyProperties(sysKafkaLogDO, sysKafkaLog);
        return sysKafkaLog;
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanKafkaLog() {
        kafkaLogManager.cleanKafkaLog();
    }

    @Override
    public PageInfo<SysKafkaLog> selectKafkaLogPage(SysKafkaLog operLog) {
        List<SysKafkaLogDO> sysKafkaLogDOS = kafkaLogManager.selectKafkaLogList(operLog);
        PageInfo<SysKafkaLogDO> pageInfo = new PageInfo<>(sysKafkaLogDOS);
        List<SysKafkaLog> sysKafkaLogs = new ArrayList<>(sysKafkaLogDOS.size());
        BeanUtils.copyListProperties(sysKafkaLogDOS, sysKafkaLogs, SysKafkaLog.class);
        PageInfo<SysKafkaLog> resultPageInfo = new PageInfo<>(sysKafkaLogs);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }
}
