package com.microservices.otmp.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BankLimit {

    private String bankId;
    private String bankName;
    private BigDecimal limit;

    public BankLimit() {
    }

    public BankLimit(String bankId, Double limit) {
        this(bankId, null, limit);
    }

    public BankLimit(String bankId, String bankName, Double limit) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.limit = new BigDecimal(String.valueOf(limit));
    }

}
