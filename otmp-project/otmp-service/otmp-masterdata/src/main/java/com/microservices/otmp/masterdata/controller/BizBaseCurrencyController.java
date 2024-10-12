package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.CommonUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseCurrency;
import com.microservices.otmp.masterdata.domain.BizBaseDropDownList;
import com.microservices.otmp.masterdata.domain.BizBaseRegionMarket;
import com.microservices.otmp.masterdata.domain.dto.BizBaseCurrencyDTO;
import com.microservices.otmp.masterdata.service.IBizBaseCurrencyService;
import com.microservices.otmp.masterdata.service.IBizBaseRegionMarketService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * BizBasePaymentIntegrationModeController
 *
 * @author lovefamily
 * @date 2022-04-09
 */
@RestController
@RequestMapping("/bizBaseCurrency")
public class BizBaseCurrencyController extends BaseController {

        @Autowired
        private IBizBaseCurrencyService bizBaseCurrencyService;

        @Autowired
        private IBizBaseRegionMarketService bizBaseRegionMarketService;

        /**
         * 查询bu列表
         */
        @RequiresPermissions("masterData:currency:list")
        @GetMapping("/list")
        public TableDataInfo list(BizBaseCurrency bizBaseCurrency)
        {
            startPage();
            List<BizBaseCurrency> list = bizBaseCurrencyService.selectBizBaseCurrencyList(bizBaseCurrency);
            return getDataTable(list);
        }

    /**
     * 导出bu列表
     */
    @RequiresPermissions("masterData:bizBaseCurrency:export")
    @OperLog(title = "bu", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseCurrency bizBaseCurrency)
    {
        List<BizBaseCurrency> list = bizBaseCurrencyService.selectBizBaseCurrencyList(bizBaseCurrency);
        NewExcelUtil<BizBaseCurrency> util = new NewExcelUtil<>(BizBaseCurrency.class);
        util.exportExcel(response, list, "Currency");
    }

    @PostMapping("/importExcel")
    @HasPermissions("masterData:currency:import")
    @OperLog(title = "bu", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseCurrency> util = new ExcelUtil<>(BizBaseCurrency.class);
        List <BizBaseCurrency> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseCurrencyService.importExcel(bizs,getLoginName());

        return ResultDTO.success(message);
    }

        /**
         * 获取bu详细信息
         */
        @RequiresPermissions("masterData:currency:query")
        @GetMapping(value = "/{id}")
        public ResultDTO<BizBaseCurrency> getInfo(@PathVariable("id") String id)
        {
            return ResultDTO.success(bizBaseCurrencyService.selectBizBaseCurrencyById(id));
        }

        /**
         * 新增bu
         */
        @RequiresPermissions("masterData:bizBaseCurrency:add")
        @OperLog(title = "bu", businessType = BusinessType.INSERT)
        @PostMapping
        public ResultDTO<Integer> add(@RequestBody BizBaseCurrency bizBaseCurrency)
        {
            //唯一性校验
            BizBaseCurrency currency = new BizBaseCurrency();
            currency.setCurrencyCode(bizBaseCurrency.getCurrencyCode());
            currency.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
            BizBaseCurrencyDTO dto = bizBaseCurrencyService.selectBizBaseCurrencyByCode(bizBaseCurrency.getCurrencyCode());
            if(dto != null) {
                return ResultDTO.fail("Record exists already");
            }
            bizBaseCurrency.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
            bizBaseCurrency.setCreateBy(getLoginName());
            return toResultDTO(bizBaseCurrencyService.insertBizBaseCurrency(bizBaseCurrency),true);
        }

        /**
         * 修改bu
         */
        @RequiresPermissions("masterData:bizBaseCurrency:edit")
        @OperLog(title = "bu", businessType = BusinessType.UPDATE)
        @PutMapping
        public ResultDTO<Integer> edit(@RequestBody BizBaseCurrency bizBaseCurrency)
        {
            bizBaseCurrency.setUpdateBy(getLoginName());
            return toResultDTO(bizBaseCurrencyService.updateBizBaseCurrency(bizBaseCurrency),true);
        }

