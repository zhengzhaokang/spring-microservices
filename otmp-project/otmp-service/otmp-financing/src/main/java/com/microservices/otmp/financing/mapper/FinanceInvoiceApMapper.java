package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Mapper
public interface FinanceInvoiceApMapper extends BaseMapper<FinanceInvoiceApDo> {

    List<FinanceInvoiceApDo> availableInvoiceFinanceList(@Param("maturityTime") String maturityTime
            , @Param("companyCodes") List<String> companyCodes
            , @Param("codes") List<SupplierUniqueIdDo> codes
            , @Param("queryOffset") Integer queryOffset
            , @Param("querySize") Integer querySize);

    Long availableInvoiceFinanceListCount(@Param("maturityTime") String maturityTime
            , @Param("companyCodes") List<String> companyCodes
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    List<FinanceInvoiceApDo> availableInvoiceFreeList(@Param("type") String type
            , @Param("issueStartTime") String issueStartTime
            , @Param("issueEndTime") String issueEndTime
            , @Param("maturityStartTime") String maturityStartTime
            , @Param("maturityEndTime") String maturityEndTime
            , @Param("companyCodes") List<String> companyCodes
            , @Param("vendorCodes") List<SupplierUniqueIdDo> codes);

    List<RejectedInvoiceDo> rejectedInvoiceList(@Param("type") String type
            , @Param("entityName") String entityName
            , @Param("invoiceNumber") String invoiceNumber
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    List<SubmittedInvoiceApDo> submittedInvoiceList(@Param("type") String type
            , @Param("entityName") String entityName
            , @Param("batchNumber") String batchNumber
            , @Param("submittedDate") String submittedDate
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    List<FinancedInvoiceBatchDo> financedInvoiceList(@Param("entityName") String entityName
            , @Param("discountDate") String discountDate
            , @Param("batchNum") String batchNum
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    List<FinanceInvoiceApDo> batchInvoiceDetail(@Param("batchId") Long batchId, @Param("type") String type);

    List<FinanceInvoiceApDo> selectInvoicesByIds(@Param("invoiceIds") List<Long> invoiceIds);

    List<FinanceInvoiceApDo> selectInvoicesBySupplierId(@Param("vendorCodes") Collection<String> vendorCodes, @Param("ids") List<Long> invoiceIds);

    void updateStatus(@Param("status") String status,
                      @Param("oldStatus") String oldStatus,
                      @Param("updateTime") LocalDateTime updateTime,
                      @Param("updateBy") String updateBy,
                      @Param("ids") List<Long> submitIds);

    List<FinanceInvoiceApDo> availableDebitFinance(@Param("codes") Collection<String> companyCodeSet, @Param("vendorCodes") List<SupplierUniqueIdDo> codes);

    List<FinanceInvoiceApDo> availableCreditFinance(@Param("codes") Collection<String> companyCodeSet, @Param("vendorCodes") List<SupplierUniqueIdDo> codes);

    BigDecimal availableDebitAmount(@Param("codes") Collection<String> companyCodes, @Param("vendorCodes") List<SupplierUniqueIdDo> codes);

    BigDecimal availableCreditAmount(@Param("codes") Collection<String> companyCodes, @Param("vendorCodes") List<SupplierUniqueIdDo> codes);

    List<FinanceInvoiceApDo> selectConfirmInvoices(@Param("maturityTime") String maturityTime
            , @Param("invoiceIds") List<Long> invoiceIds
            , @Param("companyCodes") List<String> companyCodes
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    Long selectConfirmInvoicesCount(@Param("maturityTime") String maturityTime
            , @Param("invoiceIds") List<Long> invoiceIds
            , @Param("companyCodes") List<String> companyCodes
            , @Param("codes") List<SupplierUniqueIdDo> codes);

    void updateConfirmedMaturityDate(@Param("time") LocalDateTime confirmedMaturityDate,@Param("data") List<FinanceInvoiceApDo> invoiceApList);

    void updateConfirmedMaturityDateByIds(@Param("ids") List<Long> invoiceIds,@Param("maturity") String maturity,@Param("confirmed") String confirmed,@Param("updateTime") LocalDateTime updateTime,@Param("updateBy") String updateBy);

    void insertListWithId(@Param("invoices") List<FinanceInvoiceApDo> invoices);
}
