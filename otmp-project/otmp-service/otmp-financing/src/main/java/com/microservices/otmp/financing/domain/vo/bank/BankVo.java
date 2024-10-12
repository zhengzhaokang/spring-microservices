package com.microservices.otmp.financing.domain.vo.bank;

import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.entity.BankDo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BankVo {

    public BankVo(BankDo b){
        this.id = String.valueOf(b.getId());
        this.bankName = b.getBankName();
        this.region = b.getRegion();
        this.iban = b.getBankIban();
        this.swift = b.getSwiftCode();
        this.account = b.getBankAccount();
        this.contactFocal = b.getContactFocal();
        this.contactMail = b.getContactEmail();
        this.integrationChain = b.getBankIntegrationChain();
        this.erpVendorId = b.getErpVendorId();
        this.bankAddress = b.getBankAddress();
        this.rank = b.getRank();
        this.iconFileId = b.getIconFileId();
        this.iconPath = b.getIconPath();
        if(b.getCurrency() !=null){
            this.currency = Arrays.asList(b.getCurrency().split(StringConstants.PROP_SEPARATOR));
        }
    }

    private String id;
    private String bankName;
    private String region;
    private List<String> currency;
    private String iban;
    private String swift;
    private String account;
    private String contactFocal;
    private String contactMail;
    private String integrationChain;
    private String erpVendorId;
    private String bankAddress;
    private Integer rank;
    private Long iconFileId;
    private String iconPath;

    private Double margin;
    private Double amount;

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }
        if(!(obj instanceof BankVo)){
            return false;
        }
        return obj.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

}
