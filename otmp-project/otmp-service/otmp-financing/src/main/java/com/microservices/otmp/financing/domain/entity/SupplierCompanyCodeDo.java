package com.microservices.otmp.financing.domain.entity;

import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "supplier_company_code")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierCompanyCodeDo {

    @Id
    private Long id;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 供应商code
     */
    private String supplierCode;
    /**
     * 公司code
     */
    private String companyCode;
    /**
     * 数据状态，0：正常，1：禁用
     */
    private String deleteFlag;
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;

    public SupplierCompanyCodeDo(Long supplierId, String companyCode) {
        this.setSupplierId(supplierId);
        this.setCompanyCode(companyCode);
        this.setId(SnowFlakeUtil.nextId());
        this.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
    }

    public SupplierCompanyCodeDo(Long supplierId, String companyCode,String supplierCode) {
        this.setSupplierId(supplierId);
        this.setCompanyCode(companyCode);
        this.setSupplierCode(supplierCode);
        this.setId(SnowFlakeUtil.nextId());
        this.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!(obj instanceof SupplierCompanyCodeDo)){
            return false;
        }
        return this.hashCode() == obj.hashCode();
    }

    @Override
    public int hashCode(){
        return (supplierId + supplierCode + companyCode).hashCode();
    }
}
