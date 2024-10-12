package com.microservices.otmp.financing.domain.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankWithEntityIdDo extends BankDo{

    private Long entityId;

}
