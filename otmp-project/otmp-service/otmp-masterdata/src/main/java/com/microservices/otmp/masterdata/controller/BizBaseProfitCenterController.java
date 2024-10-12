package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseProfitCenter;
import com.microservices.otmp.masterdata.service.IBizBaseProfitCenterService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseProfitCenterController
 *
 * @author lovefamily
 * @date 2022-04-22
 */
@RestController
@RequestMapping("/bizBaseProfitCenter")
public class BizBaseProfitCenterController extends BaseController {
    @Autowired
    private IBizBaseProfitCenterService bizBaseProfitCenterService;

    /**
     * 查询BaseProfitCenter列表
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseProfitCenter bizBaseProfitCenter) {
        startPage();
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.selectBizBaseProfitCenterList(bizBaseProfitCenter);
        return getDataTable(list);
    }

    /**
     * 精确查询，外部是系统使用
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:selectProfitCenterListPrecise")
    @GetMapping("/selectProfitCenterListPrecise")
    public ResultDTO<List<BizBaseProfitCenter>> selectProfitCenterListPrecise(BizBaseProfitCenter bizBaseProfitCenter) {
        startPage();
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.selectProfitCenterListPrecise(bizBaseProfitCenter);
        return ResultDTO.success(list);
    }

    @RequiresPermissions("masterData:bizBaseProfitCenter:list")
    @GetMapping("/getProfitCenterlist")
    public TableDataInfo getProfitCenterlist(BizBaseProfitCenter bizBaseProfitCenter) {
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.selectProfitCenterlist(bizBaseProfitCenter);
        return getDataTable(list);
    }

    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/searchInfo")
    public TableDataInfo searchInfo(BizBaseProfitCenter bizBaseProfitCenter) {
        startPage();
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.getDropDownList(bizBaseProfitCenter);
        return getDataTable(list);
    }

    /**
     * 导出BaseProfitCenter列表
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:export")
    @OperLog(title = "BaseProfitCenter", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseProfitCenter bizBaseProfitCenter) {
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.selectBizBaseProfitCenterList(bizBaseProfitCenter);
        NewExcelUtil<BizBaseProfitCenter> util = new NewExcelUtil<>(BizBaseProfitCenter.class);
        util.exportExcel(response, list, "ProfitCenter");
    }

    /**
     * 导入BaseProfitCenter列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterdata:bizBaseProfitCenter:import")
    @OperLog(title = "BaseProfitCenter", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseProfitCenter> util = new ExcelUtil<>(BizBaseProfitCenter.class);
        List<BizBaseProfitCenter> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseProfitCenterService.importExcel(bizs, getLoginName());

        return ResultDTO.success(message);
    }

    /**
     * 获取BaseProfitCenter详细信息
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseProfitCenter> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseProfitCenterService.selectBizBaseProfitCenterById(id));
    }

    /**
     * 新增BaseProfitCenter
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:add")
    @OperLog(title = "BaseProfitCenter", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseProfitCenter bizBaseProfitCenter) {
        //重复性校验
        BizBaseProfitCenter center = new BizBaseProfitCenter();
        center.setBusinessGroup(bizBaseProfitCenter.getBusinessGroup());
        center.setGeoCode(bizBaseProfitCenter.getGeoCode());
        center.setRegionMarketCode(bizBaseProfitCenter.getRegionMarketCode());
        center.setSalesOrgCode(bizBaseProfitCenter.getSalesOrgCode());
        center.setSalesOfficeCode(bizBaseProfitCenter.getSalesOfficeCode());
        center.setProfitCenterCode(bizBaseProfitCenter.getProfitCenterCode());
        center.setDummyGtnMtm(bizBaseProfitCenter.getDummyGtnMtm());
        center.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseProfitCenter> list = bizBaseProfitCenterService.selectBizBaseProfitCenterList(center);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseProfitCenter.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        bizBaseProfitCenter.setCreateBy(getLoginName());
        bizBaseProfitCenter.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseProfitCenterService.insertBizBaseProfitCenter(bizBaseProfitCenter), true);
    }

    /**
     * 修改BaseProfitCenter
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:edit")
    @OperLog(title = "BaseProfitCenter", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseProfitCenter bizBaseProfitCenter) {
        bizBaseProfitCenter.setUpdateBy(getLoginName());
        bizBaseProfitCenter.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseProfitCenterService.updateBizBaseProfitCenter(bizBaseProfitCenter), true);
    }

    /**
     * 删除BaseProfitCenter
     */
    @RequiresPermissions("masterData:bizBaseProfitCenter:remove")
    @OperLog(title = "BaseProfitCenter", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseProfitCenterService.deleteBizBaseProfitCenterByIds(ids), true);
    }
}
