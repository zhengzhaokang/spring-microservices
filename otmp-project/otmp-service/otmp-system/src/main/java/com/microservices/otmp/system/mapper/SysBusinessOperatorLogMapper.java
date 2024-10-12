package com.microservices.otmp.system.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


/**
 * SysBusinessOperatorLogMapper接口
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
public interface SysBusinessOperatorLogMapper
{
    /**
     * 查询SysBusinessOperatorLog
     * 
     * @param id SysBusinessOperatorLog主键
     * @return SysBusinessOperatorLog
     */
    public SysBusinessOperatorLogDO selectSysBusinessOperatorLogById(Long id);

    /**
     * 查询SysBusinessOperatorLog列表
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return SysBusinessOperatorLog集合
     */
    public List<SysBusinessOperatorLogDO> selectSysBusinessOperatorLogList(SysBusinessOperatorLogDTO sysBusinessOperatorLog);

    /**
     * 新增SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDO SysBusinessOperatorLog
     * @return 结果
     */
    public int insertSysBusinessOperatorLog(SysBusinessOperatorLogDO sysBusinessOperatorLog);

    /**
     * 修改SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDO SysBusinessOperatorLog
     * @return 结果
     */
    public int updateSysBusinessOperatorLog (SysBusinessOperatorLogDO sysBusinessOperatorLog);

    /**
     * 删除SysBusinessOperatorLog
     * 
     * @param id SysBusinessOperatorLog主键
     * @return 结果
     */
    public int deleteSysBusinessOperatorLogById(Long id);

    /**
     * 批量删除SysBusinessOperatorLog
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBusinessOperatorLogByIds(Long[] ids);

    int deleteSysBusinessOperatorLogByOperationDate(@Param("startQuarter") Date startQuarter, @Param("lastQuarter")Date lastQuarter);
}
