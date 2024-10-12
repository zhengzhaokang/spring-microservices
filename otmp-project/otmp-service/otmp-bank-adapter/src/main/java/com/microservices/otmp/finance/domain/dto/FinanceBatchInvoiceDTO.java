package com.microservices.otmp.finance.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Finance Batch Invoice对象 finance_batch_invoice
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceBatchInvoiceDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Batch Number */
    @Excel(name = "Batch Number")
    private String batchNumber;

    /** Bank Id */
    @Excel(name = "Bank Id")
    private String bankId;

    /** Bank Name */
    @Excel(name = "Bank Name")
    private String bankName;

    /** Invoice Id */
    @Excel(name = "Invoice Id")
    private Long invoiceId;

    /** 状态（0正常 1停用） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 是否删除 0否 1是 */
    @Excel(name = "是否删除 0否 1是")
    private String deleteFlag;

    /** Supplier Code */
    @Excel(name = "Supplier Code")
    private String supplierCode;

    /** Invoice Reference */
    @Excel(name = "Invoice Reference")
    private String invoiceReference;

    /** Entity Id */
    @Excel(name = "Entity Id")
    private Long entityId;

}
