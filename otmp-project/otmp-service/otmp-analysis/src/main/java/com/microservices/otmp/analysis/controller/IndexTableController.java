package com.microservices.otmp.analysis.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.param.BatchSummaryParam;
import com.microservices.otmp.analysis.domain.vo.BatchSummaryVo;
import com.microservices.otmp.analysis.service.IndexTableService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index/table")
public class IndexTableController extends BaseController {

    @Autowired
    private IndexTableService indexTableService;

//    @OperLog(title = "查询发票批次摘要列表", businessType = BusinessType.QUERY)
    @PostMapping("/batch/summary")
    public ResultDTO<PageInfo<BatchSummaryVo>> batchSummary(@RequestBody BatchSummaryParam param){
        param.setUserId(getCurrentUserId());
        PageInfo<BatchSummaryVo> result = indexTableService.batchSummary(param);
        return ResultDTO.success(result);
    }

}
