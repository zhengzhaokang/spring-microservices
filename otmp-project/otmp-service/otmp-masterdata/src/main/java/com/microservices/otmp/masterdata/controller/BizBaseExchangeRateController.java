package com.microservices.otmp.masterdata.controller;

import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BooleanStatusEnum;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.common.utils.poi.NewExcelUtil;
import com.microservices.otmp.masterdata.domain.BizBaseExchangeRate;
import com.microservices.otmp.masterdata.domain.dto.BizBaseExchangeRateDTO;
import com.microservices.otmp.masterdata.domain.dto.ExchangeRateDTO;
import com.microservices.otmp.masterdata.service.IBizBaseExchangeRateService;
import com.microservices.otmp.system.annotation.RequiresPermissions;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Exchange RateController
 *
 * @author lovefamily
 * @date 2022-04-09
 */
@RestController
@RequestMapping("/exchangeRate")
public class BizBaseExchangeRateController extends BaseController {
    @Autowired
    private IBizBaseExchangeRateService bizBaseExchangeRateService;

    /**
     * 查询Exchange Rate列表
     */
    /*@RequiresPermissions("masterdata:ExchangeRate:list")*/
    @GetMapping("/list")
    public TableDataInfo list(BizBaseExchangeRate bizBaseExchangeRate) {
        startPage();
        List<BizBaseExchangeRate> list = bizBaseExchangeRateService.selectBizBaseExchangeRateList(bizBaseExchangeRate);
        return getDataTable(list);
    }

    /**
     * 导出Exchange Rate列表
     */
    @RequiresPermissions("masterData:bizBaseExchangeRete:export")
    @OperLog(title = "Exchange Rate", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizBaseExchangeRate bizBaseExchangeRate) {
        List<BizBaseExchangeRate> list = bizBaseExchangeRateService.selectBizBaseExchangeRateList(bizBaseExchangeRate);
        NewExcelUtil<BizBaseExchangeRate> util = new NewExcelUtil<>(BizBaseExchangeRate.class);
        util.exportExcel(response, list, "ExchangeRate");
    }

    @PostMapping("/importExcel")
   /* @HasPermissions("masterData:ExchangeRate:import")*/
    @OperLog(title = "Exchange Rate", businessType = BusinessType.IMPORT)
    public ResultDTO<String> importExcel(MultipartFile file) throws Exception {
        ExcelUtil<BizBaseExchangeRate> util = new ExcelUtil<>(BizBaseExchangeRate.class);
        List<BizBaseExchangeRate> bizs = util.importExcel(file.getInputStream());
        String message = bizBaseExchangeRateService.importExcel(bizs, getLoginName());

        return ResultDTO.success(message);
    }

    /**
     * 获取Exchange Rate详细信息
     */
  /*  @RequiresPermissions("masterdata:ExchangeRate:query")*/
    @GetMapping(value = "/{id}")
    public ResultDTO<BizBaseExchangeRate> getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bizBaseExchangeRateService.selectBizBaseExchangeRateById(id));
    }

    /**
     *  因为实际会高频访问 ,需要存入redis
     * 新增Exchange Rate
     * 输入参数(ratedate，fromcurrency，tocurrency)。 输出参数(retevalue)
     */
//    @RequiresPermissions("masterdata:ExchangeRate:list")
    @GetMapping("/getExchangeRate")
    public ResultDTO<BizBaseExchangeRateDTO> getExchangeRateByRemote(@DateTimeFormat(pattern = "MM/dd/yyyy") @RequestParam("rateDate") Date rateDate, @RequestParam("fromCurrency") String fromCurrency, @RequestParam("toCurrency") String toCurrency) throws ParseException {

        return ResultDTO.success(bizBaseExchangeRateService.getExchangeRateByRemote(rateDate, fromCurrency, toCurrency));
    }

    /**
     * 批量获取汇率
     * @param exchangeRateDTOList
     * @return
     * @throws ParseException
     */
    @PostMapping("/getExchangeRateList")
    public ResultDTO<List<BizBaseExchangeRateDTO>> getExchangeRateList(@RequestBody List<ExchangeRateDTO> exchangeRateDTOList) throws ParseException {

        return ResultDTO.success(bizBaseExchangeRateService.getExchangeRateByRemoteList(exchangeRateDTOList));
    }

    /**
     * 新增Exchange Rate
     */
    @RequiresPermissions("masterData:bizBaseExchangeRete:add")
    @OperLog(title = "Exchange Rate", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO<Integer> add(@RequestBody BizBaseExchangeRate bizBaseExchangeRate) {
        BizBaseExchangeRate exchangeRate = new BizBaseExchangeRate();
        exchangeRate.setCurrencyCode(bizBaseExchangeRate.getCurrencyCode());
        exchangeRate.setRateValue(bizBaseExchangeRate.getRateValue());
        exchangeRate.setRateDate(bizBaseExchangeRate.getRateDate());
        exchangeRate.setExchangeRateType(bizBaseExchangeRate.getExchangeRateType());
        exchangeRate.setStatus(BooleanStatusEnum.TRUE_Y.getCode());
        List<BizBaseExchangeRate> list = bizBaseExchangeRateService.selectBizBaseExchangeRateList(exchangeRate);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultDTO.fail("Record exists already");
        }
        return toResultDTO(bizBaseExchangeRateService.insertBizBaseExchangeRate(bizBaseExchangeRate), true);
    }

    /**
     * 修改Exchange Rate
     */
    @RequiresPermissions("masterData:bizBaseExchangeRete:edit")
    @OperLog(title = "Exchange Rate", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO<Integer> edit(@RequestBody BizBaseExchangeRate bizBaseExchangeRate) {
        return toResultDTO(bizBaseExchangeRateService.updateBizBaseExchangeRate(bizBaseExchangeRate), true);
    }

    /**
     * 删除Exchange Rate
     */
    @RequiresPermissions("masterData:bizBaseExchangeRete:removee")
    @OperLog(title = "Exchange Rate", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO<Integer> remove(@PathVariable Long[] ids) {
        return toResultDTO(bizBaseExchangeRateService.deleteBizBaseExchangeRateByIds(ids), true);
    }

    /**
     * 查询Exchange Rate列表
     */
   /* @RequiresPermissions("masterdata:ExchangeRate:list")*/
    @GetMapping("/exchangeRate")
    public ResultDTO<BizBaseExchangeRate> exchangeRate(String transactionCurrency, String targetCurrency) throws ParseException {
        BizBaseExchangeRate exchangeRates = bizBaseExchangeRateService.exchangeRates(transactionCurrency, targetCurrency);
        return ResultDTO.success(exchangeRates);
    }
}
