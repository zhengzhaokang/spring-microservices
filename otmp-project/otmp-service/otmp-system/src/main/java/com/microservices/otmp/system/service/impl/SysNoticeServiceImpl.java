package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.core.text.Convert;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.SysNotice;
import com.microservices.otmp.system.domain.entity.SysNoticeDO;
import com.microservices.otmp.system.manager.SysNoticeManager;
import com.microservices.otmp.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author lovefamily
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
    @Autowired
    private SysNoticeManager sysNoticeManager;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        SysNoticeDO sysNoticeDO = sysNoticeManager.selectNoticeById(noticeId);
        SysNotice sysNotice = new SysNotice();
        org.springframework.beans.BeanUtils.copyProperties(sysNoticeDO, sysNotice);
        return sysNotice;
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        List<SysNoticeDO> sysNoticeDOS = sysNoticeManager.selectNoticeList(notice);
        List<SysNotice> sysNotices = new ArrayList<>(sysNoticeDOS.size());
        BeanUtils.copyListProperties(sysNoticeDOS, sysNotices, SysNotice.class);
        return sysNotices;
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice) {
        SysNoticeDO sysNoticeDO = new SysNoticeDO();
        org.springframework.beans.BeanUtils.copyProperties(notice, sysNoticeDO);
        return sysNoticeManager.insertNotice(sysNoticeDO);
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice) {
        SysNoticeDO sysNoticeDO = new SysNoticeDO();
        org.springframework.beans.BeanUtils.copyProperties(notice, sysNoticeDO);
        return sysNoticeManager.updateNotice(sysNoticeDO);
    }

    /**
     * 删除公告对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids) {
        return sysNoticeManager.deleteNoticeByIds(Convert.toStrArray(ids));
    }
}
