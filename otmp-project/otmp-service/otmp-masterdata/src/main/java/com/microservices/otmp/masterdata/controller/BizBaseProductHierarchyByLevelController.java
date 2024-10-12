package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseProductHierarchyByLevel;
import com.microservices.otmp.masterdata.service.IBizBaseProductHierarchyByLevelService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseProductHierarchyByLevelController
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@RestController
@RequestMapping("/bizBaseProductHierarchyByLevel")
public class BizBaseProductHierarchyByLevelController extends BaseController
{
    @Autowired
    private IBizBaseProductHierarchyByLevelService bizBaseProductHierarchyByLevelService;

    /**
     * 查询BaseProductHierarchyByLevel列表
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        startPage();
        List<BizBaseProductHierarchyByLevel> list = bizBaseProductHierarchyByLevelService.selectBizBaseProductHierarchyByLevelList(bizBaseProductHierarchyByLevel);
        return getDataTable(list);
    }

    /**
     * 导出BaseProductHierarchyByLevel列表
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:export")
    @Log(title = "BaseProductHierarchyByLevel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        List<BizBaseProductHierarchyByLevel> list = bizBaseProductHierarchyByLevelService.selectBizBaseProductHierarchyByLevelList(bizBaseProductHierarchyByLevel);
        ExcelUtil<BizBaseProductHierarchyByLevel> util = new ExcelUtil<BizBaseProductHierarchyByLevel>(BizBaseProductHierarchyByLevel.class);
        util.exportExcel(response, list, "BaseProductHierarchyByLevel数据");
    }

    /**
    * 导入BaseProductHierarchyByLevel列表
    */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:import")
    @Log(title = "BaseProductHierarchyByLevel", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {

        NewExcelUtil<BizBaseProductHierarchyByLevel> util = new NewExcelUtil<BizBaseProductHierarchyByLevel>(BizBaseProductHierarchyByLevel.class);
        List <BizBaseProductHierarchyByLevel> bizs = util.importExcel(file.getInputStream());
        return ResultDTO.success(bizBaseProductHierarchyByLevelService.importExcel(bizs,getLoginName()));
    }


    /**
     * 获取BaseProductHierarchyByLevel详细信息
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseProductHierarchyByLevel> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseProductHierarchyByLevelService.selectBizBaseProductHierarchyByLevelById(id));
    }

    /**
     * 新增BaseProductHierarchyByLevel
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:add")
    @Log(title = "BaseProductHierarchyByLevel", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        return toResultDTO(bizBaseProductHierarchyByLevelService.insertBizBaseProductHierarchyByLevel(bizBaseProductHierarchyByLevel),true);
    }

    /**
     * 修改BaseProductHierarchyByLevel
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:edit")
    @Log(title = "BaseProductHierarchyByLevel", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseProductHierarchyByLevel bizBaseProductHierarchyByLevel)
    {
        return toResultDTO(bizBaseProductHierarchyByLevelService.updateBizBaseProductHierarchyByLevel(bizBaseProductHierarchyByLevel),true);
    }

    /**
     * 删除BaseProductHierarchyByLevel
     */
    @RequiresPermissions("masterdata:bizBaseProductHierarchyByLevel:remove")
    @Log(title = "BaseProductHierarchyByLevel", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseProductHierarchyByLevelService.deleteBizBaseProductHierarchyByLevelByIds(ids),true);
    }
}
