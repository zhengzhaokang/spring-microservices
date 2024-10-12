package com.microservices.otmp.financing.domain.vo.finance;

import com.microservices.otmp.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancingRateVO implements Serializable {
    private Long id;
    @Excel(name = "Rate Type")
    private String rateType;
    private Date rateDate;
    @Excel(name = "Rate Period")
    private String ratePeriod;
    @Excel(name = "Rate")
    private BigDecimal rate;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Boolean deleteFlag;
    @Excel(name = "Rate Date")
    private String rateDateF;
    private Integer ratePeriodKey;
}
