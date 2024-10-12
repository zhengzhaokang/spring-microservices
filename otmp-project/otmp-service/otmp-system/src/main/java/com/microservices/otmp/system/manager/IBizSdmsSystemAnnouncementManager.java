package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;

import java.util.List;


/**
 * BizSdmsSystemAnnouncementManager接口
 * 
 * @author lovefamily
 * @date 2023-02-28
 */
public interface IBizSdmsSystemAnnouncementManager
{
    /**
     * 查询BizSdmsSystemAnnouncement
     * 
     * @param id BizSdmsSystemAnnouncement主键
     * @return BizSdmsSystemAnnouncement
     */
    public BizSdmsSystemAnnouncementDO selectBizSdmsSystemAnnouncementById(Long id);

    /**
     * 查询BizSdmsSystemAnnouncement列表
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return BizSdmsSystemAnnouncement集合
     */
    public List<BizSdmsSystemAnnouncementDO> selectBizSdmsSystemAnnouncementList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);

    /**
     * 新增BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int insertBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement);

    /**
     * 修改BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int updateBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement);

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

    List<BizSdmsSystemAnnouncementDTO> getList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);
}
