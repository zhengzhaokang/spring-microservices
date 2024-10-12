package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseMbgCustomerDrmTomsTofiDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseMbgCustomerDrmTomsTofiDO;
import com.microservices.otmp.masterdata.service.IBizBaseMbgCustomerDrmTomsTofiService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizBaseMbgCustomerDrmTomsTofiController
 *
 * @author lovefamily
 * @date 2023-03-01
 */
@RestController
@RequestMapping("/bizBaseMbgCustomerDrmTomsTofi")
public class BizBaseMbgCustomerDrmTomsTofiController extends BaseController {
    @Autowired
    private IBizBaseMbgCustomerDrmTomsTofiService bizBaseMbgCustomerDrmTomsTofiService;

    /**
     * 查询BizBaseMbgCustomerDrmTomsTofi列表
     */
    @RequiresPermissions("masterdata:tofi:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        startPage();
        List<BizBaseMbgCustomerDrmTomsTofiDTO> list = bizBaseMbgCustomerDrmTomsTofiService.selectBizBaseMbgCustomerDrmTomsTofiList(bizBaseMbgCustomerDrmTomsTofi);
        return getDataTable(list);
    }

    /**
     * 导出BizBaseMbgCustomerDrmTomsTofi列表
     */
    @RequiresPermissions("masterdata:tofi:export")
    @Log(title = "BizBaseMbgCustomerDrmTomsTofi", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        List<BizBaseMbgCustomerDrmTomsTofiDTO> list = bizBaseMbgCustomerDrmTomsTofiService.selectBizBaseMbgCustomerDrmTomsTofiList(bizBaseMbgCustomerDrmTomsTofi);
        NewExcelUtil<BizBaseMbgCustomerDrmTomsTofiDTO> util = new NewExcelUtil<>(BizBaseMbgCustomerDrmTomsTofiDTO.class);
        util.exportExcel(response, list, "BizBaseMbgCustomerDrmTomsTofi数据");
    }

    /**
     * 获取BizBaseMbgCustomerDrmTomsTofi详细信息
     */
    @RequiresPermissions("masterdata:tofi:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseMbgCustomerDrmTomsTofiDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseMbgCustomerDrmTomsTofiService.selectBizBaseMbgCustomerDrmTomsTofiById(id));
    }

    /**
     * 新增BizBaseMbgCustomerDrmTomsTofi
     */
    @RequiresPermissions("masterdata:tofi:add")
    @Log(title = "BizBaseMbgCustomerDrmTomsTofi", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        return toResultDTO(bizBaseMbgCustomerDrmTomsTofiService.insertBizBaseMbgCustomerDrmTomsTofi(bizBaseMbgCustomerDrmTomsTofi), true);
    }

    /**
     * 修改BizBaseMbgCustomerDrmTomsTofi
     */
    @RequiresPermissions("masterdata:tofi:edit")
    @Log(title = "BizBaseMbgCustomerDrmTomsTofi", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        return toResultDTO(bizBaseMbgCustomerDrmTomsTofiService.updateBizBaseMbgCustomerDrmTomsTofi(bizBaseMbgCustomerDrmTomsTofi), true);
    }

    /**
     * 删除BizBaseMbgCustomerDrmTomsTofi
     */
    @RequiresPermissions("masterdata:tofi:remove")
    @Log(title = "BizBaseMbgCustomerDrmTomsTofi", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseMbgCustomerDrmTomsTofiService.deleteBizBaseMbgCustomerDrmTomsTofiByIds(ids), true);
    }

    @GetMapping(value = "/getByCustomerNumber")
    public ResultDTO<BizBaseMbgCustomerDrmTomsTofiDTO> getByCustomerNumber(@RequestParam("customerNumber") String customerNumber,
                                                                           @RequestParam("businessGroup") String businessGroup) {
        BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofiDTO = bizBaseMbgCustomerDrmTomsTofiService.getByCustomerNumber(customerNumber, businessGroup);
        return ResultDTO.success(bizBaseMbgCustomerDrmTomsTofiDTO);

    }

    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseMbgCustomerDrmTomsTofiDTO bizBaseMbgCustomerDrmTomsTofi) {
        startPage();
        List<BizBaseMbgCustomerDrmTomsTofiDO> list = bizBaseMbgCustomerDrmTomsTofiService.getDropDownList(bizBaseMbgCustomerDrmTomsTofi);
        return getDataTable(list);
    }
}
