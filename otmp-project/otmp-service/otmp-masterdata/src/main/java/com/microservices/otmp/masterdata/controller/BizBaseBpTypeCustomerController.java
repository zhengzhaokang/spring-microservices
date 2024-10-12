package com.microservices.otmp.masterdata.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.utils.poi.NewExcelUtil;
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
import com.microservices.otmp.masterdata.domain.BizBaseBpTypeCustomer;
import com.microservices.otmp.masterdata.service.IBizBaseBpTypeCustomerService;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.web.multipart.MultipartFile;
import com.microservices.otmp.common.core.page.TableDataInfo;

/**
 * BaseBpTypeCustomerController
 * 
 * @author lovefamily
 * @date 2022-06-24
 */
@RestController
@RequestMapping("/bizBaseBpTypeCustomer")
public class BizBaseBpTypeCustomerController extends BaseController
{
    @Autowired
    private IBizBaseBpTypeCustomerService bizBaseBpTypeCustomerService;

    /**
     * 查询BaseBpTypeCustomer列表
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        startPage();
        List<BizBaseBpTypeCustomer> list = bizBaseBpTypeCustomerService.selectBizBaseBpTypeCustomerList(bizBaseBpTypeCustomer);
        return getDataTable(list);
    }
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:list")
    @GetMapping("/list/distinct/bpType")
    public TableDataInfo distinct(BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        startPage();
        List<BizBaseBpTypeCustomer> list = bizBaseBpTypeCustomerService.selectBizBaseBpTypeCustomerListDistinctBpType(bizBaseBpTypeCustomer);
        return getDataTable(list);
    }
    /**
     * 导出BaseBpTypeCustomer列表
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:export")
    @Log(title = "BaseBpTypeCustomer", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        List<BizBaseBpTypeCustomer> list = bizBaseBpTypeCustomerService.selectBizBaseBpTypeCustomerList(bizBaseBpTypeCustomer);
        NewExcelUtil<BizBaseBpTypeCustomer> util = new NewExcelUtil<BizBaseBpTypeCustomer>(BizBaseBpTypeCustomer.class);
        util.exportExcel(response, list, "BaseBpTypeCustomer数据");
    }

    /**
    * 导入BaseBpTypeCustomer列表
    */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:import")
    @Log(title = "BaseBpTypeCustomer", businessType = BusinessType.IMPORT)
    @PostMapping("/importExcel")
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {

        ExcelUtil<BizBaseBpTypeCustomer> util = new ExcelUtil<BizBaseBpTypeCustomer>(BizBaseBpTypeCustomer.class);
        List <BizBaseBpTypeCustomer> bizs = util.importExcel(file.getInputStream());
        return ResultDTO.success(bizBaseBpTypeCustomerService.importExcel(bizs,getLoginName()));
    }


    /**
     * 获取BaseBpTypeCustomer详细信息
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseBpTypeCustomer> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseBpTypeCustomerService.selectBizBaseBpTypeCustomerById(id));
    }

    /**
     * 新增BaseBpTypeCustomer
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:add")
    @Log(title = "BaseBpTypeCustomer", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        return toResultDTO(bizBaseBpTypeCustomerService.insertBizBaseBpTypeCustomer(bizBaseBpTypeCustomer),true);
    }

    /**
     * 修改BaseBpTypeCustomer
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:edit")
    @Log(title = "BaseBpTypeCustomer", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseBpTypeCustomer bizBaseBpTypeCustomer)
    {
        return toResultDTO(bizBaseBpTypeCustomerService.updateBizBaseBpTypeCustomer(bizBaseBpTypeCustomer),true);
    }

    /**
     * 删除BaseBpTypeCustomer
     */
    @RequiresPermissions("masterdata:bizBaseBpTypeCustomer:remove")
    @Log(title = "BaseBpTypeCustomer", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseBpTypeCustomerService.deleteBizBaseBpTypeCustomerByIds(ids),true);
    }
}
