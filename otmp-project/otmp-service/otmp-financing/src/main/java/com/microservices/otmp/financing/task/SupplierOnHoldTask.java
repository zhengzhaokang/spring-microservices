package com.microservices.otmp.financing.task;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.financing.service.SupplierService;
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
public class SupplierOnHoldTask {

    private static final String ON_HOLD_TASK_KEY = "otfp::financing::task::onHold::task";
    private static final String CANCEL_HOLD_TASK_KEY = "otfp::financing::task::cancelHold::task";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SupplierService supplierService;

    /**
     * 设置onHold状态
     */
    @Scheduled(cron = "0 * * * * *")
    public void setOnHoldStatus() {
        RLock lock = redissonClient.getLock(ON_HOLD_TASK_KEY);
        try {
            if (lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                supplierService.doOnHold();
            }
        } catch (InterruptedException e) {
            log.error("setOnHoldStatus,try lock error", e);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR), DefaultErrorMessage.SERVER_ERROR.intValue());
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    /**
     * 取消onHold状态
     */
    @Scheduled(cron = "0 * * * * *")
    public void cancelOnHoldStatus() {
        RLock lock = redissonClient.getLock(CANCEL_HOLD_TASK_KEY);
        try {
            if (lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                supplierService.doCancelOnHold();
            }
        } catch (InterruptedException e) {
            log.error("cancelOnHoldStatus,try lock error", e);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR), DefaultErrorMessage.SERVER_ERROR.intValue());
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
