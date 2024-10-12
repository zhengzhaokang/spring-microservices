package com.microservices.otmp.erp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InvoiceMaturityAmountInfo {

    private Long id;
    private String supplierCode;
    private String companyCode;
    private String status;
    private BigDecimal amount;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date maturityDate;
    private Date createTime;
    private Date updateTime;
}
