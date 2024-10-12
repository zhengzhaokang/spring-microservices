package com.microservices.otmp.analysis.controller;

import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.service.ISupplierInvoiceService;
import com.microservices.otmp.common.core.domain.ResultDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/index")
@Api(value = "IndexController", tags = { "首页接口模块" })
public class IndexController {

    @Autowired
    private ISupplierInvoiceService supplierInvoiceService;

    @ApiOperation(value = "Suppliers", notes = "查询Suppliers数据")
    @GetMapping(value = "/suppliersCount")
    public ResultDTO<Object> getSuppliersCount() {
       return ResultDTO.success(supplierInvoiceService.findSupplierInvoiceCount());
    }

    @ApiOperation(value = "SuppliersAmount", notes = "查询SuppliersAmount数据")
    @GetMapping(value = "/suppliersAmount")
    public ResultDTO<Object> getSuppliersAmount() {
        return ResultDTO.success(supplierInvoiceService.findSupplierAmount());
    }

    @ApiOperation(value = "AccountsAmount", notes = "查询AccountsPayable数据")
    @PostMapping(value = "/AccountsAmount")
    public ResultDTO<Object> getAccountsAmount(@RequestBody QueryAccountsPayableParam param) {
        return ResultDTO.success(supplierInvoiceService.findAccountsPayable(param));
    }

    @ApiOperation(value = "Suppliers", notes = "执行Suppliers数据存库")
    @GetMapping(value = "/accountsPayableJob")
    public ResultDTO<Object> accountsPayableJob() {
        supplierInvoiceService.doAccountsPayableJob();
        return ResultDTO.success();
    }

    @ApiOperation(value = "modelRatio", notes = "modelRatio")
    @GetMapping(value = "/modelRatio")
    public ResultDTO<Object> getModelRatio() {
        return ResultDTO.success(supplierInvoiceService.findModelRatio());
    }
}
