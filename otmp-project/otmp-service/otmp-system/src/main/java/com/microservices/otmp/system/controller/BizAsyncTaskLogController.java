package com.microservices.otmp.system.controller;

import com.github.pagehelper.Page;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.AsyncTaskStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.service.IBizAsyncTaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 记录文件上传下载信息Controller
 *
 * @author lovefamily
 * @date 2022-09-29
 */
@RestController
@RequestMapping("/bizAsyncTaskLog")
public class BizAsyncTaskLogController extends BaseController {
    @Autowired
    private IBizAsyncTaskLogService bizAsyncTaskLogService;

    /**
     * 查询记录文件上传下载信息列表
     */
    @RequiresPermissions("system:bizAsyncTaskLog:list")
    @GetMapping("/list")
    public TableDataInfo list(BizAsyncTaskLogDTO bizAsyncTaskLog) {

        Page<Object> count = startPage();
        List<BizAsyncTaskLogDTO> list = bizAsyncTaskLogService.selectBizAsyncTaskLogList(bizAsyncTaskLog);
        for (BizAsyncTaskLogDTO bizAsyncTaskLogDTO : list) {
            bizAsyncTaskLogDTO.setStatusName(AsyncTaskStatusEnum.getName(bizAsyncTaskLogDTO.getStatus()));
        }
        return getDataTable(list, count);
    }


    /**
     * 获取记录文件上传下载信息详细信息
     */
    @RequiresPermissions("system:bizAsyncTaskLog:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizAsyncTaskLogDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizAsyncTaskLogService.selectBizAsyncTaskLogById(id));
    }

    /**
     * 新增记录文件上传下载信息
     */
    @RequiresPermissions("system:bizAsyncTaskLog:add")
    @Log(title = "记录文件上传下载信息", businessType = BusinessType.INSERT)
    @PostMapping("add")
    public ResultDTO<BizAsyncTaskLogDTO> add(@RequestBody BizAsyncTaskLogDTO bizAsyncTaskLog) {
        BizAsyncTaskLogDTO bizAsyncTaskLogDTO = bizAsyncTaskLogService.insertBizAsyncTaskLog(bizAsyncTaskLog);
        return ResultDTO.success(bizAsyncTaskLogDTO);
    }

    /**
     * 修改记录文件上传下载信息
     */
    @RequiresPermissions("system:bizAsyncTaskLog:edit")
    @Log(title = "记录文件上传下载信息", businessType = BusinessType.UPDATE)
    @PostMapping("edit")
    public ResultDTO<Integer> edit(@RequestBody BizAsyncTaskLogDTO bizAsyncTaskLog) {
        return toResultDTO(bizAsyncTaskLogService.updateBizAsyncTaskLog(bizAsyncTaskLog), true);
    }

    /**
     * 删除记录文件上传下载信息
     */
    @RequiresPermissions("system:bizAsyncTaskLog:remove")
    @Log(title = "记录文件上传下载信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizAsyncTaskLogService.deleteBizAsyncTaskLogByIds(ids), true);
    }

    /**
     * 获取记录文件上传下载信息详细信息
     */
    @GetMapping(value = "/getProgress")
    public ResultDTO<Object> getProgress(@RequestParam Long[] ids) {
        List<BizAsyncTaskLogDTO> progress = bizAsyncTaskLogService.getProgress(ids);
        for (BizAsyncTaskLogDTO bizAsyncTaskLogDTO : progress) {
            bizAsyncTaskLogDTO.setStatusName(AsyncTaskStatusEnum.getName(bizAsyncTaskLogDTO.getStatus()));
        }
        return ResultDTO.success(progress);
    }

    /**
     * 中断异步任务
     */
    @RequiresPermissions("system:bizAsyncTaskLog:deleteAsyncTask")
    @Log(title = "删除异步任务", businessType = BusinessType.UPDATE)
    @GetMapping("deleteAsyncTask")
    public ResultDTO<Object> edit(Long id) {
        bizAsyncTaskLogService.deleteAsyncTask(id);
        return ResultDTO.success();
    }

    /**
     * 异步 业务类型 下拉
     */
    @RequiresPermissions("system:bizAsyncTaskLog:businessTypeSelect")
    @GetMapping("/businessTypeSelect")
    public TableDataInfo businessTypeSelect() {
        List<SysDictData> list = bizAsyncTaskLogService.businessTypeSelect();
        return getDataTable(list);
    }

    /**
     * module 下拉列表
     */
    @RequiresPermissions("system:bizAsyncTaskLog:moduleSelect")
    @GetMapping("/moduleSelect")
    public TableDataInfo moduleSelect() {
        List<SysDictData> list = bizAsyncTaskLogService.moduleSelect();
        return getDataTable(list);
    }

    /**
     * module 下拉列表
     */
    @RequiresPermissions("system:bizAsyncTaskLog:statusSelect")
    @GetMapping("/statusSelect")
    public TableDataInfo statusSelect() {
        List<SysDictData> list = bizAsyncTaskLogService.statusSelect();
        return getDataTable(list);
    }

}
