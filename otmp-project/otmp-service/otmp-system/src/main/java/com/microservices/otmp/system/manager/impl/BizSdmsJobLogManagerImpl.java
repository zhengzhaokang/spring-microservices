package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsJobLogDO;
import com.microservices.otmp.system.manager.IBizSdmsJobLogManager;
import com.microservices.otmp.system.mapper.BizSdmsJobLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * jobMonitorManager业务层处理
 * 
 * @author dhc
 * @date 2022-10-11
 */
@Service
public class BizSdmsJobLogManagerImpl implements IBizSdmsJobLogManager
{
    @Autowired
    private BizSdmsJobLogMapper bizSdmsJobLogMapper;

    /**
     * 查询jobMonitor
     * 
     * @param id jobMonitor主键
     * @return jobMonitorDO
     */
    @Override
    public BizSdmsJobLogDO selectBizSdmsJobLogById(Long id)
    {
        return bizSdmsJobLogMapper.selectBizSdmsJobLogById(id);
    }

    /**
     * 查询jobMonitor列表
     *
     * @param bizSdmsJobLog jobMonitor
     * @return jobMonitorDO
     */
    @Override
    public List<BizSdmsJobLogDO> selectBizSdmsJobLogList(BizSdmsJobLogDTO bizSdmsJobLog)
    {
        return bizSdmsJobLogMapper.selectBizSdmsJobLogList(bizSdmsJobLog);
    }

    /**
     * 新增jobMonitor
     *
     * @param bizSdmsJobLog jobMonitor
     * @return 结果
     */
    @Override
    public int insertBizSdmsJobLog(BizSdmsJobLogDO bizSdmsJobLog)
    {
        return bizSdmsJobLogMapper.insertBizSdmsJobLog (bizSdmsJobLog);
    }

    /**
     * 修改jobMonitor
     *
     * @param bizSdmsJobLog  jobMonitor
     * @return 结果
     */
    @Override
    public int updateBizSdmsJobLog(BizSdmsJobLogDO bizSdmsJobLog)
    {
        return bizSdmsJobLogMapper.updateBizSdmsJobLog (bizSdmsJobLog);
    }

    /**
     * 批量删除jobMonitor
     * 
     * @param ids 需要删除的jobMonitor主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsJobLogByIds(Long[] ids)
    {
        return bizSdmsJobLogMapper.deleteBizSdmsJobLogByIds(ids);
    }

    /**
     * 删除jobMonitor信息
     * 
     * @param id jobMonitor主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsJobLogById(Long id)
    {
        return bizSdmsJobLogMapper.deleteBizSdmsJobLogById(id);
    }
}
