package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.FinanceBatchInvoiceDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceBatchInvoiceMapper extends BaseMapper<FinanceBatchInvoiceDo> {

    void insertListWithId(@Param("invoices") List<FinanceBatchInvoiceDo> invoiceList);
}
