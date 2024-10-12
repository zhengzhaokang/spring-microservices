package com.microservices.otmp.system.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.domain.dto.BizSdmsJobLogDTO;
import com.microservices.otmp.system.domain.dto.RemoteSdmsJobLogDTO;
import com.microservices.otmp.system.service.IBizSdmsJobLogService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * jobMonitorController
 *
 * @author dhc
 * @date 2022-10-11
 */
@RestController
@RequestMapping("/jobMonitor")
public class BizSdmsJobLogController extends BaseController {
    @Autowired
    private IBizSdmsJobLogService bizSdmsJobLogService;

    /**
     * 查询jobMonitor列表
     */
    /* @RequiresPermissions("system:jobMonitor:list")*/
    @GetMapping("/list")
    public TableDataInfo list(BizSdmsJobLogDTO bizSdmsJobLog) {
        startPage();
        List<BizSdmsJobLogDTO> list = bizSdmsJobLogService.selectBizSdmsJobLogList(bizSdmsJobLog);
        return getDataTable(list);
    }

    protected TableDataInfo getDataTable(List<BizSdmsJobLogDTO> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        if (list.size() > 1) {
            PageInfo<BizSdmsJobLogDTO> of = PageInfo.of(list);
            rspData.setTotal(of.getTotal());
        }
        return rspData;
    }

    /**
     * 导出jobMonitor列表
     */
    /* @RequiresPermissions("system:jobMonitor:export")*/
    @Log(title = "jobMonitor", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSdmsJobLogDTO bizSdmsJobLog) {
        List<BizSdmsJobLogDTO> list = bizSdmsJobLogService.selectBizSdmsJobLogList(bizSdmsJobLog);
        ExcelUtil<BizSdmsJobLogDTO> util = new ExcelUtil<>(BizSdmsJobLogDTO.class);
        util.exportExcel(response, list, "jobMonitor数据");
    }

    /**
     * 获取jobMonitor详细信息
     */
    /* @RequiresPermissions("system:jobMonitor:query")*/
    @GetMapping(value = "/{id}")
    public ResultDTO<BizSdmsJobLogDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizSdmsJobLogService.selectBizSdmsJobLogById(id));
    }

    /**
     * 新增jobMonitor
     */
    @Log(title = "jobMonitor", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public ResultDTO<Object> add(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog) {
        BizSdmsJobLogDTO bizSdmsJobLogDTO = new BizSdmsJobLogDTO();
        BeanUtils.copyProperties(bizSdmsJobLog, bizSdmsJobLogDTO);
        int i = bizSdmsJobLogService.insertBizSdmsJobLog(bizSdmsJobLogDTO);
        bizSdmsJobLog.setId(bizSdmsJobLogDTO.getId());
        return i > 0 ? ResultDTO.success(bizSdmsJobLog) : ResultDTO.fail(500, "添加失败", bizSdmsJobLog);
    }

    @PostMapping("/getJobLogDTO")
    public ResultDTO<RemoteSdmsJobLogDTO> getSdmsJobLogDTO(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog) {
        BizSdmsJobLogDTO bizSdmsJobLogDTO = new BizSdmsJobLogDTO();
        BeanUtils.copyProperties(bizSdmsJobLog, bizSdmsJobLogDTO);
        bizSdmsJobLog.setId(getSdmsJobLogId(bizSdmsJobLogDTO));
        return ResultDTO.success(bizSdmsJobLog);
    }

    private Long getSdmsJobLogId(BizSdmsJobLogDTO bizSdmsJobLogDTO) {
        List<BizSdmsJobLogDTO> bizSdmsJobLogDTOS = bizSdmsJobLogService.selectBizSdmsJobLogList(bizSdmsJobLogDTO);
        if (CollectionUtils.isEmpty(bizSdmsJobLogDTOS)) {
            return null;
        }
        BizSdmsJobLogDTO sdmsJobLogDTO = bizSdmsJobLogDTOS.get(0);
        return sdmsJobLogDTO.getId();
    }

    /**
     * 修改jobMonitor
     */
    /*@RequiresPermissions("system:jobMonitor:edit")*/
    @Log(title = "jobMonitor", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public ResultDTO<Integer> edit(@RequestBody RemoteSdmsJobLogDTO bizSdmsJobLog) {
        BizSdmsJobLogDTO bizSdmsJobLogDTO = new BizSdmsJobLogDTO();
        BeanUtils.copyProperties(bizSdmsJobLog, bizSdmsJobLogDTO);
        return toResultDTO(bizSdmsJobLogService.updateBizSdmsJobLog(bizSdmsJobLogDTO), true);
    }

    /**
     * 删除jobMonitor
     */
    /* @RequiresPermissions("system:jobMonitor:remove")*/
    @Log(title = "jobMonitor", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizSdmsJobLogService.deleteBizSdmsJobLogByIds(ids), true);
    }
}
