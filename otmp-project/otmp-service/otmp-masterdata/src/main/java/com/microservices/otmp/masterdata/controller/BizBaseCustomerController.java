package com.microservices.otmp.masterdata.controller;

import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseCustomer;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCustomerDTO;
import com.microservices.otmp.masterdata.service.IBizBaseCustomerService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BaseCustomerController
 * 
 * @author lovefamily
 * @date 2022-04-24
 */
@RestController
@RequestMapping("/bizBaseCustomer")
public class BizBaseCustomerController extends BaseController
{
    @Cacheable(value = "bizBaseCustomer", key = "#customerNumber")
    @GetMapping("/getCustomerName")
    public String getCustomerNameByNumber(@RequestParam("customerNumber") String customerNumber){
        BizBaseCustomer bizBaseCustomer = new BizBaseCustomer();
        bizBaseCustomer.setCustomerNumber(customerNumber);
        List<BizBaseCustomer> list = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
        if (CollectionUtils.isNotEmpty(list  )) {
            BizBaseCustomer baseCustomer = list.get(0);
            if( null != baseCustomer){
                return baseCustomer.getCustomerName();
            }
        }
        return "";
    }

    @Autowired
    private IBizBaseCustomerService bizBaseCustomerService;

    /**
     * 查询BaseCustomer列表
     */
    @RequiresPermissions("masterData:bizBaseCustomer:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseCustomer bizBaseCustomer)
    {
        startPage();
        List<BizBaseCustomer> list = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
        return getDataTable(list);
    }
    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/searchInfo")
    public TableDataInfo searchInfo(BizBaseCustomer bizBaseCustomer)
    {
        startPage();
        List<BizBaseCustomer> list = bizBaseCustomerService.getDropDownList(bizBaseCustomer);
        return getDataTable(list);
    }

    /**
     * 导出BaseCustomer列表
     */
    @RequiresPermissions("masterData:bizBaseCustomer:export")
    @OperLog(title = "BaseCustomer", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseCustomer bizBaseCustomer)
    {
        List<BizBaseCustomer> list = bizBaseCustomerService.selectBizBaseCustomerList(bizBaseCustomer);
        NewExcelUtil<BizBaseCustomer> util = new NewExcelUtil<>(BizBaseCustomer.class);
        util.exportExcel(response, list, "Customer");
    }
    @PostMapping("/importExcel")
    @HasPermissions("masterData:bizBaseCustomer:import")
    @OperLog(title = "BaseCustomer", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseCustomer> util = new ExcelUtil<>(BizBaseCustomer.class);
        List <BizBaseCustomer> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseCustomerService.importExcel(bizs,getLoginName());

        return ResultDTO.success(message);
    }

    /**
     * 获取BaseCustomer详细信息
     */
    @RequiresPermissions("masterData:bizBaseCustomer:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseCustomer> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseCustomerService.selectBizBaseCustomerById(id));
    }

    /**
     * 新增BaseCustomer
     */
    @RequiresPermissions("masterData:bizBaseCustomer:add")
    @OperLog(title = "BaseCustomer", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseCustomer bizBaseCustomer)
    {
        bizBaseCustomer.setStatus("Y");
        bizBaseCustomer.setCreateBy(getLoginName());
        bizBaseCustomer.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseCustomerService.insertBizBaseCustomer(bizBaseCustomer),true);
    }

    /**
     * 修改BaseCustomer
     */
    @RequiresPermissions("masterData:bizBaseCustomer:edit")
    @OperLog(title = "BaseCustomer", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseCustomer bizBaseCustomer)
    {
        bizBaseCustomerService.removeCache(bizBaseCustomer.getId());
        bizBaseCustomer.setUpdateBy(getLoginName());
        bizBaseCustomer.setUpdateTime(DateUtils.getNowDate());
        bizBaseCustomer.setUpdateBy(getLoginName());
        return toResultDTO(bizBaseCustomerService.updateBizBaseCustomer(bizBaseCustomer),true);
    }

    /**
     * 删除BaseCustomer
     */
    @RequiresPermissions("masterData:bizBaseCustomer:remove")
    @OperLog(title = "BaseCustomer", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        for (Long id : ids) {
            bizBaseCustomerService.removeCache(id);
        }
        return toResultDTO(bizBaseCustomerService.deleteBizBaseCustomerByIds(ids),true);
    }

    /**
     * 获取BaseCustomer详细信息
     */
    @RequiresPermissions("masterData:bizBaseCustomer:query")
    @GetMapping(value = "getCustomerInfo")
    public ResultDTO<BizBaseCustomer> getCustomerInfo( BizBaseCustomer bizBaseCustomer)
    {
        bizBaseCustomer = bizBaseCustomerService.getCustomerInfo(bizBaseCustomer);
        return ResultDTO.success(bizBaseCustomer);
    }

    @PostMapping(value = "getCustomer")
    public BizBaseCustomerDTO getCustomer(@RequestBody  BizBaseCustomerDTO customerDTO) {
        if (null == customerDTO|| StrUtil.isBlank(customerDTO.getCustomerNumber())||StrUtil.isBlank(customerDTO.getSalesOfficeCode())||StrUtil.isBlank(customerDTO.getSalesOrgCode())) {
            return null;
        }
        BizBaseCustomer baseCustomer = new BizBaseCustomer();
        BeanUtils.copyProperties(customerDTO, baseCustomer);
        BizBaseCustomer bizBaseCustomerInfo = bizBaseCustomerService.getCustomer(baseCustomer);
        if (null == bizBaseCustomerInfo) {
            return null;
        }
        BizBaseCustomerDTO bizBaseCustomerDTO = new BizBaseCustomerDTO();
        BeanUtils.copyProperties(bizBaseCustomerInfo, bizBaseCustomerDTO);
        return bizBaseCustomerDTO;
    }

}
