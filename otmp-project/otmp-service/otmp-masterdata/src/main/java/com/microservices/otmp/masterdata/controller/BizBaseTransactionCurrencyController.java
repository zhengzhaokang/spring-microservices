package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseTransactionCurrency;
import com.microservices.otmp.masterdata.service.IBizBaseTransactionCurrencyService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Transaction CurrencyController
 * 
 * @author lovefamily
 * @date 2022-04-09
 */
@RestController
@RequestMapping("/transactionCurrency")
public class BizBaseTransactionCurrencyController extends BaseController
{
    @Autowired
    private IBizBaseTransactionCurrencyService bizBaseTransactionCurrencyService;

    /**
     * 查询Transaction Currency列表
     */
    @RequiresPermissions("masterdata:TransactionCurrency:list")
    @GetMapping("/list")
    public TableDataInfo list(BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        startPage();
        List<BizBaseTransactionCurrency> list = bizBaseTransactionCurrencyService.selectBizBaseTransactionCurrencyList(bizBaseTransactionCurrency);
        return getDataTable(list);
    }

    /**
     * 导出Transaction Currency列表
     */
    @RequiresPermissions("masterdata:TransactionCurrency:export")
    @OperLog(title = "Transaction Currency", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        List<BizBaseTransactionCurrency> list = bizBaseTransactionCurrencyService.selectBizBaseTransactionCurrencyList(bizBaseTransactionCurrency);
        NewExcelUtil<BizBaseTransactionCurrency> util = new NewExcelUtil<>(BizBaseTransactionCurrency.class);
        util.exportExcel(response, list, "TransactionCurrency");
    }

    /**
     * 获取Transaction Currency详细信息
     */
    @RequiresPermissions("masterdata:TransactionCurrency:query")
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseTransactionCurrency> getInfo(@PathVariable("id") Long id)
    {
        return ResultDTO.success(bizBaseTransactionCurrencyService.selectBizBaseTransactionCurrencyById(id));
    }

    /**
     * 新增Transaction Currency
     */
    @RequiresPermissions("masterdata:TransactionCurrency:add")
    @OperLog(title = "Transaction Currency", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        return toResultDTO(bizBaseTransactionCurrencyService.insertBizBaseTransactionCurrency(bizBaseTransactionCurrency),true);
    }

    /**
     * 修改Transaction Currency
     */
    @RequiresPermissions("masterdata:TransactionCurrency:edit")
    @OperLog(title = "Transaction Currency", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseTransactionCurrency bizBaseTransactionCurrency)
    {
        return toResultDTO(bizBaseTransactionCurrencyService.updateBizBaseTransactionCurrency(bizBaseTransactionCurrency),true);
    }

    /**
     * 删除Transaction Currency
     */
    @RequiresPermissions("masterdata:TransactionCurrency:remove")
    @OperLog(title = "Transaction Currency", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids)
    {
        return toResultDTO(bizBaseTransactionCurrencyService.deleteBizBaseTransactionCurrencyByIds(ids),true);
    }
}
