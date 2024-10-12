package com.microservices.otmp.system.manager.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.entity.BizSdmsReportFlowStatusSummaryDO;
import com.microservices.otmp.system.manager.BizSdmsReportFlowStatusSummaryManager;
import com.microservices.otmp.system.mapper.BizSdmsReportFlowStatusSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryManager业务层处理
 * 
 * @author lovefamily
 * @date 2022-07-20
 */
@Service
public class BizSdmsReportFlowStatusSummaryManagerImpl implements BizSdmsReportFlowStatusSummaryManager
{
    @Autowired
    private BizSdmsReportFlowStatusSummaryMapper bizSdmsReportFlowStatusSummaryMapper;

    /**
     * 查询BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return BizSdmsReportFlowStatusSummary
     */
    @Override
    public BizSdmsReportFlowStatusSummaryDO selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId)
    {
        return bizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(statusSummaryId);
    }

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return BizSdmsReportFlowStatusSummary
     */
    @Override
    public List<BizSdmsReportFlowStatusSummaryDO> selectBizSdmsReportFlowStatusSummaryList(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary)
    {
        return bizSdmsReportFlowStatusSummaryMapper.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);
    }

    /**
     * 新增BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    @Override
    public int insertBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary)
    {
        bizSdmsReportFlowStatusSummary.setCreateTime(DateUtils.getNowDate());
        return bizSdmsReportFlowStatusSummaryMapper.insertBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);
    }

    /**
     * 修改BizSdmsReportFlowStatusSummary
     * 
     * @param bizSdmsReportFlowStatusSummary BizSdmsReportFlowStatusSummary
     * @return 结果
     */
    @Override
    public int updateBizSdmsReportFlowStatusSummary(BizSdmsReportFlowStatusSummaryDO bizSdmsReportFlowStatusSummary)
    {
        bizSdmsReportFlowStatusSummary.setUpdateTime(DateUtils.getNowDate());
        return bizSdmsReportFlowStatusSummaryMapper.updateBizSdmsReportFlowStatusSummary(bizSdmsReportFlowStatusSummary);
    }

    /**
     * 批量删除BizSdmsReportFlowStatusSummary
     * 
     * @param statusSummaryIds 需要删除的BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(Long[] statusSummaryIds)
    {
        return bizSdmsReportFlowStatusSummaryMapper.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryIds(statusSummaryIds);
    }

    /**
     * 删除BizSdmsReportFlowStatusSummary信息
     * 
     * @param statusSummaryId BizSdmsReportFlowStatusSummary主键
     * @return 结果
     */
    @Override
    public int deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(Long statusSummaryId)
    {
        return bizSdmsReportFlowStatusSummaryMapper.deleteBizSdmsReportFlowStatusSummaryByStatusSummaryId(statusSummaryId);
    }
}
