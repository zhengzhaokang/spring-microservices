package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.domain.dto.BizSdmsSystemAnnouncementDTO;
import com.microservices.otmp.system.service.IBizSdmsSystemAnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizSdmsSystemAnnouncementController
 *
 * @author lovefamily
 * @date 2023-02-28
 */
@RestController
@RequestMapping("/announcement")
public class BizSdmsSystemAnnouncementController extends BaseController {
    @Autowired
    private IBizSdmsSystemAnnouncementService bizSdmsSystemAnnouncementService;

    /**
     * 查询BizSdmsSystemAnnouncement列表
     */
    @RequiresPermissions("system:announcement:list")
    @GetMapping("/list")
    public TableDataInfo list(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        List<BizSdmsSystemAnnouncementDTO> list = bizSdmsSystemAnnouncementService.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement).getList();
        return getDataTable(list);
    }

    /**
     * 查询BizSdmsSystemAnnouncement列表
     */
    @RequiresPermissions("system:announcement:list")
    @GetMapping("/see")
    public TableDataInfo see(BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        List<BizSdmsSystemAnnouncementDTO> list = bizSdmsSystemAnnouncementService.see(bizSdmsSystemAnnouncement);
        return getDataTable(list);
    }

    /**
     * 导出BizSdmsSystemAnnouncement列表
     */
    @RequiresPermissions("system:announcement:export")
    @Log(title = "BizSdmsSystemAnnouncement", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        List<BizSdmsSystemAnnouncementDTO> list = bizSdmsSystemAnnouncementService.selectBizSdmsSystemAnnouncementList(bizSdmsSystemAnnouncement).getList();
        ExcelUtil<BizSdmsSystemAnnouncementDTO> util = new ExcelUtil<>(BizSdmsSystemAnnouncementDTO.class);
        util.exportExcel(response, list, "BizSdmsSystemAnnouncement数据");
    }

    /**
     * 获取BizSdmsSystemAnnouncement详细信息
     */
    @RequiresPermissions("system:announcement:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizSdmsSystemAnnouncementDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizSdmsSystemAnnouncementService.selectBizSdmsSystemAnnouncementById(id));
    }

    /**
     * 新增BizSdmsSystemAnnouncement
     */
    @RequiresPermissions("system:announcement:add")
    @Log(title = "BizSdmsSystemAnnouncement", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        String loginName = getLoginName();
        bizSdmsSystemAnnouncement.setCreateBy(loginName);
        bizSdmsSystemAnnouncement.setUpdateBy(loginName);
        return toResultDTO(bizSdmsSystemAnnouncementService.insertBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement), true);
    }

    /**
     * 修改BizSdmsSystemAnnouncement
     */
    @RequiresPermissions("system:announcement:edit")
    @Log(title = "BizSdmsSystemAnnouncement", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizSdmsSystemAnnouncementDTO bizSdmsSystemAnnouncement) {
        String loginName = getLoginName();
        bizSdmsSystemAnnouncement.setUpdateBy(loginName);
        return toResultDTO(bizSdmsSystemAnnouncementService.updateBizSdmsSystemAnnouncement(bizSdmsSystemAnnouncement), true);
    }

    /**
     * 删除BizSdmsSystemAnnouncement
     */
    @RequiresPermissions("system:announcement:remove")
    @Log(title = "BizSdmsSystemAnnouncement", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizSdmsSystemAnnouncementService.deleteBizSdmsSystemAnnouncementByIds(ids), true);
    }
}
