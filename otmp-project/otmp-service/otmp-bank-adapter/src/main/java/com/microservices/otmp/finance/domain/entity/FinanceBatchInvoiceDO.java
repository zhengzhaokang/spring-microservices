package com.microservices.otmp.finance.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Finance Batch Invoice对象 finance_batch_invoice
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceBatchInvoiceDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Batch Number */

    private String batchNumber;

    /** Bank Id */

    private String bankId;
    private String bankName;

    /** Invoice Id */

    private Long invoiceId;

    /** 状态（0正常 1停用） */

    private String status;

    /** 是否删除 0否 1是 */

    private Boolean deleteFlag;

    /** Supplier Code */

    private String supplierCode;

    /** Invoice Reference */

    private String invoiceReference;

    /** Entity Id */

    private Long entityId;


}
