package com.microservices.otmp.masterdata.manager.impl;

import com.microservices.otmp.masterdata.domain.entity.BizBaseCurrencyDO;
import com.microservices.otmp.masterdata.manager.BizBaseCurrencyManager;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author guowb1
 * @description
 * @date 2022/6/1 18:02
 */

@Service
public class BizBaseCurrencyManagerImpl implements BizBaseCurrencyManager {

    @Autowired
    private BizBaseCurrencyMapper bizBaseCurrencyMapper;

    @Override
    public BizBaseCurrencyDO selectBizBaseCurrencyByCode(String currencyCode) {
        return bizBaseCurrencyMapper.selectBizBaseCurrencyByCode(currencyCode);
    }

    @Override
    public List<BizBaseCurrencyDO> selectBizBaseCurrencyByCodeList(List<String> currencyCodeList) {
        return bizBaseCurrencyMapper.selectBizBaseCurrencyByCodeList(currencyCodeList);
    }
}
