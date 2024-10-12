package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

@Data
public class BankNameDo {

    private Long id;
    private Long supplierId;
//    private Long entityId;
    private String bankId;
//    private String entityName;
    private String bankName;

}
