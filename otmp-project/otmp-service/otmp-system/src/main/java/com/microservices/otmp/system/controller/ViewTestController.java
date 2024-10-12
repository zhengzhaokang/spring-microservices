package com.microservices.otmp.system.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.system.domain.TestViewExcel;
import com.microservices.otmp.system.domain.ViewTemplate;
import com.microservices.otmp.system.domain.ViewTest;
import com.microservices.otmp.system.service.IViewTemplateService;
import com.microservices.otmp.system.service.IViewTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 测试：测试动态展示字段 提供者
 *
 * @author sdms
 * @date 2022-02-23
 */
@RestController
@RequestMapping("test")
public class ViewTestController extends BaseController {

    @Autowired
    private IViewTestService viewTestService;
    @Autowired
    private IViewTemplateService viewTemplateService;
    
    private static  final String VALUE = "VALUE";
    private static  final String NAME = "name";

    /**
     * 查询${tableComment}
     */
    @GetMapping("get")
    public ResultDTO<Object> get(String name) {
        return ResultDTO.success(viewTestService.selectViewTestById(name));
    }

    /**
     * 查询测试：测试动态展示字段列表
     */
    @GetMapping("list")
    public TableDataInfo list(ViewTest viewTest) {
        startPage();
        return getDataTable(viewTestService.selectViewTestList(viewTest));
    }


    /**
     * 新增保存测试：测试动态展示字段
     */
    @PostMapping("save")
    public ResultDTO<Integer> addSave(@RequestBody ViewTest viewTest) {
        return ResultDTO.success(viewTestService.insertViewTest(viewTest));
    }

    /**
     * 修改保存测试：测试动态展示字段
     */
    @PostMapping("update")
    public ResultDTO<Integer> editSave(@RequestBody ViewTest viewTest) {
        return ResultDTO.success(viewTestService.updateViewTest(viewTest));
    }

    /**
     * 删除${tableComment}
     */
    @PostMapping("remove")
    public ResultDTO<Integer> remove(String ids) {
        return ResultDTO.success(viewTestService.deleteViewTestByIds(ids));
    }

    /**
     * 导出流程表单列表
     */
    @PostMapping("/export")
    public void export(@RequestBody TestViewExcel testViewExcel) {
        ViewTemplate viewTemplate = testViewExcel.getViewTemplate();
        viewTemplate.setItCode(getLoginName());
        List<ViewTest> viewTests = viewTestService.selectViewTestList(testViewExcel.getEntity());
        ExcelUtil.exportExcel("test", viewTemplateService.getSelectedFields(viewTemplate), viewTests);
    }

    /**
     * 查询${tableComment}
     */
    @GetMapping("province")
    public ResultDTO<Object> province() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapT = new HashMap<>();
        mapT.put(NAME, "天津省");
        mapT.put(VALUE, "天津省");
        Map<String, Object> mapG = new HashMap<>();
        mapG.put(NAME, "广东省");
        mapG.put(VALUE, "广东省");
        list.add(mapT);
        list.add(mapG);
        return ResultDTO.success(list);
    }

    /**
     * 查询${tableComment}
     */
    @GetMapping("city")
    public ResultDTO<Object> city(String name) {
        List<Map<String, Object>> list = null;
        if ("天津省".equals(name)) {
            list = new ArrayList<>();
            Map<String, Object> mapT = new HashMap<>();
            mapT.put(name, "天津市");
            mapT.put(VALUE, "天津市");
            list.add(mapT);
        } else if ("广东省".equals(name)) {
            list = new ArrayList<>();
            Map<String, Object> mapT = new HashMap<>();
            mapT.put(name, "陶冶市");
            mapT.put(VALUE, "陶冶市");
            Map<String, Object> mapG = new HashMap<>();
            mapG.put(name, "广东市");
            mapG.put(VALUE, "广东市");
            list.add(mapT);
            list.add(mapG);
        }
        return ResultDTO.success(list);
    }

    /**
     * 查询${tableComment}
     */
    @GetMapping("area")
    public ResultDTO<Object> area(String name) {
        List<Map<String, Object>> list = null;
        if ("天津市".equals(name)) {
            list = new ArrayList<>();
            Map<String, Object> mapT = new HashMap<>();
            mapT.put(name, "空港区");
            mapT.put(VALUE, "空港区");
            list.add(mapT);
            Map<String, Object> mapW = new HashMap<>();
            mapW.put(name, "乌龙区");
            mapW.put(VALUE, "乌龙区");
            list.add(mapW);
        } else if ("陶冶市".equals(name)) {
            list = new ArrayList<>();
            Map<String, Object> mapT = new HashMap<>();
            mapT.put(name, "胡辣汤县");
            mapT.put(VALUE, "胡辣汤县");
            list.add(mapT);
        } else if ("广东市".equals(name)) {
            list = new ArrayList<>();
            Map<String, Object> mapT = new HashMap<>();
            mapT.put(name, "广东区");
            mapT.put(VALUE, "广东区");
            list.add(mapT);
        }
        return ResultDTO.success(list);
    }

}
