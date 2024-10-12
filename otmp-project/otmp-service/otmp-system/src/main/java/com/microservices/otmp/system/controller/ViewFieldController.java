package com.microservices.otmp.system.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.system.common.ConstantViewField;
import com.microservices.otmp.system.domain.ViewField;
import com.microservices.otmp.system.domain.ViewFieldExcel;
import com.microservices.otmp.system.service.IViewFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 页面字段展示信息 提供者
 *
 * @author sdms
 * @date 2022-02-15
 */
@RestController
@RequestMapping("field")
public class ViewFieldController extends BaseController {

    @Autowired
    private IViewFieldService viewFieldService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get")
    public ResultDTO<Object> get(Integer id) {
        return ResultDTO.success(viewFieldService.selectViewFieldById(id));
    }

    /**
     * 查询页面字段展示信息列表
     */
    @GetMapping("list")
    public TableDataInfo list(ViewField viewField) {
        startPage();
        return getDataTable(viewFieldService.selectFieldTiled(viewField));
    }

    /**
     * 查询页面字段展示信息列表
     */
    @GetMapping("tree")
    public TableDataInfo tree(@RequestParam(required = false) Map<String, Object> map) {
        startPage();
        return getDataTable(viewFieldService.tree(map));
    }

    /**
     * 查询页面字段展示信息列表
     */
    @GetMapping("treeSyncData")
    public ResultDTO<Object> treeSyncData(@RequestParam(required = false) Map<String, Object> map) {
        return ResultDTO.success(viewFieldService.treeSyncData(map));
    }

    /**
     * 新增保存页面字段展示信息
     */
    @PostMapping("save")
    @OperLog(title = "view field", businessType = BusinessType.INSERT)
    public ResultDTO<Integer> addSave(@RequestBody Map<String, Object> viewField) {
        String loginName = null == getLoginName() ? UserConstants.SYSTEM_ADMIN : getLoginName();
        viewField.put("createBy", loginName);
        viewField.put("updateBy", loginName);
        return ResultDTO.success(viewFieldService.insertViewField(viewField));
    }

    /**
     * 修改保存页面字段展示信息
     */
    @PostMapping("update")
    @OperLog(title = "view field", businessType = BusinessType.UPDATE)
    public ResultDTO<Integer> editSave(@RequestBody Map<String, Object> viewField) {
        String loginName = null == getLoginName() ? UserConstants.SYSTEM_ADMIN : getLoginName();
        viewField.put("createBy", loginName);
        viewField.put("updateBy", loginName);
        return ResultDTO.success(viewFieldService.updateViewField(viewField));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        return ResultDTO.success(viewFieldService.deleteViewFieldByIds(ids));
    }

    /**
     * 查询页面字段展示信息列表
     */
    @GetMapping("fields")
    public TableDataInfo list(String relName) {
        return getDataTable(viewFieldService.fields(relName));
    }

    /**
     * 列头信息
     */
    @GetMapping("columnHeader")
    public ResultDTO<Object> columnHeader() {
        return ResultDTO.success(viewFieldService.columnHeader());
    }

    /**
     * 复制模板的表头 列头信息
     */
    @GetMapping("columnHeaderByCopy")
    public ResultDTO<Object> columnHeaderByCopy() {
        return ResultDTO.success(viewFieldService.columnHeaderByCopy());
    }

    /**
     * 修改保存页面字段展示信息
     */
    @PostMapping("disableViewField")
    public ResultDTO<Object> disableViewField(@RequestBody ViewField viewField) {
        return ResultDTO.success(viewFieldService.disableViewField(viewField));
    }

    /**
     * 获取块信息
     */
    @GetMapping("pageKeyList")
    public TableDataInfo pageKeyList(@RequestParam(required = false) Map<String, Object> map) {
        String loginName = null == getLoginName() ? UserConstants.SYSTEM_ADMIN : getLoginName();
        map.put(ConstantViewField.IT_CODE_DEF, loginName);
        return getDataTable(viewFieldService.pageKeyList(map));
    }

    /**
     * 查询页面字段展示信息列表
     */
    @GetMapping("selectFieldTree")
    public TableDataInfo selectFieldTree(ViewField viewField) {
        startPage();
        return getDataTable(viewFieldService.selectFieldTree(viewField));
    }

