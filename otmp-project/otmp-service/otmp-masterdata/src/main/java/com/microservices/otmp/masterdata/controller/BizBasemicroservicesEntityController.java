package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBasemicroservicesEntityDTO;
import com.microservices.otmp.masterdata.service.IBizBasemicroservicesEntityService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * microservices Entity Table From ECCController
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@RestController
@RequestMapping("/bizBasemicroservicesEntity")
public class BizBasemicroservicesEntityController extends BaseController
{
    @Autowired
    private IBizBasemicroservicesEntityService bizBasemicroservicesEntityService;

    /**
     * 查询microservices Entity Table From ECC列表
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        startPage();
        List<BizBasemicroservicesEntityDTO> list = bizBasemicroservicesEntityService.selectBizBasemicroservicesEntityList(bizBasemicroservicesEntity);
        return getDataTable(list);
    }

    /**
     * 导出microservices Entity Table From ECC列表
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:export")
    @Log(title = "microservices Entity Table From ECC", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        List<BizBasemicroservicesEntityDTO> list = bizBasemicroservicesEntityService.selectBizBasemicroservicesEntityList(bizBasemicroservicesEntity);
        NewExcelUtil<BizBasemicroservicesEntityDTO> util = new NewExcelUtil<>(BizBasemicroservicesEntityDTO.class);
        util.exportExcel(response, list, "microservices Entity Table From ECC数据");
    }

    /**
     * 获取microservices Entity Table From ECC详细信息
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBasemicroservicesEntityDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBasemicroservicesEntityService.selectBizBasemicroservicesEntityById(id));
    }

    /**
     * 新增microservices Entity Table From ECC
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:add")
    @Log(title = "microservices Entity Table From ECC", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        return toResultDTO(bizBasemicroservicesEntityService.insertBizBasemicroservicesEntity(bizBasemicroservicesEntity),true);
    }

    /**
     * 修改microservices Entity Table From ECC
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:edit")
    @Log(title = "microservices Entity Table From ECC", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBasemicroservicesEntityDTO bizBasemicroservicesEntity)
    {
        return toResultDTO(bizBasemicroservicesEntityService.updateBizBasemicroservicesEntity(bizBasemicroservicesEntity),true);
    }

    /**
     * 删除microservices Entity Table From ECC
     */
    @RequiresPermissions("masterData:bizBasemicroservicesEntity:remove")
    @Log(title = "microservices Entity Table From ECC", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBasemicroservicesEntityService.deleteBizBasemicroservicesEntityByIds(ids),true);
    }
}
