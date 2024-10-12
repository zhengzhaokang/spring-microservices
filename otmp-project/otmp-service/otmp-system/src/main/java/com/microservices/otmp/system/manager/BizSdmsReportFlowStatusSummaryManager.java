package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO;

import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryManager接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsReportFlowStatusSummaryManager
{
    /**
     * 查询BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return BizSdmsReportFlowStatusSummaryDO
     */
    public BizSdmsReportFlowStatusSummaryDO selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId);

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return BizSdmsReportFlowStatusSummaryDO集合
     */
    public List<BizSdmsReportFlowStatusSummaryDO> selectBizSdmsReportFlowStatusSummaryList(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary);

    /**
     * 新增BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    public int insertBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary);

    /**
     * 修改BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    public int updateBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary);

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
