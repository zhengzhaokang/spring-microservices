package com.microservices.otmp.bank.controller;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.service.IBankCommonResponseItemInfoService;
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
 * Bank Common Response Item InfoController
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@RestController
@RequestMapping("/bankCommonResponseItemInfo")
public class BankCommonResponseItemInfoController extends BaseController {
    @Autowired
    private IBankCommonResponseItemInfoService bankCommonResponseItemInfoService;

    /**
     * 查询Bank Common Response Item Info列表
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        startPage();
        List<BankCommonResponseItemInfoDTO> list = bankCommonResponseItemInfoService.selectBankCommonResponseItemInfoList(bankCommonResponseItemInfo);
        return getDataTable(list);
    }

    /**
     * 导出Bank Common Response Item Info列表
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:export")
    @Log(title = "Bank Common Response Item Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        List<BankCommonResponseItemInfoDTO> list = bankCommonResponseItemInfoService.selectBankCommonResponseItemInfoList(bankCommonResponseItemInfo);
        ExcelUtil<BankCommonResponseItemInfoDTO> util = new ExcelUtil<BankCommonResponseItemInfoDTO>(BankCommonResponseItemInfoDTO.class);
        util.exportExcel(response, list, "Bank Common Response Item Info数据");
    }

    /**
     * 获取Bank Common Response Item Info详细信息
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:query")
    @GetMapping(value = "/{id}")
    public ResultDTO getInfo(@PathVariable("id") Long id) {
        return ResultDTO.success(bankCommonResponseItemInfoService.selectBankCommonResponseItemInfoById(id));
    }

    /**
     * 新增Bank Common Response Item Info
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:add")
    @Log(title = "Bank Common Response Item Info", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        return toResultDTO(bankCommonResponseItemInfoService.insertBankCommonResponseItemInfo(bankCommonResponseItemInfo), true);
    }

    /**
     * 修改Bank Common Response Item Info
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:edit")
    @Log(title = "Bank Common Response Item Info", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        return toResultDTO(bankCommonResponseItemInfoService.updateBankCommonResponseItemInfo(bankCommonResponseItemInfo), true);
    }

    /**
     * 删除Bank Common Response Item Info
     */
    @HasPermissions("bankCommonResponseItemInfo:bankCommonResponseItemInfo:remove")
    @Log(title = "Bank Common Response Item Info", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public ResultDTO remove(@PathVariable Long[] ids) {
        return toResultDTO(bankCommonResponseItemInfoService.deleteBankCommonResponseItemInfoByIds(ids), true);
    }
}
