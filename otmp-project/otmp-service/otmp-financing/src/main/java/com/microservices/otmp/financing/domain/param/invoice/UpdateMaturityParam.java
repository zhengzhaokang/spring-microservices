package com.microservices.otmp.financing.domain.param.invoice;

import lombok.Data;

import java.util.List;

@Data
public class UpdateMaturityParam {

    private List<Long> unchecked;
    private List<Long> checked;
    private String bankId;
    private List<ParamItem> dates;
    private Long userId;

    @Data
    public static class ParamItem{
        private String maturityDate;
        private String confirmedDate;
    }
}
