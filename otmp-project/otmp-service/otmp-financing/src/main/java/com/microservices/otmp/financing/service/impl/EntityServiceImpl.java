package com.microservices.otmp.financing.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.param.entity.AddEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EditEntityParam;
import com.microservices.otmp.financing.domain.param.entity.EntityListParam;
import com.microservices.otmp.financing.domain.param.entity.EntityParam;
import com.microservices.otmp.financing.domain.vo.bank.BankVo;
import com.microservices.otmp.financing.domain.vo.entity.EntityVo;
import com.microservices.otmp.financing.mapper.*;
import com.microservices.otmp.financing.remote.RemoteFileService;
import com.microservices.otmp.financing.remote.param.BatchUpdateParam;
import com.microservices.otmp.financing.remote.param.FileUpdateParam;
import com.microservices.otmp.financing.service.EntityService;
import com.microservices.otmp.financing.util.RedisKeyUtil;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteLogService;
import com.microservices.otmp.system.feign.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EntityServiceImpl implements EntityService {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private EntityCompanyCodeMapper entityCompanyCodeMapper;
    @Autowired
    private EntityBankSettingMapper entityBankSettingMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private RemoteLogService remoteLogService;

    private static final String USER_TYPE_SUPPLIER = "02";

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean add(AddEntityParam param) {
        log.info("add,param:{}", JsonUtil.toJSON(param));
        // 校验名称重复
        Example exist = new Example(EntityDo.class);
        exist.createCriteria()
                .andEqualTo("entityName", param.getEntityName())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        EntityDo entityDo = entityMapper.selectOneByExample(exist);
        if (entityDo != null) {
            throw throwEx(DefaultErrorMessage.ENTITY_NAME_EXIST);
        }
        // 保存entity对象
        LocalDateTime now = LocalDateTime.now();
        EntityDo entity = new EntityDo();
        fillEntity(entity, param);
        entity.setId(SnowFlakeUtil.nextId());
        entity.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
        entity.setCreateBy(param.getCreateBy());
        entity.setCreateTime(now);
        log.info("add,entity:{}", JsonUtil.toJSON(entity));
        entityMapper.insert(entity);
        updateCache(entity);
        Long entityId = entity.getId();
        // 保存companyCode的关联关系
        saveCompanyCodes(false, param, entityId, now);
        // 保存entity和Bank的关联关系
        Set<String> bankIds = new HashSet<>(param.getBankIds());
        log.info("add,bankIds:{}", JsonUtil.toJSON(bankIds));
        List<EntityBankSettingDo> entityBankSettings = new ArrayList<>(bankIds.size());
        for (String id : bankIds) {
            EntityBankSettingDo setting = createEntityBankSetting(entityId, id);
            setting.setCreateBy(param.getCreateBy());
            setting.setCreateTime(now);
            entityBankSettings.add(setting);
        }
        entityBankSettingMapper.insertListWithId(entityBankSettings);
        if (CollectionUtils.isNotEmpty(param.getKycDocumentIds())) {
            updateFiles(param, entityId);
        }
        return true;
    }

    private void updateFiles(EntityParam param, Long entityId) {
        List<String> contractIds = param.getContractIds();
        List<String> kycDocumentIds = param.getKycDocumentIds();
        log.info("updateFiles,kycDocumentIds:{}", JsonUtil.toJSON(kycDocumentIds));
        List<String> allFiles = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(contractIds)) {
            allFiles.addAll(contractIds);
        }
        if (CollectionUtils.isNotEmpty(kycDocumentIds)) {
            allFiles.addAll(kycDocumentIds);
        }
        updateFileAsync(entityId, allFiles);
    }

    @Async
    public void updateFileAsync(Long id, List<String> fileData) {
        if (org.springframework.util.CollectionUtils.isEmpty(fileData)) {
            log.warn("updateFileAsync,fileData is empty,return");
            return;
        }
        log.info("updateFileAsync,id:{},fileData:{}", id, JsonUtil.toJSON(fileData));
        BatchUpdateParam updateParam = new BatchUpdateParam();
        List<FileUpdateParam> params = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fileData.size(); i++) {
            String data = fileData.get(i);
            builder.append(data);
            if (i < fileData.size() - 1) {
                builder.append(StringConstants.PROP_SEPARATOR);
            }
        }
        params.add(new FileUpdateParam(String.valueOf(id), builder.toString()));
        updateParam.setSystemFileUpdateVOList(params);
        ResultDTO<Object> execResult = remoteFileService.batchUpdate(updateParam);
        if (!execResult.isSuccess()) {
            log.error("updateFileAsync execute error,msg:{}", execResult.getMsg());
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        log.info("updateFileAsync execute success");
    }

    private void saveCompanyCodes(boolean update, EntityParam param, Long entityId, LocalDateTime now) {
        if (CollectionUtils.isEmpty(param.getErpEntities())) {
            log.warn("saveCompanyCodes,erpEntities is empty,entityId:{}", entityId);
            return;
        }
        Set<String> companyCodes = new HashSet<>(param.getErpEntities());
        log.info("saveCompanyCodes,companyCodes:{}", JsonUtil.toJSON(companyCodes));
        List<EntityCompanyCodeDo> entityCompanyCodes = new ArrayList<>(companyCodes.size());
        String userName = null;
        if (param instanceof AddEntityParam) {
            userName = ((AddEntityParam) param).getCreateBy();
        } else if (param instanceof EditEntityParam) {
            userName = ((EditEntityParam) param).getUpdateBy();
        } else {
            log.warn("saveCompanyCodes,userName is null,param:{}", JsonUtil.toJSON(param));
        }

        // 检查companyCode是否已绑定其他entity
        Long entityIdForUpdate = update ? entityId : null;
        log.info("saveCompanyCodes,checkRelation,entityIdForUpdate:{}", entityIdForUpdate);
        EntityCompanyCodeDo rel = entityCompanyCodeMapper.checkRelation(companyCodes, entityIdForUpdate);
        if (rel != null) {
            log.info("saveCompanyCodes,entity-companyCode relation already exists,entityCompanyCodes:{},entityId:{}", JsonUtil.toJSON(entityCompanyCodes), entityIdForUpdate);
            throw throwEx(DefaultErrorMessage.ENTITY_COMPANY_CODE_RELATION_EXIST);
        }

        // 保存entity和companyCode关联关系
        for (String companyCode : companyCodes) {
            EntityCompanyCodeDo code = createEntityCompanyCodeDo(entityId, companyCode);
            code.setCreateBy(userName);
            code.setCreateTime(now);
            entityCompanyCodes.add(code);
        }
        log.info("saveCompanyCodes,entityCompanyCodes:{}", JsonUtil.toJSON(entityCompanyCodes));
        entityCompanyCodeMapper.insertListWithId(entityCompanyCodes);
    }

    private EntityBankSettingDo createEntityBankSetting(Long entityId, String bankId) {
        EntityBankSettingDo setting = new EntityBankSettingDo();
        setting.setId(SnowFlakeUtil.nextId());
        setting.setEntityId(entityId);
        setting.setBankId(bankId);
        setting.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
        return setting;
    }

    private EntityCompanyCodeDo createEntityCompanyCodeDo(Long entityId, String companyCode) {
        EntityCompanyCodeDo code = new EntityCompanyCodeDo();
        code.setId(SnowFlakeUtil.nextId());
        code.setEntityId(entityId);
        code.setCompanyCode(companyCode);
        code.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
        return code;
    }

    private void fillEntity(EntityDo entityDo, EntityParam param) {
        log.info("fillEntity,param:{}", JsonUtil.toJSON(param));
        if (param == null) {
            log.warn("fillEntity,param is null,return");
            return;
        }
        entityDo.setEntityName(param.getEntityName());
        entityDo.setCurrency(String.join(StringConstants.PROP_SEPARATOR, param.getCurrency()));
        entityDo.setRegion(param.getRegion());
        entityDo.setRegisterAddress(param.getAddress());
        entityDo.setType(param.getType());
        if (CollectionUtils.isNotEmpty(param.getContractIds())) {
            List<String> contractIds = param.getContractIds();
            List<String> contractIdStr = contractIds.stream().map(String::valueOf).collect(Collectors.toList());
            entityDo.setFileIds(String.join(StringConstants.PROP_SEPARATOR, contractIdStr));
        }
        // 清除之前的文件数据
        entityDo.setKycDocumentIds(null);
        if (CollectionUtils.isNotEmpty(param.getKycDocumentIds())) {
            List<String> kycDocumentIds = param.getKycDocumentIds();
            List<String> kycDocumentIdStr = kycDocumentIds.stream().map(String::valueOf).collect(Collectors.toList());
            entityDo.setKycDocumentIds(String.join(StringConstants.PROP_SEPARATOR, kycDocumentIdStr));
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean update(EditEntityParam param, SysOperLog operatorLog) {
        EditEntityParam paramForLog = copyParam(param);
        log.info("update,param:{}", JsonUtil.toJSON(param));
        // 检查名称重复
        Example exist = new Example(EntityDo.class);
        exist.createCriteria()
                .andEqualTo("entityName", param.getEntityName())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andNotEqualTo("id", param.getId());
        EntityDo entityDo = entityMapper.selectOneByExample(exist);
        if (entityDo != null) {
            throw throwEx(DefaultErrorMessage.ENTITY_NAME_EXIST);
        }
        entityDo = entityMapper.selectByPrimaryKey(param.getId());
        if (entityDo == null) {
            throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
        }
        LocalDateTime now = LocalDateTime.now();
        fillEntity(entityDo, param);
        entityDo.setId(param.getId());
        entityDo.setUpdateBy(param.getUpdateBy());
        entityDo.setUpdateTime(now);
        log.info("update,entityDo:{}", JsonUtil.toJSON(entityDo));
        entityMapper.updateByPrimaryKey(entityDo);
        updateCache(entityDo);
        // 保存entity和Bank的关联关系
        updateEntityBankSettings(param, now);
        // 更新entity和CompanyCode关系
        List<EntityCompanyCodeDo> entityCompanyCodeDos = entityCompanyCodeMapper.selectByEntityIds(Collections.singletonList(param.getId()));
        List<String> oldCompanyCodes = new ArrayList<>();
        List<String> companyCodeForLog = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(entityCompanyCodeDos)) {
            oldCompanyCodes = entityCompanyCodeDos.stream().map(EntityCompanyCodeDo::getCompanyCode).collect(Collectors.toList());
            companyCodeForLog = new ArrayList<>(oldCompanyCodes);
        }
        entityCompanyCodeMapper.removeEntityCompanyCodeRelation(param.getId());
        saveCompanyCodes(true, param, param.getId(), now);
        // 更新受影响的supplier的need_update字段
        if (needRefresh(param.getErpEntities(), oldCompanyCodes)) {
            supplierMapper.updateSupplierNeedRefresh(param.getId());
        }
        if (CollectionUtils.isNotEmpty(param.getKycDocumentIds())) {
            updateFiles(param, param.getId());
        }
        // 记录日志
        saveLog(operatorLog, paramForLog, companyCodeForLog);
        return true;
    }

    private EditEntityParam copyParam(EditEntityParam param){
        EditEntityParam result = new EditEntityParam();
        result.setUpdateBy(param.getUpdateBy());
        result.setId(param.getId());
        result.setAddress(param.getAddress());
        result.setEntityName(param.getEntityName());
        result.setRegion(param.getRegion());
        result.setType(param.getType());
        result.setCurrency(new ArrayList<>(param.getCurrency()));
        result.setBankIds(new ArrayList<>(param.getBankIds()));
        result.setContractIds(new ArrayList<>(param.getContractIds()));
        result.setErpEntities(new ArrayList<>(param.getErpEntities()));
        result.setKycDocumentIds(new ArrayList<>(param.getKycDocumentIds()));
        return result;
    }

    @Async
    private void saveLog(SysOperLog operatorLog, EditEntityParam param, List<String> oldCompanyCodes) {
        List<Object> paramObjects = new ArrayList<>();
        paramObjects.add(param);
        paramObjects.add(oldCompanyCodes);
        String paramStr = JsonUtil.toJSON(paramObjects);
        operatorLog.setOperParam(StringUtils.substring(paramStr, 0, 2000));
        remoteLogService.insertOperlog(operatorLog);
    }

    private boolean needRefresh(List<String> newCompanyCodes, List<String> oldCompanyCodes) {
        // 均为空,不需要刷新supplier信息
        if (CollectionUtils.isEmpty(newCompanyCodes) && StringUtils.isEmpty(oldCompanyCodes)) {
            return false;
        }
        List<String> common = new ArrayList<>(oldCompanyCodes);
        common.retainAll(newCompanyCodes);
        newCompanyCodes.removeAll(common);
        oldCompanyCodes.removeAll(common);
        return !newCompanyCodes.isEmpty() || !oldCompanyCodes.isEmpty();
    }

    private void updateEntityBankSettings(EditEntityParam param, LocalDateTime now) {
        Long entityId = param.getId();
        List<EntityBankSettingDo> entityBankSettingDos = entityBankSettingMapper.selectByEntityId(entityId, StateConstants.FLAG_NORMAL_STR);
        Set<String> bankIds = new HashSet<>(param.getBankIds());
        Set<String> oldBankIds = entityBankSettingDos.stream().map(EntityBankSettingDo::getBankId).collect(Collectors.toSet());
        Set<String> commonBankIds = new HashSet<>(bankIds);
        commonBankIds.retainAll(oldBankIds);
        log.info("updateEntityBankSettings,bankIds:{},oldBankIds:{},commonBankIds:{}", JsonUtil.toJSON(bankIds), JsonUtil.toJSON(oldBankIds), JsonUtil.toJSON(commonBankIds));
        // 需要删除的关系
        oldBankIds.removeAll(commonBankIds);
        // 需要新增的关系
        bankIds.removeAll(commonBankIds);
        log.info("updateEntityBankSettings,oldBankIds:{},bankIds:{}", JsonUtil.toJSON(oldBankIds), JsonUtil.toJSON(bankIds));
        List<EntityBankSettingDo> entityBankSettings = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(oldBankIds)) {
            entityBankSettingMapper.removeByEntityId(entityId);
            entityBankSettingMapper.deleteByEntityIdAndBankIds(entityId, oldBankIds);
        }
        if (CollectionUtils.isNotEmpty(bankIds)) {
            for (String id : bankIds) {
                EntityBankSettingDo setting = createEntityBankSetting(entityId, id);
                setting.setCreateBy(param.getUpdateBy());
                setting.setCreateTime(now);
                entityBankSettings.add(setting);
            }
            entityBankSettingMapper.insertListWithId(entityBankSettings);
        }
    }

    @Async
    public void updateCache(EntityDo entityDo) {
        String entityCacheKey = RedisKeyUtil.getEntityInfoKey(entityDo.getId());
        log.info("updateCache,entityCacheKey:{}", entityCacheKey);
        RBucket<EntityDo> bucket = redissonClient.getBucket(entityCacheKey);
        bucket.set(entityDo);
        RMap<Long, EntityDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_ENTITY);
        map.put(entityDo.getId(), entityDo);
    }

    @Override
    public PageInfo<EntityVo> list(EntityListParam param) {
        int pageNum = param.getPageNum() == null ? Constants.DEFAULT_PAGE_NUM : param.getPageNum();
        int pageSize = param.getPageSize() == null ? Constants.DEFAULT_PAGE_SIZE : param.getPageSize();

        SysUser sysUser = remoteUserService.selectSysUserByUserId(param.getUserId());
//        SysUser sysUser = new SysUser();
        if (sysUser == null) {
            log.error("list,sysUser is null,userId:{}", param.getUserId());
            throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
        }
//        SysUser sysUser = new SysUser();
        List<EntityDo> entityDos;
        if (USER_TYPE_SUPPLIER.equals(sysUser.getUserType())) {
            entityDos = entityMapper.selectEnitiesBySupplierId(String.valueOf(param.getStatus()), sysUser.getSupplierId());
        } else {
            Example condition = new Example(EntityDo.class);
            condition.createCriteria().andEqualTo("deleteFlag", String.valueOf(param.getStatus()));
            condition.setOrderByClause("id DESC");
            PageHelper.startPage(pageNum, pageSize);
            entityDos = entityMapper.selectByExample(condition);
        }
        log.info("list,entityDos:{}", JsonUtil.toJSON(entityDos));
        PageInfo<EntityDo> pageInfo = new PageInfo<>(entityDos);
        PageInfo<EntityVo> result = new PageInfo<>();
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        result.setTotal(pageInfo.getTotal());
        if (CollectionUtils.isEmpty(entityDos)) {
            result.setList(new ArrayList<>());
            return result;
        }
        List<EntityVo> resultList = new ArrayList<>(entityDos.size());
        List<Long> ids = entityDos.stream().map(EntityDo::getId).collect(Collectors.toList());
        List<BankWithEntityIdDo> bankDos = bankMapper.banksByEntityId(ids, StateConstants.FLAG_NORMAL.equals(param.getStatus()) ? StateConstants.FLAG_NORMAL_STR : StateConstants.FLAG_DELETED_STR);
        Map<Long, List<BankWithEntityIdDo>> bankMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(bankDos)) {
            bankMap = bankDos.parallelStream().collect(Collectors.groupingBy(BankWithEntityIdDo::getEntityId));
        }
        log.info("list,bankMap:{}", JsonUtil.toJSON(bankMap));
        for (EntityDo entity : entityDos) {
            EntityVo entityVo = convertToVo(entity);
            List<BankWithEntityIdDo> bankWithEntityIdDos = bankMap.get(entity.getId());
            entityVo.setBanks(convertToBankVoList(bankWithEntityIdDos));
            resultList.add(entityVo);
        }
        result.setList(resultList);
        log.info("list,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private List<BankVo> convertToBankVoList(List<BankWithEntityIdDo> bankWithEntityIdDoList) {
        if (CollectionUtils.isEmpty(bankWithEntityIdDoList)) {
            return null;
        }
        List<BankVo> result = new ArrayList<>(bankWithEntityIdDoList.size());
        for (BankWithEntityIdDo b : bankWithEntityIdDoList) {
            result.add(new BankVo(b));
        }
        return result;
    }

    private EntityVo convertToVo(EntityDo entity) {
        if (entity == null) {
            return null;
        }
        EntityVo vo = new EntityVo();
        vo.setId(String.valueOf(entity.getId()));
        vo.setEntityName(entity.getEntityName());
        vo.setRegion(entity.getRegion());
        if (StringUtils.isNotEmpty(entity.getCurrency())) {
            vo.setCurrency(Arrays.asList(entity.getCurrency().split(StringConstants.PROP_SEPARATOR)));
        }
        vo.setRegion(entity.getRegion());
        vo.setAddress(entity.getRegisterAddress());
        vo.setType(entity.getType());
        if (entity.getFileIds() != null) {
            vo.setContractIds(Arrays.asList(entity.getFileIds().split(StringConstants.PROP_SEPARATOR)));
        }
        if (entity.getKycDocumentIds() != null) {
            vo.setKycDocumentIds(Arrays.asList(entity.getKycDocumentIds().split(StringConstants.PROP_SEPARATOR)));
        }
        return vo;
    }

    @Override
    public EntityVo detail(Long id) {
        Example condition = new Example(EntityDo.class);
        condition.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        EntityDo entityDo = entityMapper.selectOneByExample(condition);
        if (entityDo == null) {
            throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
        }
        EntityVo entityVo = convertToVo(entityDo);
        List<BankWithEntityIdDo> bankWithEntityIdDos = bankMapper.banksBySingleEntityId(id);
        log.info("detail,bankWithEntityIdDos:{}", JsonUtil.toJSON(bankWithEntityIdDos));
        if (CollectionUtils.isNotEmpty(bankWithEntityIdDos)) {
            List<BankVo> bankVos = convertToBankVoList(bankWithEntityIdDos);
            entityVo.setBanks(bankVos);
        }
        Example companyCodeCondition = new Example(EntityCompanyCodeDo.class);
        companyCodeCondition.createCriteria()
                .andEqualTo("entityId", id)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<EntityCompanyCodeDo> entityCompanyCodeDos = entityCompanyCodeMapper.selectByExample(companyCodeCondition);
        log.info("detail,entityCompanyCodeDos:{}", JsonUtil.toJSON(entityCompanyCodeDos));
        if (!CollectionUtils.isEmpty(entityCompanyCodeDos)) {
            List<EntityVo.ErpEntityId> list = new ArrayList<>(entityCompanyCodeDos.size());
            for (EntityCompanyCodeDo code : entityCompanyCodeDos) {
                EntityVo.ErpEntityId entityId = new EntityVo.ErpEntityId();
                entityId.setId(String.valueOf(code.getId()));
                entityId.setCode(code.getCompanyCode());
                list.add(entityId);
            }
            entityVo.setErpEntityIds(list);
        }
        log.info("detail,entityVo:{}", JsonUtil.toJSON(entityDo));
        return entityVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateStatus(Long id, String status) {
        // 数据原状态，如果要设置为启用/禁用，原状态为禁用/启用
        String oldStatus = StateConstants.FLAG_DELETED_STR.equals(status)
                ? StateConstants.FLAG_NORMAL_STR
                : StateConstants.FLAG_DELETED_STR;
        Example condition = new Example(EntityDo.class);
        condition.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("deleteFlag", oldStatus);
        log.info("updateStatus,id:{},status:{},oldStatus:{}", id, status, oldStatus);
        EntityDo entityDo = entityMapper.selectOneByExample(condition);
        if (entityDo == null) {
            log.info("updateStatus,entityDo is null,id:{},status:{},oldStatus:{}", id, status, oldStatus);
            throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
        }
        if (StateConstants.FLAG_DELETED_STR.equals(status)) {
            // 禁用entity时需要检查关联对象
            SupplierBankSettingDo one = supplierBankSettingMapper.selectOneByEntityId(id);
            if (one != null) {
                log.info("updateStatus,entity-supplier relation is active,could not deactivate,entityId:{}", id);
                throw throwEx(DefaultErrorMessage.ENTITY_HAS_SUPPLIER_REL);
            }
            // 删除关联的bank/companyCode数据
            entityBankSettingMapper.removeByEntityId(id);
            entityBankSettingMapper.deleteByEntityId(id);
            entityCompanyCodeMapper.removeByEntityId(id);
            entityCompanyCodeMapper.deleteByEntityId(id);
        }
        if (StateConstants.FLAG_NORMAL_STR.equals(status)) {
            // 重新启用时，检查名称是否重复
            checkEntityName(entityDo);
            // 重新启用时，检查关联的Bank是否存在
            List<EntityBankSettingDo> settings = entityBankSettingMapper.selectByEntityId(id, StateConstants.FLAG_DELETED_STR);
            if (CollectionUtils.isEmpty(settings)) {
                log.info("updateStatus,active,settings is not empty");
                throw throwEx(DefaultErrorMessage.ENTITY_BANK_SETTING_NOT_FOUND);
            }
            Set<String> bankIds = settings.stream().map(EntityBankSettingDo::getBankId).collect(Collectors.toSet());
            List<BankDo> suspendBanks = bankMapper.selectByIdsAndStatus(bankIds, StateConstants.FLAG_NORMAL_STR);
            log.info("updateStatus,active,bankIds:{},suspendBanks:{}", JsonUtil.toJSON(bankIds), JsonUtil.toJSON(suspendBanks));
            if (suspendBanks == null || suspendBanks.size() < bankIds.size()) {
                log.info("updateStatus,active,suspendBanks is not empty,suspendBanks:{}", JsonUtil.toJSON(suspendBanks));
                throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
            }
            // 重新启用时，检查是否有companyCode重复
            EntityCompanyCodeDo one = entityCompanyCodeMapper.selectOneForReactive(id);
            if (one != null) {
                log.info("updateStatus,active,entity-companyCode relation already exist,id:{},exists obj:{}", id, JsonUtil.toJSON(one));
                throw throwEx(DefaultErrorMessage.ENTITY_COMPANY_CODE_RELATION_EXIST);
            }
            // 激活关联的bank/companyCode数据
            entityBankSettingMapper.activeByEntityId(id);
            entityCompanyCodeMapper.activeByEntityId(id);
        }
        entityDo.setDeleteFlag(status);
        entityDo.setUpdateBy(entityDo.getUpdateBy());
        entityDo.setUpdateTime(LocalDateTime.now());
        entityMapper.updateByPrimaryKey(entityDo);
        return true;
    }

    private void checkEntityName(EntityDo entity) {
        Example exist = new Example(EntityDo.class);
        exist.createCriteria()
                .andEqualTo("entityName", entity.getEntityName())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<EntityDo> entityDos = entityMapper.selectByExample(exist);
        if (CollectionUtils.isNotEmpty(entityDos)) {
            throw throwEx(DefaultErrorMessage.ENTITY_NAME_EXIST);
        }
    }

}
