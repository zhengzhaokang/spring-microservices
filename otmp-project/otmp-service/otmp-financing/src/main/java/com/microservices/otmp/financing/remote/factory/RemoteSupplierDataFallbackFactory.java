package com.microservices.otmp.financing.remote.factory;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.financing.remote.RemoteSupplierDataService;
import com.microservices.otmp.financing.remote.param.RemoteInvoiceSubmitParam;
import com.microservices.otmp.financing.remote.param.SupplierActivationParam;
import com.microservices.otmp.financing.remote.param.SupplierCodeParam;
import com.microservices.otmp.financing.remote.result.MaturityDateAmountResult;
import com.microservices.otmp.financing.remote.result.SupplierInfoResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteSupplierDataFallbackFactory implements FallbackFactory<RemoteSupplierDataService> {
    @Override
    public RemoteSupplierDataService create(Throwable throwable) {
        log.error("create,call remote error,msg:",throwable);
        return new RemoteSupplierDataService() {
            @Override
            public ResultDTO<SupplierInfoResult> supplierByVendorCode(SupplierCodeParam param) {
                log.error("remote call supplierByVendorCode error,param:{}",JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<Object> callBackBankSuppliers(List<SupplierActivationParam> param) {
                log.error("remote call callBackBankSuppliers error,request:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<Object> submitInvoices(List<RemoteInvoiceSubmitParam> param) {
                log.error("remote call submitInvoices error,request:{}", JsonUtil.toJSON(param));
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public ResultDTO<List<MaturityDateAmountResult>> maturityDateAmount(String bankId) {
                log.error("remote call maturityDateAmount error,bankId:{}",bankId);
                throw new OtmpException(throwable.getLocalizedMessage());
            }

            @Override
            public void computeInvoiceMaturityAmount(int type, List<String> invoiceIds) {
                log.error("remote call computeInvoiceMaturityAmount error,invoiceIds:{}",invoiceIds);
                throw new OtmpException(throwable.getLocalizedMessage());
            }
        };
    }
}
