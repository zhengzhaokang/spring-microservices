package com.microservices.otmp.bank.service.impl;

import java.util.List;
import java.util.ArrayList;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.otmp.bank.manager.IBankCommonResponseItemInfoManager;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.bank.service.IBankCommonResponseItemInfoService;

/**
 * Bank Common Response Item InfoService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Service
public class BankCommonResponseItemInfoServiceImpl implements IBankCommonResponseItemInfoService {
    private static final Logger log = LoggerFactory.getLogger(BankCommonResponseItemInfoServiceImpl.class);

    @Autowired
    private IBankCommonResponseItemInfoManager bankCommonResponseItemInfoManager;

    /**
     * 查询Bank Common Response Item Info
     *
     * @param id Bank Common Response Item Info主键
     * @return Bank Common Response Item InfoDTO
     */
    @Override
    public BankCommonResponseItemInfoDTO selectBankCommonResponseItemInfoById(Long id) {
        BankCommonResponseItemInfoDO bankCommonResponseItemInfoDO = bankCommonResponseItemInfoManager.selectBankCommonResponseItemInfoById(id);
        BankCommonResponseItemInfoDTO bankCommonResponseItemInfoDTO = new BankCommonResponseItemInfoDTO();
        BeanUtils.copyProperties(bankCommonResponseItemInfoDO, bankCommonResponseItemInfoDTO);
        return bankCommonResponseItemInfoDTO;
    }

    /**
     * 查询Bank Common Response Item Info列表
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return Bank Common Response Item InfoDTO
     */
    @Override
    public List<BankCommonResponseItemInfoDTO> selectBankCommonResponseItemInfoList(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {

        List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoDOS =
                bankCommonResponseItemInfoManager.selectBankCommonResponseItemInfoList(bankCommonResponseItemInfo);
        List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoList = new ArrayList<>(bankCommonResponseItemInfoDOS.size());
        BeanUtils.copyListProperties(bankCommonResponseItemInfoDOS, bankCommonResponseItemInfoList, BankCommonResponseItemInfoDTO.class);
        return bankCommonResponseItemInfoList;

    }

    /**
     * 新增Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return 结果
     */
    @Override
    public int insertBankCommonResponseItemInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        bankCommonResponseItemInfo.setCreateTime(DateUtils.getNowDate());
        BankCommonResponseItemInfoDO bankCommonResponseItemInfoDO = new BankCommonResponseItemInfoDO();
        BeanUtils.copyProperties(bankCommonResponseItemInfo, bankCommonResponseItemInfoDO);
        return bankCommonResponseItemInfoManager.insertBankCommonResponseItemInfo(bankCommonResponseItemInfoDO);
    }

    /**
     * 修改Bank Common Response Item Info
     *
     * @param bankCommonResponseItemInfo Bank Common Response Item Info
     * @return 结果
     */
    @Override
    public int updateBankCommonResponseItemInfo(BankCommonResponseItemInfoDTO bankCommonResponseItemInfo) {
        BankCommonResponseItemInfoDO bankCommonResponseItemInfoDO = new BankCommonResponseItemInfoDO();
        BeanUtils.copyProperties(bankCommonResponseItemInfo, bankCommonResponseItemInfoDO);
        return bankCommonResponseItemInfoManager.updateBankCommonResponseItemInfo(bankCommonResponseItemInfoDO);
    }

    /**
     * 批量删除Bank Common Response Item Info
     *
     * @param ids 需要删除的Bank Common Response Item Info主键
     * @return 结果
     */
    @Override
    public int deleteBankCommonResponseItemInfoByIds(Long[] ids) {
        return bankCommonResponseItemInfoManager.deleteBankCommonResponseItemInfoByIds(ids);
    }

    /**
     * 删除Bank Common Response Item Info信息
     *
     * @param id Bank Common Response Item Info主键
     * @return 结果
     */
    @Override
    public int deleteBankCommonResponseItemInfoById(Long id) {
        return bankCommonResponseItemInfoManager.deleteBankCommonResponseItemInfoById(id);
    }
}
