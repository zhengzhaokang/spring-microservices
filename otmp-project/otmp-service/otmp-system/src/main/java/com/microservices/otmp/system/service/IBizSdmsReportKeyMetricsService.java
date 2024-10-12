package com.microservices.otmp.system.service;

import com.microservices.otmp.system.domain.BizSdmsReportKeyMetrics;

import java.util.List;

/**
 * BizSdmsReportKeyMetricsService接口
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
public interface IBizSdmsReportKeyMetricsService
{
    /**
     * 查询BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return BizSdmsReportKeyMetrics
     */
    public BizSdmsReportKeyMetrics selectBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId);

    /**
     * 查询BizSdmsReportKeyMetrics列表
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return BizSdmsReportKeyMetrics集合
     */
    public List<BizSdmsReportKeyMetrics> selectBizSdmsReportKeyMetricsList(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics);

    /**
     * 新增BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    public int insertBizSdmsReportKeyMetrics(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics);

    /**
     * 修改BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    public int updateBizSdmsReportKeyMetrics(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics);

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
