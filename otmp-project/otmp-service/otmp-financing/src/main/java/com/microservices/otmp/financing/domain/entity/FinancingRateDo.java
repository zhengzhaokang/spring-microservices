package com.microservices.otmp.financing.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "financing_rate")
public class FinancingRateDo {

    public static final String RATE_TYPE_SOFR = "SOFR";

    public static final String PERIOD_1M = "TSFR1M Index";
    public static final String PERIOD_3M = "TSFR3M Index";
    public static final String PERIOD_6M = "TSFR6M Index";
    public static final String PERIOD_12M = "TSFR12M Index";

    @Id
    private Long id;
    private String rateType;
    private Date rateDate;
    private String ratePeriod;
    private BigDecimal rate;
    private String createBy;
    private Date createTime;
    private String updateBy;
    private Date updateTime;
    private Boolean deleteFlag;

}
