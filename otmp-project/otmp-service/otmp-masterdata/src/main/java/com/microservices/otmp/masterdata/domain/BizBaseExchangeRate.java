package com.microservices.otmp.masterdata.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Exchange Rate对象 biz_base_exchange_rate
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
@Data
public class BizBaseExchangeRate extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    private  String ids;
    private List<Long> idsList;

    @Excel(name = "Currency", width = 20)
    private String currencyCode;

    /*ratevalue 区间 实际值如 1.3,232.3 */
    private String rateValueStr;

    @Excel(name = "Rate Value", align = Excel.Align.RIGHT, width = 15)
    private BigDecimal rateValue;

    @JsonFormat(pattern = "yyyy/MM/dd")
    @Excel(name = "Rate Date", width = 15, dateFormat = "MM/dd/yyyy")
    private Date rateDate;

    private String rateDateStr;

    @Excel(name = "ExchangeRate Type" ,width = 20)
    private String exchangeRateType;

    @Excel(name = "Creator",width = 15)
    private String createBy;

    private Date createTime;

    @Excel(name = "Creator Date", width = 15, dateFormat = "MM/dd/yyyy")
    private Date createDate;

    @Excel(name = "Creator Time", width = 15, dateFormat = "HH:mm:ss")
    private Date createSecond;

    @Excel(name = "Modifer",width = 15)
    private String updateBy;

    private Date updateTime;

    @Excel(name = "Modifer Date", width = 15, dateFormat = "MM/dd/yyyy")
    private Date updateDate;

    @Excel(name = "Modifer Time", width = 15, dateFormat = "HH:mm:ss")
    private Date updateSecond;

    private String status;

    /** 描述 */
    @Excel(name = "Remark",width = 15)
    private String remark;

    /**
     * Exchange Rate (T-L)
     */
    private BigDecimal exchangeRateL;
    /**
     * Exchange Rate (T-U)：
     */
    private BigDecimal exchangeRateU;

    /**
     * target Exchange Rate (T-L、P..)
     */
    private BigDecimal targetExchangeRate;

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        if(StringUtils.isNotBlank(currencyCode)){
            this.currencyCode = currencyCode.trim();
        }
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
        this.createDate = createTime;
        this.createSecond = createTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        this.updateDate = updateTime;
        this.updateSecond = updateTime;
    }
}
