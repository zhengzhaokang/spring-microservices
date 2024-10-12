package com.microservices.otmp.bank.service.impl;

import com.microservices.otmp.bank.domain.dto.BankTransferInvoiceResponseInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankTransferInvoiceResponseInfoDO;
import com.microservices.otmp.bank.manager.IBankTransferInvoiceResponseInfoManager;
import com.microservices.otmp.bank.service.IBankTransferInvoiceResponseInfoService;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 银行处理后返回发票状态信息Service业务层处理
 *
 * @author lovefamily
 * @date 2023-10-09
 */
@Service
public class BankTransferInvoiceResponseInfoServiceImpl implements IBankTransferInvoiceResponseInfoService {
    private static final Logger log = LoggerFactory.getLogger(BankTransferInvoiceResponseInfoServiceImpl.class);

    @Autowired
    private IBankTransferInvoiceResponseInfoManager bankTransferInvoiceResponseInfoManager;

    /**
     * 查询银行处理后返回发票状态信息
     *
     * @param id 银行处理后返回发票状态信息主键
     * @return 银行处理后返回发票状态信息DTO
     */
    @Override
    public BankTransferInvoiceResponseInfoDTO selectBankTransferInvoiceResponseInfoById(Long id) {
        BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfoDO = bankTransferInvoiceResponseInfoManager.selectBankTransferInvoiceResponseInfoById(id);
        BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfoDTO = new BankTransferInvoiceResponseInfoDTO();
        BeanUtils.copyProperties(bankTransferInvoiceResponseInfoDO, bankTransferInvoiceResponseInfoDTO);
        return bankTransferInvoiceResponseInfoDTO;
    }

    /**
     * 查询银行处理后返回发票状态信息列表
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 银行处理后返回发票状态信息DTO
     */
    @Override
    public List<BankTransferInvoiceResponseInfoDTO> selectBankTransferInvoiceResponseInfoList(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {

        List<BankTransferInvoiceResponseInfoDO> bankTransferInvoiceResponseInfoDOS =
                bankTransferInvoiceResponseInfoManager.selectBankTransferInvoiceResponseInfoList(bankTransferInvoiceResponseInfo);
        List<BankTransferInvoiceResponseInfoDTO> bankTransferInvoiceResponseInfoList = new ArrayList<>(bankTransferInvoiceResponseInfoDOS.size());
        BeanUtils.copyListProperties(bankTransferInvoiceResponseInfoDOS, bankTransferInvoiceResponseInfoList, BankTransferInvoiceResponseInfoDTO.class);
        return bankTransferInvoiceResponseInfoList;

    }

    /**
     * 新增银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    @Override
    public int insertBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        bankTransferInvoiceResponseInfo.setCreateTime(DateUtils.getNowDate());
        BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfoDO = new BankTransferInvoiceResponseInfoDO();
        BeanUtils.copyProperties(bankTransferInvoiceResponseInfo, bankTransferInvoiceResponseInfoDO);
        return bankTransferInvoiceResponseInfoManager.insertBankTransferInvoiceResponseInfo(bankTransferInvoiceResponseInfoDO);
    }

    /**
     * 修改银行处理后返回发票状态信息
     *
     * @param bankTransferInvoiceResponseInfo 银行处理后返回发票状态信息
     * @return 结果
     */
    @Override
    public int updateBankTransferInvoiceResponseInfo(BankTransferInvoiceResponseInfoDTO bankTransferInvoiceResponseInfo) {
        bankTransferInvoiceResponseInfo.setUpdateTime(DateUtils.getNowDate());
        BankTransferInvoiceResponseInfoDO bankTransferInvoiceResponseInfoDO = new BankTransferInvoiceResponseInfoDO();
        BeanUtils.copyProperties(bankTransferInvoiceResponseInfo, bankTransferInvoiceResponseInfoDO);
        return bankTransferInvoiceResponseInfoManager.updateBankTransferInvoiceResponseInfo(bankTransferInvoiceResponseInfoDO);
    }

    /**
     * 批量删除银行处理后返回发票状态信息
     *
     * @param ids 需要删除的银行处理后返回发票状态信息主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferInvoiceResponseInfoByIds(Long[] ids) {
        return bankTransferInvoiceResponseInfoManager.deleteBankTransferInvoiceResponseInfoByIds(ids);
    }

    /**
     * 删除银行处理后返回发票状态信息信息
     *
     * @param id 银行处理后返回发票状态信息主键
     * @return 结果
     */
    @Override
    public int deleteBankTransferInvoiceResponseInfoById(Long id) {
        return bankTransferInvoiceResponseInfoManager.deleteBankTransferInvoiceResponseInfoById(id);
    }
}
