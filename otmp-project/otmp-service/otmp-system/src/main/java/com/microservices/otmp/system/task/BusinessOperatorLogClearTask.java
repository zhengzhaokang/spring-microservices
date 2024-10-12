package com.microservices.otmp.system.task;

import cn.hutool.core.date.DateUtil;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.service.ISysBusinessMassUploadLogService;
import com.microservices.otmp.system.service.ISysBusinessOperatorLogService;
//import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
public class BusinessOperatorLogClearTask {
    @Autowired
    private ISysBusinessOperatorLogService businessOperatorLogService;
    @Autowired
    private ISysBusinessMassUploadLogService businessMassUploadLogService;

    //秒 分 时 当月哪天 月 周
    //@Scheduled(cron = "*/1 * * * * ?")
//    @XxlJob("autoClearBusinessOperatorLog")
    public void handleOrderTimeout() {
        log.info("定时任务==================>autoClearBusinessOperatorLog");
        Date startQuarter = getStartQuarter(new Date());
        log.info("startQuarter = {}", startQuarter);
        String s = DateUtils.dateFormat(startQuarter, "yyyy-MM-dd HH:mm:ss");
        log.info("s = {}", s);
        Date lastQuarter = getLastQuarter(new Date());
        log.info("lastQuarter = {}", lastQuarter);
        String s2 = DateUtils.dateFormat(lastQuarter, "yyyy-MM-dd HH:mm:ss");
        log.info("s2 = {}", s2);
        int currentQ = DateUtil.quarter(new Date()) - 1;
        log.info("currentQ = {} ", currentQ);
        int num = businessOperatorLogService.deleteSysBusinessOperatorLogByOperationDate(startQuarter, lastQuarter);
        int logTotal = businessMassUploadLogService.deleteSysBusinessOperatorLogByOperationDate(startQuarter, lastQuarter);
        log.info("num = {}", num);
        log.info("logTotal = {}", logTotal);
    }

    /**
     * 获取当前日期上一季度 开始时间
     *
     * @return
     */
    public static Date getStartQuarter(Date date) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(date);
        startCalendar.set(Calendar.MONTH, (startCalendar.get(Calendar.MONTH) / 3 - 2) * 3);
        startCalendar.set(Calendar.DAY_OF_MONTH, 1);
        setMinTime(startCalendar);
        return startCalendar.getTime();
    }

    /**
     * 获取当前日期上一季度 结束时间
     *
     * @return
     */
    public static Date getLastQuarter(Date date) {
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(date);
        endCalendar.set(Calendar.MONTH, (endCalendar.get(Calendar.MONTH) / 3 - 2) * 3 + 2);
        endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        setMaxTime(endCalendar);

        return endCalendar.getTime();
    }

    /**
     * 最小时间
     *
     * @param calendar
     */
    private static void setMinTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    /**
     * 最大时间
     *
     * @param calendar
     */
    private static void setMaxTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
    }
}
