package com.microservices.otmp.notice.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Grafana 报警消息
 * <a href="https://grafana.com/docs/grafana/latest/alerting/manage-notifications/webhook-notifier/"></a>
 *
 * @author db117
 * @since 2023/3/9
 */
@NoArgsConstructor
@Data
public class GrafanaAlertMsgDto {

    @JsonProperty("receiver")
    private String receiver;
    @JsonProperty("status")
    private String status;
    @JsonProperty("orgId")
    private Integer orgId;
    @JsonProperty("alerts")
    private List<AlertsDTO> alerts;
    @JsonProperty("externalURL")
    private String externalURL;
    @JsonProperty("version")
    private String version;
    @JsonProperty("groupKey")
    private String groupKey;
    @JsonProperty("truncatedAlerts")
    private Integer truncatedAlerts;
    @JsonProperty("title")
    private String title;
    @JsonProperty("state")
    private String state;
    @JsonProperty("message")
    private String message;

    @NoArgsConstructor
    @Data
    public static class AlertsDTO {
        @JsonProperty("status")
        private String status;
        @JsonProperty("labels")
        private Map<String, String> labels;
        @JsonProperty("annotations")
        private Map<String, String> annotations;
        @JsonProperty("startsAt")
        private String startsAt;
        @JsonProperty("endsAt")
        private String endsAt;
        @JsonProperty("generatorURL")
        private String generatorURL;
        @JsonProperty("fingerprint")
        private String fingerprint;
        @JsonProperty("silenceURL")
        private String silenceURL;
        @JsonProperty("dashboardURL")
        private String dashboardURL;
        @JsonProperty("panelURL")
        private String panelURL;
        @JsonProperty("valueString")
        private String valueString;
    }
}
