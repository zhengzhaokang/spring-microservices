package com.microservices.otmp.bank.mapper;

import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankTransferInvoiceResponseInfoDO;

import java.util.List;


/**
 * 银行处理后返回发票状态信息Mapper接口
 *
 * @author lovefamily
 * @date 2023-10-09
 */
public interface BankTransferInvoiceResponseInfoMapper {
    /**
     * 查询银行处理后返回发票状态信息
     *
     * @param id 银行处理后返回发票状态信息主键
     * @return 银行处理后返回发票状态信息
     */
    public BankTransferInvoiceResponseInfoDO selectBankTransferInvoiceResponseInfoById(Long id);

    /**
     * 查询银行处理后返回发票状态信息列表
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 银行处理后返回发票状态信息集合
     */
    public List<BankTransferInvoiceResponseInfoDO> selectBankTransferInvoiceResponseInfoList(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo);

    /**
     * 新增银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    public int insertBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfo);

    /**
     * 修改银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    public int updateBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfo);

    /**
     * 删除银行处理后返回发票状态信息
     *
     * @param id 银行处理后返回发票状态信息主键
     * @return 结果
     */
    public int deleteBankTransferInvoiceResponseInfoById(Long id);

    /**
     * 批量删除银行处理后返回发票状态信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBankTransferInvoiceResponseInfoByIds(Long[] ids);
}
