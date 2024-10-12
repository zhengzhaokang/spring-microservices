package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO;

import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryMapper接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsReportFlowStatusSummaryMapper
{
    /**
     * 查询BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return BizSdmsReportFlowStatusSummary
     */
    public BizSdmsReportFlowStatusSummaryDO selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId);

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return BizSdmsReportFlowStatusSummary集合
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
     * 删除BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId);

    /**
     * 批量删除BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(Long[] statusSummaryIds);
}
