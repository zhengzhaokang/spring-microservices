package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCompanyLocalCurrecyMappingDTO;
import com.microservices.otmp.masterdata.service.IBizBaseCompanyLocalCurrecyMappingService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * companyCode和localcurrency映射Controller
 * 
 * @author daihuaicai
 * @date 2022-09-19
 */
@RestController
@RequestMapping("/bizBaseCompanyLocalCurrency")
public class BizBaseCompanyLocalCurrecyMappingController extends BaseController
{
    @Autowired
    private IBizBaseCompanyLocalCurrecyMappingService bizBaseCompanyLocalCurrecyMappingService;

    /**
     * 查询companyCode和localcurrency映射列表
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        startPage();
        List<BizBaseCompanyLocalCurrecyMappingDTO> list = bizBaseCompanyLocalCurrecyMappingService.selectBizBaseCompanyLocalCurrecyMappingList(bizBaseCompanyLocalCurrecyMapping);
        return getDataTable(list);
    }

    @GetMapping("/getLocalCurrencyByCompanyCode")
    public ResultDTO<BizBaseCompanyLocalCurrecyMappingDTO> getLocalCurrencyByCompanyCode(@RequestParam("companyCode") String companyCode) {
        BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping = new BizBaseCompanyLocalCurrecyMappingDTO();
        bizBaseCompanyLocalCurrecyMapping.setCompanyCode(companyCode);
        List<BizBaseCompanyLocalCurrecyMappingDTO> list = bizBaseCompanyLocalCurrecyMappingService.selectBizBaseCompanyLocalCurrecyMappingList(bizBaseCompanyLocalCurrecyMapping);
        if (null != list && list.size() > 0) {
            return ResultDTO.success(list.get(0));
        }
        return ResultDTO.success();
    }


    /**
     * 导出companyCode和localcurrency映射列表
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:export")
    @Log(title = "companyCode和localcurrency映射", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        List<BizBaseCompanyLocalCurrecyMappingDTO> list = bizBaseCompanyLocalCurrecyMappingService.selectBizBaseCompanyLocalCurrecyMappingList(bizBaseCompanyLocalCurrecyMapping);
        ExcelUtil<BizBaseCompanyLocalCurrecyMappingDTO> util = new ExcelUtil<BizBaseCompanyLocalCurrecyMappingDTO>(BizBaseCompanyLocalCurrecyMappingDTO.class);
        util.exportExcel(response, list, "companyCode和localcurrency映射数据");
    }

    /**
     * 获取companyCode和localcurrency映射详细信息
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseCompanyLocalCurrecyMappingDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseCompanyLocalCurrecyMappingService.selectBizBaseCompanyLocalCurrecyMappingById(id));
    }

    /**
     * 新增companyCode和localcurrency映射
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:add")
    @Log(title = "companyCode和localcurrency映射", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        return toResultDTO(bizBaseCompanyLocalCurrecyMappingService.insertBizBaseCompanyLocalCurrecyMapping(bizBaseCompanyLocalCurrecyMapping),true);
    }

    /**
     * 修改companyCode和localcurrency映射
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:edit")
    @Log(title = "companyCode和localcurrency映射", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseCompanyLocalCurrecyMappingDTO bizBaseCompanyLocalCurrecyMapping)
    {
        return toResultDTO(bizBaseCompanyLocalCurrecyMappingService.updateBizBaseCompanyLocalCurrecyMapping(bizBaseCompanyLocalCurrecyMapping),true);
    }

    /**
     * 删除companyCode和localcurrency映射
     */
    @RequiresPermissions("masterdata:bizBaseCompanyLocalCurrency:remove")
    @Log(title = "companyCode和localcurrency映射", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseCompanyLocalCurrecyMappingService.deleteBizBaseCompanyLocalCurrecyMappingByIds(ids),true);
    }
}
