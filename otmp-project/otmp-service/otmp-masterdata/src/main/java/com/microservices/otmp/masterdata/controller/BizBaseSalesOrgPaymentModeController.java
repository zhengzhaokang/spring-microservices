package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrgPaymentMode;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgPaymentModeService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseSalesOrgPaymentModeController
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseSalesOrgPaymentMode")
public class BizBaseSalesOrgPaymentModeController extends BaseController
{
    @Autowired
    private IBizBaseSalesOrgPaymentModeService bizBaseSalesOrgPaymentModeService;

    /**
     * 查询BaseSalesOrgPaymentMode列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        startPage();
        List<BizBaseSalesOrgPaymentMode> list = bizBaseSalesOrgPaymentModeService.selectBizBaseSalesOrgPaymentModeList(bizBaseSalesOrgPaymentMode);
        return getDataTable(list);
    }

    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition geo)
    {
        List<BizBaseSalesOrgPaymentMode> list = bizBaseSalesOrgPaymentModeService.getDropDownList(geo);
        return getDataTable(list);
    }

    /**
     * 导出BaseSalesOrgPaymentMode列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:export")
    @OperLog(title = "BaseSalesOrgPaymentMode", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        List<BizBaseSalesOrgPaymentMode> list = bizBaseSalesOrgPaymentModeService.selectBizBaseSalesOrgPaymentModeList(bizBaseSalesOrgPaymentMode);
        NewExcelUtil<BizBaseSalesOrgPaymentMode> util = new NewExcelUtil<>(BizBaseSalesOrgPaymentMode.class);
        util.exportExcel(response, list, "SalesOrgPaymentMode");
    }
    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseSalesOrgPaymentMode:import")
    @OperLog(title = "BaseSalesOrgPaymentMode", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseSalesOrgPaymentMode> util = new ExcelUtil<>(BizBaseSalesOrgPaymentMode.class);
        List <BizBaseSalesOrgPaymentMode> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseSalesOrgPaymentModeService.importExcel(bizs,getLoginName());
        return ResultDTO.success(message);
    }
    /**
     * 获取BaseSalesOrgPaymentMode详细信息
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseSalesOrgPaymentMode> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseSalesOrgPaymentModeService.selectBizBaseSalesOrgPaymentModeById(id));
    }

    /**
     * 新增BaseSalesOrgPaymentMode
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:add")
    @OperLog(title = "BaseSalesOrgPaymentMode", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        //重复性校验
        BizBaseSalesOrgPaymentMode mode = new BizBaseSalesOrgPaymentMode();
        mode.setGeoCode(bizBaseSalesOrgPaymentMode.getGeoCode());
        mode.setRegionMarketCode(bizBaseSalesOrgPaymentMode.getRegionMarketCode());
        mode.setSalesOrgCode(bizBaseSalesOrgPaymentMode.getSalesOrgCode());
        mode.setPaymentModeCode(bizBaseSalesOrgPaymentMode.getPaymentModeCode());
        mode.setIntegrateMode(bizBaseSalesOrgPaymentMode.getIntegrateMode());
        mode.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseSalesOrgPaymentMode> list = bizBaseSalesOrgPaymentModeService.selectBizBaseSalesOrgPaymentModeList(mode);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseSalesOrgPaymentMode.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        bizBaseSalesOrgPaymentMode.setCreateBy(getLoginName());
        bizBaseSalesOrgPaymentMode.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSalesOrgPaymentModeService.insertBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode),true);
    }

    /**
     * 修改BaseSalesOrgPaymentMode
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:edit")
    @OperLog(title = "BaseSalesOrgPaymentMode", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseSalesOrgPaymentMode bizBaseSalesOrgPaymentMode)
    {
        bizBaseSalesOrgPaymentMode.setUpdateBy(getLoginName());
        bizBaseSalesOrgPaymentMode.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSalesOrgPaymentModeService.updateBizBaseSalesOrgPaymentMode(bizBaseSalesOrgPaymentMode),true);
    }

    /**
     * 删除BaseSalesOrgPaymentMode
     */
    @RequiresPermissions("masterData:bizBaseSalesOrgPaymentMode:remove")
    @OperLog(title = "BaseSalesOrgPaymentMode", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseSalesOrgPaymentModeService.deleteBizBaseSalesOrgPaymentModeByIds(ids),true);
    }
}
