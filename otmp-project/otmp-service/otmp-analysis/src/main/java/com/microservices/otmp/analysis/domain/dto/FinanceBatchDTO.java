package com.microservices.otmp.analysis.domain.dto;

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
 * @date 2023-11-10
 */
@Data
public class FinanceBatchDTO extends BaseDTO {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * Status
     */
    @Excel(name = "Status Description",width = 25)
    private String status;


    /**
     * Batch Number
     */
    @Excel(name = "Batch Number",width = 25)
    private String batchNumber;

    /**
     * Submission Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Submission Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submissionDate;


    /**
     * Submit Amount
     */
    @Excel(name = "Financing Amount" ,align = Excel.Align.RIGHT)
    private BigDecimal submitAmount;
    /**
     * Submit Currency
     */
    @Excel(name = "Financing Currency")
    private String submitCurrency;


    /**
     * Discount Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
   // @Excel(name = "Discount Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discountDate;
    /**
     * Discount Amount
     */
    //@Excel(name = "Discount Amount" ,align = Excel.Align.RIGHT)
    private BigDecimal discountAmount;

    /**
     * Delete Flag
     */
    //@Excel(name = "Delete Flag")
    private Boolean deleteFlag;

    /**
     * Maturity Date
     */
    //@Excel(name = "Maturity Date")
    private String maturityDate;


    /**
     * Status Description
     */
    //@Excel(name = "Status Description")
    private String statusDescription;

    /**
     * Supplier Id
     */
    private Long supplierId;

    /**
     * Supplier Name
     */
    @Excel(name = "Supplier Name")
    private String supplierName;

    /**
     * Bank Id
     */
    private String bankId;

    /**
     * Bank Name
     */
    @Excel(name = "Bank Name")
    private String bankName;

    /**
     * Entity Id
     */
    private Long entityId;

    /**
     * Entity Name
     */
    @Excel(name = "Entity Name")
    private String entityName;

    /**
     * Interest Rate
     */
  //  @Excel(name = "Interest Rate" ,align = Excel.Align.RIGHT)
    private BigDecimal interestRate;

    /**
     * Tenor
     */
    //@Excel(name = "Tenor")
    private String tenor;

    /**
     * Interest Amount
     */
   // @Excel(name = "Interest Amount" ,align = Excel.Align.RIGHT)
    private BigDecimal discount;

    private String batchNumberList;


}
