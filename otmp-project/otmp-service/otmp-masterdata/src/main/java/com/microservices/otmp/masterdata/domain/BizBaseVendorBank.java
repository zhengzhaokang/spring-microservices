package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.utils.StringUtils;
import lombok.Data;

import java.util.Date;

/**
 * combine_base_data
 * 
 * @author sdms
 * @date 2022-01-15
 */
@Data
public class BizBaseVendorBank
{
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String vendorCode;

    private String bankType;

    private String bankAccount;

    private String bankAccountStar;

    private String bankNumber;

    private String bankName;

    private String bankCountry;

    private String swiftCode;

    private String iban;

    private String accountHolder;

    private String branchCode;

    private String status;

    private Date createTime;

    private Date updateTime;

    public void setBankAccount(String bankAccount) {
        if (StringUtils.isNotEmpty(bankAccount)) {
            this.bankAccount = bankAccount.trim();
            this.bankAccountStar = "******" + this.bankAccount.substring(this.bankAccount.length() - 3);
        }
    }
}
