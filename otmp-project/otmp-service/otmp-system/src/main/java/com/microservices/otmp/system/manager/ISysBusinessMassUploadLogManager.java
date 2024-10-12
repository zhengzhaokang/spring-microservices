package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;

import java.util.Date;
import java.util.List;


/**
 * SysBusinessMassUploadLogManager接口
 * 
 * @author lovefamily
 * @date 2022-08-19
 */
public interface ISysBusinessMassUploadLogManager
{
    /**
     * 查询SysBusinessMassUploadLog
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return SysBusinessMassUploadLog
     */
    public SysBusinessMassUploadLogDO selectSysBusinessMassUploadLogById(Long id);

    /**
     * 查询SysBusinessMassUploadLog列表
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return SysBusinessMassUploadLog集合
     */
    public List<SysBusinessMassUploadLogDO> selectSysBusinessMassUploadLogList(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog);

    /**
     * 新增SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return 结果
     */
    public int insertSysBusinessMassUploadLog(SysBusinessMassUploadLogDO sysBusinessMassUploadLog);

    /**
     * 修改SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return 结果
     */
    public int updateSysBusinessMassUploadLog(SysBusinessMassUploadLogDO sysBusinessMassUploadLog);

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
