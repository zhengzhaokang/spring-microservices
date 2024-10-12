package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.AjaxResult;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.BizBaseEndCustomer;
import com.microservices.otmp.masterdata.service.IBizBaseEndCustomerService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseEndCustomerController
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseEndCustomer")
public class BizBaseEndCustomerController extends BaseController
{
    @Autowired
    private IBizBaseEndCustomerService bizBaseEndCustomerService;

    /**
     * 查询BaseEndCustomer列表
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseEndCustomer bizBaseEndCustomer)
    {
        startPage();
        List<BizBaseEndCustomer> list = bizBaseEndCustomerService.selectBizBaseEndCustomerList(bizBaseEndCustomer);
        return getDataTable(list);
    }

    @GetMapping("/getEndCustomerName")
    public String getEndCustomerName(@RequestParam("endCustomerId") String endCustomerId) {
        BizBaseEndCustomer bizBaseEndCustomer = new BizBaseEndCustomer();
        bizBaseEndCustomer.setEndCustomerId(endCustomerId);
        List<BizBaseEndCustomer> list = bizBaseEndCustomerService.selectBizBaseEndCustomerList(bizBaseEndCustomer);
        if (null != list && list.size() > 0) {
            BizBaseEndCustomer baseEndCustomer = list.get(0);
            if (null != baseEndCustomer) {
                return baseEndCustomer.getEndCustomerName();
            }
        }
        return "";
    }
    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/searchInfo")
    public TableDataInfo searchInfo(BizBaseEndCustomer bizBaseEndCustomer)
    {
        startPage();
        List<BizBaseEndCustomer> list = bizBaseEndCustomerService.getDropDownList(bizBaseEndCustomer);
        return getDataTable(list);
    }

    /**
     * 导出BaseEndCustomer列表
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:export")
    @OperLog(title = "BaseEndCustomer", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseEndCustomer bizBaseEndCustomer)
    {
        List<BizBaseEndCustomer> list = bizBaseEndCustomerService.selectBizBaseEndCustomerList(bizBaseEndCustomer);
        NewExcelUtil<BizBaseEndCustomer> util = new NewExcelUtil<>(BizBaseEndCustomer.class);
        util.exportExcel(response, list, "EndCustomer");
    }
    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseEndCustomer:import")
    @OperLog(title = "BaseEndCustomer", businessType = BusinessType.IMPORT)
    public AjaxResult importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseEndCustomer> util = new ExcelUtil<>(BizBaseEndCustomer.class);
        List <BizBaseEndCustomer> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseEndCustomerService.importExcel(bizs,getLoginName());

        return AjaxResult.success(message);
    }
    /**
     * 获取BaseEndCustomer详细信息
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bizBaseEndCustomerService.selectBizBaseEndCustomerById(id));
    }

    /**
     * 新增BaseEndCustomer
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:add")
    @OperLog(title = "BaseEndCustomer", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizBaseEndCustomer bizBaseEndCustomer)
    {
        //重复性校验
        BizBaseEndCustomer customer = new BizBaseEndCustomer();
        customer.setRegionMarketCode(bizBaseEndCustomer.getRegionMarketCode());
        customer.setCountryCode(bizBaseEndCustomer.getCountryCode());
        List<BizBaseEndCustomer> list = bizBaseEndCustomerService.selectBizBaseEndCustomerList(customer);
//        if (CollectionUtils.isNotEmpty(list)) {
//            return AjaxResult.error("Record exists already");
//        }
        bizBaseEndCustomer.setStatus("Y");
        bizBaseEndCustomer.setCreateBy(getLoginName());
        bizBaseEndCustomer.setCreateTime(DateUtils.getNowDate());
        return toAjax(bizBaseEndCustomerService.insertBizBaseEndCustomer(bizBaseEndCustomer),true);
    }

    /**
     * 修改BaseEndCustomer
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:edit")
    @OperLog(title = "BaseEndCustomer", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizBaseEndCustomer bizBaseEndCustomer)
    {
        bizBaseEndCustomer.setUpdateBy(getLoginName());
        bizBaseEndCustomer.setUpdateTime(DateUtils.getNowDate());
        return toAjax(bizBaseEndCustomerService.updateBizBaseEndCustomer(bizBaseEndCustomer),true);
    }

    /**
     * 删除BaseEndCustomer
     */
    @RequiresPermissions("masterData:bizBaseEndCustomer:remove")
    @OperLog(title = "BaseEndCustomer", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bizBaseEndCustomerService.deleteBizBaseEndCustomerByIds(ids),true);
    }
}
