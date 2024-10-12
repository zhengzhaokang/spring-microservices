package com.microservices.otmp.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.entity.BankDo;
import com.microservices.otmp.financing.domain.entity.EntityNameBankSettingDo;
import com.microservices.otmp.financing.domain.entity.OnBoardingSupplierByBankDo;
import com.microservices.otmp.financing.domain.param.bank.AddBankParam;
import com.microservices.otmp.financing.domain.param.bank.BankListParam;
import com.microservices.otmp.financing.domain.param.bank.BankParam;
import com.microservices.otmp.financing.domain.param.bank.EditBankParam;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.mapper.BankMapper;
import com.microservices.otmp.financing.mapper.EntityBankSettingMapper;
import com.microservices.otmp.financing.mapper.SupplierBankSettingMapper;
import com.microservices.otmp.financing.service.BankService;
import com.microservices.otmp.financing.util.RedisKeyUtil;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class BankServiceImpl implements BankService {

    private static final String BANK_IMG_TYPE_DBS = "bankimgTwo.png";
    private static final String BANK_IMG_TYPE_BNPP = "bankimg.png";
    private static final String USER_TYPE = "02";
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private EntityBankSettingMapper entityBankSettingMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;
    @Autowired
    private RemoteUserService remoteUserService;

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    @Override
    public Boolean add(AddBankParam param) {
        log.info("add,param:{}", JsonUtil.toJSON(param));
        Example existCondition = bankExistExample(param, false);
        List<BankDo> bankDos = bankMapper.selectByExample(existCondition);
        if (CollectionUtils.isNotEmpty(bankDos)) {
            log.info("add,bankDos is empty");
            throw throwEx(DefaultErrorMessage.BANK_NAME_EXIST);
        }
        if (CollectionUtils.isEmpty(param.getCurrency())) {
            throw throwEx(DefaultErrorMessage.BANK_CURRENCY_IS_NULL);
        }
        BankDo bankDo = new BankDo();
        bankDo.setId(String.valueOf(SnowFlakeUtil.nextId()));
        fillProp(param, bankDo);
        bankDo.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
        bankDo.setCreateTime(LocalDateTime.now());
        bankDo.setCreateBy(param.getCreateBy());
        log.info("add,bankDo:{}", JsonUtil.toJSON(bankDo));
        bankMapper.insert(bankDo);
        updateCache(bankDo);
        return true;
    }

    /**
     * 填充bank对象属性，无业务逻辑
     */
    private void fillProp(BankParam param, BankDo bankDo) {
        bankDo.setBankName(param.getBankName());
        bankDo.setBankAccount(param.getAccount());
        bankDo.setRegion(param.getRegion());
        bankDo.setCurrency(String.join(StringConstants.PROP_SEPARATOR, param.getCurrency()));
        bankDo.setBankIban(param.getIban());
        bankDo.setSwiftCode(param.getSwift());
        bankDo.setContactEmail(param.getContactMail());
        bankDo.setContactFocal(param.getContactFocal());
        bankDo.setBankIntegrationChain(param.getIntegrationChain());
        bankDo.setErpVendorId(param.getErpVendorId());
        bankDo.setBankAddress(param.getBankAddress());
        bankDo.setRank(param.getRank());
//        bankDo.setIconFileId(param.getIconFileId());
        if(param.getIntegrationChain().equals(BankDo.CHAIN_DBS)){
            bankDo.setIconPath(BANK_IMG_TYPE_DBS);
        }else{
            bankDo.setIconPath(BANK_IMG_TYPE_BNPP);
        }
    }

    private Example bankExistExample(BankParam param, boolean updateDate) {
        Example example = new Example(BankDo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankName", param.getBankName())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        if (updateDate && (param instanceof EditBankParam)) {
            EditBankParam editBankParam = (EditBankParam) param;
            criteria.andNotEqualTo("id", editBankParam.getId());
        }
        return example;
    }

    @Override
    public Boolean update(EditBankParam param) {
        log.info("update,param:{}", JsonUtil.toJSON(param));
        // 检查名称重复
        Example existCondition = bankExistExample(param, true);
        List<BankDo> bankDos = bankMapper.selectByExample(existCondition);
        if (CollectionUtils.isNotEmpty(bankDos)) {
            log.info("update,bankDos is empty");
            throw throwEx(DefaultErrorMessage.BANK_NAME_EXIST);
        }
        BankDo bankDo = bankMapper.selectByPrimaryKey(param.getId());
        if (bankDo == null) {
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        fillProp(param, bankDo);
        bankDo.setUpdateBy(param.getUpdateBy());
        bankDo.setUpdateTime(LocalDateTime.now());
        log.info("update,bankDo:{}", JsonUtil.toJSON(bankDo));
        bankMapper.updateByPrimaryKey(bankDo);
        updateCache(bankDo);
        return true;
    }

    @Async
    public void updateCache(BankDo bankDo) {
        String bankCacheKey = RedisKeyUtil.getBankInfoKey(bankDo.getId());
        log.info("updateCache,bankCacheKey:{}", bankCacheKey);
        RBucket<BankDo> bucket = redissonClient.getBucket(bankCacheKey);
        bucket.set(bankDo);
        RMap<String, BankDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_BANK);
        map.put(bankDo.getId(), bankDo);
    }

    @Override
    public PageInfo<BankVo> list(BankListParam param) {
        log.info("list,param:{}", JsonUtil.toJSON(param));
        SysUser sysUser = remoteUserService.selectSysUserByUserId(param.getUserId());
        log.info("list,sysUser:{}", JsonUtil.toJSON(sysUser));
        if (sysUser == null) {
            log.info("list,sysUser is null,userId:{}", param.getUserId());
            throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
        }
        PageInfo<BankVo> result = new PageInfo<>();
        int pageNum = param.getPageNum() == null ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = param.getPageSize() == null ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();
        String supplierId = USER_TYPE.equals(sysUser.getUserType()) ? sysUser.getSupplierId() : null;
        Integer status = param.getStatus();
        log.info("list,supplierId:{},status:{}", supplierId, status);
        PageHelper.startPage(pageNum, pageSize);
        List<BankDo> bankList = bankMapper.bankList(String.valueOf(status), supplierId);
        PageInfo<BankDo> pageInfo = new PageInfo<>(bankList);
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        if (CollectionUtils.isEmpty(bankList)) {
            log.info("list,bankList is empty");
            result.setList(new ArrayList<>());
            return result;
        }
        List<BankVo> resultList = new ArrayList<>();
        for (BankDo bank : bankList) {
            resultList.add(new BankVo(bank));
        }
        result.setList(resultList);
        log.info("list,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    @Override
    public BankVo info(Long id) {
        log.info("info,id:{}", id);
        BankDo bankDo = bankMapper.selectByPrimaryKey(String.valueOf(id));
        if (bankDo == null || StateConstants.FLAG_DELETED_STR.equals(bankDo.getDeleteFlag())) {
            log.info("info,bankDo:{}", JsonUtil.toJSON(bankDo));
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        return new BankVo(bankDo);
    }

    @Override
    public Boolean changeStatus(Long id, String status) {
        String oldStatus = StateConstants.FLAG_DELETED_STR.equals(status)
                ? StateConstants.FLAG_NORMAL_STR
                : StateConstants.FLAG_DELETED_STR;
        Example condition = new Example(BankDo.class);
        condition.createCriteria()
                .andEqualTo("id", String.valueOf(id))
                .andEqualTo("deleteFlag", oldStatus);
        log.info("updateStatus,id:{},status:{},oldStatus:{}", id, status, oldStatus);
        BankDo bankDo = bankMapper.selectOneByExample(condition);
        if (bankDo == null) {
            log.info("changeStatus,bankDo is null,id:{},status:{},oldStatus:{}", id, status, oldStatus);
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        if (StateConstants.FLAG_DELETED_STR.equals(status)) {
            // 禁用银行时需要检查关联对象
            checkBankRel(id);
        }
        if (StateConstants.FLAG_NORMAL_STR.equals(status)) {
            // 启用时检查名称重复
            checkBankName(bankDo);
        }
        bankDo.setDeleteFlag(status);
        bankDo.setUpdateTime(LocalDateTime.now());
        bankDo.setUpdateBy(bankDo.getUpdateBy());
        bankMapper.updateByPrimaryKey(bankDo);
        return true;
    }

    private void checkBankName(BankDo bank) {
        Example example = new Example(BankDo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bankName", bank.getBankName())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<BankDo> bankDos = bankMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(bankDos)){
            throw throwEx(DefaultErrorMessage.BANK_NAME_EXIST);
        }
    }

    private void checkBankRel(Long bankId) {
        log.info("checkBankRel,bankId:{}", bankId);
        // 检查关联的supplier对象
        List<OnBoardingSupplierByBankDo> onBoardingSupplierByBankDos = supplierBankSettingMapper.onboardingByBank(Collections.singletonList(String.valueOf(bankId)));
        if (CollectionUtils.isNotEmpty(onBoardingSupplierByBankDos)) {
            log.info("checkBankRel,onBoardingSupplierByBankDos is not empty,bankId:{}", bankId);
            throw throwEx(DefaultErrorMessage.BANK_HAS_SUPPLIER_REL);
        }

        List<EntityNameBankSettingDo> entityBankSettingDos = entityBankSettingMapper.selectEntityNamesByBankId(String.valueOf(bankId));
        if (CollectionUtils.isNotEmpty(entityBankSettingDos)) {
            log.info("checkBankRel,entityBankSettingDos is not empty,bankId:{}", bankId);
            throw throwEx(DefaultErrorMessage.BANK_HAS_ENTITY_REL);
        }
    }
}
