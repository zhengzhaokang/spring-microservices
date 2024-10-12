package com.microservices.otmp.financing.domain.vo.limit;

import lombok.Data;

import java.util.List;

@Data
public class LimitVo {

    private String id;
    private String bankName;
    private String bankId;
    private String entities;
    private String entityId;
    private List<SupplierSimpleInfo> suppliers;
    private String bankLimit;
    private String outstandingAmount;
    private String availableAmount;
    private String adhocLimit;
    private String adhocExpireDate;
    private List<OutStanding> outStandings;

    @Data
    public static class SupplierSimpleInfo{
        private String supplierId;
        private String supplierName;
        private String activationDate;
    }

    @Data
    public static class OutStanding{
        private String supplierId;
        private String supplierName;
        private String batchNumber;
        private String financingAmount;
        private String discountDate;
    }
}
