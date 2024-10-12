package com.microservices.otmp.financing.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.financing.constant.InvoiceConstants;
import com.microservices.otmp.financing.domain.dto.AvailableExportDto;
import com.microservices.otmp.financing.domain.param.invoice.*;
import com.microservices.otmp.financing.domain.vo.invoice.SubmitResultVo;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.domain.vo.invoice.*;
import com.microservices.otmp.financing.service.InvoiceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/supplier/invoice")
@RestController
public class SupplierInvoiceController extends BaseController {

    @Autowired
    private InvoiceService invoiceService;

    @GetMapping("/amount/maturity")
    public ResultDTO<List<MaturityDateAmountVo>> maturityDateAmount(@RequestParam("bankId") String bankId) {
        List<MaturityDateAmountVo> maturityDateAmountVos = invoiceService.maturityDateAmount(bankId);
        return ResultDTO.success(maturityDateAmountVos);
    }

    @GetMapping("/quote/time/ttl")
    public ResultDTO<Long> getQuoteTime() {
        Long time = invoiceService.confirmTimeExpiry(getCurrentUserId());
        return ResultDTO.success(time);
    }

    @PostMapping("/quote/time/mark")
    public ResultDTO<Boolean> saveQuoteTime() {
        Boolean result = invoiceService.setConfirmTime(getCurrentUserId());
        return ResultDTO.success(result);
    }

    @PostMapping("/quote/time/clear")
    public ResultDTO<Boolean> timeClear() {
        Boolean result = invoiceService.clearConfirmTime(getCurrentUserId());
        return ResultDTO.success(result);
    }

    /**
     * 银行列表，排序依据：bank.rank（高在前） -> margin（低在前） -> 可用额度（多在前）
     */
    @GetMapping("/bank/rank")
    public ResultDTO<List<BankVo>> rankList() {
        List<BankVo> result = invoiceService.rankList(getCurrentUserId());
        return ResultDTO.success(result);
    }

    /**
     * 发票列表（手动选择模式），已弃用，使用availableFreeSelectList方法代替
     * TODO 删除弃用代码
     */
//    @Deprecated
//    @PostMapping("/available/list/free")
//    public ResultDTO<PageInfo<InvoiceVo>> availableFreeList(@Validated @RequestBody AvailableFreeListParam param) {
//        param.setUserId(getCurrentUserId());
//        PageInfo<InvoiceVo> result = invoiceService.availableInvoicesFree(param);
//        return handleResultData(result);
//    }

    @OperLog(title = "导出供应商可用发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/available/export/free")
    public void exportAvailableListFree(AvailableFreeListParam param, HttpServletResponse response) {
        param.setUserId(getCurrentUserId());
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        log.info("exportAvailableListFree,param:{}", JsonUtil.toJSON(param));
        FinanceInvoiceListVo queryResult = invoiceService.availableInvoicesFreeWithRelation(param);

        if (queryResult == null) {
            log.warn("exportAvailableListFree is null,return");
            return;
        }
        doExport(response, queryResult);
    }

    private void doExport(HttpServletResponse response, FinanceInvoiceListVo queryResult) {
        List<AvailableExportDto> data = new ArrayList<>();
        List<InvoiceVo> debitVos = queryResult.getDebits();
        if (CollectionUtils.isNotEmpty(debitVos)) {
            for (InvoiceVo vo : debitVos) {
                data.add(new AvailableExportDto(vo).setInvoiceType(InvoiceConstants.INVOICE_TYPE_DEBIT_STR));
            }
        }
        List<InvoiceVo> creditVos = queryResult.getCredits();
        if (CollectionUtils.isNotEmpty(creditVos)) {
            for (InvoiceVo vo : creditVos) {
                data.add(new AvailableExportDto(vo).setInvoiceType(InvoiceConstants.INVOICE_TYPE_CREDIT_STR));
            }
        }
        doExport(new NewExcelUtil<>(AvailableExportDto.class), response, data);
    }

    private void doExport(NewExcelUtil<?> util, HttpServletResponse response, List<?> data) {
        util.createWorkbook();
        util.exportByFileType(response, data, "invoices", getRequest().getHeader(Constants.FILE_TYPE));
    }

    /**
     * 发票列表（手动选择模式）
     */
    @PostMapping("/available/select/free")
    public ResultDTO<FinanceInvoiceListVo> availableFreeSelectList(@Validated @RequestBody AvailableFreeListParam param) {
        param.setUserId(getCurrentUserId());
        FinanceInvoiceListVo result = invoiceService.availableInvoicesFreeWithRelation(param);
        return handleResultData(result);
    }

