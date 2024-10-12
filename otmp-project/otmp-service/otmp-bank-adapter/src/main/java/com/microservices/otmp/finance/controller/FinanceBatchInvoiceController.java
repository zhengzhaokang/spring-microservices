package com.microservices.otmp.finance.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.otmp.finance.domain.dto.FinanceBatchInvoiceDTO;
import com.microservices.otmp.finance.service.IFinanceBatchInvoiceService;


/**
 * Finance Batch InvoiceController
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@RestController
@RequestMapping("/financeBatchInvoice")
public class FinanceBatchInvoiceController extends BaseController {
    @Autowired
    private IFinanceBatchInvoiceService financeBatchInvoiceService;

    /**
     * 查询Finance Batch Invoice列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FinanceBatchInvoiceDTO financeBatchInvoice) {
        startPage();
        List<FinanceBatchInvoiceDTO> list = financeBatchInvoiceService.selectFinanceBatchInvoiceList(financeBatchInvoice);
        return getDataTable(list);
    }

    /**
     * 导出Finance Batch Invoice列表
     */
    @Log(title = "Finance Batch Invoice", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceBatchInvoiceDTO financeBatchInvoice) {
        List<FinanceBatchInvoiceDTO> list = financeBatchInvoiceService.selectFinanceBatchInvoiceList(financeBatchInvoice);
        ExcelUtil<FinanceBatchInvoiceDTO> util = new ExcelUtil<FinanceBatchInvoiceDTO>(FinanceBatchInvoiceDTO.class);
        util.exportExcel(response, list, "Finance Batch Invoice数据");
    }

    /**
     * 获取Finance Batch Invoice详细信息
     */
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(financeBatchInvoiceService.selectFinanceBatchInvoiceById(id));
    }

    /**
     * 新增Finance Batch Invoice
     */
    @Log(title = "Finance Batch Invoice", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody FinanceBatchInvoiceDTO financeBatchInvoice) {
        return toResultDTO(financeBatchInvoiceService.insertFinanceBatchInvoice(financeBatchInvoice), true);
    }

    /**
     * 修改Finance Batch Invoice
     */
    @Log(title = "Finance Batch Invoice", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody FinanceBatchInvoiceDTO financeBatchInvoice) {
        return toResultDTO(financeBatchInvoiceService.updateFinanceBatchInvoice(financeBatchInvoice), true);
    }

    /**
     * 删除Finance Batch Invoice
     */
    @Log(title = "Finance Batch Invoice", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(financeBatchInvoiceService.deleteFinanceBatchInvoiceByIds(ids), true);
    }
}
