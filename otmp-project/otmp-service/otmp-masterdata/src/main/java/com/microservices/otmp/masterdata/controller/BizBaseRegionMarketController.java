package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.service.IBizBaseRegionMarketService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizBaseRegionMarketController
 * 
 * @author lovefamily
 * @date 2022-04-22
 */
@RestController
@RequestMapping("/bizBaseRegionMarket")
public class BizBaseRegionMarketController extends BaseController
{
    @Autowired
    private IBizBaseRegionMarketService bizBaseRegionMarketService;

    /**
     * 查询BizBaseRegionMarket列表
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseRegionMarket bizBaseRegionMarket)
    {
        startPage();
        List<BizBaseRegionMarket> list = bizBaseRegionMarketService.selectBizBaseRegionMarketList(bizBaseRegionMarket);
        return getDataTable(list);
    }


    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        List<BizBaseRegionMarket> list = bizBaseRegionMarketService.getDropDownList(bizBaseDropDownCondition);
        return getDataTable(list);
    }

    /**
     * 导出BizBaseRegionMarket列表
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:export")
    @OperLog(title = "BizBaseRegionMarket", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseRegionMarket bizBaseRegionMarket)
    {
        List<BizBaseRegionMarket> list = bizBaseRegionMarketService.selectBizBaseRegionMarketList(bizBaseRegionMarket);
        NewExcelUtil<BizBaseRegionMarket> util = new NewExcelUtil<>(BizBaseRegionMarket.class);
        util.exportExcel(response, list, "RegionMarket");
    }
    /**
     * 导入BizBaseRegionMarket列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterdata:bizBaseRegionMarket:import")
    @OperLog(title = "BizBaseRegionMarket", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseRegionMarket> util = new ExcelUtil<>(BizBaseRegionMarket.class);
        List <BizBaseRegionMarket> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseRegionMarketService.importExcel(bizs,getLoginName());

        return ResultDTO.success(message);
    }


    /**
     * 获取BizBaseRegionMarket详细信息
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseRegionMarket> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseRegionMarketService.selectBizBaseRegionMarketById(id));
    }

    /**
     * 新增BizBaseRegionMarket
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:add")
    @OperLog(title = "BizBaseRegionMarket", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseRegionMarket bizBaseRegionMarket)
    {
        bizBaseRegionMarket.setStatus("Y");
        bizBaseRegionMarket.setCreateBy(getLoginName());
        bizBaseRegionMarket.setCreateTime(DateUtils.getNowDate());
        bizBaseRegionMarket.setRegionMarketName(bizBaseRegionMarket.getRegionMarketCode());
        return toResultDTO(bizBaseRegionMarketService.insertBizBaseRegionMarket(bizBaseRegionMarket),true);
    }

    /**
     * 修改BizBaseRegionMarket
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:edit")
    @OperLog(title = "BizBaseRegionMarket", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseRegionMarket bizBaseRegionMarket)
    {
        bizBaseRegionMarket.setUpdateBy(getLoginName());
        bizBaseRegionMarket.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseRegionMarketService.updateBizBaseRegionMarket(bizBaseRegionMarket),true);
    }

    /**
     * 删除BizBaseRegionMarket
     */
    @RequiresPermissions("masterData:bizBaseRegionMarket:remove")
    @OperLog(title = "BizBaseRegionMarket", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseRegionMarketService.deleteBizBaseRegionMarketByIds(ids),true);
    }

    @GetMapping("/getRegionMarket")
    public ResultDTO<BizBaseRegionMarket> getRegionMarket(BizBaseRegionMarket bizBaseRegionMarket) {
        return handleResultData(bizBaseRegionMarketService.selectBizBaseRegionMarket(bizBaseRegionMarket));
    }

}
