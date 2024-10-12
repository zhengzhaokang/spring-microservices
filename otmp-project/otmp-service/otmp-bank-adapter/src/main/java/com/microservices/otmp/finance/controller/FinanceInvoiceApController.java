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

import com.microservices.otmp.finance.domain.dto.FinanceInvoiceApDTO;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;


/**
 * Finance Invoice ApController
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@RestController
@RequestMapping("/financeInvoiceAp")
public class FinanceInvoiceApController extends BaseController {
    @Autowired
    private IFinanceInvoiceApService financeInvoiceApService;

    /**
     * 查询Finance Invoice Ap列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FinanceInvoiceApDTO financeInvoiceAp) {
        startPage();
        List<FinanceInvoiceApDTO> list = financeInvoiceApService.selectFinanceInvoiceApList(financeInvoiceAp);
        return getDataTable(list);
    }

    /**
     * 导出Finance Invoice Ap列表
     */
    @Log(title = "Finance Invoice Ap", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceInvoiceApDTO financeInvoiceAp) {
        List<FinanceInvoiceApDTO> list = financeInvoiceApService.selectFinanceInvoiceApList(financeInvoiceAp);
        ExcelUtil<FinanceInvoiceApDTO> util = new ExcelUtil<FinanceInvoiceApDTO>(FinanceInvoiceApDTO.class);
        util.exportExcel(response, list, "Finance Invoice Ap数据");
    }

    /**
     * 获取Finance Invoice Ap详细信息
     */
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(financeInvoiceApService.selectFinanceInvoiceApById(id));
    }

    /**
     * 新增Finance Invoice Ap
     */
    @Log(title = "Finance Invoice Ap", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody FinanceInvoiceApDTO financeInvoiceAp) {
        return toResultDTO(financeInvoiceApService.insertFinanceInvoiceAp(financeInvoiceAp), true);
    }

    /**
     * 修改Finance Invoice Ap
     */
    @Log(title = "Finance Invoice Ap", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody FinanceInvoiceApDTO financeInvoiceAp) {
        return toResultDTO(financeInvoiceApService.updateFinanceInvoiceAp(financeInvoiceAp), true);
    }

    /**
     * 删除Finance Invoice Ap
     */
    @Log(title = "Finance Invoice Ap", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(financeInvoiceApService.deleteFinanceInvoiceApByIds(ids), true);
    }
}
