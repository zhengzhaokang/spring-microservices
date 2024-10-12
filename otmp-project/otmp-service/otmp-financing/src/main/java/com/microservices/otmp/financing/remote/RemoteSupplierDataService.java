package com.microservices.otmp.financing.remote;

import com.microservices.otmp.common.constant.HttpStatus;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.financing.remote.factory.RemoteSupplierDataFallbackFactory;
import com.microservices.otmp.financing.remote.param.RemoteInvoiceSubmitParam;
import com.microservices.otmp.financing.remote.param.SupplierActivationParam;
import com.microservices.otmp.financing.remote.param.SupplierCodeParam;
import com.microservices.otmp.financing.remote.result.MaturityDateAmountResult;
import com.microservices.otmp.financing.remote.result.SupplierInfoResult;
import com.microservices.otmp.system.interceptor.APIQualifierInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "${otfp.service.erp.uri}", fallbackFactory = RemoteSupplierDataFallbackFactory.class, configuration = APIQualifierInterceptor.class)
public interface RemoteSupplierDataService {

    String STATUS_SUCCESS = "SUCCESS";
    String CODE_OK = "001";
    int CODE_OK_NUM = HttpStatus.SUCCESS;

    /**
     * 根据vendorCode查询
     */
    @PostMapping(value = "/api/supplierData/supplierDataSync")
    ResultDTO<SupplierInfoResult> supplierByVendorCode(@RequestBody SupplierCodeParam param);

    /**
     * 激活/onHold/cancel onHold
     */
    @PostMapping(value = "/api/supplierData/supplierFinancingActivation")
    ResultDTO<Object> callBackBankSuppliers(@RequestBody List<SupplierActivationParam> param);

    @PostMapping(value = "/api/supplierData/callBackBankSuppliers")
    ResultDTO<Object> submitInvoices(@RequestBody List<RemoteInvoiceSubmitParam> param);

    /**
     * 查询maturityDate对应的可融资金额
     */
    @GetMapping(value = "/api/maturity/amount")
    ResultDTO<List<MaturityDateAmountResult>> maturityDateAmount(@RequestParam("bankId") String bankId);

    @PostMapping(value = "/api/supplierData/computeInvoiceMaturityAmount/{type}")
    void computeInvoiceMaturityAmount(@PathVariable(value = "type") int type, @RequestBody List<String> invoiceIds);

}
