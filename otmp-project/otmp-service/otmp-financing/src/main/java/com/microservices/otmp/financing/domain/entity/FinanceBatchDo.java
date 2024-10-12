package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "finance_batch")
@Data
public class FinanceBatchDo {

    public static final Integer STATUS_SUBMIT = 0;
    public static final Integer STATUS_PENDING = 1;
    public static final Integer STATUS_COMPLETED = 2;
    public static final Integer STATUS_REJECTED = 3;

    @Id
    private Long id;
    /**
     * 批编号
     */
    private String batchNumber;
    /**
     * 提交日期
     */
    private LocalDateTime submissionDate;
    private LocalDateTime discountDate;
    /**
     * 利息总额
     */
    private Double discountAmount;

    private String status;
    /**
     * 数据状态
     */
    private Boolean deleteFlag;
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;

    private String maturityDate;

    private String submitCurrency;
    private Double submitAmount;

    private Long supplierId;
    private String bankId;
    private Long entityId;
    private BigDecimal interestRate;
    private String tenor;
    private BigDecimal discount;
}
