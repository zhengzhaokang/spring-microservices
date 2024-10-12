package com.microservices.otmp.erp.mapper;

import com.microservices.otmp.erp.domain.InvoiceMaturityAmountInfo;
import com.microservices.otmp.erp.domain.vo.InvoiceMaturityAmountInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InvoiceMaturityAmountInfoMapper {

    void saveBatch(@Param("list") List<InvoiceMaturityAmountInfo> invoiceMaturityAmountInfos);

    List<InvoiceMaturityAmountInfoVo> findInvoiceBankMaturityAmountInfo(@Param("bankId") String bankId, @Param("supplierId") String supplierId);

    List<InvoiceMaturityAmountInfo> findDbInvoiceMaturityAmountInfo(@Param("supplierCode") String supplierCode,@Param("companyCode") String companyCode);
}
