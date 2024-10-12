package com.microservices.otmp.erp.controller;


import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.erp.domain.vo.InvoiceMaturityAmountInfoVo;
import com.microservices.otmp.erp.service.InvoiceMaturityAmountSerice;
import com.microservices.otmp.common.core.domain.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/maturity")
public class InvoiceMaturityAmountController {

    @Autowired
    private InvoiceMaturityAmountSerice invoiceMaturityAmountSerice;

    @GetMapping(value = "/amount")
    public ResultDTO<List<InvoiceMaturityAmountInfoVo>> aaturityAmount(@RequestParam("bankId") String bankId, @RequestParam("supplierId") String supplierId) {
        return ResultDTO.success(invoiceMaturityAmountSerice.searchMaturityAmount(bankId,supplierId));
    }
}
