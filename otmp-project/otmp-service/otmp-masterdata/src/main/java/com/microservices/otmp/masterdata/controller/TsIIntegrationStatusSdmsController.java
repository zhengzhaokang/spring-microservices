package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.TsIIntegrationStatusSdmsDTO;
import com.microservices.otmp.masterdata.service.ITsIIntegrationStatusSdmsService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TsIIntegrationStatusSdmsController
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/tsIIntegrationStatusSdms")
public class TsIIntegrationStatusSdmsController extends BaseController
{
    @Autowired
    private ITsIIntegrationStatusSdmsService tsIIntegrationStatusSdmsService;

    /**
     * 查询TsIIntegrationStatusSdms列表
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:list")
    @GetMapping("/list")
    public TableDataInfo list(TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        startPage();
        List<TsIIntegrationStatusSdmsDTO> list = tsIIntegrationStatusSdmsService.selectTsIIntegrationStatusSdmsList(tsIIntegrationStatusSdms);
        return getDataTable(list);
    }

    /**
     * 导出TsIIntegrationStatusSdms列表
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:export")
    @Log(title = "TsIIntegrationStatusSdms", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        List<TsIIntegrationStatusSdmsDTO> list = tsIIntegrationStatusSdmsService.selectTsIIntegrationStatusSdmsList(tsIIntegrationStatusSdms);
        ExcelUtil<TsIIntegrationStatusSdmsDTO> util = new ExcelUtil<TsIIntegrationStatusSdmsDTO>(TsIIntegrationStatusSdmsDTO.class);
        util.exportExcel(response, list, "TsIIntegrationStatusSdms数据");
    }

    /**
     * 获取TsIIntegrationStatusSdms详细信息
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<TsIIntegrationStatusSdmsDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(tsIIntegrationStatusSdmsService.selectTsIIntegrationStatusSdmsById(id));
    }

    /**
     * 新增TsIIntegrationStatusSdms
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:add")
    @Log(title = "TsIIntegrationStatusSdms", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        return toResultDTO(tsIIntegrationStatusSdmsService.insertTsIIntegrationStatusSdms(tsIIntegrationStatusSdms),true);
    }

    /**
     * 修改TsIIntegrationStatusSdms
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:edit")
    @Log(title = "TsIIntegrationStatusSdms", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody TsIIntegrationStatusSdmsDTO tsIIntegrationStatusSdms)
    {
        return toResultDTO(tsIIntegrationStatusSdmsService.updateTsIIntegrationStatusSdms(tsIIntegrationStatusSdms),true);
    }

    /**
     * 删除TsIIntegrationStatusSdms
     */
    @RequiresPermissions("system:tsIIntegrationStatusSdms:remove")
    @Log(title = "TsIIntegrationStatusSdms", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(tsIIntegrationStatusSdmsService.deleteTsIIntegrationStatusSdmsByIds(ids),true);
    }
}
