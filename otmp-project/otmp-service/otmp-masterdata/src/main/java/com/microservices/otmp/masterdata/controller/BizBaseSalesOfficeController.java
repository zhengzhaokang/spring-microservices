package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.AjaxResult;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOfficeService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.AjaxResult;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOffice;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizBaseSalesOfficeController
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
@RestController
@RequestMapping("/bizBaseSalesOffice")
public class BizBaseSalesOfficeController extends BaseController
{
    @Autowired
    private IBizBaseSalesOfficeService bizBaseSalesOfficeService;

    /**
     * 查询BizBaseSalesOffice列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSalesOffice bizBaseSalesOffice)
    {
        startPage();
        List<BizBaseSalesOffice> list = bizBaseSalesOfficeService.selectBizBaseSalesOfficeList(bizBaseSalesOffice);
        return getDataTable(list);
    }

    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        List<BizBaseSalesOffice> list = bizBaseSalesOfficeService.getDropDownList(bizBaseDropDownCondition);
        return getDataTable(list);
    }

    /**
     * 导出BizBaseSalesOffice列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:export")
    @OperLog(title = "BizBaseSalesOffice", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSalesOffice bizBaseSalesOffice)
    {
        List<BizBaseSalesOffice> list = bizBaseSalesOfficeService.selectBizBaseSalesOfficeList(bizBaseSalesOffice);
        NewExcelUtil<BizBaseSalesOffice> util = new NewExcelUtil<>(BizBaseSalesOffice.class);
        util.exportExcel(response, list, "SalesOffice");
    }

    /**
     * 导入BizBaseSalesOffice列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterdata:bizBaseSalesOffice:import")
    @OperLog(title = "BizBaseSalesOffice", businessType = BusinessType.IMPORT)
    public AjaxResult importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseSalesOffice> util = new ExcelUtil<>(BizBaseSalesOffice.class);
        List <BizBaseSalesOffice> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseSalesOfficeService.importExcel(bizs,getLoginName());

        return AjaxResult.success(message);
    }

    /**
     * 获取BizBaseSalesOffice详细信息
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bizBaseSalesOfficeService.selectBizBaseSalesOfficeById(id));
    }

    /**
     * 新增BizBaseSalesOffice
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:add")
    @OperLog(title = "BizBaseSalesOffice", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizBaseSalesOffice bizBaseSalesOffice)
    {
        //重复性校验
        BizBaseSalesOffice office = new BizBaseSalesOffice();
        office.setSalesOrgCode(bizBaseSalesOffice.getSalesOrgCode());
        office.setSalesOfficeCode(bizBaseSalesOffice.getSalesOfficeCode());
        List<BizBaseSalesOffice> list = bizBaseSalesOfficeService.selectBizBaseSalesOfficeList(office);
//        if (CollectionUtils.isNotEmpty(list)) {
//            return AjaxResult.error("Record exists already");
//        }
        bizBaseSalesOffice.setStatus("Y");
        bizBaseSalesOffice.setCreateBy(getLoginName());
        bizBaseSalesOffice.setCreateTime(DateUtils.getNowDate());
        return toAjax(bizBaseSalesOfficeService.insertBizBaseSalesOffice(bizBaseSalesOffice),true);
    }

    /**
     * 修改BizBaseSalesOffice
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:edit")
    @OperLog(title = "BizBaseSalesOffice", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizBaseSalesOffice bizBaseSalesOffice)
    {
        bizBaseSalesOffice.setUpdateBy(getLoginName());
        bizBaseSalesOffice.setUpdateTime(DateUtils.getNowDate());
        return toAjax(bizBaseSalesOfficeService.updateBizBaseSalesOffice(bizBaseSalesOffice),true);
    }

    /**
     * 删除BizBaseSalesOffice
     */
    @RequiresPermissions("masterData:bizBaseSalesOffice:remove")
    @OperLog(title = "BizBaseSalesOffice", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizBaseSalesOfficeService.deleteBizBaseSalesOfficeByIds(ids),true);
    }
}
