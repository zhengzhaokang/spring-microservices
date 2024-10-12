package com.microservices.otmp.bank.service.strategy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 策略工厂
 */
@Component
@Slf4j
public class BankStrategyFactory implements ApplicationContextAware {

    protected Map<String, IBankStrategy> strategyMap = new HashMap();

    /**
     * 加载所有的策略
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IBankStrategy> sourceStrategyMap = applicationContext.getBeansOfType(IBankStrategy.class);
        sourceStrategyMap.forEach((key, value) -> strategyMap.put(value.strategyType(), value));
        log.info("[加载BankStrategy策略完成，支持的类型：{}", strategyMap.keySet());

    }

    public IBankStrategy getBankStrategy(String strategyType) {
        if (strategyMap.isEmpty()) {
            log.error("{},BankStrategy策略未成功加载", strategyType);
            throw new RuntimeException("BankStrategy策略未成功加载");
        }
        IBankStrategy handleStrategy = strategyMap.get(strategyType);
        if (Objects.isNull(handleStrategy)) {
            log.error("未找到对应的BankStrategy策略,方法类型[{}]", strategyType);
            throw new RuntimeException("未找到对应的BankStrategy策略,方法类型[" + strategyType + "]");
        }
        return handleStrategy;

    }
}
