package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AwaitingSupplierListParam extends SupplierListParam{

    private String emailAddress;
    private String inviteDate;
    private String inviteStartTime;
    private String inviteEndTime;

}
