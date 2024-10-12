package com.microservices.otmp.erp.mapper;

import java.util.Date;
import java.util.List;

import com.microservices.otmp.erp.domain.PayCycleCalendarConfig;
import com.microservices.otmp.erp.domain.S4ApiItem;
import com.microservices.otmp.erp.domain.SupplierCalendarConfig;
import com.microservices.otmp.erp.domain.SupplierInvoiceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author sunpan1
 */
@Mapper
public interface SupplierInvoiceInfoMapper {

    int insertBatch(@Param("list") List<SupplierInvoiceInfo> supplierInvoiceInfoList);

    void saveBatchItem(@Param("list")List<S4ApiItem> s4ApiItems);

    List<S4ApiItem> searchS4ApiItem();

    List<PayCycleCalendarConfig> findPayCycleCalendarConfig();

    void insertFinalBatch(@Param("list") List<SupplierInvoiceInfo> supplierInvoiceInfoList);

    List<String> findInvoiceReference(@Param("supplierCode") String supplierCode);

    SupplierCalendarConfig findSupplierCalendarConfig(@Param("supplierCode") String supplierCode);

    List<SupplierInvoiceInfo> findExpiredData(@Param("date") Date date);

    void deleteExpiredData(@Param("list") List<SupplierInvoiceInfo> expiredData);

    void updateFeedStatus(@Param("list") List<S4ApiItem> updateFeeds);

    void updateSupplierStatus(@Param("list") List<SupplierInvoiceInfo> suppliers);
    void updateSupplierStatusBatch(@Param("status") String status
            , @Param("statusDesc") String statusDesc
            , @Param("maturityDate") Date maturityDate
            , @Param("updateTime") Date updateTime
            , @Param("list") List<SupplierInvoiceInfo> suppliers);

    void updateFinalSupplierStatus(@Param("list") List<SupplierInvoiceInfo> suppliers);

    void updateFinalSupplierStatusBatch(@Param("status") String status
            , @Param("statusDesc") String statusDesc
            , @Param("updateTime") Date updateTime
            , @Param("list") List<SupplierInvoiceInfo> suppliers);

    List<String> findCurrency(@Param("supplierCode") String supplierCode);

    void updateExpiredDataStatus(@Param("list")List<SupplierInvoiceInfo> expiredData);

    List<SupplierInvoiceInfo> findSuppliersByIds(@Param("list") List<String> supplierIds);

    List<SupplierInvoiceInfo> findSuppliersByBatchNumbers(@Param("list")List<String> batchNumbers);

    List<SupplierInvoiceInfo> findInvoiceData(@Param("list") List<String> invoiceNo);
}