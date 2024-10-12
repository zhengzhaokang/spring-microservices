package com.microservices.otmp.monitor.meter.service;

import io.micrometer.core.instrument.MeterRegistry;

/**
 * 自定义指标 统一刷新接口
 *
 * @author db117
 * @since 2023/4/18
 */
public interface DataRefresh {
    void refresh(MeterRegistry registry);
}
