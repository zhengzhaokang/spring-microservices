package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.common.EntityUtil;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessOperatorLogDO;
import com.microservices.otmp.system.manager.ISysBusinessOperatorLogManager;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysBusinessOperatorLogService业务层处理
 * 
 * @author lovefamily
 * @date 2022-08-18
 */
@Service
public class SysBusinessOperatorLogServiceImpl implements ISysBusinessOperatorLogService
{
    @Autowired
    private ISysBusinessOperatorLogManager sysBusinessOperatorLogManager;

    /**
     * 查询SysBusinessOperatorLog
     * 
     * @param id SysBusinessOperatorLog主键
     * @return SysBusinessOperatorLogDTO
     */
    @Override
    public SysBusinessOperatorLogDTO selectSysBusinessOperatorLogById(Long id)
    {
         SysBusinessOperatorLogDO sysBusinessOperatorLogDO =  sysBusinessOperatorLogManager.selectSysBusinessOperatorLogById(id);
         SysBusinessOperatorLogDTO sysBusinessOperatorLogDTO = new SysBusinessOperatorLogDTO();
        org.springframework.beans. BeanUtils.copyProperties(sysBusinessOperatorLogDO, sysBusinessOperatorLogDTO);
        return sysBusinessOperatorLogDTO;
    }

    /**
     * 查询SysBusinessOperatorLog列表
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return SysBusinessOperatorLogDTO
     */
    @Override
    public PageInfo<SysBusinessOperatorLogDTO> selectSysBusinessOperatorLogList(SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        //去空格
        EntityUtil.objectToTrim(sysBusinessOperatorLog);
        List<SysBusinessOperatorLogDO> sysBusinessOperatorLogDOS =
                    sysBusinessOperatorLogManager.selectSysBusinessOperatorLogList(sysBusinessOperatorLog);
        List<SysBusinessOperatorLogDTO> sysBusinessOperatorLogList = new ArrayList<>(sysBusinessOperatorLogDOS.size());
        BeanUtils.copyListProperties(sysBusinessOperatorLogDOS, sysBusinessOperatorLogList, SysBusinessOperatorLogDTO.class);
        PageInfo<SysBusinessOperatorLogDO> pageInfo = new PageInfo<>(sysBusinessOperatorLogDOS);
        PageInfo<SysBusinessOperatorLogDTO> resultPageInfo = new PageInfo<>(sysBusinessOperatorLogList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;

    }

    /**
     * 新增SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return 结果
     */
    @Override
    public int insertSysBusinessOperatorLog(SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        sysBusinessOperatorLog.setCreateTime(DateUtils.getNowDate());
        SysBusinessOperatorLogDO sysBusinessOperatorLogDO =new  SysBusinessOperatorLogDO();
        org.springframework.beans.BeanUtils.copyProperties(sysBusinessOperatorLog, sysBusinessOperatorLogDO);
        return sysBusinessOperatorLogManager.insertSysBusinessOperatorLog(sysBusinessOperatorLogDO);
    }

    /**
     * 修改SysBusinessOperatorLog
     * 
     * @param sysBusinessOperatorLogDTO SysBusinessOperatorLog
     * @return 结果
     */
    @Override
    public int updateSysBusinessOperatorLog(SysBusinessOperatorLogDTO sysBusinessOperatorLog)
    {
        sysBusinessOperatorLog.setUpdateTime(DateUtils.getNowDate());
       SysBusinessOperatorLogDO sysBusinessOperatorLogDO =new  SysBusinessOperatorLogDO();
        org.springframework.beans.BeanUtils.copyProperties(sysBusinessOperatorLog, sysBusinessOperatorLogDO);
        return sysBusinessOperatorLogManager.updateSysBusinessOperatorLog(sysBusinessOperatorLogDO);
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
        return sysBusinessOperatorLogManager.deleteSysBusinessOperatorLogByIds(ids);
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
        return sysBusinessOperatorLogManager.deleteSysBusinessOperatorLogById(id);
    }

    @Override
    public int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter) {
        return sysBusinessOperatorLogManager.deleteSysBusinessOperatorLogByOperationDate(startQuarter,  lastQuarter);
    }
}
