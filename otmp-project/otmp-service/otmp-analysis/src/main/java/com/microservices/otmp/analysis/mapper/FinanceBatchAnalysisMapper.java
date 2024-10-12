package com.microservices.otmp.analysis.mapper;

import com.microservices.otmp.analysis.domain.entity.FinanceBatchAnalysisDo;
import com.microservices.otmp.common.core.dao.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface FinanceBatchAnalysisMapper extends BaseMapper<FinanceBatchAnalysisDo> {

    List<String> selectBatchNumByParam(@Param("startTime") Date startTime
            , @Param("endTime") Date endTime
            , @Param("bankId") String bankId
            , @Param("supplierId") Long supplierId
            , @Param("entityId") Long entityId);
}
