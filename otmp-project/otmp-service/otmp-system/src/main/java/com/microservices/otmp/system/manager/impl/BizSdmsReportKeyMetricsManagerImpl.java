package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO;
import com.microservices.otmp.system.manager.BizSdmsReportKeyMetricsManager;
import com.microservices.otmp.system.mapper.BizSdmsReportKeyMetricsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BizSdmsReportKeyMetricsManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsReportKeyMetricsManagerImpl implements BizSdmsReportKeyMetricsManager
{
    @Autowired
    private BizSdmsReportKeyMetricsMapper bizSdmsReportKeyMetricsMapper;

    /**
     * 查询BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return BizSdmsReportKeyMetrics
     */
    @Override
    public BizSdmsReportKeyMetricsDO selectBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId)
    {
        return bizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsByKeyMetricsId(keyMetricsId);
    }

    /**
     * 查询BizSdmsReportKeyMetrics列表
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return BizSdmsReportKeyMetrics
     */
    @Override
    public List<BizSdmsReportKeyMetricsDO> selectBizSdmsReportKeyMetricsList(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics)
    {
        return bizSdmsReportKeyMetricsMapper.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);
    }

    /**
     * 新增BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    @Override
    public int insertBizSdmsReportKeyMetrics(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics)
    {
        bizSdmsReportKeyMetrics.setCreateTime(DateUtils.getNowDate());
        return bizSdmsReportKeyMetricsMapper.insertBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);
    }

    /**
     * 修改BizSdmsReportKeyMetrics
     * 
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    @Override
    public int updateBizSdmsReportKeyMetrics(BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetrics)
    {
        bizSdmsReportKeyMetrics.setUpdateTime(DateUtils.getNowDate());
        return bizSdmsReportKeyMetricsMapper.updateBizSdmsReportKeyMetrics(bizSdmsReportKeyMetrics);
    }

    /**
     * 批量删除BizSdmsReportKeyMetrics
     * 
     * @param keyMetricsIds 需要删除的BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsIds(Long[] keyMetricsIds)
    {
        return bizSdmsReportKeyMetricsMapper.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(keyMetricsIds);
    }

    /**
     * 删除BizSdmsReportKeyMetrics信息
     * 
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId)
    {
        return bizSdmsReportKeyMetricsMapper.deleteBizSdmsReportKeyMetricsByKeyMetricsId(keyMetricsId);
    }
}
