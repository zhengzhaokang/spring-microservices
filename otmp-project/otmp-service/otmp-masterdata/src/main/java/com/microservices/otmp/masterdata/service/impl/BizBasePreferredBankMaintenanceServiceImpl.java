package com.microservices.otmp.masterdata.service.impl;

import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.ServiceException;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.masterdata.domain.BizBasePreferredBankMaintenance;
import com.microservices.otmp.masterdata.manager.IBizBaseVendorManager;
import com.microservices.otmp.masterdata.mapper.BizBasePreferredBankMaintenanceMapper;
import com.microservices.otmp.masterdata.service.IBizBasePreferredBankMaintenanceService;
import com.microservices.otmp.masterdata.util.ConvertStringToLongListUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * BizBasePreferredBankMaintenanceService业务层处理
 *
 * @author xiaozy8
 * @date 2022-11-30
 */
@Service
public class BizBasePreferredBankMaintenanceServiceImpl implements IBizBasePreferredBankMaintenanceService
{
    @Autowired
    private BizBasePreferredBankMaintenanceMapper bizBasePreferredBankMaintenanceMapper;
    @Autowired
    private IBizBaseVendorManager bizBaseVendorManager;

    @Override
    public BizBasePreferredBankMaintenance selectBizBasePreferredBankMaintenanceById(Long id) {
        return bizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceById(id);
    }

    @Override
    public List<BizBasePreferredBankMaintenance> selectBizBasePreferredBankMaintenanceList(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance) {
        List<Long> longIdsList = ConvertStringToLongListUtil.splitToLongList(bizBasePreferredBankMaintenance.getIds());
        bizBasePreferredBankMaintenance.setIdsList(longIdsList);
        return bizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(bizBasePreferredBankMaintenance);
    }

    @CacheEvict(value = "MASTER_PERFER_BANK_INFO", key = "#bizBasePreferredBankMaintenance.vendorCode")
    @Override
    public int insertBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance) {
        int count = bizBasePreferredBankMaintenanceMapper.insertBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance);
        if (count == 1) {
            bizBaseVendorManager.updateErrorInfoByVendor(bizBasePreferredBankMaintenance.getVendorCode(), null);
        }
        return count;
    }

    @Override
    public int updateBizBasePreferredBankMaintenance(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance) {
        return bizBasePreferredBankMaintenanceMapper.updateBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance);
    }

    @Override
    public int deleteBizBasePreferredBankMaintenanceByIds(Long[] ids) {
        return bizBasePreferredBankMaintenanceMapper.deleteBizBasePreferredBankMaintenanceByIds(ids);
    }

    @Override
    public int deleteBizBasePreferredBankMaintenanceById(Long id) {
        return bizBasePreferredBankMaintenanceMapper.deleteBizBasePreferredBankMaintenanceById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultDTO<String> importExcel(List<BizBasePreferredBankMaintenance> bizs, String loginName) {
        AtomicInteger successNum = new AtomicInteger();
        AtomicInteger failureNum = new AtomicInteger();
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        Date date = DateUtils.getNowDate();
        HashSet<String> hashSet = new HashSet<>();
        BizBasePreferredBankMaintenance check = new BizBasePreferredBankMaintenance();
        List<BizBasePreferredBankMaintenance> list = null;

        if (CollectionUtils.isEmpty(bizs)) {
            throw new ServiceException("导入MasterData 数据不能为空！");
        }
        for (BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance : bizs) {
            //校验文件中的vendorCode是否存在重复
            if (!hashSet.add(bizBasePreferredBankMaintenance.getVendorCode())) {
                return ResultDTO.fail("很抱歉，导入失败，导入数据中存在重复数据：vendorCode = " + bizBasePreferredBankMaintenance.getVendorCode());
            }
            //校验vendorCode与库中数据是否重复
            check.setVendorCode(bizBasePreferredBankMaintenance.getVendorCode());
            check.setBankType(bizBasePreferredBankMaintenance.getBankType());
            check.setBankAccount(bizBasePreferredBankMaintenance.getBankAccount());
            check.setDeleteFlag(false);
            list = bizBasePreferredBankMaintenanceMapper.selectBizBasePreferredBankMaintenanceList(check);
            if (CollectionUtils.isNotEmpty(list)) {
                return ResultDTO.fail("Record exists already");
            }
            //校验是否满足lgvm bank数据数据
            int count = bizBasePreferredBankMaintenanceMapper.checkLgvmBank(bizBasePreferredBankMaintenance);
            if (count == 0) {
                return ResultDTO.fail("The bank information does not exist in the LGVM");
            }
        }
        for (BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance : bizs) {
            try {
                bizBasePreferredBankMaintenance.setCreateTime(date);
                bizBasePreferredBankMaintenance.setUpdateTime(date);
                bizBasePreferredBankMaintenance.setCreateBy(loginName);
                bizBasePreferredBankMaintenanceMapper.insertBizBasePreferredBankMaintenance(bizBasePreferredBankMaintenance);
                successNum.getAndIncrement();
            } catch (Exception e) {
                failureNum.getAndIncrement();
                String msg = "<br/>" + failureNum + "MasterData导入失败：";
                failureMsg.append(msg + e.getMessage());
            }
        }
        if (failureNum.intValue() > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return ResultDTO.success(successMsg.toString());
    }

    @Override
    public int checkLgvmBank(BizBasePreferredBankMaintenance bizBasePreferredBankMaintenance) {
        return bizBasePreferredBankMaintenanceMapper.checkLgvmBank(bizBasePreferredBankMaintenance);
    }
}
