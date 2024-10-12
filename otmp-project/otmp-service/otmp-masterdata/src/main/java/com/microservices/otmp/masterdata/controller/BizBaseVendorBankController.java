package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.service.IBizBaseVendorBankService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * biz_base_vendor_bank from M&SController
 *
 * @author lovefamily
 * @date 2022-04-25
 */
@RestController
@RequestMapping("/bizBaseVendorBank")
public class BizBaseVendorBankController extends BaseController {
    @Autowired
    private IBizBaseVendorBankService bizBaseVendorBankService;

    /**
     * 查询biz_base_vendor_bank from M&S列表
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseVendorBank bizBaseVendorBank) {
        startPage();
        List<BizBaseVendorBank> list = bizBaseVendorBankService.selectBizBaseVendorBankList(bizBaseVendorBank);
        return getDataTable(list);
    }

    /**
     * 查询biz_base_vendor_bank from M&S列表
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:list")
    @GetMapping("/listByPartnerIncentiveId")
    public TableDataInfo listByPartnerIncentiveId(BizBaseVendorBank bizBaseVendorBank) {
        startPage();
        List<BizBaseVendorBank> list = bizBaseVendorBankService.selectBizBaseVendorBankList(bizBaseVendorBank);
        if (list.isEmpty()) {
            throw new OtmpException("Partner Incentive ID doesn't match master data.");
        }
        return getDataTable(list);
    }

    /**
     * 导出biz_base_vendor_bank from M&S列表
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:export")
    @Log(title = "biz_base_vendor_bank from M&S", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseVendorBank bizBaseVendorBank) {
        List<BizBaseVendorBank> list = bizBaseVendorBankService.selectBizBaseVendorBankList(bizBaseVendorBank);
        NewExcelUtil<BizBaseVendorBank> util = new NewExcelUtil<>(BizBaseVendorBank.class);
        util.exportExcel(response, list, "VendorBankFromM&S");
    }

    /**
     * 获取biz_base_vendor_bank from M&S详细信息
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseVendorBank> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseVendorBankService.selectBizBaseVendorBankById(id));
    }

    /**
     * 新增biz_base_vendor_bank from M&S
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:add")
    @Log(title = "biz_base_vendor_bank from M&S", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseVendorBank bizBaseVendorBank) {
        return toResultDTO(bizBaseVendorBankService.insertBizBaseVendorBank(bizBaseVendorBank), true);
    }

    /**
     * 修改biz_base_vendor_bank from M&S
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:edit")
    @Log(title = "biz_base_vendor_bank from M&S", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseVendorBank bizBaseVendorBank) {
        return toResultDTO(bizBaseVendorBankService.updateBizBaseVendorBank(bizBaseVendorBank), true);
    }

    /**
     * 删除biz_base_vendor_bank from M&S
     */
    @RequiresPermissions("masterdata:bizBaseVendorBank:remove")
    @Log(title = "biz_base_vendor_bank from M&S", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseVendorBankService.deleteBizBaseVendorBankByIds(ids), true);
    }

    /**
     * 查询biz_base_vendor_bank from M&S列表
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/searchInfo")
    public TableDataInfo getVendorBankByCode(BizBaseVendorBank bizBaseVendorBank) {
        startPage();
        bizBaseVendorBank.setStatus("Y");
        List<BizBaseVendorBank> list = bizBaseVendorBankService.selectBizBaseVendorBankList(bizBaseVendorBank);
        return getDataTable(list);
    }
}
