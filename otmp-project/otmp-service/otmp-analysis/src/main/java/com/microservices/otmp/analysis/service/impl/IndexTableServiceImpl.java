package com.microservices.otmp.analysis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.param.BatchSummaryParam;
import com.microservices.otmp.analysis.domain.vo.BatchSummaryVo;
import com.microservices.otmp.analysis.domain.vo.BatchSummaryWrapperVo;
import com.microservices.otmp.analysis.mapper.FinanceBatchAnalysisMapper;
import com.microservices.otmp.analysis.remote.RemoteFinancingService;
import com.microservices.otmp.analysis.remote.param.RemoteBatchSummaryParam;
import com.microservices.otmp.analysis.service.IndexTableService;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.analysis.mapper.FinanceBatchAnalysisMapper;
import com.microservices.otmp.analysis.remote.RemoteFinancingService;
import com.microservices.otmp.analysis.remote.param.RemoteBatchSummaryParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class IndexTableServiceImpl implements IndexTableService {

    @Autowired
    private FinanceBatchAnalysisMapper financeBatchAnalysisMapper;
    @Autowired
    private RemoteFinancingService remoteFinancingService;

    @Override
    public PageInfo<BatchSummaryVo> batchSummary(BatchSummaryParam param) {
        log.info("batchSummary,param:{}", JsonUtil.toJSON(param));
        String timePattern;
        int interval;
        if (BatchSummaryParam.VALUE_MONTH.equals(param.getType())) {
            timePattern = DateUtils.MONTH_PATTERN;
            interval = Calendar.MONTH;
        } else {
            timePattern = DateUtils.YYYY;
            interval = Calendar.YEAR;
        }

        Date start;
        Date end;
        try {
            start = DateUtils.dateParse(param.getTypeValue(), timePattern);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(start);
            calendar.add(interval, 1);
            end = calendar.getTime();
            log.info("limitDashBoardValues,start:{},end:{}", start, end);
        } catch (Exception e) {
            log.error("limitDashBoardValues,parse time error,value:{},pattern:{}", param.getTypeValue(), timePattern, e);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR));
        }

        PageInfo<BatchSummaryVo> result = new PageInfo<>();
        int pageNum = (param.getPageNum() == null || param.getPageNum() <= 0) ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = (param.getPageSize() == null || param.getPageSize() <= 0) ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        List<String> batchNumbers = financeBatchAnalysisMapper.selectBatchNumByParam(start, end, param.getBankId(), param.getSupplierId(), param.getEntityId());
        if (CollectionUtils.isEmpty(batchNumbers)) {
            log.info("limitDashBoardValues,batchNumbers is empty,return");
            result.setList(new ArrayList<>());
            result.setTotal(0);
            return result;
        }

        PageInfo<String> pageInfo = new PageInfo<>(batchNumbers);
        List<String> remoteParam = pageInfo.getList();
        log.info("limitDashBoardValues,remoteParam:{}", JsonUtil.toJSON(remoteParam));
        RemoteBatchSummaryParam remoteBatchSummaryParam = new RemoteBatchSummaryParam();
        remoteBatchSummaryParam.setBatchNumberList(remoteParam);
        log.info("limitDashBoardValues,remoteBatchSummaryParam:{}", JsonUtil.toJSON(remoteBatchSummaryParam));
        ResultDTO<BatchSummaryWrapperVo> batchSummaryData = remoteFinancingService.getBatchSummaryData(remoteBatchSummaryParam);
        log.info("limitDashBoardValues,batchSummaryData:{}", JsonUtil.toJSON(batchSummaryData));
        if (!batchSummaryData.isSuccess()) {
            throw new OtmpException(batchSummaryData.getMsg());
        }
        if (batchSummaryData.getData() == null || CollectionUtils.isEmpty(batchSummaryData.getData().getSummaryList())) {
            result.setList(new ArrayList<>());
            result.setTotal(0);
            return result;
        }
        List<BatchSummaryVo> summaryList = batchSummaryData.getData().getSummaryList();
        result.setList(summaryList);
        result.setTotal(pageInfo.getTotal());
        log.info("limitDashBoardValues,result:{}", JsonUtil.toJSON(result));
        return result;
    }
}
