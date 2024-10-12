package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;

import java.util.List;


/**
 * jobMonitorManager接口
 * 
 * @author dhc
 * @date 2022-10-11
 */
public interface IBizSdmsJobLogManager
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
     * @param bizSdmsJobLog jobMonitor
     * @return jobMonitor集合
     */
    public List<BizSdmsJobLogDO> selectBizSdmsJobLogList(BizSdmsJobLogDTO bizSdmsJobLog);

    /**
     * 新增jobMonitor
     *
     * @param bizSdmsJobLog jobMonitor
     * @return 结果
     */
    public int insertBizSdmsJobLog(BizSdmsJobLogDO bizSdmsJobLog);

    /**
     * 修改jobMonitor
     *
     * @param bizSdmsJobLog jobMonitor
     * @return 结果
     */
    public int updateBizSdmsJobLog(BizSdmsJobLogDO bizSdmsJobLog);

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
