package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.financing.domain.param.limit.EditLimitParam;
import com.microservices.otmp.financing.domain.param.limit.LimitListParam;
import com.microservices.otmp.financing.domain.vo.limit.LimitVo;
import com.microservices.otmp.financing.service.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@Slf4j
@RequestMapping("/anchor/limit")
@RestController
public class AnchorLimitController extends BaseController {

    @Autowired
    private LimitService limitService;

    @OperLog(title = "计算outstanding/limit图表数据", businessType = BusinessType.OTHER)
    @GetMapping("/cal")
    public ResultDTO<Boolean> calculateOutstanding(@RequestParam(value = "date",required = false) String date){
        try {
            Date calDate = StringUtils.isEmpty(date) ? null : DateUtils.parseDate(date, DateUtils.DATE_PATTERN);
            limitService.statisticLimitDaily(calDate);
        } catch (ParseException e) {
            log.error("calculateOutstanding,date parse error,date:{}",date);
            return ResultDTO.fail();
        }
        return ResultDTO.success(true);
    }

//    @OperLog(title = "查询Limit列表", businessType = BusinessType.QUERY)
    @HasPermissions("financing:limit:list")
    @PostMapping("/list")
    public ResultDTO<PageInfo<LimitVo>> list(@Validated @RequestBody LimitListParam param){
        PageInfo<LimitVo> list = limitService.list(param);
        return ResultDTO.success(list);
    }

    @OperLog(title = "编辑Limit信息", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public ResultDTO<Boolean> edit(@Validated @RequestBody EditLimitParam param){
        Boolean result = limitService.edit(param);
        return  ResultDTO.success(result);
    }

}
