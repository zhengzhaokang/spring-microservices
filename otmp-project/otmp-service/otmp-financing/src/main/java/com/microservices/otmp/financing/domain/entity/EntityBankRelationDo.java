package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

@Data
public class EntityBankRelationDo {

    private Long id;
    private Long entityId;
    private String entityName;
    private String entityCurrency;
    private String bankId;
    private String bankName;
    private String bankCurrency;

}
