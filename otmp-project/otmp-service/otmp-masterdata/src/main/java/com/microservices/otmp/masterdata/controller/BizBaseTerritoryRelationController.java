package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.service.IBizBaseOrgOfficeService;
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
import com.microservices.otmp.masterdata.domain.BizBaseTerritoryRelation;
import com.microservices.otmp.masterdata.service.IBizBaseTerritoryRelationService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * BaseTerritoryRelationController
 * 
 * @author lovefamily
 * @date 2022-04-26
 */
@RestController
@RequestMapping("/bizBaseTerritoryRelation")
public class BizBaseTerritoryRelationController extends BaseController
{
    @Autowired
    private IBizBaseTerritoryRelationService bizBaseTerritoryRelationService;

    @Autowired
    private IBizBaseOrgOfficeService bizBaseGeographyService;

    /**
     * 查询BaseTerritoryRelation列表
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        startPage();
        List<BizBaseTerritoryRelation> list = bizBaseTerritoryRelationService.selectBizBaseTerritoryRelationList(bizBaseTerritoryRelation);
        return getDataTable(list);
    }


    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        List<BizBaseTerritoryRelation> list = bizBaseGeographyService.getTerritoryDropDownList(bizBaseDropDownCondition);
        return getDataTable(list);
    }
    /**
     * 导出BaseTerritoryRelation列表
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:export")
    @Log(title = "BaseTerritoryRelation", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        List<BizBaseTerritoryRelation> list = bizBaseTerritoryRelationService.selectBizBaseTerritoryRelationList(bizBaseTerritoryRelation);
        NewExcelUtil<BizBaseTerritoryRelation> util = new NewExcelUtil<>(BizBaseTerritoryRelation.class);
        util.exportExcel(response, list, "TerritoryRelation");
    }

    /**
    * 导入BaseTerritoryRelation列表
    */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:import")
    @Log(title = "BaseTerritoryRelation", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {

        ExcelUtil<BizBaseTerritoryRelation> util = new ExcelUtil<BizBaseTerritoryRelation>(BizBaseTerritoryRelation.class);
        List <BizBaseTerritoryRelation> bizs = util.importExcel(file.getInputStream());
        return ResultDTO.success(bizBaseTerritoryRelationService.importExcel(bizs,getLoginName()));
    }


    /**
     * 获取BaseTerritoryRelation详细信息
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseTerritoryRelation> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseTerritoryRelationService.selectBizBaseTerritoryRelationById(id));
    }

    /**
     * 新增BaseTerritoryRelation
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:add")
    @Log(title = "BaseTerritoryRelation", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        return toResultDTO(bizBaseTerritoryRelationService.insertBizBaseTerritoryRelation(bizBaseTerritoryRelation),true);
    }

    /**
     * 修改BaseTerritoryRelation
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:edit")
    @Log(title = "BaseTerritoryRelation", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseTerritoryRelation bizBaseTerritoryRelation)
    {
        return toResultDTO(bizBaseTerritoryRelationService.updateBizBaseTerritoryRelation(bizBaseTerritoryRelation),true);
    }

    /**
     * 删除BaseTerritoryRelation
     */
    @RequiresPermissions("masterData:bizBaseTerritoryRelation:remove")
    @Log(title = "BaseTerritoryRelation", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseTerritoryRelationService.deleteBizBaseTerritoryRelationByIds(ids),true);
    }
}
