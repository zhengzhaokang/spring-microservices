package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;

import java.util.List;

@Data
public class EntityBankPairVo {
    private String id;
    private String bankId;
    private String bankName;
    private String entityId;
    private String entityName;
    private List<String> commonCurrency;
}
