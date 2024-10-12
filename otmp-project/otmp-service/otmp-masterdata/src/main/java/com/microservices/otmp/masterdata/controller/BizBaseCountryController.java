package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseCountry;
import com.microservices.otmp.masterdata.service.IBizBaseCountryService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseCountryController
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseCountry")
public class BizBaseCountryController extends BaseController {
    @Autowired
    private IBizBaseCountryService bizBaseCountryService;

    @Autowired
    private RedisUtils redis;

    private static final String EXPORT_EXCEL_SHEET_NAME = "Country";

    /**
     * 查询BaseCountry列表
     */
    @RequiresPermissions("masterData:bizBaseCountry:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseCountry bizBaseCountry) {
        startPage();
        List<BizBaseCountry> list = bizBaseCountryService.selectBizBaseCountryList(bizBaseCountry);
        return getDataTable(list);
    }

    @RequiresPermissions("masterData:bizBaseCountry:list")
    @GetMapping("/getCountrylist")
    public TableDataInfo getCountrylist(BizBaseCountry bizBaseCountry) {
        List<BizBaseCountry> list = bizBaseCountryService.selectCountrylist(bizBaseCountry);
        return getDataTable(list);
    }

    @RequiresPermissions("masterData:bizBaseCountry:list")
    @GetMapping("/countrySelect")
    public TableDataInfo countrySelect(BizBaseCountry bizBaseCountry) {
        List<BizBaseCountry> bizBaseCountries = bizBaseCountryService.countrySelect(bizBaseCountry);
        return getDataTable(bizBaseCountries);
    }

    /**
     * 导出BaseCountry列表
     */
    @RequiresPermissions("masterData:bizBaseCountry:export")
    @OperLog(title = "BaseCountry", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseCountry bizBaseCountry) {
        List<BizBaseCountry> list = bizBaseCountryService.selectBizBaseCountryList(bizBaseCountry);
        NewExcelUtil<BizBaseCountry> util = new NewExcelUtil<>(BizBaseCountry.class);
        util.exportExcel(response, list, EXPORT_EXCEL_SHEET_NAME);
    }

    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseCountry:import")
    @OperLog(title = "BaseCountry", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseCountry> util = new ExcelUtil<>(BizBaseCountry.class);
        List<BizBaseCountry> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseCountryService.importExcel(bizs, getLoginName());

        return ResultDTO.success(message);
    }

    /**
     * 获取BaseCountry详细信息
     */
    @RequiresPermissions("masterData:bizBaseCountry:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseCountry> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseCountryService.selectBizBaseCountryById(id));
    }

    /**
     * 新增BaseCountry
     */
    @RequiresPermissions("masterData:bizBaseCountry:add")
    @OperLog(title = "BaseCountry", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseCountry bizBaseCountry) {
        bizBaseCountry.setStatus("Y");
        bizBaseCountry.setCreateBy(getLoginName());
        bizBaseCountry.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseCountryService.insertBizBaseCountry(bizBaseCountry), true);
    }

    /**
     * 修改BaseCountry
     */
    @RequiresPermissions("masterData:bizBaseCountry:edit")
    @OperLog(title = "BaseCountry", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseCountry bizBaseCountry) {
        bizBaseCountry.setUpdateBy(getLoginName());
        bizBaseCountry.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseCountryService.updateBizBaseCountry(bizBaseCountry), true);
    }

    /**
     * 删除BaseCountry
     */
    @RequiresPermissions("masterData:bizBaseCountry:remove")
    @OperLog(title = "BaseCountry", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseCountryService.deleteBizBaseCountryByIds(ids), true);
    }
}
