package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import com.microservices.otmp.system.manager.ISysBusinessOperatorLogManager;
import com.microservices.otmp.system.mapper.SysBusinessOperatorLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SysBusinessOperatorLogManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
@Service
public class SysBusinessOperatorLogManagerImpl implements ISysBusinessOperatorLogManager
{
    @Autowired
    private SysBusinessOperatorLogMapper sysBusinessOperatorLogMapper;

    /**
     * 查询SysBusinessOperatorLog
     * 
     * @param id SysBusinessOperatorLog主键
     * @return SysBusinessOperatorLogDO
     */
    @Override
    public SysBusinessOperatorLogDO selectSysBusinessOperatorLogById(Long id)
    {
        return sysBusinessOperatorLogMapper.selectSysBusinessOperatorLogById(id);
    }

    /**
     * 查询SysBusinessOperatorLog列表
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return SysBusinessOperatorLogDO
     */
    @Override
    public List<SysBusinessOperatorLogDO> selectSysBusinessOperatorLogList(SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        return sysBusinessOperatorLogMapper.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);
    }

    /**
     * 新增SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDO SysBusinessOperatorLog
     * @return 结果
     */
    @Override
    public int insertSysBusinessOperatorLog(SysBusinessOperatorLogDO sysBusinessOperatorLog)
    {
        sysBusinessOperatorLog.setCreateTime(DateUtils.getNowDate());
        return sysBusinessOperatorLogMapper.insertSysBusinessOperatorLog (sysBusinessOperatorLog);
    }

    /**
     * 修改SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDO  SysBusinessOperatorLog
     * @return 结果
     */
    @Override
    public int updateSysBusinessOperatorLog(SysBusinessOperatorLogDO sysBusinessOperatorLog)
    {
        sysBusinessOperatorLog.setUpdateTime(DateUtils.getNowDate());
        return sysBusinessOperatorLogMapper.updateSysBusinessOperatorLog (sysBusinessOperatorLog);
    }

    /**
     * 批量删除SysBusinessOperatorLog
     * 
     * @param ids 需要删除的SysBusinessOperatorLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessOperatorLogByIds(Long[] ids)
    {
        return sysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogByIds(ids);
    }

    /**
     * 删除SysBusinessOperatorLog信息
     * 
     * @param id SysBusinessOperatorLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessOperatorLogById(Long id)
    {
        return sysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogById(id);
    }

    @Override
    public int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter) {
        return sysBusinessOperatorLogMapper.deleteSysBusinessOperatorLogByOperationDate(startQuarter,  lastQuarter);
    }
}
