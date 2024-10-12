package com.microservices.otmp.masterdata.service;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;

import java.util.List;

/**
 * bizBaseRegionMarketService接口
 *
 * @author lovefamily
 * @date 2022-04-18
 */
public interface IBizBaseDropDownService
{
    public List<BizBaseDropDownList> getBptype(BizBaseDropDownCondition bd);

}
