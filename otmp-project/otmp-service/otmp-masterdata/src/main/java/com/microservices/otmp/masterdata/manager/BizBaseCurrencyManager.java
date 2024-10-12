package com.microservices.otmp.masterdata.manager;

import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */
public interface BizBaseCurrencyManager {

    /**
     * 通过currencyCode获取Currency数据
     * @param currencyCode
     * @return
     */
    BizBaseCurrencyDO selectBizBaseCurrencyByCode(@Param("currencyCode") String currencyCode);

    /**
     * 通过currencyCode集合批量获取Currency数据
     * @param currencyCodeList
     * @return
     */
    List<BizBaseCurrencyDO> selectBizBaseCurrencyByCodeList(@Param("currencyCodeList") List<String> currencyCodeList);
}
