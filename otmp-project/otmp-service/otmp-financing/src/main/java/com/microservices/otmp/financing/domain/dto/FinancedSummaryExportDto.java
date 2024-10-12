package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FinancedSummaryExportDto extends ExportDto {

    @Excel(name="Batch Number")
    private String batchNumber;

    @Excel(name="Supplier Name")
    private String supplierName;

    @Excel(name="Entity Name")
    private String entityName;

    @Excel(name="currency")
    private String currency;

    @Excel(name="Invoice Count")
    private Integer invoiceCount;

    @Excel(name="Invoice Amount", align = Excel.Align.RIGHT)
    private String invoiceAmount;

    @Excel(name="Credit Notes Count")
    private Integer creditCount;

    @Excel(name="Credit Notes Amount", align = Excel.Align.RIGHT)
    private String creditAmount;

    @Excel(name="Net Financed Amount", align = Excel.Align.RIGHT)
    private String netFinancedAmount;

    @Excel(name="Discount Date")
    private String discountDate;

    @Excel(name="Adjusted Due Date")
    private String adjustedDueDate;

    @Excel(name="No of Tenor Days")
    private String noOfTenorDays;

    @Excel(name="Confirmed Discount Rate")
    private String confirmedDiscountRate;

    @Excel(name="Confirmed Discount Charge", align = Excel.Align.RIGHT)
    private String confirmedDiscountCharge;
}
