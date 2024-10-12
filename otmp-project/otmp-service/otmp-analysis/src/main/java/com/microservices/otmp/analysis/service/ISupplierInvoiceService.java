package com.microservices.otmp.analysis.service;

import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsVO;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsValue;

import java.util.List;
import java.util.Map;

public interface ISupplierInvoiceService {

    Map<String, Integer> findSupplierInvoiceCount();

    List<Map<String, Object>>  findSupplierAmount();

    List<SupplierAccountsVO> findAccountsPayable(QueryAccountsPayableParam param);

    void doAccountsPayableJob();

    Map<String, Long> findModelRatio();
}
