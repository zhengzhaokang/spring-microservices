package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierDetailVo extends SupplierSimpleVo {

    private List<SupplierInformationVo> supplierInfoList;
    private List<BankSettingVo> bankSettings;

    @Data
    public static class BankSettingVo {
        private String id;
        private String bankId;
        private String companyCode;
        private String buyerOrg;
        private String buyerOrgId;
        private String entityId;
        private Double maximumInvoiceTenor;
        private Integer daysFromPostingDate;
        private Double invoicePercentage;
        private String benchmark;
        private String margin;
        private Double maximumFinanceTenor;
        private boolean isDiff;
        private String uniqueId;
        private List<ErpSupplier> erps;
        private String entityBankRelId;
        private List<String> currency;
    }

    @Data
    public static class ErpSupplier{
        private String id;
        private String supplierCode;
        private String uniqueId;
    }
}
