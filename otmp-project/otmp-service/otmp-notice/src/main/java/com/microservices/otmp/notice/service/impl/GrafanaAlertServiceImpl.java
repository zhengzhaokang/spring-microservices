package com.microservices.otmp.notice.service.impl;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.microservices.otmp.notice.config.EmailUtils;
import com.microservices.otmp.notice.domain.EmailEntity;
import com.microservices.otmp.notice.domain.EmailParamEntity;
import com.microservices.otmp.notice.domain.entity.GrafanaAlertMsgDto;
import com.microservices.otmp.notice.service.IGrafanaAlertService;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author db117
 * @since 2023/3/9
 */
@Service
@Slf4j
@Ignore
public class GrafanaAlertServiceImpl implements IGrafanaAlertService {
    private static final ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
    @Value("${email-config.token}")
    private String emailToken;
    @Value("${email-config.text-api-url}")
    private String textApiPath;
    @Value("${email-config.service}")
    private String serviceName;

    @Override
    public void process(GrafanaAlertMsgDto msg, String sendTo) {
        List<String> send = StrUtil.split(sendTo, ',');
        if (send.isEmpty()) {
            log.warn("grafana 发送报警 没有配置发送人[{}]", msg);
            return;
        }
        // 构建发送对象
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setTextSendURL(textApiPath);
        EmailParamEntity param = new EmailParamEntity();
        emailEntity.setEmailParamEntity(param);
        param.setToken(emailToken);
        param.setService(serviceName);
        param.setTo(send);

        for (GrafanaAlertMsgDto.AlertsDTO alert : msg.getAlerts()) {
            try {
                // 获取头
                param.setSubject(alert.getLabels().get("alertname"));
                // 发送json 可以用html（后期优化）
                param.setContent(objectWriter.writeValueAsString(alert));

                EmailUtils.sendEmail(emailEntity);
            } catch (Exception e) {
                log.error("grafana 发送报警[{}]异常->[{}]", alert, e.getMessage(), e);
            }
        }
    }
}
