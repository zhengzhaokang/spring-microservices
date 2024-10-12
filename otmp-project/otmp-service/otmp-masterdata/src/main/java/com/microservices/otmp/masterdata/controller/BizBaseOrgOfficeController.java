package com.microservices.otmp.masterdata.controller;

import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.dto.BizBaseOrgOfficeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeNameAndCodeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.BizBaseOrgOfficeTreeDTO;
import com.microservices.otmp.masterdata.service.IBizBaseOrgOfficeService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * BizBaseOrgOfficeController Controller
 *
 * @author lovefamily
 * @date 2022-12-23
 */
@RestController
@RequestMapping("/bizBaseOrgOffice")
public class BizBaseOrgOfficeController extends BaseController {
    @Autowired
    private IBizBaseOrgOfficeService bizBaseOrgOfficeService;

    /**
     * 查询 BizBaseOrgOffice 列表
     */
    @RequiresPermissions("masterData:bizBaseOrgOffice:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        startPage();
        List<BizBaseOrgOfficeDTO> list = bizBaseOrgOfficeService.selectBizBaseOrgOfficeList(bizBaseOrgOffice);
        return getDataTable(list);
    }

    /**
     * 查询 BizBaseOrgOffice 列表
     */
    //@RequiresPermissions("masterData:bizBaseOrgOffice:list")
    @GetMapping("/treeList")
    public TableDataInfo treeList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        startPage();
        PageInfo<BizBaseOrgOfficeTreeDTO> list = bizBaseOrgOfficeService.selectBizBaseOrgOfficeTreeList(bizBaseOrgOffice);
        return getDataTable(list);
    }

    /**
     * 导出 BizBaseOrgOffice 列表
     */
    @RequiresPermissions("masterData:bizBaseOrgOffice:export")
    @Log(title = "导出BizBaseOrgOffice列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        List<List<?>> lists = new ArrayList<>();
        List<BizBaseOrgOfficeDTO> officeList = new ArrayList<>();
        List<BizBaseOrgOfficeTreeDTO> list = bizBaseOrgOfficeService.selectBizBaseOrgOfficeTreeList(bizBaseOrgOffice).getList();
        if (CollectionUtils.isNotEmpty(list)) {
            for (BizBaseOrgOfficeTreeDTO bizBaseOrgOfficeTreeDTO : list) {
                officeList.addAll(bizBaseOrgOfficeTreeDTO.getChildren());
            }
        }
        lists.add(list);
        lists.add(officeList);
        List<Class> clazzs = new ArrayList<>();
        clazzs.add(BizBaseOrgOfficeTreeDTO.class);
        clazzs.add(BizBaseOrgOfficeDTO.class);
        NewExcelUtil util = new NewExcelUtil<>();
        util.clazzs = clazzs;
        util.exportMoreSheetExcel(response, lists, new String[]{"Sales Org", "Sales Office"});
    }

    /**
     * 获取 BizBaseOrgOffice 详细信息
     */
    @RequiresPermissions("masterData:bizBaseOrgOffice:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseOrgOfficeTreeDTO> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseOrgOfficeService.selectBizBaseOrgOfficeById(id));
    }

    /**
     * 新增 BizBaseOrgOffice
     */
    @RequiresPermissions("masterData:bizBaseOrgOffice:add")
    @Log(title = "新增BizBaseOrgOffice", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseOrgOfficeTreeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeService.insertBizBaseOrgOffice(bizBaseOrgOffice, getLoginName());
    }

    /**
     * 导入BizBaseOrgOffice列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterData:bizBaseOrgOffice:import")
    @OperLog(title = "BizBaseSelfInvoice", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseOrgOfficeDTO> util = new ExcelUtil<>(BizBaseOrgOfficeDTO.class);
        List<BizBaseOrgOfficeDTO> bizs = util.importExcel(file.getInputStream());
        return bizBaseOrgOfficeService.importExcel(bizs, getLoginName());
    }

    /**
     * 修改 BizBaseOrgOffice
     */
    @RequiresPermissions("masterData:OrgOffice:edit")
    @Log(title = "修改BizBaseOrgOffice", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseOrgOfficeTreeDTO bizBaseOrgOffice) {
        return bizBaseOrgOfficeService.updateBizBaseOrgOffice(bizBaseOrgOffice, getLoginName());
    }

    /**
     * 删除 BizBaseOrgOffice
     */
    @RequiresPermissions("masterData:OrgOffice:remove")
    @Log(title = "删除BizBaseOrgOffice", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseOrgOfficeService.deleteBizBaseOrgOfficeByIds(ids), true);
    }

    /**
     * 获取 code / name
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "regionMarketCode，territoryCode，countryCode，bpcCountryCode，salesOrgCode，salesOfficeCode，distributionChannelCode，" +
                    "regionMarketName，territoryName，countryName，bpcCountryName，salesOrgName，salesOfficeName，distributionChannelName", required = true)
    })
    @GetMapping(value = "/getCodeAndName")
    public ResultDTO<List<BizBaseOrgOfficeNameAndCodeDTO>> getCodeAndName(String type, String code) {
        return ResultDTO.success(bizBaseOrgOfficeService.getCodeAndName(type, code));
    }

    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        startPage();
        PageInfo<BizBaseOrgOfficeDTO> list = bizBaseOrgOfficeService.getDropDownList(bizBaseOrgOffice);
        return getDataTable(list);
    }

    /**
     * 通过salesOrgCode和salesOfficeCode获取regionMarketCode
     *
     * @param salesOrgCode
     * @param salesOfficeCode
     * @return
     */
    @GetMapping("/getByOrgOffice")
    public ResultDTO<BizBaseOrgOfficeDTO> getByOrgOffice(String salesOrgCode, String salesOfficeCode) {
        return ResultDTO.success(bizBaseOrgOfficeService.getByOrgOffice(salesOrgCode, salesOfficeCode));
    }

    /**
     * 通过salesOrgCode获取accrualCompanyCode
     *
     * @param salesOrgCode
     * @param businessGroup
     * @return
     */
    @GetMapping("/getOrgOfficeByOrgAndBg")
    public ResultDTO<BizBaseOrgOfficeDTO> getOrgOfficeByOrgAndBg(String salesOrgCode, String businessGroup) {
        return ResultDTO.success(bizBaseOrgOfficeService.getOrgOfficeByOrgAndBg(salesOrgCode, businessGroup));
    }

    /**
     * 查询 BizBaseOrgOffice 列表
     */
    @RequiresPermissions("masterData:bizBaseOrgOffice:list")
    @GetMapping("/getOrgAndOffice")
    public ResultDTO<BizBaseOrgOfficeDTO> getOrgAndOffice(BizBaseOrgOfficeDTO bizBaseOrgOffice) {
        BizBaseOrgOfficeDTO bizBaseOrgOfficeDTO = bizBaseOrgOfficeService.getOrgAndOffice(bizBaseOrgOffice);
        return ResultDTO.success(bizBaseOrgOfficeDTO);
    }
}