    @OperLog(title = "导出以融资方式查询的供应商可用发票列表", businessType = BusinessType.EXPORT)
    @PostMapping("/available/export/select")
    public void exportAvailableListSelect(AvailableSelectListParam param, HttpServletResponse response) {
        param.setUserId(getCurrentUserId());
        param.setPageNum(Constants.DEFAULT_PAGE_NUM);
        param.setPageSize(Integer.MAX_VALUE);
        log.info("exportAvailableListSelect,param:{}", JsonUtil.toJSON(param));

        FinanceInvoiceListVo queryResult = invoiceService.availableInvoicesSelect(param);
        if (queryResult == null) {
            log.warn("exportAvailableListSelect,queryResult is null,return");
            return;
        }
        doExport(response, queryResult);
    }

    @PostMapping("/available/list/select")
    public ResultDTO<FinanceInvoiceListVo> availableSelectList(@Validated @RequestBody AvailableSelectListParam param) {
        param.setUserId(getCurrentUserId());
        FinanceInvoiceListVo result = invoiceService.availableInvoicesSelect(param);
        return ResultDTO.success(result);
    }

    @PostMapping("/available/list/group")
    public ResultDTO<List<MaturityGroupVo>> maturityDateGroup(@RequestBody MaturityDateGroupParam param) {
        param.setUserId(getCurrentUserId());
        List<MaturityGroupVo> result = invoiceService.maturityDateGroup(param);
        return ResultDTO.success(result);
    }

    @OperLog(title = "修改发票的MaturityDate", businessType = BusinessType.UPDATE)
    @PostMapping("/available/group/update")
    public ResultDTO<Boolean> updateMaturityDate(@RequestBody UpdateMaturityParam param) {
        param.setUserId(getCurrentUserId());
        boolean result = invoiceService.updateMaturityDate(param);
        return ResultDTO.success(result);
    }

    @PostMapping("/available/list/confirm")
    public ResultDTO<PageInfo<InvoiceVo>> confirmList(@RequestBody ConfirmInvoiceListParam param) {
        param.setUserId(getCurrentUserId());
        PageInfo<InvoiceVo> result = invoiceService.confirmInvoiceList(param);
        return ResultDTO.success(result);
    }

    @PostMapping("/available/key/renewal")
    public ResultDTO<Boolean> lockPageKeyRenewal() {
        boolean result = invoiceService.renewalLockPageKey(getCurrentUserId());
        return ResultDTO.success(result);
    }

    @PostMapping("/available/key/remove")
    public ResultDTO<Boolean> lockPageKeyRemove() {
        boolean result = invoiceService.removeLockPageKey(getCurrentUserId());
        return ResultDTO.success(result);
    }

    @OperLog(title = "提交融资发票", businessType = BusinessType.INSERT)
    @PostMapping("/available/submit")
    public ResultDTO<SubmitResultVo> submitBatch(@Validated @RequestBody InvoiceSubmitParam param) {
        long currentUserId = getCurrentUserId();
        param.setUserId(currentUserId);
        param.setLoginName(getLoginName());
        SubmitResultVo submit = invoiceService.submit(param);
        return ResultDTO.success(submit);
    }

    /**
     * Invoice - Available for finance页面的Maximum financing amount数据
     */
    @GetMapping("/available/card/maximum")
    public ResultDTO<InvoiceCardVo> availableMaximumAmountCard(@RequestParam("bankId") String bankId) {
        long currentUserId = getCurrentUserId();
        InvoiceCardVo result = invoiceService.availableMaximumAmount(currentUserId, bankId);
        return ResultDTO.success(result);
    }

    @GetMapping("/available/card/discount/rate")
    public ResultDTO<String> discountValues(@RequestParam("bankId") String bankId) {
        long currentUserId = getCurrentUserId();
        String result = invoiceService.discountRate(currentUserId, bankId);
        return ResultDTO.success(result);
    }

    @PostMapping("/available/card/discount/amount")
    public ResultDTO<String> discountAmount(@RequestBody ConfirmInvoiceListParam param) {
        long currentUserId = getCurrentUserId();
        param.setUserId(currentUserId);
        String result = invoiceService.discountAmount(param);
        return ResultDTO.success(result);
    }

}
