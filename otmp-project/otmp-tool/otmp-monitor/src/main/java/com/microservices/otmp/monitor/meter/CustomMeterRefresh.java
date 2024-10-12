package com.microservices.otmp.monitor.meter;


import com.microservices.otmp.monitor.meter.service.DataRefresh;
import com.microservices.otmp.monitor.meter.service.DataRefresh;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 自定义指标统一刷新
 *
 * @author db117
 * @since 2023/4/18
 */
@Component
public class CustomMeterRefresh implements MeterBinder {

    @Autowired
    private List<DataRefresh> dataRefreshList;
    private MeterRegistry registry;

    @Override
    public void bindTo(MeterRegistry registry) {
        this.registry = registry;
        // 兼容以后单独的指标
        // 当前用的都是 MultiGauge ，每一次都是重新注册
        refresh();
    }

    @Scheduled(cron = "*/10 * * * * *")
    public void refresh() {
        // 对每一个自定义的指标都刷新一次
        // todo db 临时方案 后面会考虑拆开，每一个都可以在配置文件中配置刷新频率  也可以放在 xxljob 中调度
        dataRefreshList.forEach(dataRefresh -> dataRefresh.refresh(registry));
    }
}