        /**
         * 删除bu
         */
        @RequiresPermissions("masterData:bizBaseCurrency:remove")
        @OperLog(title = "bu", businessType = BusinessType.DELETE)
        @DeleteMapping("/{ids}")
        public ResultDTO<Integer> remove(@PathVariable String[] ids)
        {
            return toResultDTO(bizBaseCurrencyService.deleteBizBaseCurrencyByIds(ids),true);
        }


    /**
     * 1) 系统基于sales org自动带出sales org 对应的L currency （biz_base_sales_org里的local_currency_code）。
     * 如果有多个sales org带不出唯一的currency， 则清空pool currency,由用户手工从currency master data （biz_base_currency表的currency_code字段）里选择
     * 2) 如果没有维护sales org, 则基于pool上的market/region信息，去biz_base_region_market (biz_base_region_market表里找region_market_currency字段）里找到并自动带出market对应的pool currency。
     * 3） 虽然有上述1.2逻辑，系统可以自动带出pool currency的值，系统也可允许用户从currency master data（biz_base_currency表的currency_code字段）更换为任意一种其他currency作为pool currency。
    */
    @OperLog(title = "获取dropDownList", businessType = BusinessType.QUERY)
    @GetMapping("/getPoolCurrency")
    public TableDataInfo getPoolCurrencyBySalesOrg(String salesOrgCode,String regionMarketCode)
    {
        if(StringUtils.isNotEmpty(salesOrgCode)){
            List<BizBaseDropDownList> poolCurrency = bizBaseCurrencyService.getPoolCurrencyBySalesOrg(salesOrgCode);
            if (poolCurrency.isEmpty()) {
                throw new OtmpException(DefaultErrorMessage.getErrorMessage(DefaultErrorMessage.POOL_CURRENCY_NOT_FOUND, ""), DefaultErrorMessage.POOL_CURRENCY_NOT_FOUND.intValue());
            } else if(poolCurrency.size()==1) {
                return getDataTable(poolCurrency);
            } else {
                List<BizBaseDropDownList> allCurrency = bizBaseCurrencyService.getAllCurrency();
                return getDataTable(allCurrency);
            }
        }else if(CommonUtils.stringIsNotEmpty(regionMarketCode)){
            BizBaseRegionMarket bbr = new BizBaseRegionMarket();
            bbr.setRegionMarketCode(regionMarketCode);
            List<BizBaseDropDownList> regionMarkets = bizBaseRegionMarketService.getCurrencyForPool(bbr);
            return getDataTable(regionMarkets);
        }else{
            List<BizBaseDropDownList> allCurrency = bizBaseCurrencyService.getAllCurrency();
            return getDataTable(allCurrency);
        }
    }

    /**
     * 获取bu详细信息
     *
     * @RequiresPermissions("system:currency:query")
     * */
    @GetMapping(value = "getByCode/{currencyCode}")
    public ResultDTO<BizBaseCurrencyDTO> getByCode(@PathVariable("currencyCode") String currencyCode)
    {
        return ResultDTO.success(bizBaseCurrencyService.selectBizBaseCurrencyByCode(currencyCode));
    }

   /* @RequiresPermissions("masterData:currency:query")*/
    @PostMapping(value = "getListByCodeList")
    ResultDTO<List<BizBaseCurrencyDTO>> getListByCodeList(@RequestBody List<String> currencyCodeList) {
        return ResultDTO.success(bizBaseCurrencyService.selectBizBaseCurrencyByCodeList(currencyCodeList));
    }

    @RequiresPermissions("masterData:currency:query")
    @PostMapping(value = "/getList")
    ResultDTO<List<BizBaseCurrency>> getList(@RequestBody BizBaseCurrencyDTO bizBaseCurrency) {
        BizBaseCurrency currency=new BizBaseCurrency();
        BeanUtils.copyProperties(bizBaseCurrency,currency);
        return ResultDTO.success(bizBaseCurrencyService.selectBizBaseCurrencyList(currency));
    }
}
