package com.microservices.otmp.financing.remote.result;

import lombok.Data;

import java.util.List;

@Data
public class SupplierInfoResult {

    private ResultHeader header;
    private ResultBody response;

    @Data
    public static class ResultHeader {
        private String status;
        private String statusCode;
        private String statusDesc;
    }

    @Data
    public static class ResultBody {
        private List<Record> item;
    }

    @Data
    public static class Record{
        private String supplierCode;
        private String supplierName;
        private List<BankInfo> bank;
        private List<CompanyInfo> companyLevel;
    }

    @Data
    public static class BankInfo{
        private String bankAccount;
        private String bankAddress;
        private String bankCountry;
        private String bankName;
        private String currency;
        private String iban;
        private String swift;
    }

    @Data
    public static class CompanyInfo{
        private String city;
        private String companyCode;
        private String entityName;
        private String postCode;
        private String region;
        private String street;
        private String telephone;
        private String vat;
    }
}
