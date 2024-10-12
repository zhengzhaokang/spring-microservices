package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.constant.UserConstants;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.system.common.ConstantViewField;
import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.entity.ViewTemplateDO;
import com.microservices.otmp.system.service.IViewTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 页面展示模板 提供者
 *
 * @author sdms
 * @date 2022-02-15
 */
@RestController
@RequestMapping("template")
public class ViewTemplateController extends BaseController {
    @Autowired
    private IViewTemplateService viewTemplateService;

    /**
     * 查询${tableComment}
     */
    @GetMapping("get/{id}")
    public ViewTemplateDO get(@PathVariable("id") Integer id) {
        return viewTemplateService.selectViewTemplateById(id);

    }

    /**
     * 查询页面展示模板列表
     */
    @GetMapping("list")
    public TableDataInfo list(ViewTemplate viewTemplate) {
        startPage();
        return getDataTable(viewTemplateService.selectViewTemplateList(viewTemplate));
    }


    /**
     * 新增保存页面展示模板
     */
    @PostMapping("save")
    public ResultDTO<Integer> addSave(@RequestBody ViewTemplate templateView) {
        return ResultDTO.success(viewTemplateService.insertViewTemplate(templateView));
    }

    /**
     * 修改保存页面展示模板
     */
    @PostMapping("update")
    public ResultDTO<Integer> editSave(@RequestBody ViewTemplate viewTemplate) {
        viewTemplate.setUpdateBy(getLoginName());
        return ResultDTO.success(viewTemplateService.updateViewTemplate(viewTemplate));
    }

    /**
     * 逻辑删除
     */
    @GetMapping("isDelete/{id}")
    public ResultDTO<Integer> isDelete(@PathVariable("id") Integer id) {
        ViewTemplate viewTemplate = new ViewTemplate();
        viewTemplate.setId(id);
        viewTemplate.setUpdateBy(getLoginName());
        viewTemplate.setStatus(1);
        return ResultDTO.success(viewTemplateService.updateViewTemplate(viewTemplate));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        return ResultDTO.success(viewTemplateService.deleteViewTemplateByIds(ids));
    }

    /**
     * 新增保存页面展示模板
     */
    @PostMapping("saveTemplate")
    public ResultDTO<Integer> saveTemplate(@RequestBody ViewTemplate templateView) {
        templateView.setItCode(UserConstants.SYSTEM_TEMPLATE);
        templateView.setCreateBy(getLoginName());
        templateView.setUpdateBy(getLoginName());
        return ResultDTO.success(viewTemplateService.saveTemplate(templateView));
    }

    /**
     * 查询fieldId,region,paymentMode模板。
     */
    @GetMapping("viewTemplateDetails")
    public ResultDTO<Object> viewTemplateDetails(ViewTemplate viewTemplate) {
        viewTemplate.setItCode(UserConstants.SYSTEM_TEMPLATE);
        return ResultDTO.success(viewTemplateService.viewTemplateDetails(viewTemplate));
    }

    /**
     * 查询fieldId,region,paymentMode模板。
     */
    @GetMapping("viewTemplateByUser")
    public ResultDTO<Object> viewTemplateByUser(@RequestParam Map<String, Object> viewTemplate) {
        viewTemplate.put(ConstantViewField.IT_CODE_DEF, getLoginName());
        return ResultDTO.success(viewTemplateService.viewTemplateByUser(viewTemplate));
    }

    /**
     * 查询fieldId,region,paymentMode模板。
     */
    @GetMapping("viewTemplateSelect")
    public ResultDTO<Object> viewTemplateSelect(ViewTemplate templateView) {
        templateView.setItCode(getLoginName());
        return ResultDTO.success(viewTemplateService.viewTemplateSelect(templateView));
    }

    /**
     * 用户模板查询
     */
    @GetMapping("viewTemplateDetailsByUser")
    public ResultDTO<Object> viewTemplateDetailsByUser(ViewTemplate viewTemplate) {
        return ResultDTO.success(viewTemplateService.viewTemplateDetailsByUser(viewTemplate));
    }

    /**
     * 新增保存页面展示模板
     */
    @PostMapping("addTemplateByUser")
    public ResultDTO<Integer> saveTemplateBuUser(@RequestBody ViewTemplate templateView) {
        templateView.setItCode(getLoginName());
        templateView.setCreateBy(getLoginName());
        templateView.setUpdateBy(getLoginName());
        templateView.setId(null);
        return ResultDTO.success(viewTemplateService.saveTemplateByUser(templateView));
    }

    /**
     * 新增保存页面展示模板
     */
    @PostMapping("updateTemplateByUser")
    public ResultDTO<Object> updateTemplateByUser(@RequestBody ViewTemplate templateView) {
        // 不能修改其他itCode模板
        if (null != getLoginName() && !getLoginName().equals(templateView.getItCode())) {
            return ResultDTO.fail("You cannot modify other users' templates");
        }
        templateView.setItCode(getLoginName());
        templateView.setCreateBy(getLoginName());
        templateView.setUpdateBy(getLoginName());
        return ResultDTO.success(viewTemplateService.saveTemplate(templateView));
    }

    /**
     * 模板复制
     */
    @PostMapping("copyTemplate")
    public ResultDTO<Object> copyTemplate(@RequestBody Map<String, Object> templateView) {
        templateView.put(ConstantViewField.CREATE_BY, getLoginName() == null ? UserConstants.SYSTEM_ADMIN : getLoginName());
        templateView.put(ConstantViewField.UPDATE_BY, getLoginName() == null ? UserConstants.SYSTEM_ADMIN : getLoginName());
        templateView.put(ConstantViewField.IT_CODE, getLoginName() == null ? UserConstants.SYSTEM_ADMIN : getLoginName());
        viewTemplateService.copyTemplate(templateView);
        return ResultDTO.success(1);
    }

    /**
     *
     */
    @PostMapping("deleteTemplateGroup")
    public ResultDTO<Object> deleteTemplateGroup(@RequestBody Map<String, Object> templateView) {
        templateView.put(ConstantViewField.UPDATE_BY, getLoginName() == null ? UserConstants.SYSTEM_ADMIN : getLoginName());
        viewTemplateService.deleteTemplateGroup(templateView);
        return ResultDTO.success(1);
    }

    /**
     * 修改默认模板
     */
    @GetMapping("updateDefaultTemplate")
    public ResultDTO<Object> updateDefaultTemplate(ViewTemplate viewTemplate) {
        viewTemplateService.updateDefaultTemplate(viewTemplate);
        return ResultDTO.success();
    }

}
