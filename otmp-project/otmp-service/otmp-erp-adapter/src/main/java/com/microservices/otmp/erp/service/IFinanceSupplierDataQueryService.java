package com.microservices.otmp.erp.service;


import com.microservices.otmp.erp.domain.SupplierInvoiceFeedbackInfoVo;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import com.microservices.otmp.finance.CheckResult;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IFinanceSupplierDataQueryService {

    List<SupplierInvoiceFeedbackInfoVo> invalidData(List<String> invoiceNo);

    List<SupplierInvoiceFeedbackInfoVo> checkData(List<SupplierInvoiceInfo> suppliers) throws Exception;

    List<SupplierInvoiceFeedbackInfoVo> updateSuppliers(List<SupplierInvoiceInfo> suppliers, String status, String statusDesc, Date updateTime);

    void computeInvoiceMaturityAmount(List<SupplierInvoiceInfo> suppliers, BigDecimal variable);

    List<SupplierInvoiceInfo> findSuppliersByIds(List<String> supplierIds);

    List<SupplierInvoiceFeedbackInfoVo> findSupplierInvoiceFeedbackData(List<String> batchNumbers, String status,String statusDescription);
}
