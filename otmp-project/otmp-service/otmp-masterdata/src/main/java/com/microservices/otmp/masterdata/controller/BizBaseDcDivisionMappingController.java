package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDcDivisionMapping;
import com.microservices.otmp.masterdata.service.IBizBaseDcDivisionMappingService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseDcDivisionMappingController
 *
 * @author lovefamily
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/bizBaseDcDivisionMapping")
public class BizBaseDcDivisionMappingController extends BaseController
{
    @Autowired
    private IBizBaseDcDivisionMappingService bizBaseDcDivisionMappingService;

    /**
     * 查询BaseDcDivisionMapping列表
     */
    /*@RequiresPermissions("masterData:bizBaseDcDivisionMapping:list")*/
    @GetMapping("/list")
    public TableDataInfo list(BizBaseDcDivisionMapping bizBaseDcDivisionMapping)
    {
        startPage();
        List<BizBaseDcDivisionMapping> list = bizBaseDcDivisionMappingService.selectBizBaseDcDivisionMappingList(bizBaseDcDivisionMapping);
        return getDataTable(list);
    }

    /**
     * 查询BaseDcDivisionMapping列表
     */
    /*@RequiresPermissions("masterData:bizBaseDcDivisionMapping:dropDownList")*/
    @GetMapping("/dropDownList")
    public TableDataInfo dropDownList(BizBaseDcDivisionMapping bizBaseDcDivisionMapping)
    {
        List<BizBaseDcDivisionMapping> list = bizBaseDcDivisionMappingService.dropDownList(bizBaseDcDivisionMapping);
        for (BizBaseDcDivisionMapping baseDcDivisionMapping : list) {
            baseDcDivisionMapping.setDcName(baseDcDivisionMapping.getDcCode() + " (" + baseDcDivisionMapping.getDcName() + ")");
        }
        return getDataTable(list);
    }

    /**
     * 导出BaseDcDivisionMapping列表
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:export")
    @Log(title = "BaseDcDivisionMapping", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseDcDivisionMapping bizBaseDcDivisionMapping)
    {
        List<BizBaseDcDivisionMapping> list = bizBaseDcDivisionMappingService.selectBizBaseDcDivisionMappingList(bizBaseDcDivisionMapping);
        NewExcelUtil<BizBaseDcDivisionMapping> util = new NewExcelUtil<>(BizBaseDcDivisionMapping.class);
        util.exportExcel(response, list, "DcDivisionMapping");
    }

    /**
     * 导入BaseDcDivisionMapping列表
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:import")
    @Log(title = "BaseDcDivisionMapping", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO importExcel(MultipartFile file) throws Exception {

        ExcelUtil<BizBaseDcDivisionMapping> util = new ExcelUtil<>(BizBaseDcDivisionMapping.class);
        List <BizBaseDcDivisionMapping> bizs = util.importExcel(file.getInputStream());
        return ResultDTO.success(bizBaseDcDivisionMappingService.importExcel(bizs,getLoginName()));
    }


    /**
     * 获取BaseDcDivisionMapping详细信息
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseDcDivisionMappingService.selectBizBaseDcDivisionMappingById(id));
    }

    /**
     * 新增BaseDcDivisionMapping
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:add")
    @Log(title = "BaseDcDivisionMapping", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BizBaseDcDivisionMapping bizBaseDcDivisionMapping)
    {
        //重复性校验
        BizBaseDcDivisionMapping mapping = new BizBaseDcDivisionMapping();
        mapping.setSalesOrgCode(bizBaseDcDivisionMapping.getSalesOrgCode());
        mapping.setSalesOfficeCode(bizBaseDcDivisionMapping.getSalesOfficeCode());
        mapping.setDcCode(bizBaseDcDivisionMapping.getDcCode());
        List<BizBaseDcDivisionMapping> list = bizBaseDcDivisionMappingService.selectBizBaseDcDivisionMappingList(mapping);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseDcDivisionMapping.setStatus("Y");
        bizBaseDcDivisionMapping.setCreateBy(getLoginName());
        bizBaseDcDivisionMapping.setCreateTime(DateUtils.getNowDate());

        return toResultDTO(bizBaseDcDivisionMappingService.insertBizBaseDcDivisionMapping(bizBaseDcDivisionMapping),true);
    }

    /**
     * 修改BaseDcDivisionMapping
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:edit")
    @Log(title = "BaseDcDivisionMapping", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BizBaseDcDivisionMapping bizBaseDcDivisionMapping)
    {
        bizBaseDcDivisionMapping.setUpdateBy(getLoginName());
        bizBaseDcDivisionMapping.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseDcDivisionMappingService.updateBizBaseDcDivisionMapping(bizBaseDcDivisionMapping),true);
    }

    /**
     * 删除BaseDcDivisionMapping
     */
    @RequiresPermissions("masterData:bizBaseDcDivisionMapping:remove")
    @Log(title = "BaseDcDivisionMapping", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseDcDivisionMappingService.deleteBizBaseDcDivisionMappingByIds(ids),true);
    }
}
