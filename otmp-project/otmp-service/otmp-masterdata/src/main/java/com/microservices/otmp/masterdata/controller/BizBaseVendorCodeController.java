package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseVendorCode;
import com.microservices.otmp.masterdata.service.IBizBaseVendorCodeService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * biz_base_vendor_code from ECCController
 *
 * @author lovefamily
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/bizBaseVendorCode")
public class BizBaseVendorCodeController extends BaseController {
    @Autowired
    private IBizBaseVendorCodeService bizBaseVendorCodeService;

    /**
     * 查询biz_base_vendor_code from ECC列表
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseVendorCode bizBaseVendorCode) {
        startPage();
        List<BizBaseVendorCode> list = bizBaseVendorCodeService.selectBizBaseVendorCodeList(bizBaseVendorCode);
        return getDataTable(list);
    }

    /**
     * 导出biz_base_vendor_code from ECC列表
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:export")
    @Log(title = "biz_base_vendor_code from ECC", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseVendorCode bizBaseVendorCode) {
        List<BizBaseVendorCode> list = bizBaseVendorCodeService.selectBizBaseVendorCodeList(bizBaseVendorCode);
        NewExcelUtil<BizBaseVendorCode> util = new NewExcelUtil<>(BizBaseVendorCode.class);
        util.exportExcel(response, list, "VendorCodeFromECC");
    }


    /**
     * 获取biz_base_vendor_code from ECC详细信息
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseVendorCode> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseVendorCodeService.selectBizBaseVendorCodeById(id));
    }

    /**
     * 新增biz_base_vendor_code from ECC
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:add")
    @Log(title = "biz_base_vendor_code from ECC", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseVendorCode bizBaseVendorCode) {
        return toResultDTO(bizBaseVendorCodeService.insertBizBaseVendorCode(bizBaseVendorCode), true);
    }

    /**
     * 修改biz_base_vendor_code from ECC
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:edit")
    @Log(title = "biz_base_vendor_code from ECC", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseVendorCode bizBaseVendorCode) {
        return toResultDTO(bizBaseVendorCodeService.updateBizBaseVendorCode(bizBaseVendorCode), true);
    }

    /**
     * 删除biz_base_vendor_code from ECC
     */
    @RequiresPermissions("masterdata:bizBaseVendorCode:remove")
    @Log(title = "biz_base_vendor_code from ECC", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseVendorCodeService.deleteBizBaseVendorCodeByIds(ids), true);
    }
}
