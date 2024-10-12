package com.microservices.otmp.notice.controller;

import com.microservices.otmp.notice.domain.entity.GrafanaAlertMsgDto;
import com.microservices.otmp.notice.service.IGrafanaAlertService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Grafana 报警
 *
 * @author db117
 * @since 2023/3/9
 */
@Api("Grafana 报警")
@RestController
@RequestMapping("grafana")
public class GrafanaAlertController {

    @Autowired
    private IGrafanaAlertService grafanaAlertService;

    @PostMapping("send")
    public void receive(String sendTo, @RequestBody GrafanaAlertMsgDto msgDto) {
        grafanaAlertService.process(msgDto, sendTo);
    }
}
