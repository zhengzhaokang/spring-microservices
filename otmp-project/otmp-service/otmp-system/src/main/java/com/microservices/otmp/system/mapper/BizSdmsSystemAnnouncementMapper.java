package com.microservices.otmp.system.mapper;

import com.microservices.otmp.common.auth.annotation.DataPermissions;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;

import java.util.List;


/**
 * BizSdmsSystemAnnouncementMapper接口
 * 
 * @author lovefamily
 * @date 2023-02-28
 */
public interface BizSdmsSystemAnnouncementMapper
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
     * @param bizSdmsSystemAnnouncementDTO BizSdmsSystemAnnouncement
     * @return BizSdmsSystemAnnouncement集合
     */
    public List<BizSdmsSystemAnnouncementDO> selectBizSdmsSystemAnnouncementList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);

    /**
     * 新增BizSdmsSystemAnnouncement
     * 
     * @param bizSdmsSystemAnnouncementDO BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int insertBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement);

    /**
     * 修改BizSdmsSystemAnnouncement
     * 
     * @param bizSdmsSystemAnnouncementDO BizSdmsSystemAnnouncement
     * @return 结果
     */
    public int updateBizSdmsSystemAnnouncement (BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement);

    /**
     * 删除BizSdmsSystemAnnouncement
     * 
     * @param id BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    public int deleteBizSdmsSystemAnnouncementById(Long id);

    /**
     * 批量删除BizSdmsSystemAnnouncement
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsSystemAnnouncementByIds(Long[] ids);
    @DataPermissions(tableName = "biz_sdms_system_announcement",openFullTextIndex=true)
    List<BizSdmsSystemAnnouncementDTO> getList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement);
}
