package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.domain.BizBaseVendorBank;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndBanksDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorAndPerferBankDTO;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorBankDTO;
import com.microservices.otmp.masterdata.mapper.BizBasePreferredBankMaintenanceMapper;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorBankMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import com.microservices.otmp.masterdata.domain.BizBaseVendor;
import com.microservices.otmp.masterdata.domain.dto.BizBaseVendorDTO;
import com.microservices.otmp.masterdata.domain.entity.BizBaseVendorDO;
import com.microservices.otmp.masterdata.manager.IBizBaseVendorManager;
import com.microservices.otmp.masterdata.mapper.BizBaseVendorMapper;
import com.microservices.otmp.masterdata.service.IBizBaseVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * baseComBizService业务层处理
 *
 * @author sdms
 * @date 2022-01-17
 */
@Service
public class IBizBaseVendorServiceImpl implements IBizBaseVendorService {

    private static final Logger log = LoggerFactory.getLogger(IBizBaseVendorServiceImpl.class);
    @Autowired
    private BizBaseVendorMapper bizBaseVendorMapper;

    @Autowired
    private IBizBaseVendorManager bizBaseVendorManager;

    @Autowired
    private BizBaseVendorBankMapper bizBaseVendorBankMapper;

    @Autowired
    private BizBasePreferredBankMaintenanceMapper bizBasePreferredBankMaintenanceMapper;

    /**
     * 查询lgvm vendor
     *
     * @param id lgvm vendor主键
     * @return lgvm vendorDTO
     */
    @Override
    public BizBaseVendorDTO selectBizBaseVendorById(Long id)
    {
        BizBaseVendorDO bizBaseVendorDO =  bizBaseVendorManager.selectBizBaseVendorById(id);
        BizBaseVendorDTO bizBaseVendorDTO = new BizBaseVendorDTO();
        BeanUtils.copyProperties(bizBaseVendorDO, bizBaseVendorDTO);
        return bizBaseVendorDTO;
    }

