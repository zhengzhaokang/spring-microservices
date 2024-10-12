package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.BizSdmsPersonalQuickLinkConfig;
import com.microservices.otmp.system.service.IBizSdmsPersonalQuickLinkConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizSdmsPersonalQuickLinkConfigController
 *
 * @author lovefamily
 * @date 2022-07-20
 */
@RestController
@RequestMapping("/bizSdmsPersonalQuickLinkConfig")
public class BizSdmsPersonalQuickLinkConfigController extends BaseController {
    @Autowired
    private IBizSdmsPersonalQuickLinkConfigService bizSdmsPersonalQuickLinkConfigService;

    /**
     * 查询BizSdmsPersonalQuickLinkConfig列表
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:list")
    @GetMapping("/list")
    public TableDataInfo list(BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        startPage();
        List<BizSdmsPersonalQuickLinkConfig> list = bizSdmsPersonalQuickLinkConfigService.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);
        return getDataTable(list);
    }

    /**
     * 导出BizSdmsPersonalQuickLinkConfig列表
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:export")
    @Log(title = "BizSdmsPersonalQuickLinkConfig", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        List<BizSdmsPersonalQuickLinkConfig> list = bizSdmsPersonalQuickLinkConfigService.selectBizSdmsPersonalQuickLinkConfigList(bizSdmsPersonalQuickLinkConfig);
        ExcelUtil<BizSdmsPersonalQuickLinkConfig> util = new ExcelUtil<>(BizSdmsPersonalQuickLinkConfig.class);
        util.exportExcel(response, list, "PersonalQuickLinkConfig");
    }

    /**
     * 获取BizSdmsPersonalQuickLinkConfig详细信息
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:query")
    @GetMapping(value = "/{quickLinkConfigId}")
    public ResultDTO<BizSdmsPersonalQuickLinkConfig> getInfo(@PathVariable("quickLinkConfigId") Long quickLinkConfigId) {
        return ResultDTO.success(bizSdmsPersonalQuickLinkConfigService.selectBizSdmsPersonalQuickLinkConfigByQuickLinkConfigId(quickLinkConfigId));
    }

    /**
     * 新增BizSdmsPersonalQuickLinkConfig
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:add")
    @Log(title = "BizSdmsPersonalQuickLinkConfig", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        return toResultDTO(bizSdmsPersonalQuickLinkConfigService.insertBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig), true);
    }

    /**
     * 修改BizSdmsPersonalQuickLinkConfig
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:edit")
    @Log(title = "BizSdmsPersonalQuickLinkConfig", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizSdmsPersonalQuickLinkConfig bizSdmsPersonalQuickLinkConfig) {
        return toResultDTO(bizSdmsPersonalQuickLinkConfigService.updateBizSdmsPersonalQuickLinkConfig(bizSdmsPersonalQuickLinkConfig), true);
    }

    /**
     * 删除BizSdmsPersonalQuickLinkConfig
     */
    @RequiresPermissions("system:bizSdmsPersonalQuickLinkConfig:remove")
    @Log(title = "BizSdmsPersonalQuickLinkConfig", businessType = BusinessType.DELETE)
    @DeleteMapping("/{quickLinkConfigIds}")
    public ResultDTO<Integer> remove(@PathVariable Long[] quickLinkConfigIds) {
        return toResultDTO(bizSdmsPersonalQuickLinkConfigService.deleteBizSdmsPersonalQuickLinkConfigByQuickLinkConfigIds(quickLinkConfigIds), true);
    }
}
