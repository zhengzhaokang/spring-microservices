package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;

import java.util.List;


/**
 * jobMonitorMapper接口
 * 
 * @author dhc
 * @date 2022-10-11
 */
public interface BizSdmsJobLogMapper
{
    /**
     * 查询jobMonitor
     * 
     * @param id jobMonitor主键
     * @return jobMonitor
     */
    public BizSdmsJobLogDO selectBizSdmsJobLogById(Long id);

    /**
     * 查询jobMonitor列表
     * 
     * @param bizSdmsJobLogDTO jobMonitor
     * @return jobMonitor集合
     */
    public List<BizSdmsJobLogDO> selectBizSdmsJobLogList(BizSdmsJobLogDTO bizSdmsJobLog);

    /**
     * 新增jobMonitor
     * 
     * @param bizSdmsJobLogDO jobMonitor
     * @return 结果
     */
    public int insertBizSdmsJobLog(BizSdmsJobLogDO bizSdmsJobLog);

    /**
     * 修改jobMonitor
     * 
     * @param bizSdmsJobLogDO jobMonitor
     * @return 结果
     */
    public int updateBizSdmsJobLog (BizSdmsJobLogDO bizSdmsJobLog);

    /**
     * 删除jobMonitor
     * 
     * @param id jobMonitor主键
     * @return 结果
     */
    public int deleteBizSdmsJobLogById(Long id);

    /**
     * 批量删除jobMonitor
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsJobLogByIds(Long[] ids);
}
