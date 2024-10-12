package com.microservices.otmp.masterdata.controller;


import com.google.common.collect.Lists;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.entity.vo.QueryQuarterVo;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.DateUtils;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * baseComBiz 提供者
 *
 * @author sdms
 * @date 2022-01-17
 */
@Api("Quarter")
@RestController
@RequestMapping("/find")
public class QueryQuarterController extends BaseController {


    /*@HasPermissions("masterData:bizBaseGtnType:list")*/
    @PostMapping("/quarter/list")
    public ResultDTO<ArrayList<QueryQuarterVo>> getQuarterList() {
        Date date = new Date();
        ArrayList<QueryQuarterVo> list = Lists.newArrayList();
        for (int i = 0; i <4; i++) {
            Date startQuarter = getStartQuarter(date, i);
            String format = DateUtils.dateFormat(startQuarter, "MM/dd/yyyy");
            QueryQuarterVo queryQuarterVo = new QueryQuarterVo();
            queryQuarterVo.setLabel(format);
            queryQuarterVo.setValue(format);
            list.add(queryQuarterVo);
        }
            return ResultDTO.success(list);
    }

    public static Date getStartQuarter(Date date, int num) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.MONTH, (startCalendar.get(Calendar.MONTH) / 3 + num) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);
        return startCalendar.getTime();
    }
    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}
