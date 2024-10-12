package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class ActiveSupplierParam {

    @NotNull
    private Long id;
    @NotNull
    private String supplierName;
    @NotNull
    private String contactFirstName;
    @NotNull
    private String contactLastName;
    @NotNull
    private String destination;
    @NotNull
    private String email;
    private String phone;
    private String phoneRegion;

    private String microservicesEntityName;
    private String preferredFinancingModel;
    private String primarySupplierCode;

    private String financingModel;
    private Double minimumNetFinancingAmount;
    private String currency;
    private Double topUpPricing;
    private Integer markAsInvalidDays;
    private String supplierModel;

    private String basisOfMaturityDateCalculation;
    private String paymentCycle;
    private String weekOfTheMonth;
    private Integer traceBackHistoryInvoiceDays;

    private List<String> fileIds;
    //    private List<FileData> fileData;
    @NotEmpty
    private List<ErpSupplier> erpIds;
    @NotEmpty
    private List<SupplierBankSetting> bankSettings;

    private String createBy;
    private String updateBy;
    private String maturityDateChangeable;

//    @Data
//    public static class FileData{
//        private String dataId;
//        private String attachments;
//    }

    @Data
    public static class ErpSupplier {
        private String supplierCode;
        private String companyCode;
    }

    @Data
    public static class SupplierBankSetting {
        private String bankId;
        private String buyerOrg;
        private String buyerOrgId;
        private Long entityId;
        private List<String> currency;
        private Double maximumInvoiceTenor;
        private Integer daysFromPostingDate;
        private Double invoicePercentage;
        private String benchmark;
        private String margin;
        private Double maximumFinanceTenor;
        /**
         * supplierCode配置
         */
        private UniqueIds uniqueIds;
        private Long entityBankRelId;
    }

    @Data
    public static class UniqueIds {
        private String shareSupplierUniqueId;
        private List<DifferentUniqueId> differentUniqueIds;

        public Map<String, String> getDifferentUniqueIdMap() {
            return differentUniqueIds.stream()
                    .collect(Collectors.toMap(DifferentUniqueId::getErpId, DifferentUniqueId::getUniqueId, (k1, k2) -> k1));
        }
    }

    @Data
    public static class DifferentUniqueId {
        private String erpId;
        private String uniqueId;
    }
}
