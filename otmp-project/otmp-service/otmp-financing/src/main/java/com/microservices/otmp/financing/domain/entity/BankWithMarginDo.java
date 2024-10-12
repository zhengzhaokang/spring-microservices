package com.microservices.otmp.financing.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankWithMarginDo extends BankDo{

    private BigDecimal bankLimit;
    private BigDecimal adhocLimit;
    private Date adhocExpiryDate;
    private String margin;
    private Double amount;
    private Long entityId;

}
