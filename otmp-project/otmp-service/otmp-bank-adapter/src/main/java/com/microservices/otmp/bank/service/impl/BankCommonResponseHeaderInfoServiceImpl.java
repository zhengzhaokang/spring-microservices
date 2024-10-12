package com.microservices.otmp.bank.service.impl;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;
import com.microservices.otmp.bank.domain.dto.BankCommonResponseItemInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseHeaderInfoDO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.bank.manager.IBankCommonResponseHeaderInfoManager;
import com.microservices.otmp.bank.service.IBankCommonResponseHeaderInfoService;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank Common Response Header InfoService业务层处理
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Service
public class BankCommonResponseHeaderInfoServiceImpl implements IBankCommonResponseHeaderInfoService {
    private static final Logger log = LoggerFactory.getLogger(BankCommonResponseHeaderInfoServiceImpl.class);

    @Autowired
    private IBankCommonResponseHeaderInfoManager bankCommonResponseHeaderInfoManager;


    /**
     * 查询Bank Common Response Header Info
     *
     * @param id Bank Common Response Header Info主键
     * @return Bank Common Response Header InfoDTO
     */
    @Override
    public BankCommonResponseHeaderInfoDTO selectBankCommonResponseHeaderInfoById(Long id) {
        BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfoDO = bankCommonResponseHeaderInfoManager.selectBankCommonResponseHeaderInfoById(id);
        BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfoDTO = new BankCommonResponseHeaderInfoDTO();
        BeanUtils.copyProperties(bankCommonResponseHeaderInfoDO, bankCommonResponseHeaderInfoDTO);
        return bankCommonResponseHeaderInfoDTO;
    }

    /**
     * 查询Bank Common Response Header Info列表
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return Bank Common Response Header InfoDTO
     */
    @Override
    public List<BankCommonResponseHeaderInfoDTO> selectBankCommonResponseHeaderInfoList(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {

        List<BankCommonResponseHeaderInfoDO> bankCommonResponseHeaderInfoDOS =
                bankCommonResponseHeaderInfoManager.selectBankCommonResponseHeaderInfoList(bankCommonResponseHeaderInfo);
        List<BankCommonResponseHeaderInfoDTO> bankCommonResponseHeaderInfoList = new ArrayList<>(bankCommonResponseHeaderInfoDOS.size());
        BeanUtils.copyListProperties(bankCommonResponseHeaderInfoDOS, bankCommonResponseHeaderInfoList, BankCommonResponseHeaderInfoDTO.class);
        return bankCommonResponseHeaderInfoList;

    }

    /**
     * 新增Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        bankCommonResponseHeaderInfo.setCreateTime(DateUtils.getNowDate());
        BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfoDO = new BankCommonResponseHeaderInfoDO();
        BeanUtils.copyProperties(bankCommonResponseHeaderInfo, bankCommonResponseHeaderInfoDO);
        int rows = bankCommonResponseHeaderInfoManager.insertBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfoDO);
        insertBankCommonResponseItemInfo(bankCommonResponseHeaderInfo);
        return rows;
    }

    /**
     * 修改Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    @Transactional
    @Override
    public int updateBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        bankCommonResponseHeaderInfo.setUpdateTime(DateUtils.getNowDate());
        bankCommonResponseHeaderInfoManager.deleteBankCommonResponseItemInfoByHeaderId(bankCommonResponseHeaderInfo.getId());
        insertBankCommonResponseItemInfo(bankCommonResponseHeaderInfo);
        BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfoDO = new BankCommonResponseHeaderInfoDO();
        BeanUtils.copyProperties(bankCommonResponseHeaderInfo, bankCommonResponseHeaderInfoDO);
        return bankCommonResponseHeaderInfoManager.updateBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfoDO);
    }

    /**
     * 批量删除Bank Common Response Header Info
     *
     * @param ids 需要删除的Bank Common Response Header Info主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBankCommonResponseHeaderInfoByIds(Long[] ids) {
        bankCommonResponseHeaderInfoManager.deleteBankCommonResponseItemInfoByHeaderIds(ids);
        return bankCommonResponseHeaderInfoManager.deleteBankCommonResponseHeaderInfoByIds(ids);
    }

    /**
     * 删除Bank Common Response Header Info信息
     *
     * @param id Bank Common Response Header Info主键
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteBankCommonResponseHeaderInfoById(Long id) {
        bankCommonResponseHeaderInfoManager.deleteBankCommonResponseItemInfoByHeaderId(id);
        return bankCommonResponseHeaderInfoManager.deleteBankCommonResponseHeaderInfoById(id);
    }

    /**
     * 新增Bank Common Response Item Info信息
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info对象
     */
    public void insertBankCommonResponseItemInfo(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        List<BankCommonResponseItemInfoDTO> bankCommonResponseItemInfoList = bankCommonResponseHeaderInfo.getBankCommonResponseItemInfoList();
        Long id = bankCommonResponseHeaderInfo.getId();
        if (StringUtils.isNotNull(bankCommonResponseItemInfoList)) {
            List<BankCommonResponseItemInfoDTO> list = new ArrayList<BankCommonResponseItemInfoDTO>();
            for (BankCommonResponseItemInfoDTO bankCommonResponseItemInfo : bankCommonResponseItemInfoList) {
                bankCommonResponseItemInfo.setHeaderId(id);
                list.add(bankCommonResponseItemInfo);
            }
            if (list.size() > 0) {
                List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoDoList = new ArrayList<>(list.size());
                BeanUtils.copyListProperties(list, bankCommonResponseItemInfoDoList, BankCommonResponseItemInfoDO.class);
                bankCommonResponseHeaderInfoManager.batchBankCommonResponseItemInfo(bankCommonResponseItemInfoDoList);
            }
        }
    }
}
