package com.microservices.otmp.system.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;

import java.util.Date;

/**
 * SysBusinessMassUploadLogService接口
 * 
 * @author lovefamily
 * @date 2022-08-19
 */
public interface ISysBusinessMassUploadLogService
{
    /**
     * 查询SysBusinessMassUploadLog
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return SysBusinessMassUploadLogDTO
     */
    public SysBusinessMassUploadLogDTO selectSysBusinessMassUploadLogById(Long id);

    /**
     * 查询SysBusinessMassUploadLog列表
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return SysBusinessMassUploadLogDTO集合
     */
    public PageInfo<SysBusinessMassUploadLogDTO> selectSysBusinessMassUploadLogList(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog);

    /**
     * 新增SysBusinessMassUploadLog
     * 
     * @param sysBusinessMassUploadLogDTO SysBusinessMassUploadLog
     * @return 结果
     */
    public int insertSysBusinessMassUploadLog(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog);

    /**
     * 修改SysBusinessMassUploadLog
     * 
     * @param sysBusinessMassUploadLogDTO SysBusinessMassUploadLog
     * @return 结果
     */
    public int updateSysBusinessMassUploadLog(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog);

    /**
     * 批量删除SysBusinessMassUploadLog
     * 
     * @param ids 需要删除的SysBusinessMassUploadLog主键集合
     * @return 结果
     */
    public int deleteSysBusinessMassUploadLogByIds(Long[] ids);

    /**
     * 删除SysBusinessMassUploadLog信息
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return 结果
     */
    public int deleteSysBusinessMassUploadLogById(Long id);

    int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter);
}
