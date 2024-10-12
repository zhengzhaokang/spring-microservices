package com.microservices.otmp.masterdata.domain;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Transaction Currency对象 biz_base_transaction_currency
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
public class BizBaseTransactionCurrency extends BaseDTO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** Transaction Currency */
    @Excel(name = "Transaction Currency")
    private String transactionCurrencyCode;

    /** Transaction Currency Name */
    @Excel(name = "Transaction Currency Name")
    private String transactionCurrencyName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTransactionCurrencyCode(String transactionCurrencyCode) 
    {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public String getTransactionCurrencyCode() 
    {
        return transactionCurrencyCode;
    }
    public void setTransactionCurrencyName(String transactionCurrencyName) 
    {
        this.transactionCurrencyName = transactionCurrencyName;
    }

    public String getTransactionCurrencyName() 
    {
        return transactionCurrencyName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("transactionCurrencyCode", getTransactionCurrencyCode())
            .append("transactionCurrencyName", getTransactionCurrencyName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
