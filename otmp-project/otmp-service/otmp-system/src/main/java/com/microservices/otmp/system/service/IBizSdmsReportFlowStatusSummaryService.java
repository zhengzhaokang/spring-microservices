package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.BizSdmsReportFlowStatusSummary;

import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryService接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface IBizSdmsReportFlowStatusSummaryService
{
    /**
     * 查询BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return BizSdmsReportFlowStatusSummary
     */
    public BizSdmsReportFlowStatusSummary selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId);

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return BizSdmsReportFlowStatusSummary集合
     */
    public List<BizSdmsReportFlowStatusSummary> selectBizSdmsReportFlowStatusSummaryList(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary);

    /**
     * 新增BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    public int insertBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary);

    /**
     * 修改BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    public int updateBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary);

    /**
     * 批量删除BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryIds 需要删除的BizSdmsReportFlowStatusSummary主键集合
     * @return 结果
     */
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(Long[] statusSummaryIds);

    /**
     * 删除BizSdmsReportFlowStatusSummary信息
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId);
}
