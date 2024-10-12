package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.ToMsGtnTypeDTO;
import com.microservices.otmp.masterdata.domain.entity.vo.MsGtnTypeVo;
import com.microservices.otmp.masterdata.service.IBizBaseGtnTypeService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.RemoteResponse;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseGtnType;
import com.microservices.otmp.masterdata.domain.dto.BizBaseGtnTypeDTO;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * BaseGtnTypeController
 *
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseGtnType")
public class BizBaseGtnTypeController extends BaseController
{
    @Autowired
    private IBizBaseGtnTypeService bizBaseGtnTypeService;

    /**
     * 查询BaseGtnType列表
     */
    @RequiresPermissions("masterData:bizBaseGtnType:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseGtnType bizBaseGtnType)
    {
        startPage();
        List<BizBaseGtnType> list = bizBaseGtnTypeService.selectBizBaseGtnTypeList(bizBaseGtnType);
        return getDataTable(list);
    }
    @GetMapping("/getGtnCategoryByType")
    public String getGtnCategoryByType(@RequestParam("gtnType") String gtnType) {
        BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setGtnTypeCode(gtnType);
        List<BizBaseGtnType> list = bizBaseGtnTypeService.selectBizBaseGtnTypeList(bizBaseGtnType);
        if (null != list && list.size() > 0) {
            BizBaseGtnType gType = list.get(0);
            if (null != gType) {
                return gType.getGtnCategoryCode();
            }
        }
        return "";
    }
    /**
     * 查询下拉框
     */
    @RequiresPermissions("masterData:bizBaseGtnType:list")
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition geo)
    {
        List<BizBaseGtnType> list = bizBaseGtnTypeService.getDropDownList(geo);
        return getDataTable(list);
    }

    @RequiresPermissions("masterData:bizBaseGtnType:list")
    @OperLog(title = "提供给ms的api接口(gtnTypeList)", businessType = BusinessType.QUERY)
    @GetMapping("/toMs/gtnTypeList")
    public TableDataInfo toMsGtnTypeList(ToMsGtnTypeDTO toMsGtnTypeDTO)
    {
        List<MsGtnTypeVo> list = bizBaseGtnTypeService.toMsGtnTypeList(toMsGtnTypeDTO);
        return getDataTable(list);
    }

    /**
     * 导出BaseGtnType列表
     */
    @RequiresPermissions("masterData:bizBaseGtnType:export")
    @OperLog(title = "BaseGtnType", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseGtnType bizBaseGtnType)
    {
        List<BizBaseGtnType> list = bizBaseGtnTypeService.selectBizBaseGtnTypeList(bizBaseGtnType);
        NewExcelUtil<BizBaseGtnType> util = new NewExcelUtil<>(BizBaseGtnType.class);
        util.exportExcel(response, list, "GtnType");
	    }
     @GetMapping("/remoteGetGtnType")
    public RemoteResponse<BizBaseGtnTypeDTO> remoteGetGtnType(@RequestParam("gtnType") String gtnType) {
        BizBaseGtnType bizBaseGtnType = new BizBaseGtnType();
        bizBaseGtnType.setGtnTypeCode(gtnType);
        List<BizBaseGtnType> list = bizBaseGtnTypeService.selectBizBaseGtnTypeList(bizBaseGtnType);
        List<BizBaseGtnTypeDTO> dtoList = new ArrayList<>();
        BeanUtils.copyListProperties(list, dtoList, BizBaseGtnTypeDTO.class);
        RemoteResponse<BizBaseGtnTypeDTO> remoteData = new RemoteResponse<>();
        remoteData.setRows(dtoList);
        return remoteData;
    }
    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseGtnType:import")
    @OperLog(title = "BaseGtnType", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseGtnType> util = new ExcelUtil<>(BizBaseGtnType.class);
        List <BizBaseGtnType> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseGtnTypeService.importExcel(bizs,getLoginName());

        return ResultDTO.success(message);
    }
    /**
     * 获取BaseGtnType详细信息
     */
    @RequiresPermissions("masterData:bizBaseGtnType:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseGtnType> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseGtnTypeService.selectBizBaseGtnTypeById(id));
    }

    /**
     * 新增BaseGtnType
     */
    @RequiresPermissions("masterData:bizBaseGtnType:add")
    @OperLog(title = "BaseGtnType", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseGtnType bizBaseGtnType)
    {
        //重复性校验
        BizBaseGtnType type = new BizBaseGtnType();
        type.setGeoCode(bizBaseGtnType.getGeoCode());
        type.setGtnTypeCode(bizBaseGtnType.getGtnTypeCode());
        type.setGtnCategoryCode(bizBaseGtnType.getGtnCategoryCode());
        type.setCndnOrderReason(bizBaseGtnType.getCndnOrderReason());
        type.setBusinessGroup(bizBaseGtnType.getBusinessGroup());
        type.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseGtnType> list = bizBaseGtnTypeService.selectBizBaseGtnTypeList(type);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        bizBaseGtnType.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        bizBaseGtnType.setCreateBy(getLoginName());
        bizBaseGtnType.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseGtnTypeService.insertBizBaseGtnType(bizBaseGtnType),true);
    }

    /**
     * 修改BaseGtnType
     */
    @RequiresPermissions("masterData:bizBaseGtnType:edit")
    @OperLog(title = "BaseGtnType", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseGtnType bizBaseGtnType)
    {
        bizBaseGtnType.setUpdateBy(getLoginName());
        bizBaseGtnType.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseGtnTypeService.updateBizBaseGtnType(bizBaseGtnType),true);
    }

    /**
     * 删除BaseGtnType
     */
    @RequiresPermissions("masterData:bizBaseGtnType:remove")
    @OperLog(title = "BaseGtnType", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseGtnTypeService.deleteBizBaseGtnTypeByIds(ids),true);
    }
}
