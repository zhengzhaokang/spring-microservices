package com.microservices.otmp.bank.controller;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;
import com.microservices.otmp.bank.service.IBankCommonResponseHeaderInfoService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Bank Common Response Header InfoController
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/bankCommonResponseHeaderInfo")
public class BankCommonResponseHeaderInfoController extends BaseController {
    @Autowired
    private IBankCommonResponseHeaderInfoService bankCommonResponseHeaderInfoService;

    /**
     * 查询Bank Common Response Header Info列表
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        startPage();
        List<BankCommonResponseHeaderInfoDTO> list = bankCommonResponseHeaderInfoService.selectBankCommonResponseHeaderInfoList(bankCommonResponseHeaderInfo);
        return getDataTable(list);
    }

    /**
     * 导出Bank Common Response Header Info列表
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:export")
    @Log(title = "Bank Common Response Header Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        List<BankCommonResponseHeaderInfoDTO> list = bankCommonResponseHeaderInfoService.selectBankCommonResponseHeaderInfoList(bankCommonResponseHeaderInfo);
        ExcelUtil<BankCommonResponseHeaderInfoDTO> util = new ExcelUtil<BankCommonResponseHeaderInfoDTO>(BankCommonResponseHeaderInfoDTO.class);
        util.exportExcel(response, list, "Bank Common Response Header Info数据");
    }

    /**
     * 获取Bank Common Response Header Info详细信息
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bankCommonResponseHeaderInfoService.selectBankCommonResponseHeaderInfoById(id));
    }

    /**
     * 新增Bank Common Response Header Info
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:add")
    @Log(title = "Bank Common Response Header Info", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        return toResultDTO(bankCommonResponseHeaderInfoService.insertBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfo), true);
    }

    /**
     * 修改Bank Common Response Header Info
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:edit")
    @Log(title = "Bank Common Response Header Info", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        return toResultDTO(bankCommonResponseHeaderInfoService.updateBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfo), true);
    }

    /**
     * 删除Bank Common Response Header Info
     */
    @HasPermissions("bankCommonResponseHeaderInfo:bankCommonResponseHeaderInfo:remove")
    @Log(title = "Bank Common Response Header Info", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(bankCommonResponseHeaderInfoService.deleteBankCommonResponseHeaderInfoByIds(ids), true);
    }
}