    /**
     * 查询lgvm vendor列表
     *
     * @param bizBaseVendor lgvm vendor
     * @return lgvm vendorDTO
     */
    @Override
    public List<BizBaseVendorDTO> selectBizBaseVendorList(BizBaseVendorDTO bizBaseVendor)
    {

        List<BizBaseVendorDO> bizBaseVendorDOS =
                bizBaseVendorManager.selectBizBaseVendorList(bizBaseVendor);
        List<BizBaseVendorDTO> bizBaseVendorList = new ArrayList<>(bizBaseVendorDOS.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(bizBaseVendorDOS, bizBaseVendorList, BizBaseVendorDTO.class);
        return bizBaseVendorList;

    }

    /**
     * 新增lgvm vendor
     *
     * @param bizBaseVendor lgvm vendor
     * @return 结果
     */
    @Override
    public int insertBizBaseVendor(BizBaseVendorDTO bizBaseVendor)
    {
        bizBaseVendor.setCreateTime(DateUtils.getNowDate());
        BizBaseVendorDO bizBaseVendorDO =new BizBaseVendorDO();
        BeanUtils.copyProperties(bizBaseVendor, bizBaseVendorDO);
        return bizBaseVendorManager.insertBizBaseVendor(bizBaseVendorDO);
    }

    /**
     * 修改lgvm vendor
     *
     * @param bizBaseVendor lgvm vendor
     * @return 结果
     */
    @Override
    public int updateBizBaseVendor(BizBaseVendorDTO bizBaseVendor)
    {
        bizBaseVendor.setUpdateTime(DateUtils.getNowDate());
        BizBaseVendorDO bizBaseVendorDO =new BizBaseVendorDO();
        BeanUtils.copyProperties(bizBaseVendor, bizBaseVendorDO);
        return bizBaseVendorManager.updateBizBaseVendor(bizBaseVendorDO);
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
        return bizBaseVendorManager.deleteBizBaseVendorByIds(ids);
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
        return bizBaseVendorManager.deleteBizBaseVendorById(id);
    }

    @Override
    public int insertVendorLgvmKafka(BizBaseVendor testLgvmdsKafka) {
        return bizBaseVendorMapper.insertVendorLgvmKafka(testLgvmdsKafka);
    }

    @Override
    public int selectCountByVendorCode(String vendorCode, String companyCode) {
        return bizBaseVendorMapper.selectCountByVendorCode(vendorCode, companyCode);
    }

    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#bizBaseVendor.vendorCode")
    @Override
    public int updateVendor(BizBaseVendor bizBaseVendor) {
        return bizBaseVendorMapper.updateVendor(bizBaseVendor);
    }

    @Override
    public List<BizBaseVendorDTO> getVendorAndBankInfoList(BizBaseVendorDTO bizBaseVendor) {
        return bizBaseVendorMapper.getVendorAndBankInfoList(bizBaseVendor);
    }

    /**
     * 删除updateTime ！= date的数据
     * @param date
     * @return
     */
    @Override
    public int deleteBizBaseVendorByUpdateTime(String vendorCode, Date date) {
        return bizBaseVendorManager.deleteBizBaseVendorByUpdateTime(vendorCode, date);
    }

    /**
     * 更新errorInfo
     * @param vendorCode
     * @return
     */
    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#vendorCode")
    @Override
    public int updateErrorInfoByVendor(String vendorCode) {
        /**
         * 1. 如果vendorcode当前在vendorbank中无任何关联的vendor bank信息，则显示报错  No vendor bank in LGVM
         * 2. 如果vendorcode当前在vendorbank中不存在关联的vendor bank信息，则显示报错 Vendor bank account missing
         * 3. 如果vendor存在唯一的vendor bank，或者是存在多个关联vendorbank但是用户已经设置prefer bank，则属于正常数据，error info显示为空
         */
        //默认为1,所有只需判断现在是否以满足条件2或条件3
        BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setVendorCode(vendorCode);
        bizBaseVendorBank.setStatus("Y");
        List<BizBaseVendorBank> bizBaseVendorBanks = bizBaseVendorBankMapper.selectBizBaseVendorBankList(bizBaseVendorBank);
        BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance = new BizBasePreferredBankMaintenance();
        bizBasePreferredBankMaintenance.setVendorCode(vendorCode);
        List<BizBasePreferredBankMaintenance> bizBasePreferredBankMaintenances =
                bizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(bizBasePreferredBankMaintenance);
        if ((CollectionUtils.isNotEmpty(bizBasePreferredBankMaintenances) && bizBaseVendorBanks.size() == 1) ||
                CollectionUtils.isNotEmpty(bizBasePreferredBankMaintenances)) {
            return bizBaseVendorManager.updateErrorInfoByVendor(vendorCode, null);
        }
        if (CollectionUtils.isNotEmpty(bizBasePreferredBankMaintenances) && bizBaseVendorBanks.size() > 1 && CollectionUtils.isEmpty(bizBaseVendorBanks)) {
            return bizBaseVendorManager.updateErrorInfoByVendor(vendorCode, "Prefer bank account missing");
        }
        return 0;
    }

    @Override
    public List<BizBaseVendorAndPerferBankDTO> getVendorAndPerferBank(List<String> customerIds) {
        log.info("查询vendorAndPerferBank参数customerIds:{}", customerIds);
        List<BizBaseVendorAndPerferBankDTO> dtos = new ArrayList<>();
        //循环查询,将每一条查询结果都放入缓存中
        //vendor表维护两个缓存一个是vendorCode和customerId的关系,一个是vendorCode和bank的关系
        //另外两个表都要和vendorCode的缓存关联上
        for (String customerId : customerIds) {
            String vendorCode = bizBaseVendorManager.getVendorCodeByCustomerId(customerId);
            BizBaseVendorAndPerferBankDTO dto;
            if (StringUtils.isEmpty(vendorCode)) {
                //vendorCode为空直接返回errorInfo信息
                dto = new BizBaseVendorAndPerferBankDTO();
                dto.setErrorInfo("No vendor bank in LGVM");
            } else {
                dto = bizBaseVendorManager.getVendorAndPerferBankByCustomerId(vendorCode);
            }
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public BizBaseVendorAndBanksDTO getVendorAndBanksByCode(String vendorCode) {
        BizBaseVendorDO bizBaseVendorDO =  bizBaseVendorManager.getVendorNameByCode(vendorCode);
        BizBaseVendorAndBanksDTO bizBaseVendorAndBanksDTO = new BizBaseVendorAndBanksDTO();
        BeanUtils.copyProperties(bizBaseVendorDO, bizBaseVendorAndBanksDTO);
        BizBaseVendorBank bizBaseVendorBank = new BizBaseVendorBank();
        bizBaseVendorBank.setVendorCode(vendorCode);
        bizBaseVendorBank.setStatus("Y");
        List<BizBaseVendorBank> list = bizBaseVendorBankMapper.selectBizBaseVendorBankList(bizBaseVendorBank);
        List<BizBaseVendorBankDTO> banks = new ArrayList<>(list.size());
        com.microservices.otmp.common.utils.bean.BeanUtils.copyListProperties(list, banks, BizBaseVendorBankDTO.class);
        bizBaseVendorAndBanksDTO.setBanks(banks);
        return bizBaseVendorAndBanksDTO;
    }
}
