package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * BizBasePreferredBankMaintenance对象 biz_base_preferred_bank_maintenance
 * 
 * @author xiaozy8
 * @date 2022-11-30
 */
@Data
public class BizBasePreferredBankMaintenance extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Geo */
    @Excel(name = "Geo")
    private String geo;

    /** Vendor Code */
    @Excel(name = "Vendor Code")
    private String vendorCode;

    /** Vendor Code */
    @Excel(name = "Vendor Name")
    private String vendorName;

    /** Bank Account */
    @Excel(name = "Bank Account")
    private String bankAccount;

    /** Bank Account */
    @Excel(name = "Bank Type")
    private String bankType;

    /** Remake */
    @Excel(name = "Remark")
    private String remark;

    private Date createTime;

    /** * 创建者 */
    @Excel(name = "Applicant", width = 25)
    private String createBy;

    /** * 创建时间 */
    @Excel(name = "Application Date", width = 25, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    /** * 创建时间 */
    @Excel(name = "Application Time", width = 25, dateFormat = "HH:mm:ss")
    private Date createSecond;

    private String swiftCode;

    private String accountHolder;

    private String bankName;

    private String ids;

    private List<Long> idsList;

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    public void setVendorCode(String vendorCode) {
        if(StringUtils.isNotBlank(vendorCode)) {
            this.vendorCode = vendorCode.trim();
        }
    }

    public void setVendorName(String vendorName) {
        if(StringUtils.isNotBlank(vendorName)) {
            this.vendorName = vendorName.trim();
        }
    }

    public void setBankAccount(String bankAccount) {
        if(StringUtils.isNotBlank(bankAccount)) {
            this.bankAccount = bankAccount.trim();
        }
    }

    public void setBankType(String bankType) {
        if(StringUtils.isNotBlank(bankType)) {
            this.bankType = bankType.trim();
        }
    }

    @Override
    public void setRemark(String remark) {
        if(StringUtils.isNotBlank(remark)) {
            this.remark = remark.trim();
        }

    }
}
