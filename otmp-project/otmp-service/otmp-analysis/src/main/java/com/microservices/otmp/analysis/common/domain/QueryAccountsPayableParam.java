package com.microservices.otmp.analysis.common.domain;

import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;

@Data
public class QueryAccountsPayableParam extends BaseDTO {

    private String bankId;
    private Long entityId;
    private Long supplierId;
    private Integer type;
    private Integer amountType;
    private String typeValue;
}
