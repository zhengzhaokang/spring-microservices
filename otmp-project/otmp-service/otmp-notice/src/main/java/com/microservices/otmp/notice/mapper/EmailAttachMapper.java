package com.microservices.otmp.notice.mapper;

import com.microservices.otmp.notice.domain.FinancingRate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmailAttachMapper {
    int insert(FinancingRate financingRate);

    int batchInsert(@Param("list") List<FinancingRate> list);

    FinancingRate selectRate(FinancingRate financingRate);

    int updateRate(FinancingRate financingRate);
}
