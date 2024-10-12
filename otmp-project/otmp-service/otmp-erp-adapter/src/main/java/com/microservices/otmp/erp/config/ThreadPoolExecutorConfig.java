package com.microservices.otmp.erp.config;

import cn.hutool.core.util.RandomUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.*;

@Configuration
public class ThreadPoolExecutorConfig {

    private int cpuNum = Runtime.getRuntime().availableProcessors();

    @Bean("manageInvoiceTaskExecutor")
    public Executor manageInvoiceTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum * 3);
        executor.setMaxPoolSize(cpuNum * 5);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Invoice-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("bankTransferTaskExecutor")
    public Executor manageTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum + 1);
        executor.setMaxPoolSize(cpuNum * 2);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Bank-Transfer-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("manageDeliveryTaskExecutor")
    public Executor manageDeliveryTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(cpuNum * 3);
        executor.setMaxPoolSize(cpuNum * 5);
        executor.setQueueCapacity(200);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("Delivery-Executor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("asyncExecutor")
    public ExecutorService asyncExecutor() {
        return new ThreadPoolExecutor(cpuNum + 1, cpuNum * 2, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(200), runnable -> {
            Thread thread = new Thread();
            thread.setName("AsyncTask-" + RandomUtil.getRandom());
            return thread;
        });
    }

    /**
     * 异步导出数据 线程池
     *
     * @return
     */
    @Bean("asyncExportThreadExecutor")
    public Executor asyncExportThreadExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数
        executor.setCorePoolSize(cpuNum + 1);
        // 最大线程数
        executor.setMaxPoolSize(cpuNum * 2);
        // 缓冲队列
        executor.setQueueCapacity(50);
        // 空闲时间 秒
        executor.setKeepAliveSeconds(60);
        // 线程名称前缀
        executor.setThreadNamePrefix("Async-Export-Executor-");
        // 拒绝策略  调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }



}