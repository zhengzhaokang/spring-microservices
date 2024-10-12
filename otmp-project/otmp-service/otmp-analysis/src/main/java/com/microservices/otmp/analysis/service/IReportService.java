package com.microservices.otmp.analysis.service;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsValue;
import com.microservices.otmp.analysis.domain.vo.SupplierCompanyCodeInfo;
import com.microservices.otmp.analysis.domain.vo.SupplierInvoiceInfoVo;
import com.microservices.otmp.common.core.page.TableDataInfo;

import java.util.List;

public interface IReportService {

    List<SupplierAccountsValue> findAccountsFinancingReport(QueryAccountsPayableParam param, List<SupplierCompanyCodeInfo> supplierCode);

    List<SupplierInvoiceInfoVo> findSupplierInvoiceReport(QueryAccountsPayableParam param, List<SupplierCompanyCodeInfo> supplierCode);

    List<SupplierCompanyCodeInfo> findSupplierCode(QueryAccountsPayableParam param);
}
