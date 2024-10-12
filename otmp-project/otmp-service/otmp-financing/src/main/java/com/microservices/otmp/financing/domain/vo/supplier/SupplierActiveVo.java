package com.microservices.otmp.financing.domain.vo.supplier;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
public class SupplierActiveVo extends SupplierVo{

    private Integer status;
    private String onHoldStartTime;
    private String onHoldEndTime;
    private String activationDate;
    private String financingModel;
    private String supplierModel;
    private String email;
    /**
     * 激活/禁用日期
     */
    private String modifiedDate;
    private List<ErpMapping> erpList;
    private List<String> linkedBanks;
    private List<String> linkedEntities;

    private List<String> fileIds;
    private Boolean needUpdate;

    @Data
    public static class ErpMapping {
        private String entityCode;
        private String erpId;

        @Override
        public int hashCode(){
            return (entityCode + erpId).hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if(!(obj instanceof ErpMapping)){
                return false;
            }
            return this.hashCode() == obj.hashCode();
        }
    }

}
