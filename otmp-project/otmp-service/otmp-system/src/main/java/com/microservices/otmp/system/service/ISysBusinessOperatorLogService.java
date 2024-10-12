package com.microservices.otmp.system.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;

import java.util.Date;

/**
 * SysBusinessOperatorLogService接口
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public interface ISysBusinessOperatorLogService
{
    /**
     * 查询SysBusinessOperatorLog
     * 
     * @param id SysBusinessOperatorLog主键
     * @return SysBusinessOperatorLogDTO
     */
    public SysBusinessOperatorLogDTO selectSysBusinessOperatorLogById(Long id);

    /**
     * 查询SysBusinessOperatorLog列表
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return SysBusinessOperatorLogDTO集合
     */
    public PageInfo<SysBusinessOperatorLogDTO> selectSysBusinessOperatorLogList(SysBusinessOperatorLogDTO sysBusinessOperatorLog);

    /**
     * 新增SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return 结果
     */
    public int insertSysBusinessOperatorLog(SysBusinessOperatorLogDTO sysBusinessOperatorLog);

    /**
     * 修改SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return 结果
     */
    public int updateSysBusinessOperatorLog(SysBusinessOperatorLogDTO sysBusinessOperatorLog);

    /**
     * 批量删除SysBusinessOperatorLog
     * 
     * @param ids 需要删除的SysBusinessOperatorLog主键集合
     * @return 结果
     */
    public int deleteSysBusinessOperatorLogByIds(Long[] ids);

    /**
     * 删除SysBusinessOperatorLog信息
     * 
     * @param id SysBusinessOperatorLog主键
     * @return 结果
     */
    public int deleteSysBusinessOperatorLogById(Long id);

    int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter);
}
