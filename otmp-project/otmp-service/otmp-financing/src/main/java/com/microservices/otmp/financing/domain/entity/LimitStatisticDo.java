package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "limit_statistic")
public class LimitStatisticDo {

    public static final Integer TYPE_BANK_LIMIT = 1;
    public static final Integer TYPE_OUTSTANDING = 2;

    @Id
    private Long id;
    private String bankId;
    private Long entityId;
    private String amount;
    private Integer type;
    private String statisticDate;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;

}
