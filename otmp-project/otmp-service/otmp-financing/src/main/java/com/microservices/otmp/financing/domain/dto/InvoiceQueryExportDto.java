package com.microservices.otmp.financing.domain.dto;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;

public class InvoiceQueryExportDto {

    @Excel(name = "Batch")
    private String batchNumber;
    @Excel(name = "Entity Name")
    private String entityName;
    @Excel(name = "Discount Date")
    private String discountDate;
    @Excel(name = "Discount Amount", align = Excel.Align.RIGHT)
    private String discountAmount;
    @Excel(name = "Rate(%)")
    private String rate;
    @Excel(name = "Discount")
    private String discount;

    public InvoiceQueryExportDto(InvoiceQueryDto dto) {
        this.batchNumber = dto.getBatchNumber();
        this.entityName = dto.getEntityName();
        this.discountDate = dto.getDiscountDateF();
        if (dto.getRate() != null) {
            this.rate = dto.getRate().toPlainString();
        }
        if (dto.getDiscountAmount() != null) {
            this.discountAmount = dto.getDiscountAmount().toPlainString();
        }
        if (dto.getDiscount() != null) {
            this.discount = dto.getDiscount().toPlainString();
        }
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public InvoiceQueryExportDto setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
        return this;
    }

    public String getEntityName() {
        return entityName;
    }

    public InvoiceQueryExportDto setEntityName(String entityName) {
        this.entityName = entityName;
        return this;
    }

    public String getDiscountDate() {
        return discountDate;
    }

    public InvoiceQueryExportDto setDiscountDate(String discountDate) {
        this.discountDate = discountDate;
        return this;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public InvoiceQueryExportDto setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
        return this;
    }

    public String getRate() {
        return rate;
    }

    public InvoiceQueryExportDto setRate(String rate) {
        this.rate = rate;
        return this;
    }

    public String getDiscount() {
        return discount;
    }

    public InvoiceQueryExportDto setDiscount(String discount) {
        this.discount = discount;
        return this;
    }
}
