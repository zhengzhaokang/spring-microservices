package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.financing.domain.entity.InvoiceAmountDo;
import com.microservices.otmp.financing.domain.entity.InvoiceAmountParamDo;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceAmountInVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InvoiceAmountMapper {

    List<InvoiceAmountDo> availableAmount(@Param("param") InvoiceAmountParamDo param);

    List<InvoiceAmountDo> submittedAmount(@Param("param") InvoiceAmountParamDo param);

    List<InvoiceAmountDo> rejectedAmount(@Param("param") InvoiceAmountParamDo param);

    List<InvoiceAmountDo> apRejectedAmount(@Param("param") InvoiceAmountParamDo param);

    List<InvoiceAmountDo> financedAmount(@Param("param") InvoiceAmountParamDo param);
}
