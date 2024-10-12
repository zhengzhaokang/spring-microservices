package com.microservices.otmp.financing.enums;

import com.microservices.otmp.financing.domain.vo.finance.FinancingRateVO;
import io.swagger.models.auth.In;
import lombok.Getter;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

@Getter
public enum RatePeriodEnum {
    RatePeriod1(1,"TSFR1M Index"),

    RatePeriod3(3,"TSFR3M Index"),

    RatePeriod6(6,"TSFR6M Index"),

    RatePeriod12(12,"TSFR12M Index");

    private final Integer code;
    private final String value;

    private RatePeriodEnum(Integer code, String value){
        this.code = code;
        this.value = value;
    }

    public static String getRateName(Integer index) {
        for (RatePeriodEnum c : RatePeriodEnum.values()) {
            if (c.getCode().equals(index)) {
                return c.value;
            }
        }
        return null;
    }

    public static List<FinancingRateVO> getRatePeriodCodes() {
        List<FinancingRateVO> rateTypes = Lists.newArrayList();
        for (RatePeriodEnum c : RatePeriodEnum.values()) {
            FinancingRateVO financingRateVO = new FinancingRateVO();
            financingRateVO.setRatePeriodKey(c.getCode());
            financingRateVO.setRatePeriod(c.getValue());
            rateTypes.add(financingRateVO);
        }
        return rateTypes;
    }

}
