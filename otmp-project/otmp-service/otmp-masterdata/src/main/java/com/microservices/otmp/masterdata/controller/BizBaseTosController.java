package com.microservices.otmp.masterdata.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.service.task.RunTosDataTaskService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import com.microservices.otmp.masterdata.domain.BizBaseTos;
import com.microservices.otmp.masterdata.service.IBizBaseTosService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * BaseTosController
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@RestController
@RequestMapping("/bizBaseTos")
public class BizBaseTosController extends BaseController
{
    @Autowired
    private IBizBaseTosService bizBaseTosService;

    @Autowired
    private RunTosDataTaskService runTosDataTaskService;

    /**
     * 查询BaseTos列表
     */
    @RequiresPermissions("masterdata:bizBaseTos :list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseTos bizBaseTos)
    {
        startPage();
        List<BizBaseTos> list = bizBaseTosService.selectBizBaseTosList(bizBaseTos);
        return getDataTable(list);
    }

    /**
     * 导出BaseTos列表
     */
    @RequiresPermissions("masterdata:bizBaseTos :export")
    @Log(title = "BaseTos", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseTos bizBaseTos)
    {
        List<BizBaseTos> list = bizBaseTosService.selectBizBaseTosList(bizBaseTos);
        NewExcelUtil<BizBaseTos> util = new NewExcelUtil<BizBaseTos>(BizBaseTos.class);
        util.exportExcel(response, list, "BaseTos数据");
    }

    /**
    * 导入BaseTos列表
    */
    @RequiresPermissions("masterdata:bizBaseTos :import")
    @Log(title = "BaseTos", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {

        ExcelUtil<BizBaseTos> util = new ExcelUtil<BizBaseTos>(BizBaseTos.class);
        List <BizBaseTos> bizs = util.importExcel(file.getInputStream());
        return ResultDTO.success(bizBaseTosService.importExcel(bizs,getLoginName()));
    }


    /**
     * 获取BaseTos详细信息
     */
    @RequiresPermissions("masterdata:bizBaseTos :query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseTos> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseTosService.selectBizBaseTosById(id));
    }

    /**
     * 新增BaseTos
     */
    @RequiresPermissions("masterdata:bizBaseTos :add")
    @Log(title = "BaseTos", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseTos bizBaseTos)
    {
        //重复性校验
        BizBaseTos tos = new BizBaseTos();
        tos.setMemberId(bizBaseTos.getMemberId());
        tos.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseTos> list = bizBaseTosService.selectBizBaseTosList(tos);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        return toResultDTO(bizBaseTosService.insertBizBaseTos(bizBaseTos),true);
    }

    /**
     * 修改BaseTos
     */
    @RequiresPermissions("masterdata:bizBaseTos :edit")
    @Log(title = "BaseTos", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseTos bizBaseTos)
    {
        return toResultDTO(bizBaseTosService.updateBizBaseTos(bizBaseTos),true);
    }

    /**
     * 删除BaseTos
     */
    @RequiresPermissions("masterdata:bizBaseTos :remove")
    @Log(title = "BaseTos", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseTosService.deleteBizBaseTosByIds(ids),true);
    }
    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
    /**
     * 新增BaseTos
     */
    @RequiresPermissions("masterdata:bizBaseTos :add")
    @GetMapping("/tst")
    public ResultDTO<Integer> addTos(BizBaseTos bizBaseTos) throws ParseException {
        runTosDataTaskService.runTos(bizBaseTos);
        return ResultDTO.success();

    }
}
