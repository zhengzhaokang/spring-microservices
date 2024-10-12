package com.microservices.otmp.finance.domain.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

/**
 * Finance Batch Info对象 finance_batch
 * 
 * @author lovefamily
 * @date 2023-10-12
 */
@Data
public class FinanceBatchDTO extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Batch Number */
    @Excel(name = "Batch Number")
    private String batchNumber;

    /** Submission Date */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Submission Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submissionDate;

    /** Status */
    @Excel(name = "Status")
    private String status;

    /** Discount Date */
    //@JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Discount Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discountDate;

    /** Discount Amount */
    @Excel(name = "Discount Amount")
    private BigDecimal discountAmount;

    /** Delete Flag */
    @Excel(name = "Delete Flag")
    private String deleteFlag;

    /** Maturity Date */
    @Excel(name = "Maturity Date")
    private String maturityDate;

    private String submitCurrency;
    private String submitAmount;
    private String statusDescription;
    private Long supplierId;
    private String bankId;
    private Long entityId;
    private BigDecimal interestRate;
    private BigDecimal discount;
    private String tenor;

}
