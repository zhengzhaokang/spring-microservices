package com.microservices.otmp.bank.service;

import java.util.List;
import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;

/**
 * 银行处理后返回发票状态信息Service接口
 * 
 * @author lovefamily
 * @date 2023-10-09
 */
public interface IBankTransferInvoiceResponseInfoService
{
    /**
     * 查询银行处理后返回发票状态信息
     * 
     * @param id 银行处理后返回发票状态信息主键
     * @return 银行处理后返回发票状态信息DTO
     */
    public BankTransferInvoiceResponseInfoDTO selectBankTransferInvoiceResponseInfoById(Long id);

    /**
     * 查询银行处理后返回发票状态信息列表
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 银行处理后返回发票状态信息DTO集合
     */
    public List<BankTransferInvoiceResponseInfoDTO> selectBankTransferInvoiceResponseInfoList(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo);

    /**
     * 新增银行处理后返回发票状态信息
     * 
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    public int insertBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo);

    /**
     * 修改银行处理后返回发票状态信息
     * 
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    public int updateBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo);

    /**
     * 批量删除银行处理后返回发票状态信息
     * 
     * @param ids 需要删除的银行处理后返回发票状态信息主键集合
     * @return 结果
     */
    public int deleteBankTransferInvoiceResponseInfoByIds(Long[] ids);

    /**
     * 删除银行处理后返回发票状态信息信息
     * 
     * @param id 银行处理后返回发票状态信息主键
     * @return 结果
     */
    public int deleteBankTransferInvoiceResponseInfoById(Long id);
}
