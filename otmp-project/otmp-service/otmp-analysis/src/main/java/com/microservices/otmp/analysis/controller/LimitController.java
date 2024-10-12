package com.microservices.otmp.analysis.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.domain.param.LimitDashboardParam;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardTableVo;
import com.microservices.otmp.analysis.domain.vo.LimitDashboardVo;
import com.microservices.otmp.analysis.service.LimitDashBoardService;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dashboard")
public class LimitController extends BaseController {

    @Autowired
    private LimitDashBoardService limitDashBoardService;

//    @OperLog(title = "查询outstanding-limit使用率图表", businessType = BusinessType.QUERY)
    @GetMapping("/limit/outstanding")
    public ResultDTO<List<LimitDashboardVo>> dashboardValues(@RequestParam(value = "bankId", required = false) String bankId
            , @RequestParam(value = "entityId", required = false) Long entityId
            , @RequestParam("startTime") String startTime
            , @RequestParam("endTime") String endTime
            , @RequestParam(value = "useMilli", required = false) Boolean useMilli) {
        useMilli = (useMilli == null || useMilli);
        List<LimitDashboardVo> result = limitDashBoardService.limitDashBoardValues(bankId, entityId, startTime, endTime, useMilli);
        return ResultDTO.success(result);
    }

//    @OperLog(title = "查询outstanding-limit使用率数据列表", businessType = BusinessType.QUERY)
    @PostMapping("/limit/outstanding/table")
    public ResultDTO<PageInfo<LimitDashboardTableVo>> dashboardTable(@RequestBody LimitDashboardParam param) {
        PageInfo<LimitDashboardTableVo> result = limitDashBoardService.limitDashBoardTable(param);
        return ResultDTO.success(result);
    }

//    @OperLog(title = "导出outstanding-limit使用率数据列表", businessType = BusinessType.EXPORT)
    @PostMapping("/limit/outstanding/export")
    public void dashboardExport(LimitDashboardParam param, HttpServletResponse response) {
        log.info("dashboardExport,param:{}", JsonUtil.toJSON(param));
        param.setPageSize(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        PageInfo<LimitDashboardTableVo> result = limitDashBoardService.limitDashBoardTable(param);
        log.info("dashboardExport,result:{}", JsonUtil.toJSON(result));
        NewExcelUtil<LimitDashboardTableVo> util = new NewExcelUtil<>(LimitDashboardTableVo.class);
//        util.exportExcel(response, result.getList(), "Credit Limit Utilization");
        util.exportByFileType(response,result.getList(),"Credit Limit Utilization",getRequest().getHeader(Constants.FILE_TYPE));
    }

}
