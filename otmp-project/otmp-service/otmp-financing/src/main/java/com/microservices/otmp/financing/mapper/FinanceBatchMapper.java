package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.FinanceBatchDo;
import com.microservices.otmp.financing.domain.entity.FinanceBatchExportDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FinanceBatchMapper extends BaseMapper<FinanceBatchDo> {
    void insertListWithId(@Param("batches")List<FinanceBatchDo> batches);

    FinanceBatchDo checkBatchDuplicate(@Param("batches") List<FinanceBatchDo> batches);

    List<FinanceBatchExportDo> selectByBatchNumbers(@Param("batchNumbers") List<String> batchNumbers);
}
