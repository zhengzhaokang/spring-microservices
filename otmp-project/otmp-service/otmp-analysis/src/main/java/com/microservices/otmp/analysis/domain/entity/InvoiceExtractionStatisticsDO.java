package com.microservices.otmp.analysis.domain.entity;

import java.util.Date;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * Invoice extraction statistics对象 invoice_extraction_statistics
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@Data
public class InvoiceExtractionStatisticsDO extends BaseDO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Supplier Id
     */

    private Long supplierId;

    /**
     * Supplier Code
     */

    private String supplierCode;

    private String supplierName;

    /**
     * Company Code
     */

    private String companyCode;

    /**
     * All Invoice Count
     */

    private Long allInvoiceCount;

    /**
     * All Reject Invoice Count
     */

    private Long allRejectInvoiceCount;

    /**
     * All Credit Memo Invoice Count
     */

    private Long allCreditMemoInvoiceCount;

    /**
     * All Reject Credit Memo Invoice Count
     */

    private Long allRejectCreditMemoInvoiceCount;

    /**
     * Last Update Time
     */

    private Date lastUpdateTime;


}
