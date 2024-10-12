package com.microservices.otmp.analysis.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BankInterestRateTrendVO extends BaseDTO {

    private Long id;

    /**
     * Bank Id
     */
    private String bankId;

    /**
     * Bank Name
     */
    private String bankName;
    private String status;
    /**
     * Supplier Id
     */
    private Long supplierId;
    /**
     * Supplier Name
     */
    @Excel(name = "Supplier Name",width = 30)
    private String supplierName;

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
     * Currency
     */
    @Excel(name = "Currency")
    private String currency;
    /**
     * Discount Amount
     */
    @Excel(name = "Discount Amount", align = Excel.Align.RIGHT,scale = 2)
    private BigDecimal discountAmount;

    /**
     * Discount Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Discount Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date discountDate;
    /**
     * Adjusted Due Date
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "Adjusted Due Date", width = 30, dateFormat = "yyyy-MM-dd")
    private Date adjustedDueDate;

    /**
     * No of Tenor Days
     */
    @Excel(name = "No of Tenor Days")
    private String noOfTenorDays;

    /**
     * Confirmed Interest Rate
     */
    @Excel(name = "Confirmed Interest Rate", align = Excel.Align.RIGHT,scale = 5)
    private BigDecimal confirmedInterestRate;
    /**
     * Confirmed Discount
     */
    @Excel(name = "Confirmed Discount", align = Excel.Align.RIGHT,scale = 2)
    private BigDecimal confirmedDiscount;

    private String batchNumberList;
    private String batchNumber;
    private String queryDateTime;
    private String lastUpdateTime;



}
