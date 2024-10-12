package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "bank")
@Data
public class BankDo {

    public static final String CHAIN_DBS = "DBS";
    public static final String CHAIN_BNPP = "BNPP";

    @Id
    private String id;
    /**
     * 银行名称
     */
    private String bankName;
    /**
     * 银行账号
     */
    private String bankAccount;
    /**
     * 地区
     */
    private String region;
    /**
     * 币种
     */
    private String currency;
    /**
     * 国际银行账号
     */
    private String bankIban;
    /**
     * 银行swift码
     */
    private String swiftCode;
    /**
     * 联系人
     */
    private String contactFocal;
    /**
     * 联系邮箱
     */
    private String contactEmail;
    /**
     * 整合链类型，可选值：BNPP，DBS
     */
    private String bankIntegrationChain;
    private String erpVendorId;
    private String bankAddress;
    private Integer rank;

    private Long iconFileId;
    private String iconPath;

    /**
     * 数据状态，0：禁用，1：正常
     */
    private String deleteFlag;

    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;

}
