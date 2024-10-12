package com.microservices.otmp.erp.service;

import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierInvoiceFeedbackInfoVo;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISupplierDataBackService {

    void callBack(List<SupplierInvoiceFeedbackInfoVo> backData, String type);
}
