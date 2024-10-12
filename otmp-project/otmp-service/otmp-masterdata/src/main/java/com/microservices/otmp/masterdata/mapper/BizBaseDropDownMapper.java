package com.microservices.otmp.masterdata.mapper;

import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * bizBaseRegionMarketMapper接口
 *
 * @author lovefamily
 * @date 2022-04-18
 */
public interface BizBaseDropDownMapper
{
    List<BizBaseDropDownList> getBptype(BizBaseDropDownCondition bizBaseDropDownCondition);



}
