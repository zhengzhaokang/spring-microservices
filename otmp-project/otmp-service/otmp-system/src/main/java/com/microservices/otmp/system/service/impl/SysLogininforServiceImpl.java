package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysLogininfor;
import com.microservices.otmp.system.domain.entity.SysLogininforDO;
import com.microservices.otmp.system.manager.SysLogininforManager;
import com.microservices.otmp.system.service.ISysLogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统访问日志情况信息 服务层处理
 *
 * @author lovefamily
 */
@Service
public class SysLogininforServiceImpl implements ISysLogininforService {

    @Autowired
    private SysLogininforManager sysLogininforManager;

    /**
     * 新增系统登录日志
     *
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(SysLogininfor logininfor) {
        SysLogininforDO sysLogininforDO = new SysLogininforDO();
        org.springframework.beans.BeanUtils.copyProperties(logininfor, sysLogininforDO);
        sysLogininforManager.insertLogininfor(sysLogininforDO);
    }

    /**
     * 查询系统登录日志集合
     *
     * @param logininfor 访问日志对象
     * @return 登录记录集合
     */
    @Override
    public List<SysLogininfor> selectLogininforList(SysLogininfor logininfor) {
        SysLogininforDO sysLogininforDO = new SysLogininforDO();
        org.springframework.beans.BeanUtils.copyProperties(logininfor, sysLogininforDO);
        List<SysLogininforDO> sysLogininforDOS = sysLogininforManager.selectLogininforList(sysLogininforDO);
        List<SysLogininfor> sysLogininfors = new ArrayList<>();
        BeanUtils.copyListProperties(sysLogininforDOS, sysLogininfors, SysLogininfor.class);
        return sysLogininfors;
    }

    /**
     * 批量删除系统登录日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteLogininforByIds(String ids) {
        return sysLogininforManager.deleteLogininforByIds(Convert.toStrArray(ids));
    }

    /**
     * 清空系统登录日志
     */
    @Override
    public void cleanLogininfor() {
        sysLogininforManager.cleanLogininfor();
    }

    @Override
    public PageInfo<SysLogininfor> selectLogininforPage(SysLogininfor logininfor) {
        SysLogininforDO sysLogininforDO = new SysLogininforDO();
        org.springframework.beans.BeanUtils.copyProperties(logininfor, sysLogininforDO);
        List<SysLogininforDO> sysLogininforDOS = sysLogininforManager.selectLogininforList(sysLogininforDO);
        PageInfo<SysLogininforDO> pageInfo = new PageInfo<>(sysLogininforDOS);
        List<SysLogininfor> sysLogininfors = new ArrayList<>();
        BeanUtils.copyListProperties(sysLogininforDOS, sysLogininfors, SysLogininfor.class);
        PageInfo<SysLogininfor> resultPageInfo = new PageInfo<>(sysLogininfors);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;
    }
}
