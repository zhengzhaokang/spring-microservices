package com.microservices.otmp.financing.controller;

import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.dto.*;
import com.microservices.otmp.financing.domain.param.invoice.FinancedInvoiceExportParam;
import com.microservices.otmp.financing.domain.param.invoice.InvoiceQueryParam;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateDashboard;
import com.microservices.otmp.financing.domain.vo.finance.FinancingRateVO;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceBatchSummaryVo;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryDto;
import com.microservices.otmp.financing.domain.vo.invoice.InvoiceQueryVO;
import com.microservices.otmp.financing.enums.RatePeriodEnum;
import com.microservices.otmp.financing.service.InvoiceQueryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 提交、拒绝、融资  发票列表查询
 */

@RequestMapping("/invoice/query")
@RestController
@Slf4j
public class InvoiceQueryController extends BaseController {

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    //    @OperLog(title = "查询已提交的发票列表", businessType = BusinessType.QUERY)
    @PostMapping("/submitted/list")
    public ResultDTO<InvoiceQueryVO> submittedList(@RequestBody InvoiceQueryParam param) {
        if (param == null || StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceQueryController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceQueryService.submittedList(param));
    }

    //    @OperLog(title = "导出已提交的发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/submitted/list/export")
    public void exportSubmitList(InvoiceQueryParam param, HttpServletResponse response) {
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        param.setUserId(getCurrentUserId());

        List<SubmitExportDto> allInvoices = new ArrayList<>();
        param.setType(InvoiceConstants.INVOICE_TYPE_DEBIT);
        InvoiceQueryVO debitResult = invoiceQueryService.submittedList(param);
        if (debitResult != null && CollectionUtils.isNotEmpty(debitResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : debitResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new SubmitExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR));
            }
        }

        param.setType(InvoiceConstants.INVOICE_TYPE_CREDIT);
        InvoiceQueryVO creditResult = invoiceQueryService.submittedList(param);
        if (creditResult != null && CollectionUtils.isNotEmpty(creditResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : creditResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new SubmitExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR));
            }
        }
        doExport(new NewExcelUtil<>(SubmitExportDto.class), response, allInvoices);
    }

    //    @OperLog(title = "查询已退回的发票列表", businessType = BusinessType.QUERY)
    @PostMapping("/rejected/list")
    public ResultDTO<InvoiceQueryVO> rejectedList(@RequestBody InvoiceQueryParam param) {
        if (param == null || StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceQueryController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceQueryService.rejectedList(param));
    }

    //    @OperLog(title = "导出已退回的发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/rejected/list/export")
    public void exportRejectedList(InvoiceQueryParam param, HttpServletResponse response) {
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        param.setUserId(getCurrentUserId());

        param.setType(InvoiceConstants.INVOICE_TYPE_DEBIT);
        InvoiceQueryVO debitResult = invoiceQueryService.rejectedList(param);
        List<RejectExportDto> allInvoices = new ArrayList<>();
        if (debitResult != null && CollectionUtils.isNotEmpty(debitResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : debitResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new RejectExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR));
            }
        }

        param.setType(InvoiceConstants.INVOICE_TYPE_CREDIT);
        InvoiceQueryVO creditResult = invoiceQueryService.rejectedList(param);
        if (creditResult != null && CollectionUtils.isNotEmpty(creditResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : creditResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new RejectExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR));
            }
        }
        doExport(new NewExcelUtil<>(RejectExportDto.class), response, allInvoices);
    }

    private void doExport(NewExcelUtil<?> util, HttpServletResponse response, List<?> data) {
        util.createWorkbook();
        util.exportByFileType(response, data, "invoices", getRequest().getHeader(Constants.FILE_TYPE));
    }

    //    @OperLog(title = "查询已完成融资的发票批次列表", businessType = BusinessType.QUERY)
    @PostMapping("/financed/list")
    public ResultDTO<InvoiceQueryVO> financedList(@RequestBody InvoiceQueryParam param) {
        if (param == null || StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceQueryController param supplierId is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceQueryService.financedList(param));
    }

    //    @OperLog(title = "导出已完成融资的发票摘要列表", businessType = BusinessType.EXPORT)
