package com.microservices.otmp.analysis.mapper;


import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsValue;
import com.microservices.otmp.analysis.common.domain.SupplierAmount;
import com.microservices.otmp.analysis.common.domain.SupplierInvoiceCount;
import com.microservices.otmp.analysis.domain.vo.SupplierCompanyCodeInfo;
import com.microservices.otmp.analysis.domain.vo.SupplierInvoiceInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Mapper
public interface SupplierInvoiceMapper {

   List<SupplierInvoiceCount> findSupplierInvoiceCount();

    Integer findSupplierInvoiceAllCount();

    List<SupplierAmount> findSupplierAmount(@Param("status") String status);

    BigDecimal findBankLimit();

    List<SupplierAccountsValue> findAccountsAmount(QueryAccountsPayableParam param);

    void computePayableAccountsAmount();

    void computeFinancingAccountsAmount();

   List<SupplierCompanyCodeInfo> findSupplierCode(QueryAccountsPayableParam param);

    Integer findBoeModelRatio();

    Integer findAllModelRatio();

    List<SupplierAccountsValue> findAccountsFinancingAmount(@Param("param") QueryAccountsPayableParam param,@Param("supplierCodes") List<String> supplierCodes);

   List<SupplierInvoiceInfoVo> findSupplierInvoice(@Param("param") QueryAccountsPayableParam param,@Param("supplierCodes") List<String> supplierCodes
           ,@Param("companyCodes") List<String> companyCodes);
}