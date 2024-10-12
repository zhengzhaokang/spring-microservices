package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;
import com.microservices.otmp.system.manager.IBizSdmsSystemAnnouncementManager;
import com.microservices.otmp.system.mapper.BizSdmsSystemAnnouncementMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BizSdmsSystemAnnouncementManager业务层处理
 *
 * @author lovefamily
 * @date 2023-02-28
 */
@Service
public class BizSdmsSystemAnnouncementManagerImpl implements IBizSdmsSystemAnnouncementManager {

    @Autowired
    private BizSdmsSystemAnnouncementMapper bizSdmsSystemAnnouncementMapper;

    /**
     * 查询BizSdmsSystemAnnouncement
     *
     * @param id BizSdmsSystemAnnouncement主键
     * @return BizSdmsSystemAnnouncementDO
     */
    @Override
    public BizSdmsSystemAnnouncementDO selectBizSdmsSystemAnnouncementById(Long id) {
        return bizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementById(id);
    }

    /**
     * 查询BizSdmsSystemAnnouncement列表
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return BizSdmsSystemAnnouncementDO
     */
    @Override
    public List<BizSdmsSystemAnnouncementDO> selectBizSdmsSystemAnnouncementList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        return bizSdmsSystemAnnouncementMapper.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);
    }

    /**
     * 新增BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return 结果
     */
    @Override
    public int insertBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement) {
        bizSdmsSystemAnnouncement.setCreateTime(DateUtils.getNowDate());
        return bizSdmsSystemAnnouncementMapper.insertBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);
    }

    /**
     * 修改BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return 结果
     */
    @Override
    public int updateBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncement) {
        bizSdmsSystemAnnouncement.setUpdateTime(DateUtils.getNowDate());
        return bizSdmsSystemAnnouncementMapper.updateBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement);
    }

    /**
     * 批量删除BizSdmsSystemAnnouncement
     *
     * @param ids 需要删除的BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsSystemAnnouncementByIds(Long[] ids) {
        return bizSdmsSystemAnnouncementMapper.deleteBizSdmsSystemAnnouncementByIds(ids);
    }

    /**
     * 删除BizSdmsSystemAnnouncement信息
     *
     * @param id BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsSystemAnnouncementById(Long id) {
        return bizSdmsSystemAnnouncementMapper.deleteBizSdmsSystemAnnouncementById(id);
    }

    @Override
    public List<BizSdmsSystemAnnouncementDTO> getList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        return bizSdmsSystemAnnouncementMapper.getList(bizSdmsSystemAnnouncement);
    }
}
