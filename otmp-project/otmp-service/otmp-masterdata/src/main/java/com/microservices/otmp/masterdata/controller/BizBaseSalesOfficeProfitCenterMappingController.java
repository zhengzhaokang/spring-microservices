package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseSalesOfficeProfitCenterMappingDTO;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOfficeProfitCenterMappingService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * salesOffice与profitMapping映射Controller
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/bizSalesOfficePoritCenterMapping")
public class BizBaseSalesOfficeProfitCenterMappingController extends BaseController
{
    @Autowired
    private IBizBaseSalesOfficeProfitCenterMappingService bizBaseSalesOfficeProfitCenterMappingService;

    /**
     * 查询salesOffice与profitMapping映射列表
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        startPage();
        List<BizBaseSalesOfficeProfitCenterMappingDTO> list = bizBaseSalesOfficeProfitCenterMappingService.selectBizBaseSalesOfficeProfitCenterMappingList(bizBaseSalesOfficeProfitCenterMapping);
        return getDataTable(list);
    }

    /**
     * 导出salesOffice与profitMapping映射列表
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:export")
    @Log(title = "salesOffice与profitMapping映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        List<BizBaseSalesOfficeProfitCenterMappingDTO> list = bizBaseSalesOfficeProfitCenterMappingService.selectBizBaseSalesOfficeProfitCenterMappingList(bizBaseSalesOfficeProfitCenterMapping);
        ExcelUtil<BizBaseSalesOfficeProfitCenterMappingDTO> util = new ExcelUtil<BizBaseSalesOfficeProfitCenterMappingDTO>(BizBaseSalesOfficeProfitCenterMappingDTO.class);
        util.exportExcel(response, list, "salesOffice与profitMapping映射数据");
    }

    /**
     * 获取salesOffice与profitMapping映射详细信息
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseSalesOfficeProfitCenterMappingDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseSalesOfficeProfitCenterMappingService.selectBizBaseSalesOfficeProfitCenterMappingById(id));
    }

    /**
     * 新增salesOffice与profitMapping映射
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:add")
    @Log(title = "salesOffice与profitMapping映射", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        return toResultDTO(bizBaseSalesOfficeProfitCenterMappingService.insertBizBaseSalesOfficeProfitCenterMapping(bizBaseSalesOfficeProfitCenterMapping),true);
    }

    /**
     * 修改salesOffice与profitMapping映射
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:edit")
    @Log(title = "salesOffice与profitMapping映射", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseSalesOfficeProfitCenterMappingDTO bizBaseSalesOfficeProfitCenterMapping)
    {
        return toResultDTO(bizBaseSalesOfficeProfitCenterMappingService.updateBizBaseSalesOfficeProfitCenterMapping(bizBaseSalesOfficeProfitCenterMapping),true);
    }

    /**
     * 删除salesOffice与profitMapping映射
     */
    @RequiresPermissions("system:bizSalesOfficePoritCenterMapping:remove")
    @Log(title = "salesOffice与profitMapping映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseSalesOfficeProfitCenterMappingService.deleteBizBaseSalesOfficeProfitCenterMappingByIds(ids),true);
    }
}
