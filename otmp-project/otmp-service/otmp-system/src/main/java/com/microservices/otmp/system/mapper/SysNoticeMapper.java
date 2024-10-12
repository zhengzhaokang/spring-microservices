package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.SysNotice;
import com.microservices.otmp.system.domain.entity.SysNoticeDO;

import java.util.List;

/**
 * 公告 数据层
 * 
 * @author lovefamily
 */
public interface SysNoticeMapper
{
    /**
     * 查询公告信息
     * 
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public SysNoticeDO selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     * 
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<SysNoticeDO> selectNoticeList(SysNotice notice);

    /**
     * 新增公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(SysNoticeDO notice);

    /**
     * 修改公告
     * 
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(SysNoticeDO notice);

    /**
     * 批量删除公告
     * 
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);
}