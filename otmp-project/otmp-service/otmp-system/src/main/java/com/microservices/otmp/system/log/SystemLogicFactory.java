package com.microservices.otmp.system.log;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shirui3
 */
public class SystemLogicFactory {

    private static final Map<String, SystemLogHandler> STRATEGY_MAP = new HashMap<>();

    private SystemLogicFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static SystemLogHandler getInvokeStrategy(String flag) {
        return STRATEGY_MAP.get(flag);
    }

    public static void register(String flag, SystemLogHandler systemLogHandler) {
        if (StringUtils.isEmpty(flag) || systemLogHandler == null) {
            return;
        }
        //todo 重名 报错
        STRATEGY_MAP.put(flag, systemLogHandler);
    }


}
