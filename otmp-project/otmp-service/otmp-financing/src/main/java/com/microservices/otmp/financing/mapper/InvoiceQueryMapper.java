package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.financing.domain.dto.FinancingRateDTO;
import com.microservices.otmp.financing.domain.dto.InvoiceQueryDaoDto;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceQueryMapper {
    Integer submittedCount(@Param("param") InvoiceQueryParam param);
    List<InvoiceQueryDaoDto> submittedList(@Param("param") InvoiceQueryParam param);

    Integer rejectedCount(@Param("param") InvoiceQueryParam param);
    List<InvoiceQueryDaoDto> rejectedList(@Param("param") InvoiceQueryParam param);

    Integer financedCount(@Param("param") InvoiceQueryParam param);
    List<InvoiceQueryDaoDto> financedList(@Param("param") InvoiceQueryParam param);

    List<FinancingRateDTO> selectRate(FinancingRateDTO financingRate);

    List<InvoiceQueryDaoDto> financedDetailList(@Param("batchNumber") String batchNumber,@Param("type") String type);
    List<InvoiceQueryDaoDto> financedDetailListByBatches(@Param("batches") List<String> batchNumber,@Param("type") String type);
}
