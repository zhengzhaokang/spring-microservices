package com.microservices.otmp.common.core.domain;

import lombok.Data;

import java.util.Set;

@Data
public class FlowableResult {
    /**
     * * 需要发邮件的
     */
    Set<String> sendMailItCodes;
    /**
     * *
     */
    Set<String> taskUsers;
}
