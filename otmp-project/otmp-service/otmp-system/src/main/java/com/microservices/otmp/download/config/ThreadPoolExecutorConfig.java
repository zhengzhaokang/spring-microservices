package com.microservices.otmp.download.config;

import cn.hutool.core.util.RandomUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExecutorConfig {

    private int cpuNum = Runtime.getRuntime().availableProcessors();

    @Bean("systemTaskExecutor")
    public Executor testExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum + 1);
        executor.setMaxPoolSize(cpuNum * 2);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("testExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("systemAsyncExecutor")
    public ExecutorService asyncExecutor() {
        return new ThreadPoolExecutor(cpuNum + 1, cpuNum * 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), runnable -> {
            Thread thread = new Thread();
            thread.setName("asyncTask" + RandomUtil.getRandom());
            return thread;
        });
    }
}
