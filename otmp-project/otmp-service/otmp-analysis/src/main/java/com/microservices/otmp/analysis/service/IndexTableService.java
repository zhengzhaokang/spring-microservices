package com.microservices.otmp.analysis.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.param.BatchSummaryParam;
import com.microservices.otmp.analysis.domain.vo.BatchSummaryVo;

public interface IndexTableService {

    PageInfo<BatchSummaryVo> batchSummary(BatchSummaryParam param);

}
