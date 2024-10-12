package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.TsITosBpcDTO;
import com.microservices.otmp.masterdata.service.ITsITosBpcService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * TsITosBpcController
 * 
 * @author lovefamily
 * @date 2022-08-26
 */
@RestController
@RequestMapping("/tsITosBpc")
public class TsITosBpcController extends BaseController
{
    @Autowired
    private ITsITosBpcService tsITosBpcService;

    /**
     * 查询TsITosBpc列表
     */
    @RequiresPermissions("system:tsITosBpc:list")
    @GetMapping("/list")
    public TableDataInfo list(TsITosBpcDTO tsITosBpc)
    {
        startPage();
        List<TsITosBpcDTO> list = tsITosBpcService.selectTsITosBpcList(tsITosBpc);
        return getDataTable(list);
    }

    /**
     * 导出TsITosBpc列表
     */
    @RequiresPermissions("system:tsITosBpc:export")
    @Log(title = "TsITosBpc", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TsITosBpcDTO tsITosBpc)
    {
        List<TsITosBpcDTO> list = tsITosBpcService.selectTsITosBpcList(tsITosBpc);
        ExcelUtil<TsITosBpcDTO> util = new ExcelUtil<TsITosBpcDTO>(TsITosBpcDTO.class);
        util.exportExcel(response, list, "TsITosBpc数据");
    }

    /**
     * 获取TsITosBpc详细信息
     */
    @RequiresPermissions("system:tsITosBpc:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<TsITosBpcDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(tsITosBpcService.selectTsITosBpcById(id));
    }

    /**
     * 新增TsITosBpc
     */
    @RequiresPermissions("system:tsITosBpc:add")
    @Log(title = "TsITosBpc", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody TsITosBpcDTO tsITosBpc)
    {
        return toResultDTO(tsITosBpcService.insertTsITosBpc(tsITosBpc),true);
    }

    /**
     * 修改TsITosBpc
     */
    @RequiresPermissions("system:tsITosBpc:edit")
    @Log(title = "TsITosBpc", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody TsITosBpcDTO tsITosBpc)
    {
        return toResultDTO(tsITosBpcService.updateTsITosBpc(tsITosBpc),true);
    }

    /**
     * 删除TsITosBpc
     */
    @RequiresPermissions("system:tsITosBpc:remove")
    @Log(title = "TsITosBpc", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(tsITosBpcService.deleteTsITosBpcByIds(ids),true);
    }
}
