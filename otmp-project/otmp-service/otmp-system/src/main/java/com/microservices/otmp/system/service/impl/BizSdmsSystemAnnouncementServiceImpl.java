package com.microservices.otmp.system.service.impl;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.domain.entity.BizSdmsSystemAnnouncementDO;
import com.microservices.otmp.system.manager.IBizSdmsSystemAnnouncementManager;
import com.microservices.otmp.system.service.IBizSdmsSystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BizSdmsSystemAnnouncementService业务层处理
 *
 * @author lovefamily
 * @date 2023-02-28
 */
@Service
public class BizSdmsSystemAnnouncementServiceImpl implements IBizSdmsSystemAnnouncementService {

    @Autowired
    private IBizSdmsSystemAnnouncementManager bizSdmsSystemAnnouncementManager;

    /**
     * 查询BizSdmsSystemAnnouncement
     *
     * @param id BizSdmsSystemAnnouncement主键
     * @return BizSdmsSystemAnnouncementDTO
     */
    @Override
    public BizSdmsSystemAnnouncementDTO selectBizSdmsSystemAnnouncementById(Long id) {
        BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO = bizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementById(id);
        BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncementDTO = new BizSdmsSystemAnnouncementDTO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsSystemAnnouncementDO, bizSdmsSystemAnnouncementDTO);
        return bizSdmsSystemAnnouncementDTO;
    }

    /**
     * 查询BizSdmsSystemAnnouncement列表
     *
     * @param bizSdmsSystemAnnouncement BizSdmsSystemAnnouncement
     * @return BizSdmsSystemAnnouncementDTO
     */
    @Override
    public PageInfo<BizSdmsSystemAnnouncementDTO> selectBizSdmsSystemAnnouncementList(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {

        List<BizSdmsSystemAnnouncementDO> bizSdmsSystemAnnouncementDOS =
                bizSdmsSystemAnnouncementManager.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement);
        List<BizSdmsSystemAnnouncementDTO> bizSdmsSystemAnnouncementList = new ArrayList<>(bizSdmsSystemAnnouncementDOS.size());
        BeanUtils.copyListProperties(bizSdmsSystemAnnouncementDOS, bizSdmsSystemAnnouncementList, BizSdmsSystemAnnouncementDTO.class);
        PageInfo<BizSdmsSystemAnnouncementDO> pageInfo = new PageInfo<>(bizSdmsSystemAnnouncementDOS);
        PageInfo<BizSdmsSystemAnnouncementDTO> resultPageInfo = new PageInfo<>(bizSdmsSystemAnnouncementList);
        resultPageInfo.setTotal(pageInfo.getTotal());
        return resultPageInfo;

    }

    /**
     * 新增BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncementDTO BizSdmsSystemAnnouncement
     * @return 结果
     */
    @Override
    public int insertBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        bizSdmsSystemAnnouncement.setCreateTime(DateUtils.getNowDate());
        BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO = new BizSdmsSystemAnnouncementDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsSystemAnnouncement, bizSdmsSystemAnnouncementDO);
        return bizSdmsSystemAnnouncementManager.insertBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncementDO);
    }

    /**
     * 修改BizSdmsSystemAnnouncement
     *
     * @param bizSdmsSystemAnnouncementDTO BizSdmsSystemAnnouncement
     * @return 结果
     */
    @Override
    public int updateBizSdmsSystemAnnouncement(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        bizSdmsSystemAnnouncement.setUpdateTime(DateUtils.getNowDate());
        BizSdmsSystemAnnouncementDO bizSdmsSystemAnnouncementDO = new BizSdmsSystemAnnouncementDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsSystemAnnouncement, bizSdmsSystemAnnouncementDO);
        return bizSdmsSystemAnnouncementManager.updateBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncementDO);
    }

    /**
     * 批量删除BizSdmsSystemAnnouncement
     *
     * @param ids 需要删除的BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsSystemAnnouncementByIds(Long[] ids) {
        return bizSdmsSystemAnnouncementManager.deleteBizSdmsSystemAnnouncementByIds(ids);
    }

    /**
     * 删除BizSdmsSystemAnnouncement信息
     *
     * @param id BizSdmsSystemAnnouncement主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsSystemAnnouncementById(Long id) {
        return bizSdmsSystemAnnouncementManager.deleteBizSdmsSystemAnnouncementById(id);
    }

    @Override
    public List<BizSdmsSystemAnnouncementDTO> see(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        return bizSdmsSystemAnnouncementManager.getList(bizSdmsSystemAnnouncement);
    }
}
