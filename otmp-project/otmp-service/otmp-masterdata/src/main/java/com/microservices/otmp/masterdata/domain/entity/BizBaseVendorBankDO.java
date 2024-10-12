package com.microservices.otmp.masterdata.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;

/**
 * bank info by lgvm对象 biz_base_bank_api_lgvm
 * 
 * @author lovefamily
 * @date 2023-03-20
 */
@Data
public class BizBaseVendorBankDO extends BaseDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** $column.columnComment */

    private String vendorCode;

    /** Bank Type */

    private String bankType;

    /** Bank Account */

    private String bankAccount;

    /** Bank Number */

    private String bankNumber;

    /** Bank Name */

    private String bankName;

    /** Bank Country */

    private String bankCountry;

    /** Swift Code */

    private String swiftCode;

    /** IBAN */

    private String iban;

    /** Account Holder */

    private String accountHolder;

    /** Branch Code */

    private String branchCode;

    /** 是否逻辑删除 */

    private Boolean deleteFlag;


}
