package com.microservices.otmp.bank.controller;

import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.service.IBankTransferInvoiceResponseInfoService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 银行处理后返回发票状态信息Controller
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@RestController
@RequestMapping("/bankTransferInvoiceResponseInfo")
public class BankTransferInvoiceResponseInfoController extends BaseController {
    @Autowired
    private IBankTransferInvoiceResponseInfoService bankTransferInvoiceResponseInfoService;

    /**
     * 查询银行处理后返回发票状态信息列表
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        startPage();
        List<BankTransferInvoiceResponseInfoDTO> list = bankTransferInvoiceResponseInfoService.selectBankTransferInvoiceResponseInfoList(bankTransferInvoiceResponseInfo);
        return getDataTable(list);
    }

    /**
     * 导出银行处理后返回发票状态信息列表
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:export")
    @Log(title = "银行处理后返回发票状态信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        List<BankTransferInvoiceResponseInfoDTO> list = bankTransferInvoiceResponseInfoService.selectBankTransferInvoiceResponseInfoList(bankTransferInvoiceResponseInfo);
        ExcelUtil<BankTransferInvoiceResponseInfoDTO> util = new ExcelUtil<BankTransferInvoiceResponseInfoDTO>(BankTransferInvoiceResponseInfoDTO.class);
        util.exportExcel(response, list, "银行处理后返回发票状态信息数据");
    }

    /**
     * 获取银行处理后返回发票状态信息详细信息
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bankTransferInvoiceResponseInfoService.selectBankTransferInvoiceResponseInfoById(id));
    }

    /**
     * 新增银行处理后返回发票状态信息
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:add")
    @Log(title = "银行处理后返回发票状态信息", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        return toResultDTO(bankTransferInvoiceResponseInfoService.insertBankTransferInvoiceResponseInfo(bankTransferInvoiceResponseInfo), true);
    }

    /**
     * 修改银行处理后返回发票状态信息
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:edit")
    @Log(title = "银行处理后返回发票状态信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        return toResultDTO(bankTransferInvoiceResponseInfoService.updateBankTransferInvoiceResponseInfo(bankTransferInvoiceResponseInfo), true);
    }

    /**
     * 删除银行处理后返回发票状态信息
     */
    @HasPermissions("bankTransferInvoiceResponseInfo:bankTransferInvoiceResponseInfo:remove")
    @Log(title = "银行处理后返回发票状态信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(bankTransferInvoiceResponseInfoService.deleteBankTransferInvoiceResponseInfoByIds(ids), true);
    }
}
