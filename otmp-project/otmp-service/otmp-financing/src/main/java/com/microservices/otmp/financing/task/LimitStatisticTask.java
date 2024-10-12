package com.microservices.otmp.financing.task;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.financing.service.LimitService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@Component
public class LimitStatisticTask{

    public static final String LIMIT_STATISTIC_LOCK = "otfp::financing::limit::statistic::lock";

    @Autowired
    private LimitService limitService;
    @Autowired
    private RedissonClient redissonClient;

    @Scheduled(cron = "0 0 1 * * *")
//    @Scheduled(cron = "0 * * * * *")
    public void execute() {
        RLock lock = redissonClient.getLock(LIMIT_STATISTIC_LOCK);
        try {
            boolean tryLockResult = lock.tryLock(5, 5, TimeUnit.SECONDS);
            if (tryLockResult) {
                limitService.statisticLimitDaily(DateUtils.getNowDate());
            }
        } catch (InterruptedException e) {
            log.error("execute,try lock error", e);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR), DefaultErrorMessage.SERVER_ERROR.intValue());
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

}
