package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysUserOnline;
import com.microservices.otmp.system.domain.entity.SysUserOnlineDO;
import com.microservices.otmp.system.manager.SysUserOnlineManager;
import com.microservices.otmp.system.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 在线用户 服务层处理
 *
 * @author lovefamily
 */
@Service
public class SysUserOnlineServiceImpl implements ISysUserOnlineService {
    @Autowired
    private SysUserOnlineManager sysUserOnlineManager;

    /**
     * 通过会话序号查询信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public SysUserOnline selectOnlineById(String sessionId) {
        SysUserOnlineDO sysUserOnlineDO = sysUserOnlineManager.selectOnlineById(sessionId);
        SysUserOnline sysUserOnline = new SysUserOnline();
        org.springframework.beans.BeanUtils.copyProperties(sysUserOnlineDO, sysUserOnline);
        return sysUserOnline;
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessionId 会话ID
     * @return 在线用户信息
     */
    @Override
    public void deleteOnlineById(String sessionId) {
        SysUserOnlineDO sysUserOnlineDO = sysUserOnlineManager.selectOnlineById(sessionId);
        if (StringUtils.isNotNull(sysUserOnlineDO)) {
            sysUserOnlineManager.deleteOnlineById(sessionId);
        }
    }

    /**
     * 通过会话序号删除信息
     *
     * @param sessions 会话ID集合
     * @return 在线用户信息
     */
    @Override
    public void batchDeleteOnline(List<String> sessions) {
        for (String sessionId : sessions) {
            SysUserOnlineDO sysUserOnlineDO = sysUserOnlineManager.selectOnlineById(sessionId);
            if (StringUtils.isNotNull(sysUserOnlineDO)) {
                sysUserOnlineManager.deleteOnlineById(sessionId);
            }
        }
    }

    /**
     * 保存会话信息
     *
     * @param online 会话信息
     */
    @Override
    public void saveOnline(SysUserOnline online) {
        SysUserOnlineDO sysUserOnlineDO = new SysUserOnlineDO();
        org.springframework.beans.BeanUtils.copyProperties(online, sysUserOnlineDO);
        sysUserOnlineManager.saveOnline(sysUserOnlineDO);
    }

    /**
     * 查询会话集合
     *
     * @param userOnline 在线用户
     */
    @Override
    public List<SysUserOnline> selectUserOnlineList(SysUserOnline userOnline) {
        List<SysUserOnlineDO> sysUserOnlineDOS = sysUserOnlineManager.selectUserOnlineList(userOnline);
        List<SysUserOnline> sysUserOnlines = new ArrayList<>(sysUserOnlineDOS.size());
        BeanUtils.copyListProperties(sysUserOnlineDOS, sysUserOnlines, SysUserOnline.class);
        return sysUserOnlines;
    }

    /**
     * 强退用户
     *
     * @param sessionId 会话ID
     */
    @Override
    public void forceLogout(String sessionId) {
        sysUserOnlineManager.deleteOnlineById(sessionId);
    }

    /**
     * 查询会话集合
     *
     * @param expiredDate 失效日期
     */
    @Override
    public List<SysUserOnline> selectOnlineByExpired(Date expiredDate) {
        String lastAccessTime = DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS, expiredDate);
        List<SysUserOnlineDO> sysUserOnlineDOS = sysUserOnlineManager.selectOnlineByExpired(lastAccessTime);
        List<SysUserOnline> sysUserOnlines = new ArrayList<>(sysUserOnlineDOS.size());
        BeanUtils.copyListProperties(sysUserOnlineDOS, sysUserOnlines, SysUserOnline.class);
        return sysUserOnlines;
    }
}
