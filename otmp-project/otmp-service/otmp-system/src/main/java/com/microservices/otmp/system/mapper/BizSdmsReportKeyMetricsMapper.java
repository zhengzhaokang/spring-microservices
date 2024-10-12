package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO;

import java.util.List;

/**
 * BizSdmsReportKeyMetricsMapper接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface BizSdmsReportKeyMetricsMapper
{
    /**
     * 查询BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return BizSdmsReportKeyMetrics
     */
    public BizSdmsReportKeyMetricsDO selectBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId);

    /**
     * 查询BizSdmsReportKeyMetrics列表
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return BizSdmsReportKeyMetrics集合
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
     * 删除BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId);

    /**
     * 批量删除BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsIds(Long[] keyMetricsIds);
}
