package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.ViewConditionValue;
import com.microservices.otmp.system.domain.entity.ViewConditionValueDO;
import com.microservices.otmp.system.service.IViewConditionValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * viewConditionValueController
 *
 * @author lovefamily
 * @date 2022-06-13
 */
@RestController
@RequestMapping("/viewConditionValue")
public class ViewConditionValueController extends BaseController {
    @Autowired
    private IViewConditionValueService viewConditionValueService;

    /**
     * 查询viewConditionValue列表
     */
    @RequiresPermissions("system:viewConditionValue:list")
    @GetMapping("/list")
    public TableDataInfo list(ViewConditionValue viewConditionValue) {
        startPage();
        return getDataTable(viewConditionValueService.selectViewConditionValueList(viewConditionValue));
    }

    /**
     * 导出viewConditionValue列表
     */
    @RequiresPermissions("system:viewConditionValue:export")
    @Log(title = "viewConditionValue", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ViewConditionValue viewConditionValue) {
        List<ViewConditionValueDO> list = viewConditionValueService.selectViewConditionValueList(viewConditionValue);
        ExcelUtil<ViewConditionValueDO> util = new ExcelUtil<>(ViewConditionValueDO.class);
        util.exportExcel(response, list, "viewConditionValue数据");
    }

    /**
     * 获取viewConditionValue详细信息
     */
    @RequiresPermissions("system:viewConditionValue:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<ViewConditionValueDO> getInfo(@PathVariable("id") Integer id) {
        return ResultDTO.success(viewConditionValueService.selectViewConditionValueById(id));
    }

    /**
     * 新增viewConditionValue
     */
    @RequiresPermissions("system:viewConditionValue:add")
    @Log(title = "viewConditionValue", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody ViewConditionValue viewConditionValue) {
        return ResultDTO.success(viewConditionValueService.insertViewConditionValue(viewConditionValue));
    }

    /**
     * 修改viewConditionValue
     */
    @RequiresPermissions("system:viewConditionValue:edit")
    @Log(title = "viewConditionValue", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody ViewConditionValue viewConditionValue) {
        return ResultDTO.success(viewConditionValueService.updateViewConditionValue(viewConditionValue));
    }

    /**
     * 删除viewConditionValue
     */
    @RequiresPermissions("system:viewConditionValue:remove")
    @Log(title = "viewConditionValue", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Integer[] ids) {
        return ResultDTO.success(viewConditionValueService.deleteViewConditionValueByIds(ids));
    }
}
