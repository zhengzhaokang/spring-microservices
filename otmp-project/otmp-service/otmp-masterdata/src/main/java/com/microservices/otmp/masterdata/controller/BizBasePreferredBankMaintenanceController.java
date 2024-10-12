package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.service.IBizBasePreferredBankMaintenanceService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizBasePreferredBankMaintenanceController
 * @author xiaozy8
 * @date 2022-11-30
 */
@RestController
@RequestMapping("/bizBasePreferredBankMaintenance")
public class BizBasePreferredBankMaintenanceController extends BaseController
{
    @Autowired
    private IBizBasePreferredBankMaintenanceService bizBasePreferredBankMaintenanceService;
    private String failMassage = "The bank information does not exist in the LGVM";

    /**
     * 查询BizBasePreferredBankMaintenance列表
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance)
    {
        startPage();
        List<BizBasePreferredBankMaintenance> list = bizBasePreferredBankMaintenanceService.selectBizBasePreferredBankMaintenanceList(bizBasePreferredBankMaintenance);
        return getDataTable(list);
    }

    /**
     * 导出BizBasePreferredBankMaintenance列表
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:export")
    @OperLog(title = "BizBasePreferredBankMaintenance", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance)
    {
        List<BizBasePreferredBankMaintenance> list = bizBasePreferredBankMaintenanceService.selectBizBasePreferredBankMaintenanceList(bizBasePreferredBankMaintenance);
        NewExcelUtil<BizBasePreferredBankMaintenance> util = new NewExcelUtil<>(BizBasePreferredBankMaintenance.class);
        util.exportExcel(response, list, "PreferredBank");
    }

    /**
     * 导入BizBasePreferredBankMaintenance列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterdata:bizBasePreferredBankMaintenance:import")
    @OperLog(title = "BizBasePreferredBankMaintenance", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBasePreferredBankMaintenance> util = new ExcelUtil<>(BizBasePreferredBankMaintenance.class);
        List <BizBasePreferredBankMaintenance> bizs = util.importExcel(file.getInputStream());
        return bizBasePreferredBankMaintenanceService.importExcel(bizs,getLoginName());
    }

    /**
     * 获取BizBasePreferredBankMaintenance详细信息
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBasePreferredBankMaintenance> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBasePreferredBankMaintenanceService.selectBizBasePreferredBankMaintenanceById(id));
    }

    /**
     * 新增BizBasePreferredBankMaintenance
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:add")
    @OperLog(title = "BizBasePreferredBankMaintenance", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance)
    {
        //唯一性校验,用户只允许维护一条prefer bank 数据
        BizBasePreferredBankMaintenance check = new BizBasePreferredBankMaintenance();
        check.setVendorCode(bizBasePreferredBankMaintenance.getVendorCode());
        check.setDeleteFlag(false);
        List<BizBasePreferredBankMaintenance> list = bizBasePreferredBankMaintenanceService.selectBizBasePreferredBankMaintenanceList(check);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Vendor code already maintained prefer bank info");
        }
        int count = bizBasePreferredBankMaintenanceService.checkLgvmBank(bizBasePreferredBankMaintenance);
        if (count == 0) {
            return ResultDTO.fail(failMassage);
        }
        bizBasePreferredBankMaintenance.setCreateBy(getLoginName());
        bizBasePreferredBankMaintenance.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBasePreferredBankMaintenanceService.insertBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance),true);
    }

    /**
     * 修改BizBasePreferredBankMaintenance
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:edit")
    @OperLog(title = "BizBasePreferredBankMaintenance", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance)
    {
        int count = bizBasePreferredBankMaintenanceService.checkLgvmBank(bizBasePreferredBankMaintenance);
        if (count == 0) {
            return ResultDTO.fail(failMassage);
        }
        bizBasePreferredBankMaintenance.setUpdateBy(getLoginName());
        bizBasePreferredBankMaintenance.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBasePreferredBankMaintenanceService.updateBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance),true);
    }

    /**
     * 删除BizBasePreferredBankMaintenance
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:remove")
    @OperLog(title = "BizBasePreferredBankMaintenance", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBasePreferredBankMaintenanceService.deleteBizBasePreferredBankMaintenanceByIds(ids),true);
    }

    /**
     * 获取lgvm bank信息
     */
    @RequiresPermissions("masterData:bizBasePreferredBankMaintenance:query")
    @PostMapping(value = "/checkLgvmBank")
    public ResultDTO<Integer> checkLgvmBank(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance)
    {
        int count = bizBasePreferredBankMaintenanceService.checkLgvmBank(bizBasePreferredBankMaintenance);
        if (count == 0) {
            return ResultDTO.fail(failMassage);
        }
        return ResultDTO.success();
    }

}
