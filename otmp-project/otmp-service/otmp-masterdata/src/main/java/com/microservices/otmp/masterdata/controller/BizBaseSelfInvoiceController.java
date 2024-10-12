package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseSelfInvoice;
import com.microservices.otmp.masterdata.service.IBizBaseSelfInvoiceService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 BizBaseSelfInvoiceController *
 * @author xiaozy8
 * @date 2022-09-28
 */
@RestController
@RequestMapping("/bizBaseSelfInvoice")
public class BizBaseSelfInvoiceController extends BaseController
{
    public static final String ACTIVATED = "Activated";
    public static final String DEACTIVATE = "Deactivated";
    @Autowired
    private IBizBaseSelfInvoiceService bizBaseSelfInvoiceService;

    /**
     * 查询BizBaseSelfInvoice列表
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseSelfInvoice bizBaseSelfInvoice)
    {
        startPage();
        List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
        return getDataTable(list);
    }

    /**
     * 导出BizBaseSelfInvoice列表
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:export")
    @OperLog(title = "BizBaseSelfInvoice", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseSelfInvoice bizBaseSelfInvoice)
    {
        List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice);
        NewExcelUtil<BizBaseSelfInvoice> util = new NewExcelUtil<>(BizBaseSelfInvoice.class);
        util.exportExcel(response, list, "SelfInvoice");
    }

    /**
     * 获取BizBaseSelfInvoice详细信息
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseSelfInvoice> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceById(id));
    }

    /**
     * 新增BizBaseSelfInvoice
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:add")
    @OperLog(title = "BizBaseSelfInvoice", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseSelfInvoice bizBaseSelfInvoice)
    {
        //查询partnerSignedSba是否存在
        BizBaseSelfInvoice bizBaseSelfInvoice1 = new BizBaseSelfInvoice();
        bizBaseSelfInvoice1.setSellerCountry(bizBaseSelfInvoice.getSellerCountry());
        if (StringUtils.isNotEmpty(bizBaseSelfInvoice.getGtnCategoryL1())) {
            bizBaseSelfInvoice1.setGtnCategoryL1(bizBaseSelfInvoice.getGtnCategoryL1());
        }
        if (StringUtils.isNotEmpty(bizBaseSelfInvoice.getCrmId())) {
            bizBaseSelfInvoice1.setCrmId(bizBaseSelfInvoice.getCrmId());
        }
        bizBaseSelfInvoice1.setStatus(ACTIVATED);
        List<BizBaseSelfInvoice> list = bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceList(bizBaseSelfInvoice1);
        if (list != null && list.size() > 0) {
            return ResultDTO.fail("The self invocie ID is already exsits.");
        }
        bizBaseSelfInvoice.setCreateBy(getLoginName());
        bizBaseSelfInvoice.setCreateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSelfInvoiceService.insertBizBaseSelfInvoice(bizBaseSelfInvoice),true);
    }

    /**
     * 修改BizBaseSelfInvoice
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:edit")
    @OperLog(title = "BizBaseSelfInvoice", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updateStatus/{id}")
    public ResultDTO<Integer> edit(@PathVariable("id") Long id)
    {
        BizBaseSelfInvoice bizBaseSelfInvoice = bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceById(id);
        if (bizBaseSelfInvoice.getStatus().equals(ACTIVATED)) {
            bizBaseSelfInvoice.setStatus(DEACTIVATE);
        } else {
            bizBaseSelfInvoice.setStatus(ACTIVATED);
        }
        bizBaseSelfInvoice.setUpdateBy(getLoginName());
        bizBaseSelfInvoice.setUpdateTime(DateUtils.getNowDate());
        return toResultDTO(bizBaseSelfInvoiceService.updateBizBaseSelfInvoice(bizBaseSelfInvoice),true);
    }

    /**
     * 获取BizBaseSelfInvoice详细信息
     */
    @RequiresPermissions("masterData:bizBaseSelfInvoice:query")
    @GetMapping(value = "/copy/{id}")
    public ResultDTO<BizBaseSelfInvoice> copy(@PathVariable("id") Long id)
    {
        BizBaseSelfInvoice bizBaseSelfInvoice = bizBaseSelfInvoiceService.selectBizBaseSelfInvoiceById(id);
        bizBaseSelfInvoice.setId(null);
        return ResultDTO.success(bizBaseSelfInvoice);
    }


}