//    @PostMapping("/financed/export")
//    public void financedListExport(@RequestBody InvoiceQueryParam param, HttpServletResponse response) {
//        if (param == null || StringUtils.isBlank(param.getSupplierId())) {
//            log.error("InvoiceQueryController.financedListExport param supplierId is null");
//            return;
//        }
//        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
//        param.setPageSize(Integer.MAX_VALUE);
//
//        InvoiceQueryVO invoiceQueryVO = invoiceQueryService.financedList(param);
//        log.info("InvoiceQueryController.financedListExport,invoiceQueryVO:{}",JsonUtil.toJSON(invoiceQueryVO));
//        List<InvoiceQueryExportDto> data = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(invoiceQueryVO.getInvoiceQueryDtoList())) {
//            for (InvoiceQueryDto dto : invoiceQueryVO.getInvoiceQueryDtoList()) {
//                data.add(new InvoiceQueryExportDto(dto));
//            }
//        }
//        doExport(new NewExcelUtil<>(InvoiceQueryExportDto.class), response, data);
//    }

    //    @OperLog(title = "查询已完成融资的发票批次详情", businessType = BusinessType.QUERY)
    @PostMapping("/financed/list/detail")
    public ResultDTO<InvoiceQueryVO> financedBatchDetail(@RequestBody InvoiceQueryParam param) {
        if (param == null || StringUtils.isBlank(param.getBatchNumber())) {
            log.error("InvoiceQueryController param batchNumber is null");
            return ResultDTO.success();
        }
        param.setUserId(getCurrentUserId());
        return ResultDTO.success(invoiceQueryService.financedDetailList(param));
    }

    //    @OperLog(title = "查询发票批次摘要列表", businessType = BusinessType.QUERY)
    @PostMapping("/financed/batch/summary")
    public ResultDTO<InvoiceBatchSummaryVo> batchSummary(@RequestBody FinancedInvoiceExportParam param) {
        param.setUserId(getCurrentUserId());
        InvoiceBatchSummaryVo result = invoiceQueryService.financedBatchSummary(param);
        return ResultDTO.success(result);
    }

    //    @OperLog(title = "导出已完成融资的发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/financed/batch/detail/export")
    public void exportFinanceDetail(FinancedInvoiceExportParam param, HttpServletResponse response) {
        param.setUserId(getCurrentUserId());
        log.info("exportFinanceDetail,param:{}", JsonUtil.toJSON(param));
        if (StringUtils.isEmpty(param.getBatchNumbers())) {
            log.info("exportFinanceDetail,batchNumbers is empty");
            doExport(new NewExcelUtil<>(FinancedExportDto.class), response, new ArrayList<>());
            return;
        }
        param.setBatchNumberList(new ArrayList<>(Arrays.asList(param.getBatchNumbers().split(StringConstants.PROP_SEPARATOR))));
        FinancedExportDataDto result = invoiceQueryService.financedDetailExport(param, response);
        List<FinancedExportDto> allInvoices = new ArrayList<>();
        allInvoices.addAll(result.getDebitExportList());
        allInvoices.addAll(result.getCreditExportList());
        doExport(new NewExcelUtil<>(FinancedExportDto.class), response, allInvoices);
    }

    //    @OperLog(title = "导出已完成融资的摘要列表", businessType = BusinessType.EXPORT)
    @PostMapping("/financed/batch/summary/export")
    public void exportFinanceSummary(InvoiceQueryParam param, HttpServletResponse response) {
        if (param == null || StringUtils.isBlank(param.getSupplierId())) {
            log.error("InvoiceQueryController.exportFinanceSummary param supplierId is null");
            doExport(new NewExcelUtil<>(FinancedSummaryExportDto.class), response, new ArrayList<>());
            return;
        }
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        param.setUserId(getCurrentUserId());

        InvoiceQueryVO invoiceQueryVO = invoiceQueryService.financedList(param);
        log.info("InvoiceQueryController.exportFinanceSummary,invoiceQueryVO:{}", JsonUtil.toJSON(invoiceQueryVO));
        if(invoiceQueryVO == null || CollectionUtils.isEmpty(invoiceQueryVO.getInvoiceQueryDtoList())){
            log.error("InvoiceQueryController.exportFinanceSummary batch data is null");
            doExport(new NewExcelUtil<>(FinancedSummaryExportDto.class), response, new ArrayList<>());
            return;
        }
        List<String> batchNumberList = invoiceQueryVO.getInvoiceQueryDtoList().stream().map(InvoiceQueryDto::getBatchNumber).collect(Collectors.toList());
        FinancedInvoiceExportParam exportParam = new FinancedInvoiceExportParam();
        exportParam.setUserId(getCurrentUserId());
        exportParam.setBatchNumberList(batchNumberList);
        FinancedExportDataDto financedExportDataDto = invoiceQueryService.financedDetailExport(exportParam, response);
        List<FinancedSummaryExportDto> summaryData = financedExportDataDto.getSummaryExportList();
        doExport(new NewExcelUtil<>(FinancedSummaryExportDto.class), response, summaryData);
    }

    //    @OperLog(title = "导出已完成融资的发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/financed/list/detail/export")
    public void exportFinancedList(InvoiceQueryParam param, HttpServletResponse response) {
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        param.setUserId(getCurrentUserId());

        List<FinancedExportDto> allInvoices = new ArrayList<>();
        param.setType(InvoiceConstants.INVOICE_TYPE_DEBIT);
        InvoiceQueryVO debitResult = invoiceQueryService.financedDetailList(param);
        if (debitResult != null && CollectionUtils.isNotEmpty(debitResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : debitResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new FinancedExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR));
            }
        }

        param.setType(InvoiceConstants.INVOICE_TYPE_CREDIT);
        InvoiceQueryVO creditResult = invoiceQueryService.financedDetailList(param);
        if (creditResult != null && CollectionUtils.isNotEmpty(creditResult.getInvoiceQueryDtoList())) {
            for (InvoiceQueryDto dto : creditResult.getInvoiceQueryDtoList()) {
                allInvoices.add(new FinancedExportDto(dto).setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR));
            }
        }
        doExport(new NewExcelUtil<>(FinancedExportDto.class), response, allInvoices);
    }

    @PostMapping("/entity/list")
    public ResultDTO<List<EntityDto>> entityList() {
        return ResultDTO.success(invoiceQueryService.getEntityInfo());
    }

    @PostMapping("/rate/list")
    public ResultDTO<FinancingRateDashboard> financingRateList(@RequestBody InvoiceQueryParam param) {
        FinancingRateDashboard dashboard = new FinancingRateDashboard();
        if (param == null || param.getRateType() == null || StringUtils.isBlank(param.getStartDate()) || StringUtils.isBlank(param.getEndDate())) {
            log.error("InvoiceQueryController param exist is null");
            return ResultDTO.success(dashboard);
        }

        return ResultDTO.success(invoiceQueryService.getFinancingRate(param));
    }

    @PostMapping("/rate/type")
    public ResultDTO<List<FinancingRateVO>> financingRateTypeList() {

        return ResultDTO.success(RatePeriodEnum.getRatePeriodCodes());
    }

    @PostMapping("/rate/export")
    public void financingRateExport(InvoiceQueryParam param, HttpServletResponse response) {
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);

        NewExcelUtil<FinancingRateVO> util = new NewExcelUtil<>(FinancingRateVO.class);
        util.createWorkbook();
        FinancingRateDashboard rateDashboard = invoiceQueryService.getFinancingRate(param);

        util.exportByFileType(response, rateDashboard.getFinancingRateVOList(), "rate", getRequest().getHeader(Constants.FILE_TYPE));
    }

}
