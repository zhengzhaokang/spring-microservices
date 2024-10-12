package com.microservices.otmp.bank.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.microservices.otmp.common.annotation.Log;
import com.microservices.otmp.common.auth.annotation.HasPermissions;
import com.microservices.otmp.common.core.controller.BaseController;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.enums.BusinessType;
import com.microservices.otmp.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.otmp.bank.domain.dto.BankTransferMetadataInfoDTO;
import com.microservices.otmp.bank.service.IBankTransferMetadataInfoService;


/**
 * Bank Transfer Metadata InfoController
 * 
 * @author lovefamily
 * @date 2023-08-29
 */
@RestController
@RequestMapping("/bankTransferMetadataInfo")
public class BankTransferMetadataInfoController extends BaseController
{
    @Autowired
    private IBankTransferMetadataInfoService bankTransferMetadataInfoService;

    /**
     * 查询Bank Transfer Metadata Info列表
     */
    @HasPermissions("bank:bankTransferMetadataInfo:list")
    @GetMapping("/list")
    public TableDataInfo list(BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        startPage();
        List<BankTransferMetadataInfoDTO> list = bankTransferMetadataInfoService.selectBankTransferMetadataInfoList(bankTransferMetadataInfo);
        return getDataTable(list);
    }

    /**
     * 导出Bank Transfer Metadata Info列表
     */
    @HasPermissions("bank:bankTransferMetadataInfo:export")
    @Log(title = "Bank Transfer Metadata Info", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        List<BankTransferMetadataInfoDTO> list = bankTransferMetadataInfoService.selectBankTransferMetadataInfoList(bankTransferMetadataInfo);
        ExcelUtil<BankTransferMetadataInfoDTO> util = new ExcelUtil<BankTransferMetadataInfoDTO>(BankTransferMetadataInfoDTO.class);
        util.exportExcel(response, list, "Bank Transfer Metadata Info数据");
    }

    /**
     * 获取Bank Transfer Metadata Info详细信息
     */
    @HasPermissions("bank:bankTransferMetadataInfo:query")
    @GetMapping(value = "/{metadataInfoId}")
    public ResultDTO getInfo(@PathVariable("metadataInfoId") Long metadataInfoId)
    {
        return ResultDTO.success(bankTransferMetadataInfoService.selectBankTransferMetadataInfoByMetadataInfoId(metadataInfoId));
    }

    /**
     * 新增Bank Transfer Metadata Info
     */
    @HasPermissions("bank:bankTransferMetadataInfo:add")
    @Log(title = "Bank Transfer Metadata Info", businessType = BusinessType.INSERT)
    @PostMapping
    public ResultDTO add(@RequestBody BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        return toResultDTO(bankTransferMetadataInfoService.insertBankTransferMetadataInfo(bankTransferMetadataInfo),true);
    }

    /**
     * 修改Bank Transfer Metadata Info
     */
    @HasPermissions("bank:bankTransferMetadataInfo:edit")
    @Log(title = "Bank Transfer Metadata Info", businessType = BusinessType.UPDATE)
    @PutMapping
    public ResultDTO edit(@RequestBody BankTransferMetadataInfoDTO bankTransferMetadataInfo)
    {
        return toResultDTO(bankTransferMetadataInfoService.updateBankTransferMetadataInfo(bankTransferMetadataInfo),true);
    }

    /**
     * 删除Bank Transfer Metadata Info
     */
    @HasPermissions("bank:bankTransferMetadataInfo:remove")
    @Log(title = "Bank Transfer Metadata Info", businessType = BusinessType.DELETE)
	@DeleteMapping("/{metadataInfoIds}")
    public ResultDTO remove(@PathVariable Long[] metadataInfoIds)
    {
        return toResultDTO(bankTransferMetadataInfoService.deleteBankTransferMetadataInfoByMetadataInfoIds(metadataInfoIds),true);
    }
}
