package com.microservices.otmp.financing.domain.entity;

import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.param.supplier.ActiveSupplierParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table(name="supplier_bank_setting")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierBankSettingDo {

    public static final Double DEFAULT_PERCENTAGE = 100.0;

    @Id
    private Long id;
    /**
     * 供应商id
     */
    private Long supplierId;
    /**
     * 银行id
     */
    private String bankId;
    private Long entityId;
    private String buyerOrg;
    private String buyerOrgId;
    private String currency;
    private Double maximumInvoiceTenor;
    private Integer daysFromPostingDate;
    private Double invoicePercentage;
    private String margin;
    private Double maximumFinanceTenor;
    private String benchMark;
    private String companyCode;
    private List<SupplierUniqueIdDo> uniqueInfoList;
    private Long entityBankRelId;

    /**
     * 数据状态，0：正常，1：禁用
     */
    private String deleteFlag;
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }
        if(!(obj instanceof SupplierBankSettingDo)){
            return false;
        }
        if(obj == this){
            return true;
        }
        SupplierBankSettingDo setting = (SupplierBankSettingDo) obj;
        if(setting.getBankId() == null || this.getBankId() == null){
            return false;
        }
        return this.getBankId().equals(setting.getBankId());
    }

    @Override
    public int hashCode(){
        return bankId.hashCode();
    }

    public SupplierBankSettingDo(ActiveSupplierParam param, ActiveSupplierParam.SupplierBankSetting bankSetting, SupplierDo supplier) {

        this.currency = CollectionUtils.isEmpty(bankSetting.getCurrency()) ? null : String.join(StringConstants.PROP_SEPARATOR,bankSetting.getCurrency());
        this.supplierId = supplier.getId();
        this.bankId = bankSetting.getBankId();
        this.entityId = bankSetting.getEntityId();
        this.buyerOrg = bankSetting.getBuyerOrg();
        this.buyerOrgId = bankSetting.getBuyerOrgId();
        this.maximumFinanceTenor = bankSetting.getMaximumFinanceTenor();
        this.daysFromPostingDate = bankSetting.getDaysFromPostingDate();
        this.invoicePercentage = bankSetting.getInvoicePercentage();
        this.margin = bankSetting.getMargin();
        this.maximumInvoiceTenor = bankSetting.getMaximumInvoiceTenor();
        this.benchMark = bankSetting.getBenchmark();
        this.entityBankRelId = bankSetting.getEntityBankRelId();

        this.deleteFlag = StateConstants.FLAG_NORMAL_STR;
        this.setId(SnowFlakeUtil.nextId());
    }
}
