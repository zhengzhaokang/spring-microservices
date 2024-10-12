package com.microservices.otmp.analysis.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
//@TableName("limit_statistic")
public class LimitStatisticDo {

    public static final Integer TYPE_BANK_LIMIT = 1;
    public static final Integer TYPE_OUTSTANDING = 2;

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
