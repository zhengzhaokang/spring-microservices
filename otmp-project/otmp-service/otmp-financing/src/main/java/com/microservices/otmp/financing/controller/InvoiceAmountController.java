package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceAmountInVo;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceVo;
import com.microservices.otmp.financing.service.InvoiceAmountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * invoice菜单各tab的Amount查询接口
 */
@RequestMapping("/invoice/amount")
@RestController
@Slf4j
public class InvoiceAmountController extends BaseController {

    @Autowired
    private InvoiceAmountService invoiceAmountService;

    @PostMapping("/available")
    public ResultDTO<InvoiceAmountInVo> availableAmount(@RequestBody InvoiceQueryParam param) {
        if (StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceAmountController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceAmountService.availableAmount(param));
    }

    @PostMapping("/submitted")
    public ResultDTO<InvoiceAmountInVo> submittedAmount(@RequestBody InvoiceQueryParam param) {
        if (StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceAmountController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceAmountService.submittedAmount(param));
    }

    @PostMapping("/rejected")
    public ResultDTO<InvoiceAmountInVo> rejectedAmount(@RequestBody InvoiceQueryParam param) {
        if (StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceAmountController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceAmountService.rejectedAmount(param));
    }

    @PostMapping("/financed")
    public ResultDTO<InvoiceAmountInVo> financedAmount(@RequestBody InvoiceQueryParam param) {
        if (StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceAmountController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceAmountService.financedAmount(param));
    }
}
