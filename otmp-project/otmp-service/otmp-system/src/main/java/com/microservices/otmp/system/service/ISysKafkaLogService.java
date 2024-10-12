package com.microservices.otmp.system.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.SysKafkaLog;

import java.util.List;

/**
 * 操作日志 服务层
 * 
 * @author lovefamily
 */
public interface ISysKafkaLogService
{
    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    public void insertKafkalog(SysKafkaLog operLog);

    /**
     * 查询系统操作日志集合
     * 
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    public List<SysKafkaLog> selectKafkaLogList(SysKafkaLog operLog);

    /**
     * 批量删除系统操作日志
     * 
     * @param ids 需要删除的数据
     * @return 结果
     */
    public int deleteKafkaLogByIds(String ids);

    /**
     * 查询操作日志详细
     * 
     * @param operId 操作ID
     * @return 操作日志对象
     */
    public SysKafkaLog selectKafkaLogById(Long operId);

    /**
     * 清空操作日志
     */
    public void cleanKafkaLog();

    /**
     * 分页查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    PageInfo<SysKafkaLog> selectKafkaLogPage(SysKafkaLog operLog);
}
