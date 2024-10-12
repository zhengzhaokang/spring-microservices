package com.microservices.otmp.financing.domain.dto;

import lombok.Data;

import java.util.Map;

@Data
public class BankTransferDto {

//    private Long id;
//    private String invoiceId;
    private Long createTime;
    private String microservicesBatchNumber;
    private String status;
    private String bankVendorCode;
//    private Map<Object,Object> params;
}
