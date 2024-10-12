package com.microservices.otmp.notice.service;


import com.microservices.otmp.notice.dto.NoticeDto;
import com.microservices.otmp.notice.vo.NoticeVO;

import java.util.List;

public interface NoticeService {
    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    public NoticeVO selectNoticeById(Long noticeId);

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    public List<NoticeDto> selectNoticeList(NoticeVO notice);

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int insertNotice(NoticeVO notice);

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    public int updateNotice(NoticeVO notice);

    /**
     * 删除公告信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoticeByIds(String ids);
}
