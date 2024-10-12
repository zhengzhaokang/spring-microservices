package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSegment;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsSegmentDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsSegmentVo;
import com.microservices.otmp.masterdata.service.IBizBaseSegmentService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseSegmentController
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseSegment")
public class BizBaseSegmentController extends BaseController
{
    @Autowired
    private IBizBaseSegmentService bizBaseSegmentService;

    /**
     * 查询BaseSegment列表
     */
    @RequiresPermissions("masterData:bizBaseSegment:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSegment bizBaseSegment)
    {
        startPage();
        List<BizBaseSegment> list = bizBaseSegmentService.selectBizBaseSegmentList(bizBaseSegment);
        return getDataTable(list);
    }

    /**
     * 查询下拉框
     */
    @RequiresPermissions("masterData:bizBaseSegment:list")
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        return getDataTable(bizBaseSegmentService.getDropDownList(bizBaseDropDownCondition));
    }

    @RequiresPermissions("masterData:bizBaseSegment:list")
    @OperLog(title = "获取gpn-dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/gpnCodeList")
    public TableDataInfo getGpnCodeList(BizBaseSegment bizBaseSegment)
    {
        return getDataTable(bizBaseSegmentService.getGpnCodeList(bizBaseSegment));
    }

    @RequiresPermissions("masterData:bizBaseSegment:list")
    @OperLog(title = "提供给ms的api接口(segmentList)", businessType = BusinessType.QUERY)
    @GetMapping("/toMs/segmentList")
    public TableDataInfo toMsSegmentList(ToMsSegmentDTO toMsSegmentDTO)
    {
        List<MsSegmentVo> list =    bizBaseSegmentService.toMsSegmentList(toMsSegmentDTO);
        return getDataTable(list);
    }
    /**
     * 导出BaseSegment列表
     */
    @RequiresPermissions("masterData:bizBaseSegment:export")
    @OperLog(title = "BaseSegment", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSegment bizBaseSegment)
    {
        List<BizBaseSegment> list = bizBaseSegmentService.selectBizBaseSegmentList(bizBaseSegment);
        NewExcelUtil<BizBaseSegment> util = new NewExcelUtil<>(BizBaseSegment.class);
        util.exportExcel(response, list, "Segment");
    }
    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseSegment:import")
    @OperLog(title = "BaseSegment", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseSegment> util = new ExcelUtil<>(BizBaseSegment.class);
        List <BizBaseSegment> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseSegmentService.importExcel(bizs,getLoginName());
        return ResultDTO.success(message);
    }
    /**
     * 获取BaseSegment详细信息
     */
    @RequiresPermissions("masterData:bizBaseSegment:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseSegment> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseSegmentService.selectBizBaseSegmentById(id));
    }

    /**
     * 新增BaseSegment
     */
    @RequiresPermissions("masterData:bizBaseSegment:add")
    @OperLog(title = "BaseSegment", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseSegment bizBaseSegment)
    {
        //唯一性校验bg + geo + segmentcode + segmentlevel + parentsegment + gpncode
        BizBaseSegment segment = new BizBaseSegment();
        segment.setBusinessGroup(bizBaseSegment.getBusinessGroup());
        segment.setSegmentCode(bizBaseSegment.getSegmentCode());
        segment.setSegmentLevel(bizBaseSegment.getSegmentLevel());
        segment.setParentSegment(bizBaseSegment.getParentSegment());
        segment.setGpnCode(bizBaseSegment.getGpnCode());
        bizBaseSegment.setStatus("Y");
        segment.setStatus(bizBaseSegment.getStatus());
        List<BizBaseSegment> list = bizBaseSegmentService.selectBizBaseSegmentList(segment);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseSegment.setCreateBy(getLoginName());
        bizBaseSegment.setCreateTime(DateUtils.getNowDate());
        bizBaseSegment.setCreateBy(getLoginName());
        return toResultDTO(bizBaseSegmentService.insertBizBaseSegment(bizBaseSegment),true);
    }

    /**
     * 修改BaseSegment
     */
    @RequiresPermissions("masterData:bizBaseSegment:edit")
    @OperLog(title = "BaseSegment", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseSegment bizBaseSegment)
    {
        bizBaseSegment.setUpdateBy(getLoginName());
        bizBaseSegment.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSegmentService.updateBizBaseSegment(bizBaseSegment),true);
    }

    /**
     * 删除BaseSegment
     */
    @RequiresPermissions("masterData:bizBaseSegment:remove")
    @OperLog(title = "BaseSegment", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseSegmentService.deleteBizBaseSegmentByIds(ids),true);
    }

    @GetMapping(value = "/getSegment")
    public ResultDTO<BizBaseSegment> getSegment(@RequestParam("businessGroup") String businessGroup,
                                            @RequestParam("segmentCode") String segmentCode,
                                            @RequestParam("segmentLevel") String segmentLevel) {
        BizBaseSegment baseSegment = bizBaseSegmentService.selectBizBaseSegment(businessGroup, segmentCode, segmentLevel);
        if (baseSegment != null) {
            return ResultDTO.success(baseSegment);
        }
        return ResultDTO.fail();
    }

}
