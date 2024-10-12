package com.microservices.otmp.system.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;

import java.util.List;

/**
 * BizSdmsSystemAnnouncementService接口
 * 
 * @author lovefamily
 * @date 2023-02-28
 */
public interface IBizSdmsSystemAnnouncementService
{
    /**
     * 查询BizSdmsSystemAnnouncement
     * 
     * @param id BizSdmsSystemAnnouncement主键
     * @return BizSdmsSystemAnnouncementDTO
     */
    public BizSdmsSystemAnnouncementDTO selectBizSdmsSystemAnnouncementById(Long id);

    /**
     * 查询BizSdmsSystemAnnouncement列表
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return BizSdmsSystemAnnouncementDTO集合
     */
    public PageInfo<BizSdmsSystemAnnouncementDTO> selectBizSdmsSystemAnnouncementList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);

    /**
     * 新增BizSdmsSystemAnnouncement
     * 
     * @param bizSdmsSystemAnnouncementDTO BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int insertBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);

    /**
     * 修改BizSdmsSystemAnnouncement
     * 
     * @param bizSdmsSystemAnnouncementDTO BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int updateBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);

    /**
     * 批量删除BizSdmsSystemAnnouncement
     * 
     * @param ids 需要删除的BizSdmsSystemAnnouncement主键集合
     * @return 结果
     */
    public int deleteBizSdmsSystemAnnouncementByIds(Long[] ids);

    /**
     * 删除BizSdmsSystemAnnouncement信息
     * 
     * @param id BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    public int deleteBizSdmsSystemAnnouncementById(Long id);

    List<BizSdmsSystemAnnouncementDTO> see(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);
}
