package com.microservices.otmp.analysis.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.analysis.domain.dto.FinanceBatchDTO;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchInvoiceApDTO;
import com.microservices.otmp.analysis.domain.dto.FinanceBatchQueryDTO;
import com.microservices.otmp.analysis.service.IFinanceBatchService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * Finance Batch InfoController
 *
 * @author lovefamily
 * @date 2023-11-10
 */
@RestController
@RequestMapping("/financeBatch")
public class FinanceBatchController extends BaseController {
    @Autowired
    private IFinanceBatchService financeBatchService;

    /**
     * 查询Finance Batch Info列表
     */
    @GetMapping("/list")
    public TableDataInfo list(FinanceBatchDTO financeBatch) {
        startPage();
        List<FinanceBatchDTO> list = financeBatchService.selectFinanceBatchList(financeBatch);
        return getDataTable(list);
    }

    /**
     * 查询Finance Batch Info列表
     */
    @GetMapping("/financeBatchList")
    public TableDataInfo financeBatchList(FinanceBatchQueryDTO financeBatchQueryDTO) {
        String queryDateType = financeBatchQueryDTO.getQueryDateType();
        String queryDateTime = financeBatchQueryDTO.getQueryDateTime();
        int intervals = 7;
        if (StringUtils.isNotEmpty(queryDateType)) {
            intervals = Integer.valueOf(queryDateType);
        }
        FinanceBatchDTO financeBatch = new FinanceBatchDTO();
        financeBatch.setBankId(financeBatchQueryDTO.getBankId());
        financeBatch.setSupplierId(financeBatchQueryDTO.getSupplierId());
        financeBatch.setEntityId(financeBatchQueryDTO.getEntityId());
        if (StringUtils.isNotEmpty(queryDateTime)) {
            financeBatch.setBeginTime(queryDateTime + " 00:00:00");
            financeBatch.setEndTime(queryDateTime + " 23:59:59");
        } else {
            List<String> pastDaysList = getLastDateTimeList(intervals);
            String startDateTime = pastDaysList.get(pastDaysList.size() - 1) + " 00:00:00";
            String endDateTime = pastDaysList.get(0) + " 23:59:59";
            financeBatch.setBeginTime(startDateTime);
            financeBatch.setEndTime(endDateTime);
        }
        startPage();
        return getDataTable(financeBatchService.selectFinanceBatchPageList(financeBatch));
    }

    private ArrayList<String> getLastDateTimeList(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            pastDaysList.add(DateUtils.getPastDate(i));
        }
        return pastDaysList;
    }


    /**
     * 导出Finance Batch Info列表
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, FinanceBatchDTO financeBatch) {
        List<FinanceBatchDTO> list = financeBatchService.selectFinanceBatchList(financeBatch);
        NewExcelUtil<FinanceBatchDTO> util = new NewExcelUtil<FinanceBatchDTO>(FinanceBatchDTO.class);
        util.exportByFileType(response, list, "Finance Batch Info", getRequest().getHeader(Constants.FILE_TYPE));
    }


    /**
     * 导出Finance Batch Invoice Info列表
     */
    @Log(title = "Finance Batch Info", businessType = BusinessType.EXPORT)
    @PostMapping("/exportBatchInvoice")
    public void exportBatchInvoice(HttpServletResponse response, FinanceBatchInvoiceApDTO financeBatchInvoiceApDTO) {
        List<FinanceBatchInvoiceApDTO> list = financeBatchService.findFinanceBatchInvoiceApList(financeBatchInvoiceApDTO);
        NewExcelUtil<FinanceBatchInvoiceApDTO> util = new NewExcelUtil<>(FinanceBatchInvoiceApDTO.class);
        util.exportByFileType(response, list, "Finance Batch Invoice Info", getRequest().getHeader(Constants.FILE_TYPE));
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