    /**
     * 获取块信息
     */
    @GetMapping("divSelect")
    public TableDataInfo divSelect(ViewField viewField) {
        return getDataTable(viewFieldService.divSelect(viewField));
    }

    /**
     * 修复数据
     */
    @GetMapping("repairData")
    public ResultDTO<Object> repairData(ViewField viewField) {
        viewFieldService.repairData(viewField);
        return ResultDTO.success();
    }

    /**
     * 配置字段导出
     */
    @RequiresPermissions("payment:header:export")
    @PostMapping("/exportViewField")
    public void exportViewField(@RequestBody ViewField viewField) {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        ServletRequestAttributes getResponse = (ServletRequestAttributes) requestAttributes;
        HttpServletResponse response = getResponse.getResponse();
        List<ViewFieldExcel> list = viewFieldService.export(viewField);
        NewExcelUtil<ViewFieldExcel> util = new NewExcelUtil<>(ViewFieldExcel.class);
        util.exportExcel(response, list, "配置字段导出");
    }

    /**
     * 导入BizBaseSalesOrg列表
     */
    @PostMapping("/importFieldExcel")
    @RequiresPermissions("masterdata:bizBaseSalesOrg:import")
    public ResultDTO<Object> importFieldExcel(MultipartFile file, Integer id) throws Exception {
        ExcelUtil<ViewFieldExcel> util = new ExcelUtil<>(ViewFieldExcel.class);
        List<ViewFieldExcel> bizs = util.importExcel(file.getInputStream());
        viewFieldService.importExcel(bizs, getLoginName(), id);
        return ResultDTO.success();
    }

    /**
     * 字段补全
     */
    @GetMapping("completionField")
    public ResultDTO<Object> completionField(ViewField viewField) {
        return ResultDTO.success(viewFieldService.completionField(viewField));
    }

    /**
     * 表下拉
     */
    @GetMapping("getTableNames")
    public ResultDTO<Object> getTableNames() {
        return ResultDTO.success(viewFieldService.getTableNames());
    }

    /**
     * 字段维度下拉
     */
    @GetMapping("fieldDimensionsSelect")
    public ResultDTO<Object> fieldDimensionsSelect(@RequestParam(required = false) Map<String, String> condition) {
        return ResultDTO.success(viewFieldService.fieldDimensionsSelect(condition));
    }

    /**
     * 字段维度下拉
     */
    @GetMapping("selectViewFieldAndCondition")
    public ResultDTO<Object> selectViewFieldAndCondition(@RequestParam(required = false) Map<String, Object> condition) {
        return ResultDTO.success(viewFieldService.selectViewFieldAndCondition(condition));
    }

    /**
     * 字段维度下拉
     */
    @GetMapping("dataRecovery")
    public ResultDTO<Object> dataRecovery(@RequestParam(required = false) Map<String, Object> condition) {
        viewFieldService.dataRecovery(condition);
        return ResultDTO.success();
    }

    /**
     * 字段维度下拉
     */
    @GetMapping("parentViewFieldCode")
    public String parentViewFieldCode(Integer fieldId) {
        return viewFieldService.parentViewFieldCode(fieldId);
    }

    @GetMapping("getViewFieldByBgAndGeo")
    public Object getViewFieldByBgAndGeo(@RequestParam(value = "geo") String geoCode, @RequestParam(value = "bg") String businessGroup,
                                         @RequestParam(value = "pageKey") String pageKey, @RequestParam(value = "viewType") String viewType) {
        Map<String, Object> map = new HashMap<>();
        map.put(ConstantViewField.GEO_CODE, geoCode);
        map.put(ConstantViewField.BUSINESS_GROUP, businessGroup);
        if (StrUtil.isNotBlank(pageKey) && !"null".equals(pageKey)) {
            map.put(ConstantViewField.PAGE_KEY, pageKey);
        }
        map.put(ConstantViewField.VIEW_TYPE, viewType);
        List<JSONObject> jsonObjects = viewFieldService.getViewField(map);
        return JSON.toJSON(jsonObjects);
    }
}
