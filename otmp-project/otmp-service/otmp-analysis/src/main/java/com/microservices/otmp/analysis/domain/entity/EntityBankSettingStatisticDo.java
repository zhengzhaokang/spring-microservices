package com.microservices.otmp.analysis.domain.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EntityBankSettingStatisticDo {

    private Long id;
    /**
     * 实体代码
     */
    private Long entityId;
    /**
     * 银行代码
     */
    private String bankId;
    /**
     * 限额
     */
    private Double bankLimit;
    /**
     * 临时限额
     */
    private Double adhocLimit;
    /**
     * 可用限额
     */
    private Double availableLimit;
    /**
     * adhoc过期时间
     */
    private LocalDateTime adhocExpiryDate;
    /**
     * 数据状态，0：禁用，1：正常
     */
    private String deleteFlag;

    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
}
