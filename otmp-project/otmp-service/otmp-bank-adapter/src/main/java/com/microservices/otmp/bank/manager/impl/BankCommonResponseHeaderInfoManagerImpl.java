package com.microservices.otmp.bank.manager.impl;

import com.microservices.otmp.bank.domain.dto.BankCommonResponseHeaderInfoDTO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseHeaderInfoDO;
import com.microservices.otmp.bank.domain.entity.BankCommonResponseItemInfoDO;
import com.microservices.otmp.bank.manager.IBankCommonResponseHeaderInfoManager;
import com.microservices.otmp.bank.mapper.BankCommonResponseHeaderInfoMapper;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank Common Response Header InfoManager业务层处理
 *
 * @author lovefamily
 * @date 2023-10-10
 */
@Service
public class BankCommonResponseHeaderInfoManagerImpl implements IBankCommonResponseHeaderInfoManager {
    private static final Logger log = LoggerFactory.getLogger(BankCommonResponseHeaderInfoManagerImpl.class);

    @Autowired
    private BankCommonResponseHeaderInfoMapper bankCommonResponseHeaderInfoMapper;

    /**
     * 查询Bank Common Response Header Info
     *
     * @param id Bank Common Response Header Info主键
     * @return Bank Common Response Header InfoDO
     */
    @Override
    public BankCommonResponseHeaderInfoDO selectBankCommonResponseHeaderInfoById(Long id) {
        return bankCommonResponseHeaderInfoMapper.selectBankCommonResponseHeaderInfoById(id);
    }

    /**
     * 查询Bank Common Response Header Info列表
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return Bank Common Response Header InfoDO
     */
    @Override
    public List<BankCommonResponseHeaderInfoDO> selectBankCommonResponseHeaderInfoList(BankCommonResponseHeaderInfoDTO bankCommonResponseHeaderInfo) {
        return bankCommonResponseHeaderInfoMapper.selectBankCommonResponseHeaderInfoList(bankCommonResponseHeaderInfo);
    }

    /**
     * 新增Bank Common Response Header Info
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info
     * @return 结果
     */
    @Transactional
    @Override
    public int insertBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfo) {
        bankCommonResponseHeaderInfo.setCreateTime(DateUtils.getNowDate());
        int rows = bankCommonResponseHeaderInfoMapper.insertBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfo);
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
    public int updateBankCommonResponseHeaderInfo(BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfo) {
        bankCommonResponseHeaderInfo.setUpdateTime(DateUtils.getNowDate());
        bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseItemInfoByHeaderId(bankCommonResponseHeaderInfo.getId());
        insertBankCommonResponseItemInfo(bankCommonResponseHeaderInfo);
        return bankCommonResponseHeaderInfoMapper.updateBankCommonResponseHeaderInfo(bankCommonResponseHeaderInfo);
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
        bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseItemInfoByHeaderIds(ids);
        return bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseHeaderInfoByIds(ids);
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
        bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseItemInfoByHeaderId(id);
        return bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseHeaderInfoById(id);
    }


    /**
     * 新增Bank Common Response Item Info信息
     *
     * @param bankCommonResponseHeaderInfo Bank Common Response Header Info对象
     */
    public void insertBankCommonResponseItemInfo(BankCommonResponseHeaderInfoDO bankCommonResponseHeaderInfo) {
        List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoList = bankCommonResponseHeaderInfo.getBankCommonResponseItemInfoList();
        Long id = bankCommonResponseHeaderInfo.getId();
        if (StringUtils.isNotNull(bankCommonResponseItemInfoList)) {
            List<BankCommonResponseItemInfoDO> list = new ArrayList<BankCommonResponseItemInfoDO>();
            for (BankCommonResponseItemInfoDO bankCommonResponseItemInfo : bankCommonResponseItemInfoList) {
                bankCommonResponseItemInfo.setHeaderId(id);
                list.add(bankCommonResponseItemInfo);
            }
            if (list.size() > 0) {
                bankCommonResponseHeaderInfoMapper.batchBankCommonResponseItemInfo(list);
            }
        }
    }

    @Override
    public int deleteBankCommonResponseItemInfoByHeaderIds(Long[] ids) {
        return bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseItemInfoByHeaderIds(ids);
    }

    @Override
    public int batchBankCommonResponseItemInfo(List<BankCommonResponseItemInfoDO> bankCommonResponseItemInfoList) {
        return bankCommonResponseHeaderInfoMapper.batchBankCommonResponseItemInfo(bankCommonResponseItemInfoList);
    }

    @Override
    public int deleteBankCommonResponseItemInfoByHeaderId(Long id) {
        return bankCommonResponseHeaderInfoMapper.deleteBankCommonResponseItemInfoByHeaderId(id);
    }
}
