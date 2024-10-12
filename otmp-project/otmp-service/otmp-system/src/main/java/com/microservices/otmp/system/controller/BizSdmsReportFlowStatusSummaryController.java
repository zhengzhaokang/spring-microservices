package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.BizSdmsReportFlowStatusSummary;
import com.microservices.otmp.system.service.IBizSdmsReportFlowStatusSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizSdmsReportFlowStatusSummaryController
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@RestController
@RequestMapping("/bizSdmsReportFlowStatusSummary")
public class BizSdmsReportFlowStatusSummaryController extends BaseController {
    @Autowired
    private IBizSdmsReportFlowStatusSummaryService bizSdmsReportFlowStatusSummaryService;

    /**
     * 查询BizSdmsReportFlowStatusSummary列表
     */
    @RequiresPermissions("system:bizSdmsReportFlowStatusSummary:list")
    @GetMapping("/list")
    public TableDataInfo list(BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary) {
        startPage();
        List<BizSdmsReportFlowStatusSummary> list = bizSdmsReportFlowStatusSummaryService.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);
        return getDataTable(list);
    }

    /**
     * 导出BizSdmsReportFlowStatusSummary列表
     */
    @RequiresPermissions("system:bizSdmsReportFlowStatusSummary:export")
    @Log(title = "BizSdmsReportFlowStatusSummary", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSdmsReportFlowStatusSummary bizSdmsReportFlowStatusSummary) {
        List<BizSdmsReportFlowStatusSummary> list = bizSdmsReportFlowStatusSummaryService.selectBizSdmsReportFlowStatusSummaryList(bizSdmsReportFlowStatusSummary);
        ExcelUtil<BizSdmsReportFlowStatusSummary> util = new ExcelUtil<>(BizSdmsReportFlowStatusSummary.class);
        util.exportExcel(response, list, "ReportFlowStatusSummary");
    }


    /**
     * 获取BizSdmsReportFlowStatusSummary详细信息
     */
    @RequiresPermissions("system:bizSdmsReportFlowStatusSummary:query")
    @GetMapping(value = "/{statusSummaryId}")
    public ResultDTO<BizSdmsReportFlowStatusSummary> getInfo(@PathVariable("statusSummaryId") Long statusSummaryId) {
        return ResultDTO.success(bizSdmsReportFlowStatusSummaryService.selectBizSdmsReportFlowStatusSummaryByStatusSummaryId(statusSummaryId));
    }


}
