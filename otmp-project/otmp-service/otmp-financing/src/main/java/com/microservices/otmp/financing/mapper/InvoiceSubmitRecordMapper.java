package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.BankOutstandingDo;
import com.microservices.otmp.financing.domain.entity.InvoiceGroupAmount;
import com.microservices.otmp.financing.domain.entity.InvoiceSubmitRecord;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface InvoiceSubmitRecordMapper extends BaseMapper<InvoiceSubmitRecord> {

    void insertListWithId(@Param("records") List<InvoiceSubmitRecord> records);

    List<InvoiceSubmitRecord> selectRecordsBySupplierId(@Param("supplierId") Long supplierId, @Param("bankId") String bankId);

    List<InvoiceSubmitRecord> selectRecordsByBankId(@Param("bankId") String bankId);

    List<BankOutstandingDo> selectBankOutstanding(@Param("type") String invoiceType, @Param("bankIds") Collection<String> bankIds, @Param("entityIds") Collection<Long> entityIds);

    List<Long> selectExpiredRecordIds(@Param("expiryTime") Date expiryTime, @Param("size") Integer querySize);

    int updateExpireRecords(@Param("recordIds") List<Long> recordIds, @Param("updateBy") String updateBy, @Param("updateTime") LocalDateTime updateTime);

    List<InvoiceSubmitRecord> selectRecordsBySupplierIdAndBankIds(@Param("supplierId") Long supplierId, @Param("bankIds") Collection<String> sameRankBankIds);

    List<InvoiceSubmitRecord> selectRecordsByBankIdAndEntityIds(@Param("bankId") String bankId, @Param("entityIds") Collection<Long> entityIds);

    List<InvoiceGroupAmount> selectRecordsGroups(@Param("bankId") String bankId, @Param("entityIds") Collection<Long> entityIds);

    List<InvoiceGroupAmount> selectRecordGroupsByIds(@Param("bankIds") List<String> bankIds, @Param("entityIds") Collection<Long> entityIds);

    BigDecimal selectAmountByType(@Param("bankId") String bankId, @Param("type") String type, @Param("entityIds") Collection<Long> entityIds);

    int updateStatus(@Param("vendorCode") String vendorCode
            , @Param("invoiceReferences") List<String> invoiceReferences
            , @Param("oldStatus") String oldStatus
            , @Param("newStatus") String newStatus
            , @Param("updateTime") LocalDateTime updateTime
            , @Param("updateBy") String updateBy);

    int updateStatusByBatch(@Param("batchNum") String batchNum
            , @Param("oldStatus") String oldStatus
            , @Param("newStatus") String newStatus
            , @Param("updateTime") LocalDateTime updateTime
            , @Param("updateBy") String updateBy);
}
