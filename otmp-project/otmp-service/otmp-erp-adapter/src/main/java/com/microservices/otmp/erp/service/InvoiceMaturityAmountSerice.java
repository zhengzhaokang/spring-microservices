package com.microservices.otmp.erp.service;

import com.microservices.otmp.erp.domain.InvoiceMaturityAmountInfo;
import com.microservices.otmp.erp.domain.vo.InvoiceMaturityAmountInfoVo;

import java.util.List;

public interface InvoiceMaturityAmountSerice {


    List<InvoiceMaturityAmountInfoVo> searchMaturityAmount(String bankId,String supplierId);
}
