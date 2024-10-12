package com.microservices.otmp.financing.domain.param.limit;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditLimitParam {

    @NotNull
    private String id;
    private String limitAmount;
    private String adhocLimitAmount;
    private String adhocExpireDate;

    private String updateBy;
}
