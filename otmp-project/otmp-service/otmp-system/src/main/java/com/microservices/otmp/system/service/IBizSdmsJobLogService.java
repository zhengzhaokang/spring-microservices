package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;

import java.util.List;

/**
 * jobMonitorService接口
 * 
 * @author dhc
 * @date 2022-10-11
 */
public interface IBizSdmsJobLogService
{
    /**
     * 查询jobMonitor
     * 
     * @param id jobMonitor主键
     * @return jobMonitorDTO
     */
    public BizSdmsJobLogDTO selectBizSdmsJobLogById(Long id);

    /**
     * 查询jobMonitor列表
     *
     * @param bizSdmsJobLog jobMonitor
     * @return jobMonitorDTO集合
     */
    public List<BizSdmsJobLogDTO> selectBizSdmsJobLogList(BizSdmsJobLogDTO bizSdmsJobLog);

    /**
     * 新增jobMonitor
     * 
     * @param bizSdmsJobLogDTO jobMonitor
     * @return 结果
     */
    public int insertBizSdmsJobLog(BizSdmsJobLogDTO bizSdmsJobLog);

    /**
     * 修改jobMonitor
     * 
     * @param bizSdmsJobLogDTO jobMonitor
     * @return 结果
     */
    public int updateBizSdmsJobLog(BizSdmsJobLogDTO bizSdmsJobLog);

    /**
     * 批量删除jobMonitor
     * 
     * @param ids 需要删除的jobMonitor主键集合
     * @return 结果
     */
    public int deleteBizSdmsJobLogByIds(Long[] ids);

    /**
     * 删除jobMonitor信息
     * 
     * @param id jobMonitor主键
     * @return 结果
     */
    public int deleteBizSdmsJobLogById(Long id);
}
