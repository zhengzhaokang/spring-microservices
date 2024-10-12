package com.microservices.otmp.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.InvoiceCallBackEnum;
import com.microservices.otmp.common.enums.InvoiceStatusEnum;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierInvoiceFeedbackInfoVo;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.erp.domain.request.SupplierFinancingActivationRequest;
import com.microservices.otmp.erp.service.IFinanceSupplierDataQueryService;
import com.microservices.otmp.erp.service.ISupplierDataBackService;
import com.microservices.otmp.erp.service.ISupplierDataQueryService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author sunpan1
 */
@RestController
@Slf4j
@RequestMapping("/api/supplierData/")
public class SupplierDataQueryController {

    @Autowired
    private ISupplierDataQueryService supplierDataQueryService;
    @Autowired
    private IFinanceSupplierDataQueryService financeSupplierDataQueryService;
    @Autowired
    private ISupplierDataBackService supplierDataBackService;
    public static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    /**
     * 第三方系统从SCF抽取ECC供应商数据
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/getSupplierData", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<List<SupplierInvoiceFeedbackInfoVo>> getSupplierData(@RequestBody Map<String, Object> map) {
        log.info("getInfoByParam接口入参{}", map);
        List<SupplierInvoiceFeedbackInfoVo> backData = null;
        try {
            List<SupplierInvoiceInfo> suppliers = supplierDataQueryService.getSupplierData(map);
            if (!suppliers.isEmpty()) {
                supplierDataQueryService.saveSupplierData(suppliers);
                backData = financeSupplierDataQueryService.checkData(suppliers);
                supplierDataBackService.callBack(backData, InvoiceCallBackEnum.ACK.code);
            }
        } catch (Exception e) {
            log.error("getInfoByParam接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success(backData);
    }


    /**
     * SCF Supplier同步数据 API
     */
    @PostMapping(value = "supplierDataSync")
    public ResultDTO<Object> supplierDataSync(@RequestBody HashMap<String, Object> map) throws Exception {
        log.info("supplierDataSync接口入参{}", map);
        return ResultDTO.success(supplierDataQueryService.supplierDataSync(map));

    }

    @PostMapping(value = "otmpFeedApi")
    public ResultDTO<Object> otmpFeedApi(@RequestBody List<S4ApiItem> s4ApiItems) throws Exception {
        log.info("otmpFeedApi{}", s4ApiItems);
        return ResultDTO.success(supplierDataQueryService.s4ApiCallBack(s4ApiItems));
    }

    @GetMapping(value = "searchS4ApiItem")
    public ResultDTO<List<S4ApiItem>> searchS4ApiItem() {
        return ResultDTO.success(supplierDataQueryService.searchS4ApiItem());
    }

