package com.microservices.otmp.erp.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.erp.domain.SysWording;
import com.microservices.otmp.erp.service.ISysWordingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * wordController
 * 
 * @author shirui3
 * @date 2022-05-07
 */
@RestController
@RequestMapping("/maintenance")
public class SysWordingController extends BaseController
{
    @Autowired
    private ISysWordingService sysWordingService;

    /**
     * 查询word列表
     */
    @RequiresPermissions("word:wording:list")
    @GetMapping("/list")
    public TableDataInfo list(SysWording sysWording)
    {
        startPage();
        List<SysWording> list = sysWordingService.selectSysWordingList(sysWording);
        return getDataTable(list);
    }


    @GetMapping(value = "/getWordFromRedis")
    public SysWording getWordFromRedis(@RequestParam("wordingKey") Long wordingKey)
    {
        return sysWordingService.getWordFromRedis(wordingKey);
    }

    /**
     * 导出word列表
     */
    @RequiresPermissions("word:wording:export")
    @Log(title = "word", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysWording sysWording)
    {
        List<SysWording> list = sysWordingService.selectSysWordingList(sysWording);
        NewExcelUtil<SysWording> util = new NewExcelUtil<>(SysWording.class);
        util.exportExcel(response, list, "word数据");
    }

    /**
    * 导入word列表
    */
    @RequiresPermissions("word:wording:import")
    @Log(title = "word", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {

        ExcelUtil<SysWording> util = new ExcelUtil<>(SysWording.class);
        List <SysWording> wordings = util.importExcel(file.getInputStream());
        return ResultDTO.success(sysWordingService.importExcel(wordings,getLoginName()));
    }


    /**
     * 获取word详细信息
     */
    @RequiresPermissions("word:wording:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<SysWording> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(sysWordingService.selectSysWordingById(id));
    }

    /**
     * 新增word
     */
    @RequiresPermissions("word:wording:add")
    @Log(title = "word", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody SysWording sysWording)
    {
        sysWording.setCreateBy(getLoginName());
        sysWording.setUpdateBy(getLoginName());
        sysWording.setCreateTime(DateUtils.getNowDate());
        sysWording.setUpdateTime(DateUtils.getNowDate());
        return ResultDTO.success(sysWordingService.insertSysWording(sysWording));
    }

    /**
     * 修改word
     */
    @RequiresPermissions("word:wording:edit")
    @Log(title = "word", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody SysWording sysWording)
    {
        sysWording.setUpdateBy(getLoginName());
        sysWording.setUpdateTime(DateUtils.getNowDate());
        return ResultDTO.success(sysWordingService.updateSysWording(sysWording));
    }

    /**
     * 删除word
     */
    @RequiresPermissions("word:wording:remove")
    @Log(title = "word", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return ResultDTO.success(sysWordingService.deleteSysWordingByIds(ids));
    }

    @GetMapping(value = "/refreshRedis")
    public ResultDTO<Object> refreshRedis()
    {
        sysWordingService.refreshWordIntoRedis();
        return ResultDTO.success();
    }

    /**
     * 从redis获取word提示语 json
     */
    @GetMapping(value = "/json")
    public ResultDTO<String> json()
    {
        return ResultDTO.success(sysWordingService.json());
    }
}
