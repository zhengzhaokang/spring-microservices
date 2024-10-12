package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.masterdata.service.IBizBaseBwBuBgMappingService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBwBuBgMappingDTO;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * bwBuBgMappingController
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/bizBaseBwBuBgMapping")
public class BizBaseBwBuBgMappingController extends BaseController
{
    @Autowired
    private IBizBaseBwBuBgMappingService bizBaseBwBuBgMappingService;

    /**
     * 查询bwBuBgMapping列表
     */
    @RequiresPermissions("system:mapping:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        startPage();
        List<BizBaseBwBuBgMappingDTO> list = bizBaseBwBuBgMappingService.selectBizBaseBwBuBgMappingList(bizBaseBwBuBgMapping);
        return getDataTable(list);
    }

    /**
     * 导出bwBuBgMapping列表
     */
    @RequiresPermissions("system:mapping:export")
    @Log(title = "bwBuBgMapping", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        List<BizBaseBwBuBgMappingDTO> list = bizBaseBwBuBgMappingService.selectBizBaseBwBuBgMappingList(bizBaseBwBuBgMapping);
        ExcelUtil<BizBaseBwBuBgMappingDTO> util = new ExcelUtil<BizBaseBwBuBgMappingDTO>(BizBaseBwBuBgMappingDTO.class);
        util.exportExcel(response, list, "bwBuBgMapping数据");
    }

    /**
     * 获取bwBuBgMapping详细信息
     */
    @RequiresPermissions("system:mapping:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseBwBuBgMappingDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseBwBuBgMappingService.selectBizBaseBwBuBgMappingById(id));
    }

    /**
     * 新增bwBuBgMapping
     */
    @RequiresPermissions("system:mapping:add")
    @Log(title = "bwBuBgMapping", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        return toResultDTO(bizBaseBwBuBgMappingService.insertBizBaseBwBuBgMapping(bizBaseBwBuBgMapping),true);
    }

    /**
     * 修改bwBuBgMapping
     */
    @RequiresPermissions("system:mapping:edit")
    @Log(title = "bwBuBgMapping", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseBwBuBgMappingDTO bizBaseBwBuBgMapping)
    {
        return toResultDTO(bizBaseBwBuBgMappingService.updateBizBaseBwBuBgMapping(bizBaseBwBuBgMapping),true);
    }

    /**
     * 删除bwBuBgMapping
     */
    @RequiresPermissions("system:mapping:remove")
    @Log(title = "bwBuBgMapping", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseBwBuBgMappingService.deleteBizBaseBwBuBgMappingByIds(ids),true);
    }
}
