package com.microservices.otmp.financing.domain.param.supplier;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class EditBankSettingParam {

    private String id;
    private String bankId;
    // 公司id
    private String companyCode;
    private String buyerOrg;
    private String buyerOrgId;
    private Double maximumInvoiceTenor;
    private Integer daysFromPostingDate;
    private Double invoicePercentage;
    private String benchmark;
    private String margin;
    private Double maximumFinanceTenor;
    private UniqueIds uniqueIds;

    private String updateBy;

    @Data
    public static class UniqueIds {
        private String shareSupplierUniqueId;
        private List<DifferentUniqueId> differentUniqueIds;

        public Map<String, String> getDifferentUniqueIdMap() {
            return differentUniqueIds.stream()
                    .collect(Collectors.toMap(DifferentUniqueId::getErpId, DifferentUniqueId::getUniqueId));
        }
    }

    @Data
    public static class DifferentUniqueId {
        private String id;
        private String erpId;
        private String uniqueId;
    }
}
