package com.microservices.otmp.notice.service;

import com.microservices.otmp.notice.domain.entity.GrafanaAlertMsgDto;

/**
 * Grafana 报警消息处理
 *
 * @author db117
 * @since 2023/3/9
 */
public interface IGrafanaAlertService {

    void process(GrafanaAlertMsgDto msg, String sendTo);
}
