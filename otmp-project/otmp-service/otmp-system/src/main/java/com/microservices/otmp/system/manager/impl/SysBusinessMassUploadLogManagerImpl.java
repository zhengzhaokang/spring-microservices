package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;
import com.microservices.otmp.system.manager.ISysBusinessMassUploadLogManager;
import com.microservices.otmp.system.mapper.SysBusinessMassUploadLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * SysBusinessMassUploadLogManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-19
 */
@Service
public class SysBusinessMassUploadLogManagerImpl implements ISysBusinessMassUploadLogManager
{
    @Autowired
    private SysBusinessMassUploadLogMapper sysBusinessMassUploadLogMapper;

    /**
     * 查询SysBusinessMassUploadLog
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return SysBusinessMassUploadLogDO
     */
    @Override
    public SysBusinessMassUploadLogDO selectSysBusinessMassUploadLogById(Long id)
    {
        return sysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogById(id);
    }

    /**
     * 查询SysBusinessMassUploadLog列表
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return SysBusinessMassUploadLogDO
     */
    @Override
    public List<SysBusinessMassUploadLogDO> selectSysBusinessMassUploadLogList(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog)
    {
        return sysBusinessMassUploadLogMapper.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);
    }

    /**
     * 新增SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return 结果
     */
    @Override
    public int insertSysBusinessMassUploadLog(SysBusinessMassUploadLogDO sysBusinessMassUploadLog)
    {
        sysBusinessMassUploadLog.setCreateTime(DateUtils.getNowDate());
        return sysBusinessMassUploadLogMapper.insertSysBusinessMassUploadLog (sysBusinessMassUploadLog);
    }

    /**
     * 修改SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLog  SysBusinessMassUploadLog
     * @return 结果
     */
    @Override
    public int updateSysBusinessMassUploadLog(SysBusinessMassUploadLogDO sysBusinessMassUploadLog)
    {
        sysBusinessMassUploadLog.setUpdateTime(DateUtils.getNowDate());
        return sysBusinessMassUploadLogMapper.updateSysBusinessMassUploadLog (sysBusinessMassUploadLog);
    }

    /**
     * 批量删除SysBusinessMassUploadLog
     * 
     * @param ids 需要删除的SysBusinessMassUploadLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessMassUploadLogByIds(Long[] ids)
    {
        return sysBusinessMassUploadLogMapper.deleteSysBusinessMassUploadLogByIds(ids);
    }

    /**
     * 删除SysBusinessMassUploadLog信息
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessMassUploadLogById(Long id)
    {
        return sysBusinessMassUploadLogMapper.deleteSysBusinessMassUploadLogById(id);
    }

    @Override
    public int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter) {
        return sysBusinessMassUploadLogMapper.deleteSysBusinessOperatorLogByOperationDate( startQuarter, lastQuarter);
    }
}
