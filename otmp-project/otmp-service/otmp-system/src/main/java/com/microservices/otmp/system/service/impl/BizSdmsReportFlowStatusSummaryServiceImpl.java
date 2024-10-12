package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.BizSdmsReportFlowStatusSummary;
import com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO;
import com.microservices.otmp.system.manager.BizSdmsReportFlowStatusSummaryManager;
import com.microservices.otmp.system.service.IBizSdmsReportFlowStatusSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryService业务层处理
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsReportFlowStatusSummaryServiceImpl implements IBizSdmsReportFlowStatusSummaryService {
    @Autowired
    private BizSdmsReportFlowStatusSummaryManager bizSdmsReportFlowStatusSummaryManager;

    /**
     * 查询BizSdmsReportFlowStatusSummary
     *
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return BizSdmsReportFlowStatusSummary
     */
    @Override
    public BizSdmsReportFlowStatusSummary selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId) {

        BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = bizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(statusSummaryId);
        BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary = new BizSdmsReportFlowStatusSummary();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportFlowStatusSummaryDO, bizSdmsReportFlowStatusSummary);
        return bizSdmsReportFlowStatusSummary;
    }

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     *
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return BizSdmsReportFlowStatusSummary
     */
    @Override
    public List<BizSdmsReportFlowStatusSummary> selectBizSdmsReportFlowStatusSummaryList(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary) {
        BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new BizSdmsReportFlowStatusSummaryDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportFlowStatusSummary, bizSdmsReportFlowStatusSummaryDO);
        List<BizSdmsReportFlowStatusSummaryDO> bizSdmsReportFlowStatusSummaryDOS =
                bizSdmsReportFlowStatusSummaryManager.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummaryDO);
        List<BizSdmsReportFlowStatusSummary> bizSdmsReportFlowStatusSummaries = new ArrayList<>(bizSdmsReportFlowStatusSummaryDOS.size());
        BeanUtils.copyListProperties(bizSdmsReportFlowStatusSummaryDOS, bizSdmsReportFlowStatusSummaries, BizSdmsReportFlowStatusSummary.class);
        return bizSdmsReportFlowStatusSummaries;
    }

    /**
     * 新增BizSdmsReportFlowStatusSummary
     *
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    @Override
    public int insertBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary) {
        bizSdmsReportFlowStatusSummary.setCreateTime(DateUtils.getNowDate());
        BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new BizSdmsReportFlowStatusSummaryDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportFlowStatusSummary, bizSdmsReportFlowStatusSummaryDO);
        return bizSdmsReportFlowStatusSummaryManager.insertBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummaryDO);
    }

    /**
     * 修改BizSdmsReportFlowStatusSummary
     *
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    @Override
    public int updateBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary) {
        bizSdmsReportFlowStatusSummary.setCreateTime(DateUtils.getNowDate());
        BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummaryDO = new BizSdmsReportFlowStatusSummaryDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportFlowStatusSummary, bizSdmsReportFlowStatusSummaryDO);
        return bizSdmsReportFlowStatusSummaryManager.updateBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummaryDO);
    }

    /**
     * 批量删除BizSdmsReportFlowStatusSummary
     *
     * @param statusSummaryIds 需要删除的BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(Long[] statusSummaryIds) {
        return bizSdmsReportFlowStatusSummaryManager.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(statusSummaryIds);
    }

    /**
     * 删除BizSdmsReportFlowStatusSummary信息
     *
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId) {
        return bizSdmsReportFlowStatusSummaryManager.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(statusSummaryId);
    }
}
