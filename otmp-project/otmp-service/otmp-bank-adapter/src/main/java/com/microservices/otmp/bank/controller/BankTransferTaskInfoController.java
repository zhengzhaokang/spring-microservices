package com.microservices.otmp.bank.controller;

import com.microservices.otmp.bank.domain.dto.BankTransferTaskInfoDTO;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import com.microservices.otmp.bank.service.IBankTransferTaskInfoService;
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
 * Bank Transfer Task InfoController
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@RestController
@RequestMapping("/bankTransferTaskInfo")
public class BankTransferTaskInfoController extends BaseController {
    @Autowired
    private IBankTransferTaskInfoService bankTransferTaskInfoService;

    /**
     * 查询Bank Transfer Task Info列表
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BankTransferTaskInfoDTO bankTransferTaskInfo) {
        startPage();
        List<BankTransferTaskInfoDTO> list = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        return getDataTable(list);
    }

    /**
     * 导出Bank Transfer Task Info列表
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:export")
    @Log(title = "Bank Transfer Task Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankTransferTaskInfoDTO bankTransferTaskInfo) {
        List<BankTransferTaskInfoDTO> list = bankTransferTaskInfoService.selectBankTransferTaskInfoList(bankTransferTaskInfo);
        ExcelUtil<BankTransferTaskInfoDTO> util = new ExcelUtil<BankTransferTaskInfoDTO>(BankTransferTaskInfoDTO.class);
        util.exportExcel(response, list, "Bank Transfer Task Info数据");
    }

    /**
     * 获取Bank Transfer Task Info详细信息
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:query")
    @GetMapping(value = "/{bankTransferId}")
    public ResultDTO getInfo(@PathVariable("bankTransferId") Long bankTransferId) {
        return ResultDTO.success(bankTransferTaskInfoService.selectBankTransferTaskInfoByBankTransferId(bankTransferId));
    }

    /**
     * 新增Bank Transfer Task Info
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:add")
    @Log(title = "Bank Transfer Task Info", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BankTransferTaskInfoDTO bankTransferTaskInfo) {
        return toResultDTO(bankTransferTaskInfoService.insertBankTransferTaskInfo(bankTransferTaskInfo), true);
    }

    /**
     * 修改Bank Transfer Task Info
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:edit")
    @Log(title = "Bank Transfer Task Info", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BankTransferTaskInfoDTO bankTransferTaskInfo) {
        return toResultDTO(bankTransferTaskInfoService.updateBankTransferTaskInfo(bankTransferTaskInfo), true);
    }

    /**
     * 删除Bank Transfer Task Info
     */
    @HasPermissions("bankTransferTaskInfo:bankTransferTaskInfo:remove")
    @Log(title = "Bank Transfer Task Info", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bankTransferIds}")
    public ResultDTO remove(@PathVariable Long[] bankTransferIds) {
        return toResultDTO(bankTransferTaskInfoService.deleteBankTransferTaskInfoByBankTransferIds(bankTransferIds), true);
    }
}
