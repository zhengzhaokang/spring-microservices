package com.microservices.otmp.analysis.asynctask.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "control", name = "kafka", havingValue = "true", matchIfMissing = true)
public class MsAsyncTaskInfoListener {

    private static final Logger log = LoggerFactory.getLogger(MsAsyncTaskInfoListener.class);


}
