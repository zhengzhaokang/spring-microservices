package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.BizSdmsReportKeyMetrics;
import com.microservices.otmp.system.service.IBizSdmsReportKeyMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizSdmsReportKeyMetricsController
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@RestController
@RequestMapping("/bizSdmsReportKeyMetrics")
public class BizSdmsReportKeyMetricsController extends BaseController {
    @Autowired
    private IBizSdmsReportKeyMetricsService bizSdmsReportKeyMetricsService;

    /**
     * 查询BizSdmsReportKeyMetrics列表
     */
    @RequiresPermissions("system:bizSdmsReportKeyMetrics:list")
    @GetMapping("/list")
    public TableDataInfo list(BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics) {
        startPage();
        List<BizSdmsReportKeyMetrics> list = bizSdmsReportKeyMetricsService.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);
        return getDataTable(list);
    }

    /**
     * 导出BizSdmsReportKeyMetrics列表
     */
    @RequiresPermissions("system:bizSdmsReportKeyMetrics:export")
    @Log(title = "BizSdmsReportKeyMetrics", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSdmsReportKeyMetrics bizSdmsReportKeyMetrics) {
        List<BizSdmsReportKeyMetrics> list = bizSdmsReportKeyMetricsService.selectBizSdmsReportKeyMetricsList(bizSdmsReportKeyMetrics);
        ExcelUtil<BizSdmsReportKeyMetrics> util = new ExcelUtil<>(BizSdmsReportKeyMetrics.class);
        util.exportExcel(response, list, "ReportKeyMetrics");
    }


    /**
     * 获取BizSdmsReportKeyMetrics详细信息
     */
    @RequiresPermissions("system:bizSdmsReportKeyMetrics:query")
    @GetMapping(value = "/{keyMetricsId}")
    public ResultDTO<BizSdmsReportKeyMetrics> getInfo(@PathVariable("keyMetricsId") Long keyMetricsId) {
        return ResultDTO.success(bizSdmsReportKeyMetricsService.selectBizSdmsReportKeyMetricsByKeyMetricsId(keyMetricsId));
    }


}
