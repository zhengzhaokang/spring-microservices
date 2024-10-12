package com.microservices.otmp.analysis.common.enums;

import lombok.Getter;

/**
 * 分发后的数据发送枚举
 * 决定分发的数据怎么发，发到哪(目前都是kafka)
 *
 * @author db117
 * @since 2023/5/25
 */
@Getter
public enum TransferEnum {
    /**
     * 默认使用一个，在common进行转换处理直接调用 handle。否则直接把数据扔出去
     */
    COMMON("kafka.topic.report-transfer-common"),
    PAYMENT("kafka.topic.payment-report"),
    BILLING_RESULT_DETAIL("kafka.topic.billing-aur-report"),
    //    BILLING_RESULT("kafka.topic.billing-result-report"),
    BILLING_RESULT("kafka.topic.report-transfer-common"),

    RDR("kafka.topic.report-transfer-rdr"),

    BILLING_HISTORY("kafka.topic.report-transfer-billing-history"),

    POOL_MONTH_DIFF("kafka.topic.report-transfer-pool-month-diff"),

    POOL_REAL_TIME("kafka.topic.report-transfer-common"),
    ;
    private final String propertyName;


    TransferEnum(String propertyName) {
        this.propertyName = propertyName;
    }

}
