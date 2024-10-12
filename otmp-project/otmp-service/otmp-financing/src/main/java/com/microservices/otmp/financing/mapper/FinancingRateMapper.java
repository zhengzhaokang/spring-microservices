package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.FinancingRateDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FinancingRateMapper extends BaseMapper<FinancingRateDo> {

    List<FinancingRateDo> selectLatestRate(@Param("type") String type, @Param("period") String period);

}
