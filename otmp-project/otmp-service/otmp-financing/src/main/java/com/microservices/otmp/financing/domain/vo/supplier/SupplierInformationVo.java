package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SupplierInformationVo {

    private String supplierCode;
    /**
     * 已绑定其他supplier，true：已绑定
     */
    private boolean bound;

    private SupplierGeneralInfo supplierGeneralInfo;
    private List<BankGeneralInfo> bankGeneralInfo;
    private Map<String, BankGeneralInfo> bankInfoMap;

    @Data
    public static class SupplierGeneralInfo {
        private String supplierName;
        private String companyCode;
        private String address;
        private String region;
        private String vat;
    }

    @Data
    public static class BankGeneralInfo{
        private String bankName;
        private String bankAccount;
        private String bankCountry;
        private String bankAddress;
        private String iban;
        private String swift;
    }
}
