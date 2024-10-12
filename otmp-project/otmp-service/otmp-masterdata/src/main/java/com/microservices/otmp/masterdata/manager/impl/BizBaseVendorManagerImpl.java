package com.microservices.otmp.masterdata.manager.impl;

import java.util.Date;
import java.util.List;

import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseVendorDO;
import com.microservices.otmp.masterdata.manager.IBizBaseVendorManager;
import com.microservices.otmp.masterdata.mapper.BizBasePreferredBankMaintenanceMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorBankMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorMapper;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * lgvm vendorManager业务层处理
 * 
 * @author lovefamily
 * @date 2023-03-21
 */
@Service
public class BizBaseVendorManagerImpl implements IBizBaseVendorManager
{
    private static final Logger log = LoggerFactory.getLogger(BizBaseVendorManagerImpl.class);

    @Autowired
    private BizBaseVendorMapper bizBaseVendorMapper;
    @Autowired
    private BizBasePreferredBankMaintenanceMapper bizBasePreferredBankMaintenanceMapper;
    @Autowired
    private BizBaseVendorBankMapper bizBaseVendorBankMapper;

    /**
     * 查询lgvm vendor
     * 
     * @param id lgvm vendor主键
     * @return lgvm vendorDO
     */
    @Override
    public BizBaseVendorDO selectBizBaseVendorById(Long id)
    {
        return bizBaseVendorMapper.selectBizBaseVendorById(id);
    }

    /**
     * 查询lgvm vendor列表
     *
     * @param bizBaseVendor lgvm vendor
     * @return lgvm vendorDO
     */
    @Override
    public List<BizBaseVendorDO> selectBizBaseVendorList(BizBaseVendorDTO bizBaseVendor)
    {
        return bizBaseVendorMapper.selectBizBaseVendorList(bizBaseVendor);
    }

    /**
     * 新增lgvm vendor
     *
     * @param bizBaseVendor lgvm vendor
     * @return 结果
     */
    @Override
    public int insertBizBaseVendor(BizBaseVendorDO bizBaseVendor)
    {
        bizBaseVendor.setCreateTime(DateUtils.getNowDate());
        return bizBaseVendorMapper.insertBizBaseVendor (bizBaseVendor);
    }

    /**
     * 修改lgvm vendor
     *
     * @param bizBaseVendor  lgvm vendor
     * @return 结果
     */
    @Override
    public int updateBizBaseVendor(BizBaseVendorDO bizBaseVendor)
    {
        bizBaseVendor.setUpdateTime(DateUtils.getNowDate());
        return bizBaseVendorMapper.updateBizBaseVendor (bizBaseVendor);
    }

    /**
     * 批量删除lgvm vendor
     * 
     * @param ids 需要删除的lgvm vendor主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorByIds(Long[] ids)
    {
        return bizBaseVendorMapper.deleteBizBaseVendorByIds(ids);
    }

    /**
     * 删除lgvm vendor信息
     * 
     * @param id lgvm vendor主键
     * @return 结果
     */
    @Override
    public int deleteBizBaseVendorById(Long id)
    {
        log.info("删除lgvm vendor信息,id:{}", id);
        return bizBaseVendorMapper.deleteBizBaseVendorById(id);
    }

    /**
     * 删除updateTime ！= date的数据
     * @param date
     * @return
     */
    @Override
    public int deleteBizBaseVendorByUpdateTime(String vendorCode, Date date) {
        return bizBaseVendorMapper.deleteBizBaseVendorByUpdateTime(vendorCode, date);
    }

    /**
     * 更新errorInfo
     * @param vendorCode
     * @return
     */
    @Override
    public int updateErrorInfoByVendor(String vendorCode, String errorInfo) {
        return bizBaseVendorMapper.updateErrorInfoByVendor(vendorCode, errorInfo);
    }

    @Override
    public BizBaseVendorAndPerferBankDTO getVendor(String vendorCode) {
        return bizBaseVendorMapper.getVendor(vendorCode);
    }

    @Override
    public String getVendorCodeByCustomerId(String customerId) {
        return bizBaseVendorMapper.getVendorCodeByCustomerId(customerId);
    }

    @Override
    public BizBaseVendorDO getVendorNameByCode(String vendorCode) {
        return bizBaseVendorMapper.getVendorNameByCode(vendorCode);
    }

    @Override
    public BizBaseVendorAndPerferBankDTO getVendorAndPerferBankByCustomerId(String vendorCode) {
        BizBaseVendorAndPerferBankDTO dto;
        dto = getVendor(vendorCode);
        if (StringUtils.isEmpty(dto.getErrorInfo())) {
            //没有报错信息,先查是否配置perfer bank,若没有则表示目前只有一条bank数据,去查bank表数据
            BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
            bizBasePreferredBankMaintenance.setVendorCode(dto.getVendorCode());
            List<BizBasePreferredBankMaintenance> bizBasePreferredBankMaintenances =
                    bizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(bizBasePreferredBankMaintenance);
            if (CollectionUtils.isNotEmpty(bizBasePreferredBankMaintenances)) {
                dto.setBankAccount(bizBasePreferredBankMaintenances.get(0).getBankAccount());
                dto.setBankName(bizBasePreferredBankMaintenances.get(0).getBankName());
                dto.setBankType(bizBasePreferredBankMaintenances.get(0).getBankType());
                dto.setSwiftCode(bizBasePreferredBankMaintenances.get(0).getSwiftCode());
                dto.setAccountHolder(bizBasePreferredBankMaintenances.get(0).getAccountHolder());
            } else {
                //查bank表
                BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
                bizBaseVendorBank.setVendorCode(dto.getVendorCode());
                List<BizBaseVendorBank> bizBaseVendorBanks = bizBaseVendorBankMapper.selectBizBaseVendorBankList(bizBaseVendorBank);
                dto.setBankAccount(bizBaseVendorBanks.get(0).getBankAccount());
                dto.setBankName(bizBaseVendorBanks.get(0).getBankName());
                dto.setBankType(bizBaseVendorBanks.get(0).getBankType());
                dto.setSwiftCode(bizBaseVendorBanks.get(0).getSwiftCode());
                dto.setAccountHolder(bizBaseVendorBanks.get(0).getAccountHolder());
            }
        } else {
            //有报错信息只返回errorInfo
            dto.setCustomerId(null);
            dto.setVendorCode(null);
            dto.setVendorName(null);
        }
        return dto;
    }
}
