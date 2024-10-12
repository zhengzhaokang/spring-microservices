package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankOutstandingDo {

    private String bankId;
    private Long entityId;
    private BigDecimal outstanding;

}
