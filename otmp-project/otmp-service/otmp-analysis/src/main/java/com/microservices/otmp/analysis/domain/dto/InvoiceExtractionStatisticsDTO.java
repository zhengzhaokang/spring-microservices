package com.microservices.otmp.analysis.domain.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Invoice extraction statistics对象 invoice_extraction_statistics
 *
 * @author lovefamily
 * @date 2023-10-27
 */
@Data
public class InvoiceExtractionStatisticsDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Supplier Id
     */
    @Excel(name = "Supplier Id")
    private Long supplierId;

    /**
     * Supplier Code
     */
    @Excel(name = "Supplier Code")
    private String supplierCode;

    /**
     * Supplier Name
     */
    @Excel(name = "Supplier Name")
    private String supplierName;

    /**
     * Company Code
     */
    @Excel(name = "Company Code")
    private String companyCode;

    /**
     * All Invoice Count
     */
    @Excel(name = "All Invoice Count")
    private Long allInvoiceCount;

    /**
     * All Reject Invoice Count
     */
    @Excel(name = "All Reject Invoice Count")
    private Long allRejectInvoiceCount;

    /**
     * All Credit Memo Invoice Count
     */
    @Excel(name = "All Credit Memo Invoice Count")
    private Long allCreditMemoInvoiceCount;

    /**
     * All Reject Credit Memo Invoice Count
     */
    @Excel(name = "All Reject Credit Memo Invoice Count")
    private Long allRejectCreditMemoInvoiceCount;

    /**
     * Last Update Time
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Last Update Time", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

}
