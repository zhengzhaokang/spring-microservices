package com.microservices.otmp.financing.domain.vo.invoice;

import lombok.Data;

@Data
public class MaturityGroupVo {
    private String entityName;
    private String maturityDate;
    private Integer daysFromPostingDate;

    public MaturityGroupVo() {
    }

    public MaturityGroupVo(String maturityDate) {
        this.maturityDate = maturityDate;
    }

    public MaturityGroupVo(String entityName, String maturityDate, Integer daysFromPostingDate) {
        this.entityName = entityName;
        this.maturityDate = maturityDate;
        this.daysFromPostingDate = daysFromPostingDate;
    }
}
