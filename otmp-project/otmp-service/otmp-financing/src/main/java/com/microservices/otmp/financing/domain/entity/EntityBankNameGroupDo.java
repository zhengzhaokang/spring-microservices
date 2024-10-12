package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EntityBankNameGroupDo {

    private Long id;
    private String bankId;
    private Long entityId;
    private String bankName;
    private String entityName;
    private BigDecimal bankLimit;
    private BigDecimal availableLimit;
    private BigDecimal adhocLimit;
    private LocalDateTime adhocExpiryDate;
}
