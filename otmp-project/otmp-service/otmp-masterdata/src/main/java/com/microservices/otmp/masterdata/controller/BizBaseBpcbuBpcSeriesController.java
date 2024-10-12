package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.entity.BizBaseBpcbuBpcSeriesDO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.masterdata.domain.dto.BizBaseBpcbuBpcSeriesDTO;
import com.microservices.otmp.masterdata.service.IBizBaseBpcbuBpcSeriesService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * BizBaseBpcbuBpcSeries In SDMSController
 * 
 * @author lovefamily
 * @date 2022-10-31
 */
@RestController
@RequestMapping("/bizBaseBpcbuBpcSeries")
public class BizBaseBpcbuBpcSeriesController extends BaseController
{
    @Autowired
    private IBizBaseBpcbuBpcSeriesService bizBaseBpcbuBpcSeriesService;

    /**
     * 查询BizBaseBpcbuBpcSeries In SDMS列表
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        startPage();
        return getDataTable(bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesList(bizBaseBpcbuBpcSeries));
    }

    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:list")
    @GetMapping("/getBpcList")
    public TableDataInfo getBpcList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
      List<BizBaseBpcbuBpcSeriesDTO>bizBaseBpcbuBpcSeriesDTOS=  bizBaseBpcbuBpcSeriesService.getBpcList(bizBaseBpcbuBpcSeries);
        return getDataTable(bizBaseBpcbuBpcSeriesDTOS);
    }

    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:list")
    @GetMapping("/getBpcSeriesList")
    public TableDataInfo getBpcSeriesList(BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
      List<BizBaseBpcbuBpcSeriesDTO>bizBaseBpcbuBpcSeriesDTOS=    bizBaseBpcbuBpcSeriesService.getBpcSeriesList(bizBaseBpcbuBpcSeries);
        return getDataTable(bizBaseBpcbuBpcSeriesDTOS);

    }

    /**
     * 导出BizBaseBpcbuBpcSeries In SDMS列表
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:export")
    @Log(title = "BizBaseBpcbuBpcSeries", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        List<BizBaseBpcbuBpcSeriesDTO> list = bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesList(bizBaseBpcbuBpcSeries).getList();
        NewExcelUtil<BizBaseBpcbuBpcSeriesDTO> util = new NewExcelUtil<BizBaseBpcbuBpcSeriesDTO>(BizBaseBpcbuBpcSeriesDTO.class);
        util.exportExcel(response, list, "BPCBU_BPCSeries");
    }

    /**
     * 获取BizBaseBpcbuBpcSeries In SDMS详细信息
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseBpcbuBpcSeriesDTO> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesById(id));
    }

    /**
     * 新增BizBaseBpcbuBpcSeries In SDMS
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:add")
    @Log(title = "BizBaseBpcbuBpcSeries", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        bizBaseBpcbuBpcSeries.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseBpcbuBpcSeriesDO> list = bizBaseBpcbuBpcSeriesService.selectBizBaseBpcbuBpcSeriesListCheck(bizBaseBpcbuBpcSeries);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseBpcbuBpcSeries.setCreateBy(getLoginName());
        return toResultDTO(bizBaseBpcbuBpcSeriesService.insertBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeries),true);
    }

    @PostMapping("/importExcel")
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:import")
    @Log(title = "BizBaseBpcbuBpcSeries", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseBpcbuBpcSeriesDTO> util = new ExcelUtil<>(BizBaseBpcbuBpcSeriesDTO.class);
        List <BizBaseBpcbuBpcSeriesDTO> bizs = util.importExcel(file.getInputStream());
        return bizBaseBpcbuBpcSeriesService.importExcel(bizs,getLoginName());
    }

    /**
     * 修改BizBaseBpcbuBpcSeries In SDMS
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:edit")
    @Log(title = "BizBaseBpcbuBpcSeries", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseBpcbuBpcSeriesDTO bizBaseBpcbuBpcSeries)
    {
        return toResultDTO(bizBaseBpcbuBpcSeriesService.updateBizBaseBpcbuBpcSeries(bizBaseBpcbuBpcSeries),true);
    }

    /**
     * 删除BizBaseBpcbuBpcSeries In SDMS
     */
    @RequiresPermissions("system:bizBaseBpcbuBpcSeries:remove")
    @Log(title = "BizBaseBpcbuBpcSeries", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseBpcbuBpcSeriesService.deleteBizBaseBpcbuBpcSeriesByIds(ids),true);
    }
}