    /**
     * 校验无效发票及撤回
     *
     * @return
     */
    @PostMapping(value = "invalid")
    public ResultDTO<Object> checkData(@RequestBody List<String> invoiceNo) {
        try {
            List<SupplierInvoiceFeedbackInfoVo> expiredData = financeSupplierDataQueryService.invalidData(invoiceNo);
            if (!expiredData.isEmpty()) {
                supplierDataBackService.callBack(expiredData, InvoiceCallBackEnum.BANK.code);
            }
        } catch (Exception e) {
            log.error("checkData接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success("Suceess");
    }

    /**
     * 校验发票
     *
     * @param suppliers
     * @return
     */
    @RequestMapping(value = "/checkSuppliers", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<List<SupplierInvoiceFeedbackInfoVo>> checkSuppliers(@RequestBody List<SupplierInvoiceInfo> suppliers) {
        log.info("checkSuppliers接口入参{}", suppliers);
        List<SupplierInvoiceFeedbackInfoVo> msg = null;
        try {
            msg = financeSupplierDataQueryService.checkData(suppliers);
        } catch (Exception e) {
            log.error("checkSuppliers接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success(msg);
    }

    /**
     * callBack数据
     *
     * @param backDatas
     * @return
     */
    @RequestMapping(value = "/callBackSuppliers", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<Object> callBackSuppliers(@RequestBody List<SupplierInvoiceFeedbackInfoVo> backDatas) {
        log.info("checkSuppliers接口入参{}", backDatas);
        try {
            supplierDataBackService.callBack(backDatas, InvoiceCallBackEnum.ACK.code);
        } catch (Exception e) {
            log.error("callBackSuppliers接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success("Success");
    }

    /**
     * callBack数据
     *
     * @param backDatas
     * @return
     */
    @RequestMapping(value = "/callBackBankSuppliers", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<Object> callBackBankSuppliers(@RequestBody List<SupplierInvoiceFeedbackInfoVo> backDatas) {
        log.info("checkSuppliers接口入参{}", backDatas);
        try {
            supplierDataBackService.callBack(backDatas, InvoiceCallBackEnum.BANK.code);
        } catch (Exception e) {
            log.error("callBackSuppliers接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success("Success");
    }

    /**
     * 供应商激活接口
     */
    @PostMapping(value = "supplierFinancingActivation")
    public ResultDTO<Object> supplierFinancingActivation(@RequestBody List<SupplierFinancingActivationRequest> request) {
        log.info("supplierFinancingActivation接口入参{}", JSONObject.toJSONString(request));
        return ResultDTO.success(supplierDataQueryService.supplierFinancingActivation(request));
    }

    /**
     * 手动REJECT发票
     *
     * @param suppliers
     * @return
     */
    @RequestMapping(value = "/rejectSuppliers", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<List<SupplierInvoiceFeedbackInfoVo>> updateSuppliers(@RequestBody List<SupplierInvoiceInfo> suppliers) {
        log.info("rejectSuppliers接口入参{}", suppliers);
        List<SupplierInvoiceFeedbackInfoVo> backData = null;
        try {
            backData = financeSupplierDataQueryService.updateSuppliers(suppliers, InvoiceStatusEnum.REJECTED.code,null, DateUtils.getNowDate());
            supplierDataBackService.callBack(backData, InvoiceCallBackEnum.BANK.code);
        } catch (Exception e) {
            log.error("checkSuppliers接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success(backData);
    }

    @RequestMapping(value = "/computeInvoiceMaturityAmount/{type}", method = RequestMethod.POST, consumes = "application/json")
    public ResultDTO<List<SupplierInvoiceFeedbackInfoVo>> computeInvoiceMaturityAmount(
            @PathVariable("type") Integer type,
            @RequestBody List<String> supplierIds) {
        log.info("rejectSuppliers接口入参{}", supplierIds);
        try {
            type = type > 0 ? 1 : -1;
            List<SupplierInvoiceInfo> suppliers = financeSupplierDataQueryService.findSuppliersByIds(supplierIds);
            financeSupplierDataQueryService.computeInvoiceMaturityAmount(suppliers, new BigDecimal(type));
        } catch (Exception e) {
            log.error("computeInvoiceMaturityAmount接口异常：", e.getMessage(), e);
            return ResultDTO.error();
        }
        return ResultDTO.success();
    }

    @KafkaListener(containerFactory = "submissionKafkaFactory", topics = "${kafka.topic.bank-invoice-submission}")
    public void submissionListen(ConsumerRecord<String, String> record, Acknowledgment ack) throws Exception {
        log.info("topic = {}, partition={},offset = {}, value = {}", record.topic(), record.partition(), record.offset(), record.value());
        try {
            List<String> batchNumbers = Lists.newArrayList();
            JsonNode json = mapper.readTree(record.value());
            String status = json.get(0).get("status").asText();
            json.forEach(node -> {
                String batchNumber = node.get("batchNumber").asText();
                batchNumbers.add(batchNumber);
            });
            //查询发票并更新
            List<SupplierInvoiceFeedbackInfoVo> backDatas = financeSupplierDataQueryService.findSupplierInvoiceFeedbackData(batchNumbers, status, null);
            supplierDataBackService.callBack(backDatas, InvoiceCallBackEnum.BANK.code);
        } catch (Exception e) {
            log.error("submissionListen JSONObject解析异常：{}", e.getMessage());
        }finally {
            ack.acknowledge();
        }
    }

    @Data
    public static class UpdateBatch{
        private String discountDate;
        private String batchNumber;
        private String bankVendorCode;
        private String status;
        private String statusDescription;
        private String bankMaturityDate;
    }

    @PostMapping("update")
    public void updateBatch(@RequestBody UpdateBatch param){
        List<String> batchNumbers = new ArrayList<>();
        String batchNumber = param.getBatchNumber();
        batchNumbers.add(batchNumber);
        String status = param.getStatus();
        String statusDescription = param.getStatusDescription();
        String discountDate = param.getDiscountDate();
        String bankVendorCode = param.getBankVendorCode();
        String bankMaturityDate = param.getBankMaturityDate();

        //查询发票并更新
        List<SupplierInvoiceFeedbackInfoVo> backDatas = financeSupplierDataQueryService.findSupplierInvoiceFeedbackData(batchNumbers, status, statusDescription);
        String finalDiscountDate = discountDate;
        String finalStatusDescription = statusDescription;
        backDatas.stream().forEach(o -> {
            o.setBatchReference1(batchNumber);
            o.setStatusDescription(finalStatusDescription);
            o.setDiscountDate(finalDiscountDate);
            o.setBankVendorCode(bankVendorCode);
            o.setMessage(bankMaturityDate);
        });
        supplierDataBackService.callBack(backDatas, InvoiceCallBackEnum.BANK.code);
    }

    @KafkaListener(containerFactory = "transferKafkaFactory"
            , topics = "${kafka.topic.bank-invoice-transfer}"
            , properties = {"max.poll.interval.ms:300000"
                , "heartbeat.interval.ms:10000"
                , "auto.commit.interval.ms:10000"
                , "fetch.max.bytes:5242880"})
    public void transferListen(ConsumerRecord<String, String> record, Acknowledgment ack) throws Exception {
        log.info("topic = {}, partition={},offset = {}, value = {}", record.topic(), record.partition(), record.offset(), record.value());
        try {
            List<String> batchNumbers = Lists.newArrayList();
            JsonNode json = mapper.readTree(record.value());
            JsonResolveResult result = resolveValue(json, batchNumbers);
            long start = System.currentTimeMillis();
            //查询发票并更新
            List<SupplierInvoiceFeedbackInfoVo> backDatas = financeSupplierDataQueryService.findSupplierInvoiceFeedbackData(batchNumbers, result.status, result.statusDescription);
            long end = System.currentTimeMillis();
            log.info("transferListen,query back data cost time:{}",(end-start));
            String finalDiscountDate = result.discountDate;
            String finalStatusDescription = result.statusDescription;
            backDatas.forEach(o -> {
                o.setBatchReference1(result.batchNumber);
                o.setStatusDescription(finalStatusDescription);
                o.setDiscountDate(finalDiscountDate);
                o.setBankVendorCode(result.bankVendorCode);
                if(result.bankMaturityDate != null){
                    o.setMaturityDate(result.bankMaturityDate.asText());
                }
            });
            supplierDataBackService.callBack(backDatas, InvoiceCallBackEnum.BANK.code);
        } catch (Exception e) {
            log.error("transferListen JSONObject解析异常：{}", e.getMessage(), e);
        }finally {
            ack.acknowledge();
        }
    }

    private static JsonResolveResult resolveValue(JsonNode json, List<String> batchNumbers) {
        String status = json.get("status").asText();
        String batchNumber = json.get("microservicesBatchNumber").asText();
        JsonNode statusDes = json.get("statusDescription");
        String statusDescription = null;
        if(statusDes != null) {
            statusDescription = statusDes.asText();
        }
        JsonNode dis = json.get("discountDate");
        String discountDate = null;
        if(dis != null) {
            discountDate = dis.asText();
        }
        JsonNode bankMaturityDate = json.get("bankMaturityDate");
        String bankVendorCode = json.get("bankVendorCode").asText();
        batchNumbers.add(batchNumber);
        return new JsonResolveResult(status, batchNumber, statusDescription, discountDate, bankMaturityDate, bankVendorCode);
    }

    private static class JsonResolveResult {
        public final String status;
        public final String batchNumber;
        public final String statusDescription;
        public final String discountDate;
        public final JsonNode bankMaturityDate;
        public final String bankVendorCode;

        public JsonResolveResult(String status, String batchNumber, String statusDescription, String discountDate, JsonNode bankMaturityDate, String bankVendorCode) {
            this.status = status;
            this.batchNumber = batchNumber;
            this.statusDescription = statusDescription;
            this.discountDate = discountDate;
            this.bankMaturityDate = bankMaturityDate;
            this.bankVendorCode = bankVendorCode;
        }
    }
}