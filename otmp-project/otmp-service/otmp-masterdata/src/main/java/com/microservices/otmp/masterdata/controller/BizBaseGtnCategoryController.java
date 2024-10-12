package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.masterdata.service.IBizBaseGtnCategoryService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnCategoryDTO;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * gtnCategoryController
 * 
 * @author lovefamily
 * @date 2023-06-05
 */
@RestController
@RequestMapping("/gtnCategory")
public class BizBaseGtnCategoryController extends BaseController
{
    @Autowired
    private IBizBaseGtnCategoryService bizBaseGtnCategoryService;

    /**
     * 查询gtnCategory列表
     */
    @RequiresPermissions("masterdata:gtnCategory:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseGtnCategoryDTO bizBaseGtnCategory)
    {
        startPage();
        List<BizBaseGtnCategoryDTO> list = bizBaseGtnCategoryService.selectBizBaseGtnCategoryList(bizBaseGtnCategory);
        return getDataTable(list);
    }

    /**
     * 导出gtnCategory列表
     */
    @RequiresPermissions("masterdata:gtnCategory:export")
    @Log(title = "gtnCategory", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseGtnCategoryDTO bizBaseGtnCategory)
    {
        List<BizBaseGtnCategoryDTO> list = bizBaseGtnCategoryService.selectBizBaseGtnCategoryList(bizBaseGtnCategory);
        NewExcelUtil<BizBaseGtnCategoryDTO> util = new NewExcelUtil<BizBaseGtnCategoryDTO>(BizBaseGtnCategoryDTO.class);
        util.exportExcel(response, list, "gtnCategory数据");
    }

    /**
     * 获取gtnCategory详细信息
     */
    @RequiresPermissions("masterdata:gtnCategory:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseGtnCategoryService.selectBizBaseGtnCategoryById(id));
    }


    @RequiresPermissions("masterData:gtnCategory:list")
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition downCondition)
    {
        List<BizBaseGtnCategoryDTO> list = bizBaseGtnCategoryService.getDropDownList(downCondition);
        return getDataTable(list);
    }


}
