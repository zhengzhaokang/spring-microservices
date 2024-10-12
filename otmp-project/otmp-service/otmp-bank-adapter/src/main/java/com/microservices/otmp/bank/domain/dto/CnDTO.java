package com.microservices.otmp.bank.domain.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/10
 * time: 14:33
 * Description:
 */
@Data
public class CnDTO {

    //CN 数据
    private String identifier;
    /**
     * 供应商主数据(供应商id)
     */
    private String erpId;
    /**
     * 参考
     */
    private String creditNoteReference;
    /**
     * 签发日期
     */
    private Date creditNoteIssueDate;
    /**
     * 到期日
     */
    private Date creditNoteEffectiveDate;
    /**
     * 货币
     */
    private Integer creditNoteCurrency;
    /**
     * 金额
     */
    private BigDecimal creditNoteAmount;
    /**
     * 附件1
     */
    private String additionalReference1;
    /**
     * 附件2
     */
    private Date additionalReference2;
    /**
     * 附件3
     */
    private String additionalReference3;
    /**
     * 附件4
     */
    private String additionalReference4;
}
