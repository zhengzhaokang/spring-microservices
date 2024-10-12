package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.BizSdmsReportKeyMetrics;
import com.microservices.otmp.system.domain.entity.BizSdmsReportKeyMetricsDO;
import com.microservices.otmp.system.manager.BizSdmsReportKeyMetricsManager;
import com.microservices.otmp.system.service.IBizSdmsReportKeyMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * BizSdmsReportKeyMetricsService业务层处理
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsReportKeyMetricsServiceImpl implements IBizSdmsReportKeyMetricsService {
    @Autowired
    private BizSdmsReportKeyMetricsManager bizSdmsReportKeyMetricsManager;

    /**
     * 查询BizSdmsReportKeyMetrics
     *
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return BizSdmsReportKeyMetrics
     */
    @Override
    public BizSdmsReportKeyMetrics selectBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId) {
        BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = bizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsByKeyMetricsId(keyMetricsId);
        BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics = new BizSdmsReportKeyMetrics();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportKeyMetricsDO, bizSdmsReportKeyMetrics);
        return bizSdmsReportKeyMetrics;
    }

    /**
     * 查询BizSdmsReportKeyMetrics列表
     *
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return BizSdmsReportKeyMetrics
     */
    @Override
    public List<BizSdmsReportKeyMetrics> selectBizSdmsReportKeyMetricsList(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics) {
        BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new BizSdmsReportKeyMetricsDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportKeyMetrics, bizSdmsReportKeyMetricsDO);
        List<BizSdmsReportKeyMetricsDO> bizSdmsReportKeyMetricsDOS =
                bizSdmsReportKeyMetricsManager.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetricsDO);

        List<BizSdmsReportKeyMetrics> bizSdmsReportKeyMetricsList = new ArrayList<>(bizSdmsReportKeyMetricsDOS.size());
        BeanUtils.copyListProperties(bizSdmsReportKeyMetricsDOS, bizSdmsReportKeyMetricsList, BizSdmsReportKeyMetrics.class);
        return bizSdmsReportKeyMetricsList;
    }

    /**
     * 新增BizSdmsReportKeyMetrics
     *
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    @Override
    public int insertBizSdmsReportKeyMetrics(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics) {

        bizSdmsReportKeyMetrics.setCreateTime(DateUtils.getNowDate());
        BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new BizSdmsReportKeyMetricsDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportKeyMetrics, bizSdmsReportKeyMetricsDO);
        return bizSdmsReportKeyMetricsManager.insertBizSdmsReportKeyMetrics(bizSdmsReportKeyMetricsDO);
    }

    /**
     * 修改BizSdmsReportKeyMetrics
     *
     * @param bizSdmsReportKeyMetrics BizSdmsReportKeyMetrics
     * @return 结果
     */
    @Override
    public int updateBizSdmsReportKeyMetrics(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics) {
        bizSdmsReportKeyMetrics.setUpdateTime(DateUtils.getNowDate());
        BizSdmsReportKeyMetricsDO bizSdmsReportKeyMetricsDO = new BizSdmsReportKeyMetricsDO();
        org.springframework.beans.BeanUtils.copyProperties(bizSdmsReportKeyMetrics, bizSdmsReportKeyMetricsDO);
        return bizSdmsReportKeyMetricsManager.updateBizSdmsReportKeyMetrics(bizSdmsReportKeyMetricsDO);
    }

    /**
     * 批量删除BizSdmsReportKeyMetrics
     *
     * @param keyMetricsIds 需要删除的BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsIds(Long[] keyMetricsIds) {
        return bizSdmsReportKeyMetricsManager.deleteBizSdmsReportKeyMetricsByKeyMetricsIds(keyMetricsIds);
    }

    /**
     * 删除BizSdmsReportKeyMetrics信息
     *
     * @param keyMetricsId BizSdmsReportKeyMetrics主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportKeyMetricsByKeyMetricsId(Long keyMetricsId) {
        return bizSdmsReportKeyMetricsManager.deleteBizSdmsReportKeyMetricsByKeyMetricsId(keyMetricsId);
    }
}
