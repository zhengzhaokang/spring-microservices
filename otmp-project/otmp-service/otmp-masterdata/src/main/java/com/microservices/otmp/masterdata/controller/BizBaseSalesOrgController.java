package com.microservices.otmp.masterdata.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.common.utils.wso2.Wso2ModuleType;
import com.microservices.otmp.common.utils.wso2.Wso2Util;
import com.microservices.otmp.masterdata.common.Response;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownCondition;
import com.microservices.otmp.masterdata.domain.BizBaseSalesOrg;
import com.microservices.otmp.masterdata.domain.entity.dto.SalesOrgDTO;
import com.microservices.otmp.masterdata.domain.entity.dto.Wso2GetDcDTO;
import com.microservices.otmp.masterdata.feign.RemoteCompanyCodeWso2Service;
import com.microservices.otmp.masterdata.feign.RemoteGetDcWso2Service;
import com.microservices.otmp.masterdata.service.IBizBaseSalesOrgService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * BizBaseSalesOrgController
 *
 * @author lovefamily
 * @date 2022-04-22
 */
@RestController
@RequestMapping("/bizBaseSalesOrg")
public class BizBaseSalesOrgController extends BaseController
{
    @Autowired
    private IBizBaseSalesOrgService bizBaseSalesOrgService;
    @Autowired
    Wso2Util wso2Util;
    @Autowired
    RemoteCompanyCodeWso2Service remoteCompanyCodeWso2Service;
    @Autowired
    RemoteGetDcWso2Service remoteGetDcWso2Service;
    /**
     * 查询BizBaseSalesOrg列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSalesOrg bizBaseSalesOrg)
    {
        startPage();
        List<BizBaseSalesOrg> list = bizBaseSalesOrgService.selectBizBaseSalesOrgList(bizBaseSalesOrg);
        return getDataTable(list);
    }

    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/dropDownList")
    public TableDataInfo getDropDownList(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        List<BizBaseSalesOrg> list = bizBaseSalesOrgService.getDropDownList(bizBaseDropDownCondition);
        return getDataTable(list);
    }
    /**
     * 查询下拉框
     */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/getOne")
    public ResultDTO<BizBaseSalesOrg> getOneSalesOrg(BizBaseDropDownCondition bizBaseDropDownCondition)
    {
        BizBaseSalesOrg  salesOrg = bizBaseSalesOrgService.getOne(bizBaseDropDownCondition);
        return ResultDTO.success(salesOrg);
    }
    /**
     * 导出BizBaseSalesOrg列表
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:export")
    @OperLog(title = "BizBaseSalesOrg", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSalesOrg bizBaseSalesOrg)
    {
        List<BizBaseSalesOrg> list = bizBaseSalesOrgService.selectBizBaseSalesOrgList(bizBaseSalesOrg);
        NewExcelUtil<BizBaseSalesOrg> util = new NewExcelUtil<>(BizBaseSalesOrg.class);
        util.exportExcel(response, list, "SalesOrg");
    }
    /**
     * 导入BizBaseSalesOrg列表
     */
    @PostMapping("/importExcel")
    @RequiresPermissions("masterdata:bizBaseSalesOrg:import")
    @OperLog(title = "BizBaseSalesOrg", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseSalesOrg> util = new ExcelUtil<>(BizBaseSalesOrg.class);
        List <BizBaseSalesOrg> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseSalesOrgService.importExcel(bizs,getLoginName());

        return ResultDTO.success(message);
    }

    /**
     * 获取BizBaseSalesOrg详细信息
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseSalesOrg> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseSalesOrgService.selectBizBaseSalesOrgById(id));
    }

    /**
     * 新增BizBaseSalesOrg
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:add")
    @OperLog(title = "BizBaseSalesOrg", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseSalesOrg bizBaseSalesOrg)
    {
        bizBaseSalesOrg.setStatus("Y");
        bizBaseSalesOrg.setCreateBy(getLoginName());
        bizBaseSalesOrg.setCreateTime(DateUtils.getNowDate());
        bizBaseSalesOrg.setSalesOrgName(bizBaseSalesOrg.getSalesOrgCode());
        return toResultDTO(bizBaseSalesOrgService.insertBizBaseSalesOrg(bizBaseSalesOrg),true);
    }

    /**
     * 修改BizBaseSalesOrg
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:edit")
    @OperLog(title = "BizBaseSalesOrg", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseSalesOrg bizBaseSalesOrg)
    {

        bizBaseSalesOrg.setUpdateBy(getLoginName());
        bizBaseSalesOrg.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSalesOrgService.updateBizBaseSalesOrg(bizBaseSalesOrg),true);
    }

    /**
     * 删除BizBaseSalesOrg
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:remove")
    @OperLog(title = "BizBaseSalesOrg", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseSalesOrgService.deleteBizBaseSalesOrgByIds(ids),true);
    }

    /**
     * 获取BizBaseSalesOrg详细信息
     */
    @RequiresPermissions("masterData:bizBaseSalesOrg:query")
    @GetMapping("/localCurrencyList")
    public ResultDTO<List<BizBaseSalesOrg>> localCurrencyList()
    {
        return ResultDTO.success(bizBaseSalesOrgService.localCurrencyList());
    }

    @GetMapping("/getCompanyCodeBySalesOrgCode")
    public ResultDTO<Object> getCompanyCodeBySalesOrgCode(@RequestParam("salesOrg") String salesOrg, @RequestParam(name = "salesOffice", required = false) String salesOffice, @RequestParam(name = "type", required = false) String type) {
        SalesOrgDTO salesOrgDTO = new SalesOrgDTO();
        salesOrgDTO.setSalesOrg(salesOrg);
        if (type == null) {
            type = "getCompanyCode";
        }
        salesOrgDTO.setType(type);
        String token = wso2Util.getToken(Wso2ModuleType.fi.name());
        if (type.equals("getDC")) {
            salesOrgDTO.setSalesOffice(salesOffice);
            Response<JSONArray> response = remoteGetDcWso2Service.getDc(salesOrgDTO, token);
            if (null == response || !"200".equals(response.getCode())) {
                return ResultDTO.fail("The CompayCode corresponding to this SalesOrg does not exist in ECC, please check whether the SalesOrg is correct");
            }
            List<Wso2GetDcDTO> dtos = JSON.parseArray(JSON.toJSONString(response.getData()), Wso2GetDcDTO.class);
            clear(dtos);
            return ResultDTO.success(dtos);
        } else {
            Response<JSONObject> response = remoteCompanyCodeWso2Service.getCompanyCodeBySalesOrg(salesOrgDTO, token);
            if (null == response || !"200".equals(response.getCode())) {
                return ResultDTO.fail("The CompayCode corresponding to this SalesOrg does not exist in ECC, please check whether the SalesOrg is correct");
            }
            return ResultDTO.success(response.getData());
        }
    }

    private void clear(List<Wso2GetDcDTO> dtos) {
        HashSet<String> set = new HashSet<>();
        if (CollectionUtils.isNotEmpty(dtos)) {
            Iterator<Wso2GetDcDTO> it = dtos.iterator();
            while(it.hasNext()) {
                Wso2GetDcDTO dto = it.next();
                if (!set.add(dto.getDistributionChannel())) {
                    it.remove();
                }
            }
        }
    }
}
