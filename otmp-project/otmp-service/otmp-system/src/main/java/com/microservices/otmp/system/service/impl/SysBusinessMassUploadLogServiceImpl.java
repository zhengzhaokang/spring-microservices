package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.common.EntityUtil;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.entity.SysBusinessMassUploadLogDO;
import com.microservices.otmp.system.manager.ISysBusinessMassUploadLogManager;
import com.microservices.otmp.system.service.ISysBusinessMassUploadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SysBusinessMassUploadLogService业务层处理
 *
 * @author lovefamily
 * @date 2022-08-19
 */
@Service
public class SysBusinessMassUploadLogServiceImpl implements ISysBusinessMassUploadLogService {
    @Autowired
    private ISysBusinessMassUploadLogManager sysBusinessMassUploadLogManager;

    /**
     * 查询SysBusinessMassUploadLog
     *
     * @param id SysBusinessMassUploadLog主键
     * @return SysBusinessMassUploadLogDTO
     */
    @Override
    public SysBusinessMassUploadLogDTO selectSysBusinessMassUploadLogById(Long id) {
        SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO = sysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogById(id);
        SysBusinessMassUploadLogDTO sysBusinessMassUploadLogDTO = new SysBusinessMassUploadLogDTO();
        org.springframework.beans. BeanUtils.copyProperties(sysBusinessMassUploadLogDO, sysBusinessMassUploadLogDTO);
        return sysBusinessMassUploadLogDTO;
    }

    /**
     * 查询SysBusinessMassUploadLog列表
     *
     * @param sysBusinessMassUploadLog SysBusinessMassUploadLog
     * @return SysBusinessMassUploadLogDTO
     */
    @Override
    public PageInfo<SysBusinessMassUploadLogDTO> selectSysBusinessMassUploadLogList(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        //去空格
        EntityUtil.objectToTrim(sysBusinessMassUploadLog);
        List<SysBusinessMassUploadLogDO> sysBusinessMassUploadLogDOS =
                sysBusinessMassUploadLogManager.selectSysBusinessMassUploadLogList(sysBusinessMassUploadLog);
        List<SysBusinessMassUploadLogDTO> sysBusinessMassUploadLogList = new ArrayList<>(sysBusinessMassUploadLogDOS.size());
        BeanUtils.copyListProperties(sysBusinessMassUploadLogDOS, sysBusinessMassUploadLogList, SysBusinessMassUploadLogDTO.class);
        PageInfo<SysBusinessMassUploadLogDO> pageInfo = new PageInfo<>(sysBusinessMassUploadLogDOS);
        PageInfo<SysBusinessMassUploadLogDTO> resultPageInfo = new PageInfo<>(sysBusinessMassUploadLogList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }

    /**
     * 新增SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLogDTO SysBusinessMassUploadLog
     * @return 结果
     */
    @Override
    public int insertSysBusinessMassUploadLog(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        sysBusinessMassUploadLog.setCreateTime(DateUtils.getNowDate());
        SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO = new SysBusinessMassUploadLogDO();
        org.springframework.beans.BeanUtils.copyProperties(sysBusinessMassUploadLog, sysBusinessMassUploadLogDO);
        int num = sysBusinessMassUploadLogManager.insertSysBusinessMassUploadLog(sysBusinessMassUploadLogDO);
        sysBusinessMassUploadLog.setId(sysBusinessMassUploadLogDO.getId());
        return num;
    }

    /**
     * 修改SysBusinessMassUploadLog
     *
     * @param sysBusinessMassUploadLogDTO SysBusinessMassUploadLog
     * @return 结果
     */
    @Override
    public int updateSysBusinessMassUploadLog(SysBusinessMassUploadLogDTO sysBusinessMassUploadLog) {
        sysBusinessMassUploadLog.setUpdateTime(DateUtils.getNowDate());
        SysBusinessMassUploadLogDO sysBusinessMassUploadLogDO = new SysBusinessMassUploadLogDO();
        org.springframework.beans.BeanUtils.copyProperties(sysBusinessMassUploadLog, sysBusinessMassUploadLogDO);
        return sysBusinessMassUploadLogManager.updateSysBusinessMassUploadLog(sysBusinessMassUploadLogDO);
    }

    /**
     * 批量删除SysBusinessMassUploadLog
     *
     * @param ids 需要删除的SysBusinessMassUploadLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessMassUploadLogByIds(Long[] ids) {
        return sysBusinessMassUploadLogManager.deleteSysBusinessMassUploadLogByIds(ids);
    }

    /**
     * 删除SysBusinessMassUploadLog信息
     *
     * @param id SysBusinessMassUploadLog主键
     * @return 结果
     */
    @Override
    public int deleteSysBusinessMassUploadLogById(Long id) {
        return sysBusinessMassUploadLogManager.deleteSysBusinessMassUploadLogById(id);
    }

    @Override
    public int deleteSysBusinessOperatorLogByOperationDate(Date startQuarter, Date lastQuarter) {
        return sysBusinessMassUploadLogManager.deleteSysBusinessOperatorLogByOperationDate(startQuarter, lastQuarter);
    }
}
