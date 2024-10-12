package com.microservices.otmp.financing.domain.param.invoice;

import lombok.Data;

import java.util.List;

@Data
public class MaturityDateGroupParam {

    private List<Long> unchecked;
    private List<Long> checked;
    private String bankId;

    private Long userId;
}
