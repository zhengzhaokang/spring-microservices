package com.microservices.otmp.notice.manager;

import com.microservices.otmp.notice.dto.NoticeDto;

import java.util.List;

public interface NoticeManager {
    public NoticeDto selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<NoticeDto> selectNoticeList(NoticeDto notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(NoticeDto notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(NoticeDto notice);

    /**
     * 批量删除公告
     *
     * @param noticeIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String[] noticeIds);
}
