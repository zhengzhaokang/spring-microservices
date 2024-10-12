package com.microservices.otmp.financing.task;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.financing.mapper.InvoiceSubmitRecordMapper;
import com.microservices.otmp.financing.service.InvoiceService;
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
public class InvoiceSubmitRecordTask {

    private static final String LOCK_KEY = "otfp::financing::invoice::record::task";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private InvoiceService invoiceService;

    @Scheduled(cron = "0 */10 * * * *")
    public void checkMaturityDate(){

        RLock lock = redissonClient.getLock(LOCK_KEY);
        try {
            if (lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                invoiceService.processExpiredRecords("system");
            }
        } catch (InterruptedException e) {
            log.error("checkMaturityDate,try lock error", e);
            throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.SERVER_ERROR), DefaultErrorMessage.SERVER_ERROR.intValue());
        }finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }

    }

}
