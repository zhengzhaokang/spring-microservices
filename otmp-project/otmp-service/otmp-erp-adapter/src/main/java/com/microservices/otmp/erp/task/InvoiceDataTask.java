package com.microservices.otmp.erp.task;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.microservices.otmp.common.enums.InvoiceCallBackEnum;
import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierInvoiceFeedbackInfoVo;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.erp.service.IFinanceSupplierDataQueryService;
import com.microservices.otmp.erp.service.ISupplierDataBackService;
import com.microservices.otmp.erp.service.ISupplierDataQueryService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class InvoiceDataTask {

    @Autowired
    private ISupplierDataQueryService supplierDataQueryService;
    @Autowired
    private IFinanceSupplierDataQueryService financeSupplierDataQueryService;
    @Autowired
    private ISupplierDataBackService supplierDataBackService;
    @Autowired
    private RedissonClient redissonClient;
    private static final String QUERY_INVOICE_LOCK_KEY = "tms::erp::adapter::query::lock";
    private static final String EXPIRE_INVOICE_LOCK_KEY = "tms::erp::adapter::expire::lock";

    @Scheduled(cron = "0 0 3 * * ? ")
    public void queryInvoiceDataByFeedJob() {
        RLock lock = redissonClient.getLock(QUERY_INVOICE_LOCK_KEY);
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                List<S4ApiItem> feeds = supplierDataQueryService.searchS4ApiItem();
                List<S4ApiItem> updateFeeds = Lists.newArrayList();
                feeds.stream().forEach(feed -> {
                    try {
                        //组装参数
                        Map<String, Object> param = initParam(feed);
                        List<SupplierInvoiceInfo> suppliers = doGetSupplierData(param);
                        if (!suppliers.isEmpty()) {
                            doSaveInvoce(suppliers);
                            List<SupplierInvoiceFeedbackInfoVo> backData = doCheckData(suppliers);
                            doCallBack(backData, InvoiceCallBackEnum.ACK.code);
                        }
                    } catch (Exception e) {
                        log.error("定时抽数JOB异常,参数:{}", JSONObject.toJSONString(feed), e.getMessage(), e);
                    }
                    updateFeeds.add(feed);
                });
                try {
                    doUpdateFeeds(updateFeeds);
                } catch (Exception e) {
                    log.error("定时JOB updateFeeds异常,参数:{}", JSONObject.toJSONString(updateFeeds), e.getMessage(), e);
                }
                try {
                    List<SupplierInvoiceFeedbackInfoVo> expiredData = doCheckExpiredData();
                    doCallBack(expiredData, InvoiceCallBackEnum.ACK.code);
                } catch (Exception e) {
                    log.error("定时JOB doCheckExpiredData异常", e.getMessage(), e);
                }
            }
        } catch (InterruptedException e) {
            log.error("queryInvoiceDataByFeedJob,get lock error:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }

    private List<SupplierInvoiceFeedbackInfoVo> doCheckExpiredData() {
        return financeSupplierDataQueryService.invalidData(null);
    }

    public void doUpdateFeeds(List<S4ApiItem> updateFeeds) {
        supplierDataQueryService.updateFeedStatus(updateFeeds);
    }

    public void doCallBack(List<SupplierInvoiceFeedbackInfoVo> backData, String type) {
        supplierDataBackService.callBack(backData, type);
    }

    public List<SupplierInvoiceFeedbackInfoVo> doCheckData(List<SupplierInvoiceInfo> suppliers) throws Exception {
        return financeSupplierDataQueryService.checkData(suppliers);
    }

    public void doSaveInvoce(List<SupplierInvoiceInfo> suppliers) {
        supplierDataQueryService.saveSupplierData(suppliers);
    }

    public List<SupplierInvoiceInfo> doGetSupplierData(Map<String, Object> param) {
        return supplierDataQueryService.getSupplierData(param);
    }

    public Map<String, Object> initParam(S4ApiItem feed) {
        //{"request":{"input":{"companyCode":"HK08", "vendorCode":"1000314513", "requestStartDate":"20230913", "requestEndDate":"20230913"}}}
        Map<String, Object> result = Maps.newHashMap();
        Map<String, Object> map = Maps.newHashMap();
        map.put("input", feed);
        result.put("request", map);
        return result;
    }

    @Scheduled(cron = "0 0 0 * * ? ")
    public void checkExpiredData() {
        RLock lock = redissonClient.getLock(QUERY_INVOICE_LOCK_KEY);
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)) {
                try {
                    List<SupplierInvoiceFeedbackInfoVo> expiredData = financeSupplierDataQueryService.invalidData(null);
                    if (!expiredData.isEmpty()) {
                        supplierDataBackService.callBack(expiredData, InvoiceCallBackEnum.BANK.code);
                    }
                } catch (Exception e) {
                    log.error("checkExpiredData接口异常：", e.getMessage(), e);
                }
            }
        } catch (InterruptedException e){
            log.error("checkExpiredData,get lock error:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }
}
