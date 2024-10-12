package com.microservices.otmp.system.manager;

import com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO;

import java.util.List;

/**
 * BizSdmsReportKeyMetricsManager接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsReportKeyMetricsManager
{
    /**
     * 查询BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return BizSdmsReportKeyMetricsDO
     */
    public BizSdmsReportKeyMetricsDO selectBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId);

    /**
     * 查询BizSdmsReportKeyMetrics列表
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return BizSdmsReportKeyMetricsDO集合
     */
    public List<BizSdmsReportKeyMetricsDO> selectBizSdmsReportKeyMetricsList(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics);

    /**
     * 新增BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    public int insertBizSdmsReportKeyMetrics(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics);

    /**
     * 修改BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    public int updateBizSdmsReportKeyMetrics(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics);

    /**
     * 批量删除BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsIds 需要删除的BizSdmsReportKeyMetrics主键集合
     * @return 结果
     */
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsIds(Long[] keyMetricsIds);

    /**
     * 删除BizSdmsReportKeyMetrics信息
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId);
}
