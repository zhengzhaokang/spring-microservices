package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.mapper.BizBaseCurrencyMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseDropDownMapper;
import com.microservices.otmp.masterdata.service.IBizBaseDropDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * bizBaseRegionMarketService业务层处理
 *
 * @author lovefamily
 * @date 2022-04-18
 */
@Service
public class BizBaseDropDownServiceImpl implements IBizBaseDropDownService
{
    @Autowired
    private BizBaseDropDownMapper bizBaseRegionMarketMapper;

    @Autowired
    private BizBaseCurrencyMapper bizBaseCurrencyMapper;


    @Override
    public List<BizBaseDropDownList> getBptype(BizBaseDropDownCondition bd)
    {
        return bizBaseRegionMarketMapper.getBptype(bd);
    }



}
