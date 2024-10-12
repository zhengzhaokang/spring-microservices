package com.microservices.otmp.system.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * SysBusinessMassUploadLogMapper接口
 * 
 * @author lovefamily
 * @date 2022-08-19
 */
public interface SysBusinessMassUploadLogMapper
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
     * @param sysBusinessMassUploadLogDTO SysBusinessMassUploadLog
     * @return SysBusinessMassUploadLog集合
     */
    @DataPermissions
    public List<SysBusinessMassUploadLogDO> selectSysBusinessMassUploadLogList(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog);

    /**
     * 新增SysBusinessMassUploadLog
     * 
     * @param sysBusinessMassUploadLogDO SysBusinessMassUploadLog
     * @return 结果
     */
    public int insertSysBusinessMassUploadLog(SysBusinessMassUploadLogDO sysBusinessMassUploadLog);

    /**
     * 修改SysBusinessMassUploadLog
     * 
     * @param sysBusinessMassUploadLogDO SysBusinessMassUploadLog
     * @return 结果
     */
    public int updateSysBusinessMassUploadLog (SysBusinessMassUploadLogDO sysBusinessMassUploadLog);

    /**
     * 删除SysBusinessMassUploadLog
     * 
     * @param id SysBusinessMassUploadLog主键
     * @return 结果
     */
    public int deleteSysBusinessMassUploadLogById(Long id);

    /**
     * 批量删除SysBusinessMassUploadLog
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBusinessMassUploadLogByIds(Long[] ids);

    int deleteSysBusinessOperatorLogByOperationDate(@Param("startQuarter") Date startQuarter, @Param("lastQuarter")Date lastQuarter);
}
