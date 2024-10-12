package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndBanksDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.service.IBizBaseVendorService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * bizBaseVendorController
 *
 * @author lovefamily
 * @date 2023-01-16
 */
@RestController
@RequestMapping("/bizBaseVendor")
public class BizBaseVendorController extends BaseController {
    @Autowired
    private IBizBaseVendorService bizBaseVendorService;

    /**
     * 查询bizBaseVendor列表
     */
    @RequiresPermissions("masterdata:bizBaseVendor:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseVendorDTO bizBaseVendor) {
        startPage();
        List<BizBaseVendorDTO> list = bizBaseVendorService.selectBizBaseVendorList(bizBaseVendor);
        return getDataTable(list);
    }

    /**
     * 查询bizBaseVendor列表
     */
    @RequiresPermissions("masterdata:bizBaseVendor:list")
    @GetMapping("/getVendorAndBankInfoList")
    public ResultDTO<List<BizBaseVendorDTO>> getVendorAndBankInfoList(BizBaseVendorDTO bizBaseVendor) {
        List<BizBaseVendorDTO> list = bizBaseVendorService.getVendorAndBankInfoList(bizBaseVendor);
        return ResultDTO.success(list);
    }

    /**
     * 导出bizBaseVendor列表
     */
    @RequiresPermissions("masterdata:bizBaseVendor:export")
    @Log(title = "bizBaseVendor", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseVendorDTO bizBaseVendor) {
        List<BizBaseVendorDTO> list = bizBaseVendorService.selectBizBaseVendorList(bizBaseVendor);
        NewExcelUtil<BizBaseVendorDTO> util = new NewExcelUtil<>(BizBaseVendorDTO.class);
        util.exportExcel(response, list, "bizBaseVendor数据");
    }

    /**
     * 获取bizBaseVendor详细信息
     */
    @RequiresPermissions("masterdata:bizBaseVendor:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseVendorDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseVendorService.selectBizBaseVendorById(id));
    }

    /**
     * 新增bizBaseVendor
     */
    @RequiresPermissions("masterdata:bizBaseVendor:add")
    @Log(title = "bizBaseVendor", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseVendorDTO bizBaseVendor) {
        return toResultDTO(bizBaseVendorService.insertBizBaseVendor(bizBaseVendor), true);
    }

    /**
     * 修改bizBaseVendor
     */
    @RequiresPermissions("masterdata:bizBaseVendor:edit")
    @Log(title = "bizBaseVendor", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseVendorDTO bizBaseVendor) {
        return toResultDTO(bizBaseVendorService.updateBizBaseVendor(bizBaseVendor), true);
    }

    /**
     * 删除bizBaseVendor
     */
    @RequiresPermissions("masterdata:bizBaseVendor:remove")
    @Log(title = "bizBaseVendor", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseVendorService.deleteBizBaseVendorByIds(ids), true);
    }

    /**
     * 获取bizBaseVendor详细信息
     */
    @GetMapping(value = "/getVendorAndBanksByCode")
    public ResultDTO<BizBaseVendorAndBanksDTO> getVendorAndBanksByCode(String vendorCode) {
        return ResultDTO.success(bizBaseVendorService.getVendorAndBanksByCode(vendorCode));
    }

    /**
     * 获取bizBaseVendor详细信息
     */
    @PostMapping(value = "/getVendorAndPerferBank")
    public ResultDTO<List<BizBaseVendorAndPerferBankDTO>> getVendorAndPerferBank(@RequestBody List<String> customerIds) {
        return ResultDTO.success(bizBaseVendorService.getVendorAndPerferBank(customerIds));
    }
}
