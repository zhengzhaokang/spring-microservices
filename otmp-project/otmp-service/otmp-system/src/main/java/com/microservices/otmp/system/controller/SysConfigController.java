package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.SysConfig;
import com.microservices.otmp.system.service.ISysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置 提供者
 *
 * @Author lovefamily
 * @date 2019-05-20
 */
@RestController
@RequestMapping("config")
public class SysConfigController extends BaseController {

    @Autowired
    private ISysConfigService sysConfigService;

    /**
     * 查询参数配置
     */
    @GetMapping("get/{configId}")
    public SysConfig get(@PathVariable("configId") Long configId) {
        return sysConfigService.selectConfigById(configId);

    }

    @GetMapping("/getConfigKey")
    public String getConfigKey(@RequestParam("configKey") String configKey) {
        return sysConfigService.selectConfigByKey(configKey);

    }

    /**
     * 查询参数配置列表
     */
    @GetMapping("list")
    @HasPermissions("system:config:view")
    public TableDataInfo list(SysConfig sysConfig) {
        startPage();
        return getDataTable(sysConfigService.selectConfigPage(sysConfig));
    }


    /**
     * 新增保存参数配置
     */
    @PostMapping("save")
    @RequiresPermissions("system:config:create")
    public ResultDTO<Integer> addSave(@RequestBody SysConfig sysConfig) {
        return ResultDTO.success(sysConfigService.insertConfig(sysConfig));
    }

    /**
     * 修改保存参数配置
     */
    @PostMapping("update")
    @RequiresPermissions("system:config:edit")
    public ResultDTO<Integer> editSave(@RequestBody SysConfig sysConfig) {
        return ResultDTO.success(sysConfigService.updateConfig(sysConfig));
    }

    /**
     * 删除参数配置
     */
    @PostMapping("remove")
    @RequiresPermissions("system:config:remove")
    public ResultDTO<Integer> remove(String ids) {
        return ResultDTO.success(sysConfigService.deleteConfigByIds(ids));
    }

}
