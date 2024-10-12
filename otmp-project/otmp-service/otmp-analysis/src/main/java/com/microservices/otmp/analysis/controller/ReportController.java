package com.microservices.otmp.analysis.controller;

import com.microservices.otmp.analysis.common.domain.QueryAccountsPayableParam;
import com.microservices.otmp.analysis.common.domain.SupplierAccountsValue;
import com.microservices.otmp.analysis.domain.vo.SupplierCompanyCodeInfo;
import com.microservices.otmp.analysis.domain.vo.SupplierInvoiceInfoVo;
import com.microservices.otmp.analysis.service.IReportService;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.microservices.otmp.common.exception.message.DefaultErrorMessage.REPORT_ROW_OUT_ERROR;

@RestController
@RequestMapping("/report")
@Api(value = "ReportController", tags = { "报表接口模块" })
public class ReportController extends BaseController {

    @Autowired
    private IReportService reportService;

    private static final  Integer excelMaxlimit = 50000;

    @ApiOperation(value = "AccountsAmountReport", notes = "查询AccountsAmountReport数据")
    @PostMapping(value = "/accountsAmountFinancingReport")
    public TableDataInfo getAccountsAmount(@RequestBody QueryAccountsPayableParam param) {
        List<SupplierCompanyCodeInfo> supplierCode = reportService.findSupplierCode(param);
        //开始分页查询
        startPage();
        List<SupplierAccountsValue> list = reportService.findAccountsFinancingReport(param, supplierCode);
        return getDataTable(list);
    }

    @ApiOperation(value = "supplierInvoiceReport", notes = "查询SupplierInvoiceReport数据")
    @PostMapping(value = "/supplierInvoiceReport")
    public TableDataInfo getSupplierInvoice(@RequestBody QueryAccountsPayableParam param) {
        List<SupplierCompanyCodeInfo> supplierCode = reportService.findSupplierCode(param);
        startPage();
        List<SupplierInvoiceInfoVo>  list = reportService.findSupplierInvoiceReport(param, supplierCode);
        return getDataTable(list);
    }

    @ApiOperation(value = "supplierInvoiceReport", notes = "查询SupplierInvoiceReport数据")
    @PostMapping(value = "/supplierInvoiceReport/export")
    public void exportSupplierInvoice(QueryAccountsPayableParam param, HttpServletResponse response) {
        List<SupplierCompanyCodeInfo> supplierCode = reportService.findSupplierCode(param);
        List<SupplierInvoiceInfoVo> list = reportService.findSupplierInvoiceReport(param, supplierCode);
        if(list.size() > excelMaxlimit) {
            throw new OtmpException(DefaultErrorMessage.ErrDescription.get(REPORT_ROW_OUT_ERROR), REPORT_ROW_OUT_ERROR.intValue());
        }
        NewExcelUtil<SupplierInvoiceInfoVo> util = new NewExcelUtil<>(SupplierInvoiceInfoVo.class);
        util.exportByFileType(response, list,"supplierInvoiceReportResult", getRequest().getHeader(Constants.FILE_TYPE));
    }

}
