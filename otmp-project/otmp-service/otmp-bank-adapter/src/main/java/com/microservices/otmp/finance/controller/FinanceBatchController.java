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
import com.microservices.otmp.finance.service.IFinanceBatchInvoiceService;
import com.microservices.otmp.finance.service.IFinanceInvoiceApService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.otmp.finance.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.finance.service.IFinanceBatchService;

/**
 * Finance Batch InfoController
 *
 * @author lovefamily
 * @date 2023-10-12
 */
@RestController
@RequestMapping("/financeBatch")
public class FinanceBatchController extends BaseController {
    @Autowired
    private IFinanceBatchService financeBatchService;

    /**
     * 查询Finance Batch Info列表
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.QUERY)
    @GetMapping("/list")
    public TableDataInfo list(FinanceBatchDTO financeBatch) {
        startPage();
        List<FinanceBatchDTO> list = financeBatchService.selectFinanceBatchList(financeBatch);
        return getDataTable(list);
    }

    /**
     * 导出Finance Batch Info列表
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceBatchDTO financeBatch) {
        List<FinanceBatchDTO> list = financeBatchService.selectFinanceBatchList(financeBatch);
        ExcelUtil<FinanceBatchDTO> util = new ExcelUtil<FinanceBatchDTO>(FinanceBatchDTO.class);
        util.exportExcel(response, list, "Finance Batch Info数据");
    }

    /**
     * 获取Finance Batch Info详细信息
     */
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(financeBatchService.selectFinanceBatchById(id));
    }

    /**
     * 新增Finance Batch Info
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody FinanceBatchDTO financeBatch) {
        return toResultDTO(financeBatchService.insertFinanceBatch(financeBatch), true);
    }

    /**
     * 修改Finance Batch Info
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody FinanceBatchDTO financeBatch) {
        return toResultDTO(financeBatchService.updateFinanceBatch(financeBatch), true);
    }

    /**
     * 删除Finance Batch Info
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(financeBatchService.deleteFinanceBatchByIds(ids), true);
    }
}
