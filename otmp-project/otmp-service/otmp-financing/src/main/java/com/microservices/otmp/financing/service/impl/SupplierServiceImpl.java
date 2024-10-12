package com.microservices.otmp.financing.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.constant.StateConstants;
import com.microservices.otmp.common.core.domain.ResultDTO;
import com.microservices.otmp.common.core.page.TableDataInfo;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.exception.message.DefaultErrorMessage;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.JsonUtil;
import com.microservices.otmp.common.utils.SnowFlakeUtil;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.financing.config.KafkaFactory;
import com.microservices.otmp.financing.constant.NoticeConstant;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.constant.SupplierConstants;
import com.microservices.otmp.financing.domain.dto.NoticeSendDto;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.param.IdParam;
import com.microservices.otmp.financing.domain.param.supplier.*;
import com.microservices.otmp.financing.domain.vo.supplier.*;
import com.microservices.otmp.financing.mapper.*;
import com.microservices.otmp.financing.domain.param.supplier.*;
import com.microservices.otmp.financing.domain.vo.supplier.*;
import com.microservices.otmp.financing.mapper.*;
import com.microservices.otmp.financing.remote.RemoteFileService;
import com.microservices.otmp.financing.remote.RemoteSupplierDataService;
import com.microservices.otmp.financing.remote.RemoteSystemService;
import com.microservices.otmp.financing.remote.param.BatchUpdateParam;
import com.microservices.otmp.financing.remote.param.FileUpdateParam;
import com.microservices.otmp.financing.remote.param.SupplierActivationParam;
import com.microservices.otmp.financing.remote.param.SupplierCodeParam;
import com.microservices.otmp.financing.remote.result.RegisterResult;
import com.microservices.otmp.financing.remote.result.RemoteSysUserVO;
import com.microservices.otmp.financing.remote.result.SupplierInfoResult;
import com.microservices.otmp.financing.service.SupplierService;
import com.microservices.otmp.financing.util.RedisKeyUtil;
import com.microservices.otmp.financing.util.SendMailUtil;
import com.microservices.otmp.system.domain.SysUser;
import com.microservices.otmp.system.feign.RemoteUserAnchorService;
import com.microservices.otmp.system.feign.RemoteUserService;
import com.microservices.otmp.system.feign.RemoteUserSupplierService;
import com.microservices.otmp.financing.constant.NoticeConstant;
import com.microservices.otmp.financing.constant.StringConstants;
import com.microservices.otmp.financing.constant.SupplierConstants;
import com.microservices.otmp.financing.remote.param.BatchUpdateParam;
import com.microservices.otmp.financing.remote.param.FileUpdateParam;
import com.microservices.otmp.financing.remote.param.SupplierCodeParam;
import com.microservices.otmp.financing.remote.result.RegisterResult;
import com.microservices.otmp.financing.remote.result.RemoteSysUserVO;
import com.microservices.otmp.financing.remote.result.SupplierInfoResult;
import com.microservices.otmp.financing.service.SupplierService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SupplierServiceImpl implements SupplierService {

    private static final int ACCOUNT_UPDATE_SUCCESS_CODE = 200;
    private static final String USER_TYPE = "02";
    @Value("${role.supplier.admin}")
    private Long supplierAdminRoleId;
    @Value("${kafka.topic.notice}")
    private String sendNoticeTopic;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private SupplierBankSettingMapper supplierBankSettingMapper;
    @Autowired
    private InviteLinkMapper inviteLinkMapper;
    @Autowired
    private SupplierCompanyCodeMapper supplierCompanyCodeMapper;
    @Autowired
    private EntityMapper entityMapper;
    @Autowired
    private EntityCompanyCodeMapper entityCompanyCodeMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private SupplierUniqueMapper supplierUniqueMapper;
    @Autowired
    private EntityBankSettingMapper entityBankSettingMapper;
    @Autowired
    private SysUserFinancingMapper sysUserFinancingMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SendMailUtil mailUtil;
    @Autowired
    private RemoteSupplierDataService remoteSupplierDataService;
    @Autowired
    private RemoteFileService remoteFileService;
    @Autowired
    private RemoteSystemService remoteSystemService;
    @Autowired
    private RemoteUserService remoteUserService;
    @Autowired
    private RemoteUserAnchorService remoteUserAnchorService;
    @Autowired
    private RemoteUserSupplierService remoteUserSupplierService;
    @Autowired
    private KafkaSend sender;

    private OtmpException throwEx(Long errCode) {
        return new OtmpException(DefaultErrorMessage.getErrorMessage(errCode), errCode.intValue());
    }

    /**
     * 变更supplier状态（suspend/active）
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateStatus(Long id, Integer status) {
        log.info("updateStatus,id:{},status:{}", id, status);
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(id);
        // supplier不存在
        if (supplierDo == null) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        // supplier不是active、suspend、onHold状态
        if (!SupplierDo.STATUS_ACTIVE.equals(supplierDo.getStatus())
                && !SupplierDo.STATUS_SUSPEND.equals(supplierDo.getStatus())
                && !SupplierDo.STATUS_ON_HOLD.equals(supplierDo.getStatus())) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_STATUS_IS_WRONG);
        }
        log.info("updateStatus,id:{},newStatus:{},oldStatus:{}", id, status, supplierDo.getStatus());
        int result = supplierMapper.updateStatus(id, status, supplierDo.getStatus());
        log.info("updateStatus,result:{}", result);
        // 调用LTP
        List<SupplierDo> activationParam = Collections.singletonList(supplierDo);
        List<SupplierActivationParam> supplierParams;
        // 激活Supplier
        if (SupplierDo.STATUS_ACTIVE.equals(status)) {
            // 检查绑定的Bank和Entity是否存在
            List<SupplierBankSettingDo> settings = supplierBankSettingMapper.selectBySupplierId(id, StateConstants.FLAG_DELETED_STR);
            if (CollectionUtils.isEmpty(settings)) {
                log.info("updateStatus,active,setting is empty");
                throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
            }
            Set<String> suspendBankIds = settings.stream().map(SupplierBankSettingDo::getBankId).collect(Collectors.toSet());
            List<BankDo> bankDos = bankMapper.selectByIdsAndStatus(suspendBankIds, StateConstants.FLAG_NORMAL_STR);
            log.info("updateStatus,active,suspendBankIds:{},bankDos:{}", JsonUtil.toJSON(suspendBankIds), JsonUtil.toJSON(bankDos));
            if (bankDos == null || bankDos.size() < suspendBankIds.size()) {
                throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
            }
            Set<Long> suspendEntityIds = settings.stream().map(SupplierBankSettingDo::getEntityId).collect(Collectors.toSet());
            List<EntityDo> entityDos = entityMapper.selectByIdsAndStatus(suspendEntityIds, StateConstants.FLAG_NORMAL_STR);
            log.info("updateStatus,active,suspendEntityIds:{},entityDos:{}", JsonUtil.toJSON(suspendEntityIds), JsonUtil.toJSON(entityDos));
            if (entityDos == null || entityDos.size() < suspendEntityIds.size()) {
                throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
            }
            // 判断vendorCode是否与其他supplier绑定的vendorCode重复
            SupplierUniqueIdDo uniqueIdDo = supplierUniqueMapper.selectOneForReactiveSupplier(id);
            if (uniqueIdDo != null) {
                log.info("updateStatus,active,supplier code relation already exist");
                throw throwEx(DefaultErrorMessage.SUPPLIER_CODE_RELATION_EXIST);
            }
            // 变更supplier相关数据状态（bankSetting，vendorCode，companyCode）
            supplierBankSettingMapper.activeBySupplierId(supplierDo.getId());
            supplierUniqueMapper.activeBySupplierId(supplierDo.getId());
            supplierCompanyCodeMapper.activeBySupplierId(supplierDo.getId());
            // 生成LTP调用参数
            supplierParams = getSupplierActivationParams("updateStatus,active", activationParam, SupplierConstants.REMOTE_STATUS_ACTIVE);
            // 调用LTP
            ResultDTO<Object> execResult = remoteSupplierDataService.callBackBankSuppliers(supplierParams);
            log.info("updateStatus,reactive,execResult:{}", JsonUtil.toJSON(execResult));
            if (!checkExecResult(execResult, false)) {
                log.error("updateStatus,active remote execute error,supplierId:{},msg:{}", supplierDo.getId(), execResult.getMsg());
                throw throwEx(DefaultErrorMessage.SUPPLIER_REMOTE_ACTIVE_FAILED);
            }
        }
        if (SupplierDo.STATUS_SUSPEND.equals(status)) {
            // 生成LTP调用参数
            supplierParams = getSupplierActivationParams("updateStatus,suspend", activationParam, SupplierConstants.REMOTE_STATUS_SUSPEND);
            // 调用LTP
            ResultDTO<Object> execResult = remoteSupplierDataService.callBackBankSuppliers(supplierParams);
            log.info("updateStatus,suspend,execResult:{}", JsonUtil.toJSON(execResult));
            if (!checkExecResult(execResult, false)) {
                log.error("updateStatus,suspend remote execute error,supplierId:{},msg:{}", supplierDo.getId(), execResult.getMsg());
                throw throwEx(DefaultErrorMessage.SUPPLIER_REMOTE_SUSPEND_FAILED);
            }
            // 变更supplier相关数据状态
            // delete old bank_setting
            supplierBankSettingMapper.removeBySupplierId(supplierDo.getId());
            // update bank_setting
            supplierBankSettingMapper.deleteBySupplierId(supplierDo.getId());
            // delete old supplier_unique
            supplierUniqueMapper.removeBySupplierId(supplierDo.getId());
            // update supplier_unique
            supplierUniqueMapper.deleteBySupplierId(supplierDo.getId());
            // delete old supplier_company_code
            supplierCompanyCodeMapper.removeBySupplierId(supplierDo.getId());
            // update supplier_company_code
            supplierCompanyCodeMapper.deleteBySupplierId(supplierDo.getId());
        }
        return result != 0;
    }

    /**
     * supplier列表
     */
    @Override
    public PageInfo<SupplierVo> list(SupplierListParam param, Integer status) {
        log.info("list,status:{},param:{}", status, JsonUtil.toJSON(param));
        // 创建查询条件(如果是active状态则查询active和onHold状态)
        Example condition = SupplierDo.STATUS_ACTIVE.equals(status)
                ? createQueryListCondition(param, SupplierDo.STATUS_ACTIVE, SupplierDo.STATUS_ON_HOLD)
                : createQueryListCondition(param, status);
        condition.setOrderByClause("activation_date DESC");
        int pageNum = param.getPageNum() != null ? param.getPageNum() : Constants.DEFAULT_PAGE_NUM;
        int pageSize = param.getPageSize() != null ? param.getPageSize() : Constants.DEFAULT_PAGE_SIZE;
        PageHelper.startPage(pageNum, pageSize);
        List<SupplierDo> suppliers = supplierMapper.selectByExample(condition);
        PageInfo<SupplierDo> pageInfo = new PageInfo<>(suppliers);
        PageInfo<SupplierVo> result = new PageInfo<>();
        result.setTotal(pageInfo.getTotal());
        result.setPageNum(pageInfo.getPageNum());
        result.setPageSize(pageInfo.getPageSize());
        log.info("list,suppliers:{}", JsonUtil.toJSON(suppliers));
        if (CollectionUtils.isEmpty(suppliers)) {
            result.setList(new ArrayList<>());
            return result;
        }

        List<SupplierVo> supplierList;
        if (SupplierDo.STATUS_AWAITING.equals(status) || SupplierDo.STATUS_PENDING.equals(status)) {
            // awaiting、pending状态的supplier直接转换为vo对象
            supplierList = new ArrayList<>(suppliers.size());
            convertToSupplierPendingVo(supplierList, suppliers);
        } else if (SupplierDo.STATUS_ACTIVE.equals(status) || SupplierDo.STATUS_SUSPEND.equals(status)) {
            // active(包含onHold)、suspend状态的supplier查询绑定的银行、公司信息
            supplierList = new ArrayList<>(suppliers.size());
            fillInfo(supplierList, suppliers, status);
        } else {
            log.error("list,status is wrong,status:{}", status);
            throw throwEx(DefaultErrorMessage.STATUS_IS_WRONG);
        }
        result.setList(supplierList);
        log.info("list,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private void fillInfo(List<SupplierVo> supplierVos, List<SupplierDo> suppliers, int status) {
        List<Long> supplierIds = suppliers.parallelStream().map(SupplierDo::getId).collect(Collectors.toList());
        log.info("fillInfo,supplierIds:{}", supplierIds);
//        boolean needStatus = SupplierDo.STATUS_ACTIVE.equals(status);
        String deleteFlag = SupplierDo.STATUS_ACTIVE.equals(status)
                ? StateConstants.FLAG_NORMAL_STR
                : StateConstants.FLAG_DELETED_STR;
        Example supplierUniqueCondition = new Example(SupplierUniqueIdDo.class);
        supplierUniqueCondition.createCriteria()
                .andEqualTo("deleteFlag", deleteFlag)
                .andIn("supplierId", supplierIds);

        List<SupplierUniqueIdDo> supplierUniqueIdDos = supplierUniqueMapper.selectByExample(supplierUniqueCondition);
        Map<Long, List<SupplierUniqueIdDo>> supplierUniqueIdMap;
        if (CollectionUtils.isEmpty(supplierUniqueIdDos)) {
            supplierUniqueIdMap = new HashMap<>();
        } else {
            supplierUniqueIdMap = supplierUniqueIdDos.stream().collect(Collectors.groupingBy(SupplierUniqueIdDo::getSupplierId));
        }
        log.info("fillInfo,supplierUniqueIdMap:{}", JsonUtil.toJSON(supplierUniqueIdMap));
        // 查询entity相关信息
        List<SupplierCompanyCodeWithEntityDo> supplierCompanyCodeWithEntityDos =
                supplierCompanyCodeMapper.selectAllCompanyCodeBySupplier(supplierIds, deleteFlag);
        // 按supplierId分组
        Map<Long, List<SupplierCompanyCodeWithEntityDo>> supplierCompanyCodeDoMap;
        if (CollectionUtils.isEmpty(supplierCompanyCodeWithEntityDos)) {
            supplierCompanyCodeDoMap = new HashMap<>();
        } else {
            supplierCompanyCodeDoMap = supplierCompanyCodeWithEntityDos.parallelStream()
                    .collect(Collectors.groupingBy(SupplierCompanyCodeWithEntityDo::getSupplierId));
        }
        log.info("fillInfo,supplierCompanyCodeDoMap:{}", JsonUtil.toJSON(supplierCompanyCodeDoMap));
        // 查询bank相关信息
        List<BankNameDo> entityBankNameDos = supplierBankSettingMapper.selectEntityAndBankName(supplierIds, deleteFlag);
        log.info("fillInfo,entityBankNameDos:{}", JsonUtil.toJSON(entityBankNameDos));
        // 按supplierId分组
        Map<Long, List<BankNameDo>> bankNameMap;
        if (CollectionUtils.isEmpty(entityBankNameDos)) {
            bankNameMap = new HashMap<>();
        } else {
            bankNameMap = entityBankNameDos.parallelStream().collect(Collectors.groupingBy(BankNameDo::getSupplierId));
        }
        log.info("fillInfo,bankNameMap:{}", JsonUtil.toJSON(bankNameMap));

        List<EntityWithSupplierId> entityList = entityMapper.selectEnitiesBySupplierIds(supplierIds);
        Map<Long, List<EntityWithSupplierId>> linkedEntityMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(entityList)) {
            linkedEntityMap = entityList.stream().collect(Collectors.groupingBy(EntityWithSupplierId::getSupplierId));
        }

        for (SupplierDo supplier : suppliers) {
            SupplierActiveVo vo = new SupplierActiveVo();
            vo.setSupplierModel(supplier.getSupplierModel());
            vo.setFinancingModel(supplier.getFinancingModel());
            vo.setEmail(supplier.getContactEmail());
            vo.setId(String.valueOf(supplier.getId()));
            vo.setSupplierName(supplier.getSupplierName());
            vo.setStatus(supplier.getStatus());
            vo.setActivationDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, supplier.getActivationDate()));
            if (SupplierDo.STATUS_ON_HOLD.equals(vo.getStatus())) {
                if (supplier.getOnHoldStartTime() != null) {
                    vo.setOnHoldStartTime(DateUtils.parseDateToStr(DateUtils.DATE_TIME_PATTERN, localDateTimeToDate(supplier.getOnHoldStartTime())));
                }
                if (supplier.getOnHoldEndTime() != null) {
                    vo.setOnHoldEndTime(DateUtils.parseDateToStr(DateUtils.DATE_TIME_PATTERN, localDateTimeToDate(supplier.getOnHoldEndTime())));
                }
            }
            Date modifiedDate = SupplierDo.STATUS_ACTIVE.equals(supplier.getStatus()) ? supplier.getActivationDate() : supplier.getSuspendDate();
            if (modifiedDate != null) {
                vo.setModifiedDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, modifiedDate));
            }
            List<EntityWithSupplierId> entityWithSupplierIds = linkedEntityMap.get(supplier.getId());
            if (CollectionUtils.isEmpty(entityWithSupplierIds)) {
                vo.setLinkedEntities(new ArrayList<>());
            } else {
                List<String> entityNames = entityWithSupplierIds.stream().map(EntityWithSupplierId::getEntityName).distinct().collect(Collectors.toList());
                vo.setLinkedEntities(entityNames);
            }

            // 根据supplierId从Map中获取供应商关联的entity
            List<SupplierCompanyCodeWithEntityDo> companyCodes = supplierCompanyCodeDoMap.get(supplier.getId());
            if (CollectionUtils.isEmpty(companyCodes)) {
                vo.setErpList(new ArrayList<>());
            } else {
                // erp相关信息
                List<SupplierActiveVo.ErpMapping> erpList = convertToMappingFromSupplierUnique(supplierUniqueIdMap.get(supplier.getId()));
//                log.info("fillInfo,erpList:{}", JsonUtil.toJSON(erpList));
                vo.setErpList(erpList);
                // entity
//                List<SupplierCompanyCodeWithEntityDo> groupByName = companyCodes.stream().collect(Collectors.collectingAndThen(
//                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SupplierCompanyCodeWithEntityDo::getEntityName))), ArrayList::new
//                ));
//                List<String> entityNames = groupByName.stream().map(SupplierCompanyCodeWithEntityDo::getEntityName).collect(Collectors.toList());
//                vo.setLinkedEntities(entityNames);
            }
            // 根据supplierId从Map中获取供应商关联的bank
            List<BankNameDo> entityBankNames = bankNameMap.get(supplier.getId());
//            log.info("fillInfo,entityBankNames:{}",JsonUtil.toJSON(entityBankNames));
            if (CollectionUtils.isEmpty(entityBankNames)) {
                vo.setLinkedBanks(new ArrayList<>());
//                vo.setLinkedEntities(new ArrayList<>());
            } else {
                List<BankNameDo> listByBankName = entityBankNames.stream().collect(Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(BankNameDo::getBankName))), ArrayList::new
                ));
                List<String> bankNames = listByBankName.stream().map(BankNameDo::getBankName).collect(Collectors.toList());
                vo.setLinkedBanks(bankNames);
            }
//            if(StringUtils.isNotEmpty(supplier.getFileIds())){
//                vo.setFileIds(new ArrayList<>(Arrays.asList(supplier.getFileIds().split(StringConstants.PROP_SEPARATOR))));
//            }
            vo.setNeedUpdate(supplier.getNeedUpdate());
            supplierVos.add(vo);
        }
        log.info("fillInfo,result:{}", JsonUtil.toJSON(supplierVos));
    }

    private List<SupplierActiveVo.ErpMapping> convertToMappingFromSupplierUnique(List<SupplierUniqueIdDo> supplierUniqueIdDos) {
        if (CollectionUtils.isEmpty(supplierUniqueIdDos)) {
            return new ArrayList<>();
        }
//        Set<SupplierActiveVo.ErpMapping> result = new HashSet<>(supplierUniqueIdDos.size());
        TreeSet<SupplierActiveVo.ErpMapping> result = new TreeSet<>(new Comparator<SupplierActiveVo.ErpMapping>() {
            @Override
            public int compare(SupplierActiveVo.ErpMapping o1, SupplierActiveVo.ErpMapping o2) {
                if (o1 == null || StringUtils.isEmpty(o1.getErpId()) || o2 == null || StringUtils.isEmpty(o2.getErpId())) {
                    return 0;
                }
                int result = Long.valueOf((o1.getErpId())).compareTo(Long.valueOf(o2.getErpId()));
                if (result == 0) {
                    if (StringUtils.isEmpty(o1.getEntityCode()) || StringUtils.isEmpty(o2.getEntityCode())) {
                        return 0;
                    }
                    return Integer.compare(o1.getEntityCode().hashCode(), o2.getEntityCode().hashCode());
                }
                return result;
            }
        });
        for (SupplierUniqueIdDo unique : supplierUniqueIdDos) {
            String commonCompanyCode = unique.getCommonCompanyCode();
            if (StringUtils.isEmpty(commonCompanyCode)) {
                continue;
            }
            String[] commonCodes = commonCompanyCode.split(StringConstants.PROP_SEPARATOR);
            for (String code : commonCodes) {
                SupplierActiveVo.ErpMapping mapping = new SupplierActiveVo.ErpMapping();
                mapping.setErpId(unique.getSupplierCode());
                mapping.setEntityCode(code);
                result.add(mapping);
            }
        }
        log.info("convertToMappingFromSupplierUnique,result:{}", JsonUtil.toJSON(result));
        return new ArrayList<>(result);
    }

    private void convertToSupplierPendingVo(List<SupplierVo> supplierVos, List<SupplierDo> suppliers) {
        for (SupplierDo supplier : suppliers) {
            SupplierPendingVo vo = new SupplierPendingVo();
            vo.setId(String.valueOf(supplier.getId()));
            vo.setSupplierName(supplier.getSupplierName());
            vo.setFirstName(supplier.getContactFirstName());
            vo.setLastName(supplier.getContactLastName());
            vo.setMail(supplier.getContactEmail());
            vo.setPhone(supplier.getContactPhone());
            vo.setPhoneRegion(supplier.getPhoneRegion());
            vo.setDesignation(supplier.getContactDestination());
            vo.setInviteDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, supplier.getInvitationDate()));
            vo.setOnShore(supplier.getOnShoreFlag() != null && SupplierDo.ON_SHORE_TRUE.equals(supplier.getOnShoreFlag()));
            supplierVos.add(vo);
        }
    }

    private Example createQueryListCondition(SupplierListParam param, Integer... status) {
        Example example = new Example(SupplierDo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
//        criteria.andEqualTo("status", status);
        criteria.andIn("status", Arrays.asList(status));
        if (param != null) {
            if (StringUtils.isNotEmpty(param.getSupplierName())) {
                criteria.andLike("supplierName", "%" + param.getSupplierName() + "%");
            }
            if (param instanceof AwaitingSupplierListParam) {
                AwaitingSupplierListParam p = (AwaitingSupplierListParam) param;
                criteria.andEqualTo("contactEmail", p.getEmailAddress());
                String inviteStartTime = ((AwaitingSupplierListParam) param).getInviteStartTime();
                String inviteEndTime = ((AwaitingSupplierListParam) param).getInviteEndTime();
                setTime(inviteStartTime, criteria, inviteEndTime);
            }
            if (param instanceof PendingSupplierListParam) {
                PendingSupplierListParam p = (PendingSupplierListParam) param;
                criteria.andEqualTo("contactEmail", p.getEmailAddress());
                String inviteStartTime = ((PendingSupplierListParam) param).getInviteStartTime();
                String inviteEndTime = ((PendingSupplierListParam) param).getInviteEndTime();
                setTime(inviteStartTime, criteria, inviteEndTime);
            }
            if (param instanceof ActiveSupplierListParam) {
                SysUser sysUser = remoteUserService.selectSysUserByUserId(param.getUserId());
//                SysUser sysUser = new SysUser();
                if (sysUser == null) {
                    log.info("list,sysUser is null,userId:{}", param.getUserId());
                    throw throwEx(DefaultErrorMessage.ERR_USER_NOT_EXIST);
                }
                // do nothing
                ActiveSupplierListParam p = (ActiveSupplierListParam) param;
//                criteria.andEqualTo("activationDate", p.getActivationDate());
                if (StringUtils.isNotEmpty(p.getFinancingModel())) {
                    criteria.andEqualTo("financingModel", p.getFinancingModel());
                }
                if (StringUtils.isNotEmpty(p.getSupplierModel())) {
                    criteria.andEqualTo("supplierModel", p.getSupplierModel());
                }
                if (StringUtils.isNotEmpty(p.getEmail())) {
                    criteria.andLike("contactEmail", "%" + p.getEmail() + "%");
                }
                if (USER_TYPE.equals(sysUser.getUserType())) {
                    criteria.andEqualTo("id", Long.valueOf(sysUser.getSupplierId()));
                }
            }
            if (param instanceof SuspendSupplierListParam) {
                // do nothing
                SuspendSupplierListParam p = (SuspendSupplierListParam) param;
//                criteria.andEqualTo("activationDate", p.getActivationDate());
                if (StringUtils.isNotEmpty(p.getFinancingModel())) {
                    criteria.andEqualTo("financingModel", p.getFinancingModel());
                }
                if (StringUtils.isNotEmpty(p.getSupplierModel())) {
                    criteria.andEqualTo("supplierModel", p.getSupplierModel());
                }
                if (StringUtils.isNotEmpty(p.getEmail())) {
                    criteria.andLike("contactEmail", "%" + p.getEmail() + "%");
                }
            }
        }
        return example;
    }

    private void setTime(String inviteStartTime, Example.Criteria criteria, String inviteEndTime) {
        try {
            if (StringUtils.isNotEmpty(inviteStartTime)) {
                criteria.andGreaterThanOrEqualTo("invitationDate", DateUtils.dateParse(inviteStartTime, DateUtils.DATE_PATTERN));
            }
            if (StringUtils.isNotEmpty(inviteEndTime)) {
                Date date = DateUtils.dateParse(inviteEndTime, DateUtils.DATE_PATTERN);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.add(Calendar.DATE, 1);
                criteria.andLessThan("invitationDate", calendar.getTime());
            }
        } catch (ParseException e) {
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String invite(MailParam param) {
        log.info("invite,param:{}", JsonUtil.toJSON(param));
        String lockKey = RedisKeyUtil.getSupplierInvLockKey(param.getCompanyName());
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (!lock.tryLock(3, TimeUnit.SECONDS)) {
                log.info("invite,try lock failed,lockKey:{}", lockKey);
                throw throwEx(DefaultErrorMessage.DUPLICATE_INVITE);
            }
            Date now = DateUtils.getNowDate();
            // 插入数据，考虑可能出现数据插入失败的情况，先插入数据再发送邮件，避免邮件已发送但db无数据的情况。
            Example example = new Example(SupplierDo.class);
            Example.Criteria criteria = example.createCriteria();
//            criteria.andEqualTo("supplierName", param.getCompanyName());
            criteria.andEqualTo("contactEmail", param.getEmail());
            criteria.andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
            // 查询是否存在
            SupplierDo supplierDo = supplierMapper.selectOneByExample(example);
            if (supplierDo == null) {
                // 不存在（首次邀请）直接插入
                log.info("invite,send mail to:{},companyName:{}", param.getEmail(), param.getCompanyName());
                supplierDo = new SupplierDo();
                supplierDo.setId(SnowFlakeUtil.nextId());
                fillSupplierProp(param, supplierDo);
                supplierDo.setInvitationDate(now);
                supplierDo.setCreateTime(dateToLocalDateTime(now));
                supplierDo.setCreateBy(param.getCreateBy());
                supplierMapper.insert(supplierDo);
            } else {
                // 如果供应商不是awaiting状态（是待激活/已激活/禁用状态），提示已经邀请过
                if (!SupplierDo.STATUS_AWAITING.equals(supplierDo.getStatus())) {
                    log.error("invite,supplier:{} already invited", supplierDo.getSupplierName());
                    throw throwEx(DefaultErrorMessage.ALREADY_INVITED);
                }
                // 存在（重新发送邀请）则更新
                log.info("invite,resend mail to:{},companyName:{}", param.getEmail(), param.getCompanyName());
                fillSupplierProp(param, supplierDo);
                supplierDo.setUpdateTime(dateToLocalDateTime(now));
                supplierDo.setUpdateBy(param.getUpdateBy());
                supplierMapper.updateByPrimaryKey(supplierDo);
            }
            // 查询邀请记录
            Example inviteLineExample = new Example(InviteLinkDo.class);
            inviteLineExample.createCriteria().andEqualTo("supplierName", param.getCompanyName())
                    .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
            InviteLinkDo inviteLink = inviteLinkMapper.selectOneByExample(inviteLineExample);
            if (inviteLink == null) {
                // 保存邀请记录
                inviteLink = new InviteLinkDo();
                inviteLink.setInviteTime(now);
                inviteLink.setId(SnowFlakeUtil.nextId());
                inviteLink.setLinkUniqueCode(UUID.randomUUID().toString().replace("-", ""));
                inviteLink.setSupplierName(param.getCompanyName());
                inviteLink.setSupplierId(supplierDo.getId());
                inviteLink.setCreateBy(param.getCreateBy());
                inviteLink.setCreateTime(now);
                inviteLink.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
                inviteLinkMapper.insert(inviteLink);
            } else {
                // 更新邀请记录
                inviteLink.setSupplierId(supplierDo.getId());
                inviteLink.setInviteTime(now);
                inviteLink.setUpdateTime(now);
                inviteLink.setUpdateBy(param.getUpdateBy());
                inviteLinkMapper.updateByPrimaryKey(inviteLink);
            }
            String targetName = param.getLastName() + StringConstants.SPACE_STR + param.getFirstName();
            log.info("invite,targetName:{}", targetName);
            targetName = StringUtils.isEmpty(targetName.trim()) ? inviteLink.getSupplierName() : targetName;
            boolean sendResult = mailUtil.sendInviteMail(param.getCreateBy(), inviteLink.getLinkUniqueCode(), param.getEmail(), targetName, targetName);
            if (!sendResult) {
                log.error("invite,send mail failed,supplierId:{},target:{}", supplierDo.getId(), param.getEmail());
                throw throwEx(DefaultErrorMessage.EMAIL_SEND_FAIL);
            }
            return inviteLink.getLinkUniqueCode();
        } catch (InterruptedException e) {
            log.error("invite,lock error,lockKey:{}", lockKey);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private void fillSupplierProp(MailParam param, SupplierDo supplierDo) {
        supplierDo.setContactFirstName(param.getFirstName());
        supplierDo.setContactLastName(param.getLastName());
        supplierDo.setSupplierName(param.getCompanyName());
        supplierDo.setContactPhone(param.getPhone());
        supplierDo.setPhoneRegion(param.getPhoneRegion());
        supplierDo.setContactDestination(param.getDesignation());
        supplierDo.setContactEmail(param.getEmail());
        supplierDo.setStatus(SupplierDo.STATUS_AWAITING);
        supplierDo.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
        supplierDo.setOnHoldFlag(SupplierDo.ON_HOLD_FALSE);
        supplierDo.setOnShoreFlag((param.getOnShore() != null && param.getOnShore()) ? SupplierDo.ON_SHORE_TRUE : SupplierDo.ON_SHORE_FALSE);
        supplierDo.setNeedUpdate(false);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteInvite(Long id, String updateBy) {
        log.info("deleteInvite,id:{}", id);
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria().andEqualTo("id", id);
        SupplierDo supplier = supplierMapper.selectOneByExample(condition);
        if (supplier == null) {
            log.info("deleteInvite,supplier is null,id:{}", id);
            return true;
        }
        if (!SupplierDo.STATUS_AWAITING.equals(supplier.getStatus())) {
            log.info("deleteInvite,supplier status is wrong,status:{}", supplier.getStatus());
            throw throwEx(DefaultErrorMessage.SUPPLIER_STATUS_IS_WRONG);
        }
        supplier.setDeleteFlag(StateConstants.FLAG_DELETED_STR);
        supplier.setUpdateTime(LocalDateTime.now());
        supplier.setUpdateBy(updateBy);
        supplierMapper.updateByPrimaryKey(supplier);
        return true;
    }

    @Override
    public InviteLinkVo companyName(String inventionCode) {
        log.info("companyName,inventionCode:{}", inventionCode);
        InviteLinkSupplierDo inviteLinkDo = inviteLinkMapper.companyName(inventionCode);
        if (inviteLinkDo == null) {
            log.error("companyName,invalid inventionCode:{}", inventionCode);
            throw throwEx(DefaultErrorMessage.INVALID_INVITE_CODE);
        }
        InviteLinkVo result = new InviteLinkVo();
        result.setCompanyName(inviteLinkDo.getSupplierName());
        result.setInviteDate(DateUtils.parseDateToStr(DateUtils.DATE_PATTERN, inviteLinkDo.getInviteTime()));
        result.setFirstName(inviteLinkDo.getFirstName());
        result.setLastName(inviteLinkDo.getLastName());
        result.setEmail(inviteLinkDo.getEmail());
        result.setPhone(inviteLinkDo.getPhone());
        result.setDesignation(inviteLinkDo.getDesignation());
        result.setPhoneRegion(inviteLinkDo.getPhoneRegion());
        result.setInviteCode(inventionCode);
        result.setOnshore(SupplierDo.ON_SHORE_TRUE.equals(inviteLinkDo.getOnshore()));
        log.info("companyName,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private static Example createInviteQueryCondition(String inviteCode) {
        Example example = new Example(InviteLinkDo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("linkUniqueCode", inviteCode);
        criteria.andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        return example;
    }

    @Override
    public ResultDTO<Boolean> register(RegisterParam param) {
        log.info("register,param:{}", JsonUtil.toJSON(param));
        String lockKey = RedisKeyUtil.getSupplierRegLockKey(param.getInventionCode());
        RLock lock = redissonClient.getLock(lockKey);
        try {
            if (!lock.tryLock(5, TimeUnit.SECONDS)) {
                log.info("register,try lock failed,lockKey:{}", lockKey);
                throw throwEx(DefaultErrorMessage.DUPLICATE_REGISTER);
            }
            // 检查邀请码对应的信息是否存在
            Example condition = createInviteQueryCondition(param.getInventionCode());
            InviteLinkDo inviteLinkDo = inviteLinkMapper.selectOneByExample(condition);
            if (inviteLinkDo == null) {
                log.error("register,invalid inviteCode:{}", param.getInventionCode());
                throw throwEx(DefaultErrorMessage.INVALID_INVITE_CODE);
            }
            // 对比数据库中的公司名称与参数中的名称
            String companyNameInDb = inviteLinkDo.getSupplierName();
            if (StringUtils.isNotEmpty(companyNameInDb) && !companyNameInDb.equals(param.getCompanyName())) {
                log.error("register,supplier name not match,supplier name in db:{},in param:{}", companyNameInDb, param.getCompanyName());
                throw throwEx(DefaultErrorMessage.COMPANY_NAME_NOT_MATCH);
            }

            // 检查供应商在系统中是否存在。
            SupplierDo supplier = supplierMapper.selectOneByExample(createSupplierExistCondition(inviteLinkDo.getSupplierName()));
            log.info("register,supplier:{}", JsonUtil.toJSON(supplier));
            if (supplier == null) {
                log.info("register,supplier not found,supplierName:{}", inviteLinkDo.getSupplierName());
                throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
            }
            Integer supplierStatus = supplier.getStatus();
            // supplier不为空，判断用户状态
            if (SupplierDo.STATUS_AWAITING.equals(supplierStatus)) {
                // 判断是否首次onboarding，不是则提示联系管理员
                Example example = createSupplierExistCondition(param.getCompanyName());
                SupplierDo supplierByCompanyName = supplierMapper.selectOneByExample(example);
                boolean first = supplierByCompanyName != null
                        && SupplierDo.STATUS_PENDING.equals(supplierByCompanyName.getStatus());
                if (first) {
                    throw throwEx(DefaultErrorMessage.CONTACT_SUPPLIER_ADMIN);
                }
                supplier.setPhoneRegion(param.getPhoneRegion());
                supplier.setContactPhone(param.getPhone());
                supplier.setContactDestination(param.getDesignation());
                // 更新supplier信息及状态(awaiting -> pending)
                supplier.setStatus(SupplierDo.STATUS_PENDING);
                supplier.setUpdateTime(LocalDateTime.now());
                supplierMapper.updateByPrimaryKey(supplier);
                // 发送邮件和系统消息
                sendMailAndNotice(supplier);
                return ResultDTO.success(true);
            } else if (SupplierDo.STATUS_PENDING.equals(supplierStatus)) {
                // 提示注册完成，需要等待Anchor激活
                throw throwEx(DefaultErrorMessage.WAITING_FOR_ACTIVATION);
            } else if (SupplierDo.STATUS_ACTIVE.equals(supplierStatus)) {
                // 激活状态，跳转登录
                throw throwEx(DefaultErrorMessage.REDIRECT_TO_LOGIN);
            } else if (SupplierDo.STATUS_SUSPEND.equals(supplierStatus)) {
                // 提示供应商已禁用?
                throw throwEx(DefaultErrorMessage.SUPPLIER_HAS_BEEN_SUSPEND);
            } else {
                // 异常，错误的status值
                log.error("register,status error,status:{}", supplier.getStatus());
                throw throwEx(DefaultErrorMessage.SERVER_ERROR);
            }
        } catch (InterruptedException e) {
            log.error("register,lock error,lockKey:{}", lockKey);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

/*    public static void main(String[] args) {
        String json = "{\"createBy\":\"zhengzk2@microservices.com\",\"createTime\":\"11/01/2023 10:23:16\",\"params\":{},\"userId\":74,\"loginName\":\"1234573\",\"userName\":\"1234573\",\"avatar\":\"\",\"dateStyle\":\"yyyy-MM-dd\",\"lang\":\"en_US\",\"status\":\"0\"}";
        RemoteSysUserVO vo = JsonUtil.jsonToObject(json, RemoteSysUserVO.class);
        System.out.println(vo);
    }*/

    private void sendMailAndNotice(SupplierDo supplier) {
        try {
            // 发送supplier注册邮件给supplier
            String supplierName = supplier.getSupplierName();
            SendMailUtil.MailContentBean contentBean = new SendMailUtil.MailContentBean(supplierName);
            // 查询所有的anchor
            TableDataInfo tableDataInfo = remoteUserAnchorService.selectAnchorList();
            log.info("register,tableDataInfo:{}", JsonUtil.toJSON(tableDataInfo));
            List<?> rows = tableDataInfo.getRows();
            log.info("register,rows:{}", JsonUtil.toJSON(rows));
            if (!CollectionUtils.isEmpty(rows)) {
                List<String> anchorMails = new ArrayList<>();
                List<String> anchorIds = new ArrayList<>();
                for (Object row : rows) {
                    RemoteSysUserVO vo = JsonUtil.jsonToObject(JsonUtil.toJSON(row), RemoteSysUserVO.class);
                    if (vo != null) {
                        if (StringUtils.isNotEmpty(vo.getLoginName())) {
                            anchorMails.add(vo.getLoginName());
                        }
                        if (vo.getUserId() != null) {
                            anchorIds.add(String.valueOf(vo.getUserId()));
                        }
                    }
                }
                mailUtil.sendRegMailToSupplier(supplier.getContactEmail(), supplier.getContactEmail(), Collections.singletonList(supplier.getContactEmail()), null, null, contentBean);
                log.info("sendMailAndNotice,anchorMails:{},anchorIds:{}", anchorMails, anchorIds);
                if (!CollectionUtils.isEmpty(anchorMails)) {
                    String createBy = supplier.getCreateBy();
                    // 发送supplier注册邮件给anchor
                    mailUtil.sendRegMailToAnchor(createBy, supplier.getContactEmail(), anchorMails, null, null, null);
                    // 发系统消息给anchor(所有anchor)
                    Map<String, String> titleBean = new HashMap<>();
                    titleBean.put("SupplierName", supplierName);
                    Map<String, String> noticeContentBean = new HashMap<>();
                    noticeContentBean.put("SupplierName", supplierName);
                    NoticeSendDto notice = new NoticeSendDto(NoticeConstant.NOTICE_TYPE_SUPPLIER_REG, createBy, JsonUtil.toJSON(titleBean), JsonUtil.toJSON(noticeContentBean), anchorIds, null);
                    String noticeContent = JsonUtil.toJSON(notice);
                    sender.asynSend(sendNoticeTopic, UUID.randomUUID().toString(), noticeContent, KafkaFactory.SEND_KAFKA_PRODUCER_FACTORY, new KafkaCallback(sendNoticeTopic), false);
                }
            }
        } catch (Exception e) {
            log.error("register,error:{}", e.getMessage(), e);
        }
    }

    /**
     * 构造查询supplier是否存在的查询条件
     *
     * @param supplierName 供应商名称
     * @return 查询条件
     */
    private Example createSupplierExistCondition(String supplierName) {
        Example example = new Example(SupplierDo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("supplierName", supplierName);
        criteria.andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        return example;
    }

    @Override
    public SupplierSimpleVo simple(Long id) {
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(id);
        if (supplierDo == null || !StateConstants.FLAG_NORMAL_STR.equals(supplierDo.getDeleteFlag())) {
            log.error("supplier is null,id:{}", id);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        return convertToVo(supplierDo);
    }

    private SupplierDetailVo convertToVo(SupplierDo supplier) {
        log.info("convertToVo,supplier:{}", JsonUtil.toJSON(supplier));
        SupplierDetailVo vo = new SupplierDetailVo();
        vo.setId(String.valueOf(supplier.getId()));
        vo.setDestination(supplier.getContactDestination());
        vo.setSupplierName(supplier.getSupplierName());
        vo.setFirstName(supplier.getContactFirstName());
        vo.setLastName(supplier.getContactLastName());
        vo.setEmail(supplier.getContactEmail());
        vo.setPhone(supplier.getContactPhone());
        vo.setPhoneRegion(supplier.getPhoneRegion());
        vo.setCurrency(supplier.getCurrency());
        vo.setSupplierModel(supplier.getSupplierModel());
        vo.setFinancingModel(supplier.getFinancingModel());
        vo.setMinimumNetFinancingAmount(supplier.getMinimumNetFinancingAmount());
        vo.setInvalidDaysBeforeMaturityDate(supplier.getInvalidDaysBeforeMaturityDate());

//            vo.setToUpPricing(supplier.getToUpPricing());
        Double pricing = supplier.getToUpPricing() == null ? 0.0 : supplier.getToUpPricing();
        vo.setToUpPricing(new BigDecimal(String.valueOf(pricing)).setScale(2, RoundingMode.HALF_UP).toPlainString());

        vo.setBasisOfMaturityDateCalculation(SupplierDo.convertBasisDateToNum(supplier.getBasisOfMaturityDateCalculation()));
        vo.setPaymentCycle(supplier.getPaymentCycle());
        vo.setWeekOfTheMonth(supplier.getWeekOfTheMonth());
        vo.setTraceBackHistoryInvoiceDays(supplier.getTraceBackHistoryInvoiceDays());

        vo.setOnshore(SupplierDo.ON_SHORE_TRUE.equals(supplier.getOnShoreFlag()));
        vo.setMicroservicesEntityName(supplier.getMicroservicesEntityName());
        vo.setPreferredFinancingModel(supplier.getPreferredFinancingModel());
        vo.setPrimarySupplierCode(supplier.getPrimarySupplierCode());
        vo.setMaturityDateChangeable(SupplierConstants.convertMdChangeableBool(supplier.getMaturityDateChangeable()));

        vo.setNeedUpdate(supplier.getNeedUpdate());
        if (StringUtils.isNotEmpty(supplier.getFileIds())) {
            String[] ids = supplier.getFileIds().split(StringConstants.PROP_SEPARATOR);
            vo.setFileIds(new ArrayList<>(Arrays.asList(ids)));
        }
        return vo;
    }

    @Override
    public SupplierDetailVo detail(Long id) {
        log.info("detail,id:{}", id);
        SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(id);
        if (supplierDo == null) {
            log.error("supplier is null,id:{}", id);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        SupplierDetailVo detailVo = convertToVo(supplierDo);
        Example condition = new Example(SupplierCompanyCodeDo.class);
        condition.createCriteria()
                .andEqualTo("supplierId", id)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<SupplierCompanyCodeDo> supplierCompanyCodeDos = supplierCompanyCodeMapper.selectByExample(condition);
        StopWatch watch = new StopWatch("supplier detail");
        watch.start();
        if (!CollectionUtils.isEmpty(supplierCompanyCodeDos)) {
            List<SupplierInformationVo> infoList = new ArrayList<>();
            Set<String> distinct = supplierCompanyCodeDos.stream().map(SupplierCompanyCodeDo::getSupplierCode).collect(Collectors.toSet());
            for (String code : distinct) {
                List<SupplierInformationVo> info = info(code);
                infoList.addAll(info);
            }
            detailVo.setSupplierInfoList(infoList);
        }
        watch.stop();
        log.info("queryVendorCode,cost time:\n" + watch.prettyPrint());
        // 查询银行配置
        Example bankSettingCondition = new Example(SupplierBankSettingDo.class);
        bankSettingCondition.createCriteria()
                .andEqualTo("supplierId", id)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectByExample(bankSettingCondition);
        if (!CollectionUtils.isEmpty(supplierBankSettingDos)) {
            List<String> bankIds = supplierBankSettingDos.parallelStream().map(SupplierBankSettingDo::getBankId).collect(Collectors.toList());
            List<SupplierUniqueIdDo> supplierUniqueIdDos = supplierUniqueMapper.selectUniqueId(id, bankIds);
            Map<String, List<SupplierUniqueIdDo>> uniqueMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(supplierUniqueIdDos)) {
//                uniqueMap = supplierUniqueIdDos.parallelStream().collect(Collectors.groupingBy(SupplierUniqueIdDo::getBankId));
                uniqueMap = supplierUniqueIdDos.parallelStream().collect(Collectors.groupingBy(this::getBankEntityGroupKey));
            }
            List<SupplierDetailVo.BankSettingVo> bankSettingVos = new ArrayList<>();
            for (SupplierBankSettingDo setting : supplierBankSettingDos) {
                SupplierDetailVo.BankSettingVo bankSettingVo = convertToBankSettingVo(setting);
//                List<SupplierUniqueIdDo> uniqueIdDoList = uniqueMap.get(setting.getBankId());
                List<SupplierUniqueIdDo> uniqueIdDoList = uniqueMap.get(getBankEntityGroupKey(setting.getBankId(), setting.getEntityId()));
                if (!CollectionUtils.isEmpty(uniqueIdDoList)) {
                    List<SupplierDetailVo.ErpSupplier> erpSuppliers = convertToErpList(uniqueIdDoList);
                    bankSettingVo.setErps(erpSuppliers);
                    Map<String, List<SupplierDetailVo.ErpSupplier>> collect = erpSuppliers.stream().collect(Collectors.groupingBy(SupplierDetailVo.ErpSupplier::getUniqueId));
                    if (collect.size() == 1) {
                        bankSettingVo.setDiff(true);
                        for (Map.Entry<String, List<SupplierDetailVo.ErpSupplier>> entry : collect.entrySet()) {
                            bankSettingVo.setUniqueId(entry.getKey());
                            break;
                        }
                    }
                }
                bankSettingVos.add(bankSettingVo);
            }
            detailVo.setBankSettings(bankSettingVos);
        }
        log.info("detail,detailVo:{}", JsonUtil.toJSON(detailVo));
        return detailVo;
    }

    private String getBankEntityGroupKey(SupplierUniqueIdDo unique) {
        return getBankEntityGroupKey(unique.getBankId(), unique.getEntityId());
    }

    private String getBankEntityGroupKey(String bankId, Long entityId) {
        return bankId + StringConstants.UNDER_LINE + entityId;
    }

    private List<SupplierDetailVo.ErpSupplier> convertToErpList(List<SupplierUniqueIdDo> uniqueIdDoList) {
        List<SupplierDetailVo.ErpSupplier> list = new ArrayList<>();
        for (SupplierUniqueIdDo unique : uniqueIdDoList) {
            SupplierDetailVo.ErpSupplier erp = new SupplierDetailVo.ErpSupplier();
            erp.setId(String.valueOf(unique.getId()));
            erp.setSupplierCode(unique.getSupplierCode());
            erp.setUniqueId(unique.getSellerUniqueId());
            list.add(erp);
        }
        return list;
    }

    private SupplierDetailVo.BankSettingVo convertToBankSettingVo(SupplierBankSettingDo setting) {
        SupplierDetailVo.BankSettingVo vo = new SupplierDetailVo.BankSettingVo();
        vo.setId(String.valueOf(setting.getId()));
        vo.setBankId(setting.getBankId());
        vo.setCompanyCode(setting.getCompanyCode());
        vo.setBuyerOrg(setting.getBuyerOrg());
        vo.setBuyerOrgId(String.valueOf(setting.getBuyerOrgId()));
        vo.setMaximumInvoiceTenor(setting.getMaximumInvoiceTenor());
        vo.setMaximumFinanceTenor(setting.getMaximumFinanceTenor());
        vo.setDaysFromPostingDate(setting.getDaysFromPostingDate());
        vo.setInvoicePercentage(setting.getInvoicePercentage());
        vo.setBenchmark(setting.getBenchMark());
        vo.setMargin(setting.getMargin());
        vo.setEntityId(String.valueOf(setting.getEntityId()));
        vo.setEntityBankRelId(String.valueOf(setting.getEntityBankRelId()));
        vo.setCurrency(StringUtils.isEmpty(setting.getCurrency()) ? null : new ArrayList<>(Arrays.asList(setting.getCurrency().split(StringConstants.PROP_SEPARATOR))));
        return vo;
    }

    @Override
    public List<SupplierInformationVo> info(String code) {
        log.info("info,code:{}", code);
//        RBucket<List<SupplierInformationVo>> bucket = redissonClient.getBucket(VENDOR_CODE_INFO_PREFIX + code);
//        List<SupplierInformationVo> list = bucket.get();
//        if (!CollectionUtils.isEmpty(list)) {
//            log.info("info,get list from cache");
//            return list;
//        }
        ResultDTO<SupplierInfoResult> remoteResultDto = getRemoteResultDto(code);
        log.info("info,remoteResult:{}", JsonUtil.toJSON(remoteResultDto));
        checkResult(code, remoteResultDto);
        SupplierInfoResult remoteResult = remoteResultDto.getData();
        List<SupplierInformationVo> result = new ArrayList<>();
        for (SupplierInfoResult.Record record : remoteResult.getResponse().getItem()) {
            SupplierInformationVo vo = convertToRecord(record);
            // 目前只会返回一个结果，因此可以在for内进行db查询
            SupplierUniqueIdDo one = supplierUniqueMapper.checkVendorCodeBound(record.getSupplierCode());
            vo.setBound(one != null);
            result.add(vo);
        }
//        bucket.set(result, 120, TimeUnit.MINUTES);
        return result;
    }

    @Override
    public List<String> availableCurrency(Long bankId, Long entityId) {
        log.info("availableCurrency,bankId:{},entityId:{}", bankId, entityId);
        String bankCurrency = getBankCurrency(bankId);
        String entityCurrency = getEntityCurrency(entityId);
        log.info("availableCurrency,bankCurrency:{},entityCurrency:{}", bankCurrency, entityCurrency);
        List<String> bankCurrencyList = new ArrayList<>(Arrays.asList(bankCurrency.split(StringConstants.PROP_SEPARATOR)));
        List<String> entityCurrencyList = new ArrayList<>(Arrays.asList(entityCurrency.split(StringConstants.PROP_SEPARATOR)));
        // 取两个币种的交集
        List<String> result = new ArrayList<>(bankCurrencyList);
        result.retainAll(entityCurrencyList);
        log.info("availableCurrency,result:{}", result);
        if (CollectionUtils.isEmpty(result)) {
            throw throwEx(DefaultErrorMessage.AVAILABLE_CURRENCY_IS_NULL);
        }
        return result;
    }

    private String getBankCurrency(Long bankId) {
        BankDo bankDo = bankMapper.selectByPrimaryKey(String.valueOf(bankId));
        if (bankDo == null) {
            log.info("getBankCurrency,bank not found,bankId:{}", bankId);
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        String bankCurrency = bankDo.getCurrency();
        log.info("getBankCurrency,bankId:{},bankCurrency:{}", bankId, bankCurrency);
        if (StringUtils.isEmpty(bankCurrency)) {
            throw throwEx(DefaultErrorMessage.BANK_CURRENCY_IS_NULL);
        }
        return bankCurrency;
    }

    private String getEntityCurrency(Long entityId) {
        EntityDo entityDo = entityMapper.selectByPrimaryKey(entityId);
        if (entityDo == null) {
            log.info("getEntityCurrency,entity not found,entityId:{}", entityId);
            throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
        }
        String entityCurrency = entityDo.getCurrency();
        log.info("getEntityCurrency,entityId:{},entityCurrency:{}", entityId, entityCurrency);
        if (StringUtils.isEmpty(entityCurrency)) {
            throw throwEx(DefaultErrorMessage.ENTITY_CURRENCY_IS_NULL);
        }
        return entityCurrency;
    }

    private ResultDTO<SupplierInfoResult> getRemoteResultDto(String code) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("supplierCode", code);
        SupplierCodeParam param = new SupplierCodeParam();
        param.setHeaderParam(paramMap);
        return remoteSupplierDataService.supplierByVendorCode(param);
    }

    private SupplierInformationVo convertToRecord(SupplierInfoResult.Record record) {
        SupplierInformationVo vo = new SupplierInformationVo();
        List<SupplierInfoResult.CompanyInfo> companyLevel = record.getCompanyLevel();
        if (!CollectionUtils.isEmpty(companyLevel)) {
            SupplierInformationVo.SupplierGeneralInfo supplierGeneralInfo = convertToSupplierGeneralInfoList(record, companyLevel);
            vo.setSupplierGeneralInfo(supplierGeneralInfo);
        }
        List<SupplierInfoResult.BankInfo> banks = record.getBank();
        if (!CollectionUtils.isEmpty(banks)) {
            BankInfoWrapper convertResult = convertToBankGeneralInfoList(banks);
            vo.setBankGeneralInfo(convertResult.getBankGeneralList());
            vo.setBankInfoMap(convertResult.getBankInfoMap());
        }
        vo.setSupplierCode(record.getSupplierCode());
        return vo;
    }

    private static SupplierInformationVo.SupplierGeneralInfo convertToSupplierGeneralInfoList(SupplierInfoResult.Record record, List<SupplierInfoResult.CompanyInfo> companyLevel) {
        SupplierInformationVo.SupplierGeneralInfo supplierGeneralInfo = new SupplierInformationVo.SupplierGeneralInfo();
        SupplierInfoResult.CompanyInfo companyInfo = companyLevel.get(0);
        supplierGeneralInfo.setSupplierName(record.getSupplierName());
        supplierGeneralInfo.setAddress(companyInfo.getStreet());
        supplierGeneralInfo.setRegion(companyInfo.getRegion());
        supplierGeneralInfo.setVat(StringConstants.NULL_STR.equals(companyInfo.getVat()) ? null : companyInfo.getVat());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < companyLevel.size(); i++) {
            SupplierInfoResult.CompanyInfo item = companyLevel.get(i);
            builder.append(item.getCompanyCode());
            if (i != companyLevel.size() - 1) {
                builder.append(StringConstants.PROP_SEPARATOR);
            }
        }
        supplierGeneralInfo.setCompanyCode(builder.toString());
        return supplierGeneralInfo;
    }

    @Data
    private static class BankInfoWrapper {
        private List<SupplierInformationVo.BankGeneralInfo> bankGeneralList;
        private Map<String, SupplierInformationVo.BankGeneralInfo> bankInfoMap;
    }

    private static BankInfoWrapper convertToBankGeneralInfoList(List<SupplierInfoResult.BankInfo> banks) {
        BankInfoWrapper result = new BankInfoWrapper();
        List<SupplierInformationVo.BankGeneralInfo> bankGeneralInfoList = new ArrayList<>();
        Map<String, SupplierInformationVo.BankGeneralInfo> bankInfoMap = new HashMap<>();
        for (SupplierInfoResult.BankInfo item : banks) {
            SupplierInformationVo.BankGeneralInfo bankGeneralInfo = new SupplierInformationVo.BankGeneralInfo();
            bankGeneralInfo.setBankName(item.getBankName());
            bankGeneralInfo.setBankAccount(item.getBankAccount());
            bankGeneralInfo.setBankCountry(item.getBankCountry());
            bankGeneralInfo.setBankAddress(item.getBankAddress());
            bankGeneralInfo.setIban(item.getIban());
            bankGeneralInfo.setSwift(item.getSwift());
            bankGeneralInfoList.add(bankGeneralInfo);
            bankInfoMap.put(item.getBankAccount(), bankGeneralInfo);
        }
        result.setBankGeneralList(bankGeneralInfoList);
        result.setBankInfoMap(bankInfoMap);
        return result;
    }

    private void checkResult(String code, ResultDTO<SupplierInfoResult> remoteResultDTO) {
        if (remoteResultDTO == null) {
            log.error("remoteResultDTO is empty,code:{}", code);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        if (!remoteResultDTO.isSuccess()) {
            log.error("remoteResultDTO is failed,code:{}", code);
            throw new OtmpException(remoteResultDTO.getMsg(), DefaultErrorMessage.SERVER_ERROR.intValue());
        }
        SupplierInfoResult remoteResult = remoteResultDTO.getData();
        if (remoteResult == null || remoteResult.getHeader() == null) {
            log.error("remoteResult or remoteResult.header is null,code:{}", code);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        if (remoteResult.getHeader().getStatusCode() == null
                || !remoteResult.getHeader().getStatusCode().equals(RemoteSupplierDataService.CODE_OK)) {
            log.error("remoteResult status is wrong,status:{},code:{}", remoteResult.getHeader().getStatusCode(), code);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        if (remoteResult.getResponse() == null || CollectionUtils.isEmpty(remoteResult.getResponse().getItem())) {
            log.error("remoteResult body is null,code:{}", code);
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean active(ActiveSupplierParam param) {
        log.info("active,param:{}", JsonUtil.toJSON(param));
        Long supplierId = param.getId();
        RLock lock = redissonClient.getLock(RedisKeyUtil.getSupplierActiveLockKey(supplierId));
        try {
            if (!lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                log.info("active,try lock failed,id:{}", supplierId);
                return false;
            } else {
                SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(supplierId);
                if (supplierDo == null || StateConstants.FLAG_DELETED_STR.equals(supplierDo.getDeleteFlag())) {
                    log.error("active,supplier is null,supplierId:{}", supplierId);
                    throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
                }
                // 校验数据状态
                log.info("active,supplierDo:{}", JsonUtil.toJSON(supplierDo));
                if (!SupplierDo.STATUS_PENDING.equals(supplierDo.getStatus())) {
                    log.error("active,status is wrong,supplierId:{},status:{}", supplierId, supplierDo.getStatus());
                    throw throwEx(DefaultErrorMessage.SUPPLIER_STATUS_IS_WRONG);
                }
                LocalDateTime now = LocalDateTime.now();
                // 检查supplier和supplierCode的关系是否存在
                checkSupplierVendorRelation(param, supplierId);
                // 保存供应商和公司的关系
                saveSupplierCompanyCodes(param, now);
                // 保存银行配置
                List<SupplierBankSettingDo> companyBankSettings = convertToSetting(param, supplierDo, now);
                if (CollectionUtils.isEmpty(companyBankSettings)) {
                    log.error("active,companyBankSettings is empty");
                    throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
                }
                log.info("active,companyBankSettings:{}", JsonUtil.toJSON(companyBankSettings));
                supplierBankSettingMapper.insertListWithId(companyBankSettings);
                List<SupplierUniqueIdDo> uniqueInfoListAll = new ArrayList<>();
                for (SupplierBankSettingDo companyBankSetting : companyBankSettings) {
                    List<SupplierUniqueIdDo> uniqueInfoList = companyBankSetting.getUniqueInfoList();
                    uniqueInfoListAll.addAll(uniqueInfoList);
                }
                log.info("active,uniqueInfoListAll:{}", JsonUtil.toJSON(uniqueInfoListAll));
                supplierUniqueMapper.insertWithId(uniqueInfoListAll);

                // 更新supplier信息
                fillSupplierInfo(param, supplierDo, now);
                supplierDo.setActivationDate(localDateTimeToDate(now));
                log.info("active,update supplier,supplier:{}", JsonUtil.toJSON(supplierDo));
                supplierMapper.updateByPrimaryKey(supplierDo);
                updateCache(supplierDo);
                // 申请microservicesId
                SysUser sysUser = new SysUser();
                String userName = param.getContactLastName() + StringConstants.SPACE_STR + param.getContactFirstName();
                userName = StringUtils.isEmpty(userName.trim()) ? param.getEmail() : userName.trim();
                sysUser.setUserName(userName);
                sysUser.setLoginName(param.getEmail());
                sysUser.setStatus("0");
                sysUser.setRemark("Supplier of otfp");
                sysUser.setSupplierId(String.valueOf(supplierId));
                sysUser.setRoleIds(Collections.singletonList(supplierAdminRoleId));

                ResultDTO<RegisterResult> remoteRegResult = remoteSystemService.registermicroservicesId(sysUser);
                log.info("active,call registermicroservicesId,sysUser:{},remoteRegResult:{}", JsonUtil.toJSON(sysUser), JsonUtil.toJSON(remoteRegResult));
                if (remoteRegResult == null) {
                    log.error("active,call registermicroservicesId error,remoteRegResult is null");
                    throw throwEx(DefaultErrorMessage.REGISTER_microservices_ID_FAILED);
                }
                boolean updateAccount = false;
                if (!remoteRegResult.isSuccess()) {
                    if (DefaultErrorMessage.SUPPLIER_ACCOUNT_EXIST.equals(remoteRegResult.getCode())) {
                        Example sysUserCondition = new Example(SysUserFinancingDo.class);
                        sysUserCondition.createCriteria().andEqualTo("loginName", param.getEmail());
                        SysUserFinancingDo sysUserFinancing = sysUserFinancingMapper.selectOneByExample(sysUserCondition);
                        if (sysUserFinancing == null) {
                            log.info("active,sysUserFinancing is null");
                            throw throwEx(DefaultErrorMessage.USER_ACCOUNT_NOT_FOUND);
                        }
                        sysUserFinancing.setSupplierId(supplierDo.getId());
                        sysUser.setUserId(sysUserFinancing.getUserId());
                        ResultDTO<Object> updateResultDto = remoteUserSupplierService.updateSupplier(sysUser);
                        log.info("active,updateResultDto:{}", JsonUtil.toJSON(updateResultDto));
                        if (updateResultDto != null && updateResultDto.getCode() == ACCOUNT_UPDATE_SUCCESS_CODE) {
                            updateAccount = true;
                        } else {
                            log.info("active,updateResultDto is null");
                            throw throwEx(DefaultErrorMessage.REGISTER_microservices_ID_FAILED);
                        }
                    } else {
                        log.error("active,call registermicroservicesId failed,msg:{}", remoteRegResult.getMsg());
                        throw throwEx(DefaultErrorMessage.REGISTER_microservices_ID_FAILED);
                    }
                }
                RegisterResult data = remoteRegResult.getData();
                log.info("active,call registermicroservicesId,data:{}", JsonUtil.toJSON(data));
//                String targetName = supplierDo.getContactLastName() + StringConstants.SPACE_STR + supplierDo.getContactFirstName();
                String targetName = supplierDo.getSupplierName();
                if ((data != null && RegisterResult.CODE_104.equals(data.getCode())) || updateAccount) {
                    // 已有用户
                    boolean sendResult = mailUtil.sendActiveSupplier(param.getCreateBy(), supplierDo.getContactEmail(), supplierDo.getContactEmail(), targetName, targetName);
                    log.info("active,sendResult:{},target:{},targetName:{}", sendResult, supplierDo.getContactEmail(), targetName);
                    if (!sendResult) {
                        log.warn("active,send active mail failed,target:{},targetName:{}", supplierDo.getContactEmail(), targetName);
                    }
                } else if ((data != null && data.getCode() == null) && StringUtils.isNotEmpty(data.getInitUrl())) {
                    String initUrl = data.getInitUrl().replaceAll("&", "&amp;");
                    // 新用户
                    boolean sendResult = mailUtil.sendActiveSupplierWithLink(param.getCreateBy(), supplierDo.getContactEmail(), supplierDo.getContactEmail(), targetName, targetName, initUrl);
                    log.info("active,sendResult:{},target:{},targetName:{}", sendResult, supplierDo.getContactEmail(), targetName);
                    if (!sendResult) {
                        log.warn("active,send active mail failed,target:{},targetName:{}", supplierDo.getContactEmail(), targetName);
                    }
                } else {
                    // 异常
                    log.error("active,register microservicesId failed,data:{}", JsonUtil.toJSON(data));
                    throw throwEx(DefaultErrorMessage.REGISTER_microservices_ID_FAILED);
                }

                // LTP激活
                log.info("active,ltp active");
                List<SupplierDo> activationParam = Collections.singletonList(supplierDo);
                List<ActiveSupplierParam.ErpSupplier> erpIds = param.getErpIds();
                // vendorCode - companyCode的map
                log.info("active,erpIds:{}", JsonUtil.toJSON(erpIds));
                Map<String, String> vendorCompanyCodeMap = erpIds.stream().collect(Collectors.toMap(ActiveSupplierParam.ErpSupplier::getSupplierCode, ActiveSupplierParam.ErpSupplier::getCompanyCode, (k1, k2) -> k2));
                List<SupplierActivationParam> supplierActivationParams = getSupplierActivationParams(activationParam, SupplierConstants.REMOTE_STATUS_ACTIVE, vendorCompanyCodeMap);
                log.info("active,ltp active,supplierActivationParams:{}", JsonUtil.toJSON(supplierActivationParams));
                ResultDTO<Object> ltpActiveResult = remoteSupplierDataService.callBackBankSuppliers(supplierActivationParams);
                log.info("active,ltpActiveResult:{}", JsonUtil.toJSON(ltpActiveResult));
                if (!checkExecResult(ltpActiveResult, true)) {
                    log.error("active,ltp active failed,supplierId:{},msg:{}", supplierId, ltpActiveResult.getMsg());
                    throw throwEx(DefaultErrorMessage.SUPPLIER_REMOTE_ACTIVE_FAILED);
                }
                // 更新file状态
                List<String> fileData = param.getFileIds();
                log.info("active,updateFileStatus");
                updateFileAsync(supplierId, fileData);
                return true;
            }
        } catch (InterruptedException e) {
            log.error("active,try lock error", e);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private SysUser createSupplierSysUser(SysUserFinancingDo sysUserFinancing) {
        if (sysUserFinancing == null) {
            log.warn("createSupplierSysUser,sysUserFinancing is null");
            return null;
        }
        SysUser sysUser = new SysUser();
        sysUser.setUserId(sysUserFinancing.getUserId());
        sysUser.setSupplierId(String.valueOf(sysUserFinancing.getSupplierId()));
        return sysUser;
    }

    private void checkSupplierVendorRelation(ActiveSupplierParam param, Long supplierId) {
        log.info("checkSupplierVendorRelation,supplierId:{},param:{}", supplierId, JsonUtil.toJSON(param));
        List<ActiveSupplierParam.ErpSupplier> erps = param.getErpIds();
        if (CollectionUtils.isEmpty(erps)) {
            throw throwEx(DefaultErrorMessage.ERP_IDS_IS_NULL);
        }
        Set<String> vendorCodes = erps.stream().map(ActiveSupplierParam.ErpSupplier::getSupplierCode).collect(Collectors.toSet());
        SupplierUniqueIdDo uniqueIdDo = supplierUniqueMapper.selectOneByVendorCode(supplierId, vendorCodes);
        log.info("checkSupplierVendorRelation,vendorCodes:{},uniqueIdDo:{}", JsonUtil.toJSON(vendorCodes), JsonUtil.toJSON(uniqueIdDo));
        if (uniqueIdDo != null) {
            log.info("checkSupplierVendorRelation,supplier code relation already exist");
            throw throwEx(DefaultErrorMessage.SUPPLIER_CODE_RELATION_EXIST);
        }
    }

    @Async
    public void updateFileAsync(Long id, List<String> fileData) {
        if (CollectionUtils.isEmpty(fileData)) {
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
        } else {
            log.info("updateFileAsync execute success");
        }
    }

    private void saveSupplierCompanyCodes(ActiveSupplierParam param, LocalDateTime now) {
        Long supplierId = param.getId();
        List<ActiveSupplierParam.SupplierBankSetting> bankSettings = param.getBankSettings();
        log.info("saveSupplierCompanyCodes,bankSettings:{}", JsonUtil.toJSON(bankSettings));
        if (CollectionUtils.isEmpty(bankSettings)) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        // 按bankId和currency分组，key：bankId + "_" + entityId +"_"+currency，value：ActiveSupplierParam.SupplierBankSetting列表
        Map<String, List<ActiveSupplierParam.SupplierBankSetting>> bankIdGroup = bankSettings.stream().collect(Collectors.groupingBy(t -> t.getBankId() + StringConstants.UNDER_LINE + t.getEntityId() + StringConstants.UNDER_LINE + t.getCurrency()));
        for (Map.Entry<String, List<ActiveSupplierParam.SupplierBankSetting>> entry : bankIdGroup.entrySet()) {
            if (entry.getValue() != null && entry.getValue().size() > 1) {
                log.info("saveSupplierCompanyCodes,supplier bank duplicate");
                throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_DUPLICATE);
            }
        }
        Set<Long> entityIds = bankSettings.stream().map(ActiveSupplierParam.SupplierBankSetting::getEntityId).collect(Collectors.toSet());
        Example example = new Example(EntityCompanyCodeDo.class);
        example.createCriteria().andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR).andIn("entityId", entityIds);
        List<EntityCompanyCodeDo> entityCompanyCodeDos = entityCompanyCodeMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(entityCompanyCodeDos)) {
            log.error("saveSupplierCompanyCodes,entityCompanyCodeDos is null,entityIds:{}", JsonUtil.toJSON(entityIds));
            throw throwEx(DefaultErrorMessage.ENTITY_NOT_FOUND);
        }
        Map<Long, List<EntityCompanyCodeDo>> entityCompanyCodeGroup = entityCompanyCodeDos.parallelStream().collect(Collectors.groupingBy(EntityCompanyCodeDo::getEntityId));

        Set<SupplierCompanyCodeDo> supplierCompanyCodeDos = new HashSet<>();
        // 从bankSetting中获取supplierCode
        for (ActiveSupplierParam.SupplierBankSetting setting : bankSettings) {
            ActiveSupplierParam.UniqueIds uniqueIds = setting.getUniqueIds();
            List<EntityCompanyCodeDo> companyCodes = entityCompanyCodeGroup.get(setting.getEntityId());
            if (CollectionUtils.isEmpty(companyCodes)) {
                log.warn("saveSupplierCompanyCodes,companyCodes is empty,entityId:{}", setting.getEntityId());
                continue;
            }
            if (StringUtils.isNotEmpty(uniqueIds.getShareSupplierUniqueId())) {
                List<ActiveSupplierParam.ErpSupplier> erpIds = param.getErpIds();
                if (CollectionUtils.isEmpty(erpIds)) {
                    log.warn("saveSupplierCompanyCodes erpIds is empty,entityId:{}", setting.getEntityId());
                    continue;
                }
                for (ActiveSupplierParam.ErpSupplier erp : erpIds) {
                    for (EntityCompanyCodeDo code : companyCodes) {
                        SupplierCompanyCodeDo supplierCompanyCode = new SupplierCompanyCodeDo(supplierId, code.getCompanyCode(), erp.getSupplierCode());
                        supplierCompanyCode.setCreateTime(now);
                        supplierCompanyCode.setCreateBy(param.getCreateBy());
                        supplierCompanyCodeDos.add(supplierCompanyCode);
                    }
                }
            } else {
                if (CollectionUtils.isEmpty(uniqueIds.getDifferentUniqueIds())) {
                    log.warn("saveSupplierCompanyCodes uniqueIds.getDifferentUniqueIds is empty,buyerOrgId:{}", setting.getEntityId());
                    continue;
                }
                for (ActiveSupplierParam.DifferentUniqueId item : uniqueIds.getDifferentUniqueIds()) {
                    for (EntityCompanyCodeDo code : companyCodes) {
                        SupplierCompanyCodeDo supplierCompanyCode = new SupplierCompanyCodeDo(supplierId, code.getCompanyCode(), item.getErpId());
                        supplierCompanyCode.setCreateTime(now);
                        supplierCompanyCode.setCreateBy(param.getCreateBy());
                        supplierCompanyCodeDos.add(supplierCompanyCode);
                    }
                }
            }
        }
        log.info("saveSupplierCompanyCodes,supplierCompanyCodeDos:{}", JsonUtil.toJSON(supplierCompanyCodeDos));
        supplierCompanyCodeMapper.insertListWithId(supplierCompanyCodeDos);
    }

    private void fillSupplierInfo(ActiveSupplierParam param, SupplierDo supplierDo, LocalDateTime now) {
        supplierDo.setStatus(SupplierDo.STATUS_ACTIVE);
        supplierDo.setSupplierName(param.getSupplierName());
        supplierDo.setContactFirstName(param.getContactFirstName());
        supplierDo.setContactLastName(param.getContactLastName());
        supplierDo.setContactDestination(param.getDestination());
        supplierDo.setContactEmail(param.getEmail());
        supplierDo.setContactPhone(param.getPhone());
        supplierDo.setPhoneRegion(param.getPhoneRegion());
        supplierDo.setUpdateBy(param.getUpdateBy());
        supplierDo.setFinancingModel(param.getFinancingModel());
        supplierDo.setSupplierModel(param.getSupplierModel());
        supplierDo.setUpdateTime(now);
        supplierDo.setBasisOfMaturityDateCalculation(SupplierDo.convertBasisDateToStr(param.getBasisOfMaturityDateCalculation()));
        supplierDo.setPaymentCycle(param.getPaymentCycle());
        supplierDo.setWeekOfTheMonth(param.getWeekOfTheMonth());
        supplierDo.setTraceBackHistoryInvoiceDays(param.getTraceBackHistoryInvoiceDays());
        supplierDo.setMinimumNetFinancingAmount(param.getMinimumNetFinancingAmount());
        supplierDo.setCurrency(param.getCurrency());
        supplierDo.setToUpPricing(param.getTopUpPricing());
        supplierDo.setInvalidDaysBeforeMaturityDate(param.getMarkAsInvalidDays());
        supplierDo.setPreferredFinancingModel(param.getPreferredFinancingModel());
        supplierDo.setPrimarySupplierCode(param.getPrimarySupplierCode());
        supplierDo.setMicroservicesEntityName(param.getMicroservicesEntityName());
        supplierDo.setMaturityDateChangeable(SupplierConstants.convertMdChangeable(param.getMaturityDateChangeable()));
        supplierDo.setNeedUpdate(false);
        supplierDo.setFileIds(null);
        if (!CollectionUtils.isEmpty(param.getFileIds())) {
            List<String> fileIds = param.getFileIds();
            String ids = String.join(StringConstants.PROP_SEPARATOR, fileIds);
            supplierDo.setFileIds(ids);
        }
    }

    private LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    private Date localDateTimeToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private List<SupplierBankSettingDo> convertToSetting(ActiveSupplierParam param, SupplierDo supplier, LocalDateTime createTime) {
        List<ActiveSupplierParam.ErpSupplier> erpIds = param.getErpIds();
        if (CollectionUtils.isEmpty(erpIds)) {
            log.error("convertToSetting,erpIds is empty");
            throw throwEx(DefaultErrorMessage.ERP_IDS_IS_NULL);
        }
        List<SupplierBankSettingDo> convertResult = new ArrayList<>();
        List<ActiveSupplierParam.SupplierBankSetting> bankSettings = param.getBankSettings();
        log.info("convertToSetting,bankSettings:{}", JsonUtil.toJSON(bankSettings));
        if (CollectionUtils.isEmpty(bankSettings)) {
            log.error("convertToSetting,bankSettings is empty,supplierId:{}", supplier.getId());
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        Example supplierCompanyCodeCondition = new Example(SupplierCompanyCodeDo.class);
        supplierCompanyCodeCondition.createCriteria().andEqualTo("supplierId", supplier.getId()).andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<SupplierCompanyCodeDo> supplierCompanyCodeDos = supplierCompanyCodeMapper.selectByExample(supplierCompanyCodeCondition);
        Set<String> companyCodes = supplierCompanyCodeDos.stream().map(SupplierCompanyCodeDo::getCompanyCode).collect(Collectors.toSet());

        for (ActiveSupplierParam.SupplierBankSetting bankSetting : param.getBankSettings()) {
            Set<String> commonCompanyCode = new HashSet<>();
            ActiveSupplierParam.UniqueIds uniqueIds = bankSetting.getUniqueIds();
            String shareUniqueId = uniqueIds.getShareSupplierUniqueId();
            boolean useShare = StringUtils.isNotEmpty(shareUniqueId);
            Map<String, String> differentUniqueIdMap = new HashMap<>();
            if (!useShare) {
                // 先对different unique id进行空值判断
                if (CollectionUtils.isEmpty(uniqueIds.getDifferentUniqueIds())) {
                    throw throwEx(DefaultErrorMessage.DIFFERENT_UNIQUE_IDS_IS_NULL);
                }
                differentUniqueIdMap = uniqueIds.getDifferentUniqueIdMap();
            }
            SupplierBankSettingDo supplierBankSetting = new SupplierBankSettingDo(param, bankSetting, supplier);
            List<SupplierUniqueIdDo> uniqueInfoList = new ArrayList<>();
            for (ActiveSupplierParam.ErpSupplier erpId : erpIds) {
                String uniqueId;
                if (useShare) {
                    uniqueId = shareUniqueId;
                } else {
                    uniqueId = differentUniqueIdMap.get(erpId.getSupplierCode());
                }
                if (StringUtils.isEmpty(uniqueId)) {
                    log.warn("convertToSetting, uniqueId is empty,useShare:{},erpId:{}", useShare, erpId);
                    continue;
                }
                String commonCompanyCodeStr = null;
                if (!CollectionUtils.isEmpty(companyCodes) && StringUtils.isNotEmpty(erpId.getCompanyCode())) {
                    List<String> vendorCompanyCodes = new ArrayList<>(Arrays.asList(erpId.getCompanyCode().split(StringConstants.PROP_SEPARATOR)));
                    commonCompanyCode.addAll(companyCodes);
                    commonCompanyCode.retainAll(vendorCompanyCodes);
                    log.info("convertToSetting,commonCompanyCode:{},companyCodes:{},vendorCompanyCodes:{}", JsonUtil.toJSON(commonCompanyCode), JsonUtil.toJSON(companyCodes), JsonUtil.toJSON(vendorCompanyCodes));
                    if (!CollectionUtils.isEmpty(commonCompanyCode)) {
                        commonCompanyCodeStr = String.join(StringConstants.PROP_SEPARATOR, commonCompanyCode);
                    }
                }

                SupplierUniqueIdDo uniqueIdDo = new SupplierUniqueIdDo();
                uniqueIdDo.setId(SnowFlakeUtil.nextId());
                uniqueIdDo.setCreateBy(param.getCreateBy());
                uniqueIdDo.setCreateTime(createTime);
                uniqueIdDo.setDeleteFlag(StateConstants.FLAG_NORMAL_STR);
                uniqueIdDo.setSupplierId(supplier.getId());
                uniqueIdDo.setSupplierCode(erpId.getSupplierCode());
                uniqueIdDo.setBankId(bankSetting.getBankId());
                uniqueIdDo.setSellerUniqueId(uniqueId);
                uniqueIdDo.setCommonCompanyCode(commonCompanyCodeStr);
                uniqueIdDo.setEntityId(bankSetting.getEntityId());
                uniqueInfoList.add(uniqueIdDo);
            }
            supplierBankSetting.setCreateBy(param.getCreateBy());
            supplierBankSetting.setCreateTime(createTime);
            supplierBankSetting.setUniqueInfoList(uniqueInfoList);
            convertResult.add(supplierBankSetting);
        }
        log.info("convertToSetting,convertResult:{}", JsonUtil.toJSON(convertResult));
        return convertResult;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean edit(EditSupplierParam param) {
        log.info("edit,editSupplierParam:{}", JsonUtil.toJSON(param));
        Long id = param.getId();
        RLock lock = redissonClient.getLock(RedisKeyUtil.getSupplierEditLockKey(id));
        try {
            if (!lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                // 加锁失败直接返回
                log.info("edit,try lock failed,id:{}", id);
                return false;
            } else {
                // 0，加锁成功继续执行
                SupplierDo supplierDo = supplierMapper.selectByPrimaryKey(id);
                if (supplierDo == null) {
                    throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
                }
                Integer oldTraceBack = supplierDo.getTraceBackHistoryInvoiceDays();
                LocalDateTime now = LocalDateTime.now();
                // 检查supplier和supplierCode的关系是否存在
                checkSupplierVendorRelation(param, id);

                // 1，更新supplier信息
                fillSupplierInfo(param, supplierDo, now);
                supplierDo.setNeedUpdate(false);
                supplierMapper.updateByPrimaryKey(supplierDo);
                updateCache(supplierDo);
                // 2，更新关联的supplierCode
                Example condition = new Example(SupplierCompanyCodeDo.class);
                condition.createCriteria()
                        .andEqualTo("supplierId", id)
                        .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
                List<SupplierCompanyCodeDo> supplierCompanyCodeDos = supplierCompanyCodeMapper.selectByExample(condition);
                log.info("edit,supplierCompanyCodeDos:{}", JsonUtil.toJSON(supplierCompanyCodeDos));
                if (!CollectionUtils.isEmpty(supplierCompanyCodeDos)) {
                    // 2.1，移除旧的关联关系
                    List<Long> supplierCodeIds = supplierCompanyCodeDos.parallelStream().map(SupplierCompanyCodeDo::getId).collect(Collectors.toList());
                    supplierCompanyCodeMapper.removeSupplierCodeRelation(supplierCodeIds);
                }
                // 2.2，添加新的关联关系
                saveSupplierCompanyCodes(param, now);
                // 3，更新bankSetting
                Example bankSettingCondition = new Example(SupplierBankSettingDo.class);
                bankSettingCondition.createCriteria()
                        .andEqualTo("supplierId", id)
                        .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
                List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectByExample(bankSettingCondition);
                // 查询supplier_unique
                Example supplierUniqueCondition = new Example(SupplierUniqueIdDo.class);
                supplierUniqueCondition.createCriteria()
                        .andEqualTo("supplierId", id)
                        .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
                List<SupplierUniqueIdDo> supplierUniqueIdDos = supplierUniqueMapper.selectByExample(supplierUniqueCondition);
                log.info("edit,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
                if (!CollectionUtils.isEmpty(supplierBankSettingDos)) {
                    // 3.1，移除旧的关联关系
                    List<Long> settingIds = supplierBankSettingDos.parallelStream().map(SupplierBankSettingDo::getId).collect(Collectors.toList());
                    supplierBankSettingMapper.removeSettingRelation(settingIds);
                }
                // 3.2，添加新的关联关系
                List<SupplierBankSettingDo> companyBankSettings = convertToSetting(param, supplierDo, now);
                supplierBankSettingMapper.insertListWithId(companyBankSettings);
                Map<String, String> oldSupplierVendorMap = new HashMap<>();

                // 4，更新supplier_unique
                if (!CollectionUtils.isEmpty(supplierUniqueIdDos)) {
                    log.info("edit,supplierUniqueIdDos:{}", JsonUtil.toJSON(supplierUniqueIdDos));
                    // 3.0，旧的supplier-vendorCode关系
//                    oldSupplierVendorMap = supplierUniqueIdDos.stream().collect(Collectors.toMap(SupplierUniqueIdDo::getSupplierCode, SupplierUniqueIdDo::getCommonCompanyCode,(k1,k2)->k2));
                    for (SupplierUniqueIdDo supplierUniqueIdDo : supplierUniqueIdDos) {
                        String supplierCode = supplierUniqueIdDo.getSupplierCode();
                        String commonCompanyCode = supplierUniqueIdDo.getCommonCompanyCode();
                        if (StringUtils.isNotEmpty(supplierCode) && StringUtils.isNotEmpty(commonCompanyCode)) {
                            oldSupplierVendorMap.put(supplierCode, commonCompanyCode);
                        }
                    }
                    log.info("edit,oldSupplierVendorMap:{}", JsonUtil.toJSON(oldSupplierVendorMap));
                    // 4.1 移除旧的关联关系
                    List<Long> supplierUniqueIds = supplierUniqueIdDos.parallelStream().map(SupplierUniqueIdDo::getId).collect(Collectors.toList());
                    supplierUniqueMapper.removeSupplierUniqueRelation(supplierUniqueIds);
                }
                // 新增的supplier-vendor关系
                List<SupplierUniqueIdDo> addSupplierUniqueIdList = new ArrayList<>();
                // 4.2 添加新的关联关系
                boolean newTrackBackDays = !oldTraceBack.equals(param.getTraceBackHistoryInvoiceDays());
                List<SupplierUniqueIdDo> uniqueInfoListAll = new ArrayList<>();
                for (SupplierBankSettingDo companyBankSetting : companyBankSettings) {
                    List<SupplierUniqueIdDo> uniqueInfoList = companyBankSetting.getUniqueInfoList();
                    if (CollectionUtils.isEmpty(uniqueInfoList)) {
                        continue;
                    }
                    for (SupplierUniqueIdDo uniqueIdDo : uniqueInfoList) {
                        uniqueInfoListAll.add(uniqueIdDo);
                        if (newTrackBackDays) {
                            addSupplierUniqueIdList.add(uniqueIdDo);
                        } else {
                            String remove = oldSupplierVendorMap.remove(uniqueIdDo.getSupplierCode());
                            if (remove == null) {
                                addSupplierUniqueIdList.add(uniqueIdDo);
                            }
                        }
                    }
                }
                log.info("edit,uniqueInfoListAll:{}", JsonUtil.toJSON(uniqueInfoListAll));
                supplierUniqueMapper.insertWithId(uniqueInfoListAll);
                // LTP调用
                List<SupplierActivationParam> supplierParams = getSupplierActivationParams(supplierDo, supplierBankSettingDos, addSupplierUniqueIdList, oldSupplierVendorMap);
                log.info("edit,supplierParams:{}", JsonUtil.toJSON(supplierParams));
                ResultDTO<Object> execResult = remoteSupplierDataService.callBackBankSuppliers(supplierParams);
                log.info("edit,execResult:{}", JsonUtil.toJSON(execResult));
                if (!checkExecResult(execResult, false)) {
                    log.error("edit,remote execute error,supplierId:{},msg:{}", supplierDo.getId(), execResult.getMsg());
                    throw throwEx(DefaultErrorMessage.SUPPLIER_REMOTE_CANCEL_HOLD_FAILED);
                }
                // 更新file状态
                List<String> fileData = param.getFileIds();
                updateFileAsync(param.getId(), fileData);
                return true;
            }
        } catch (InterruptedException e) {
            log.error("edit,try lock error", e);
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    private static boolean checkExecResult(ResultDTO<Object> execResult, boolean checkStatus) {
        boolean execSuccess = execResult.isSuccess();
        String resultObjectString = JsonUtil.toJSON(execResult.getData());
        if(StringUtils.isEmpty(resultObjectString)){
            log.info("checkExecResult,resultObjectString is empty,return. execSuccess:{}",execSuccess);
            return execSuccess;
        }
        String statusCode = null;
        JSONObject resultJsonObject = JsonUtil.toJsonObject(resultObjectString);
        if (resultJsonObject != null) {
            statusCode = resultJsonObject.getString("responseStatus");
        }
        log.info("checkExecResult,statusCode:{}",statusCode);
        // checkStatus = true时，需要检查内层对象的responseStatus值
        if (checkStatus) {
            boolean resultSuccess = RemoteSupplierDataService.CODE_OK.equals(statusCode);
            execSuccess = execSuccess && resultSuccess;
        }
        return execSuccess;
    }

    @Async
    public void updateCache(SupplierDo supplierDo) {
        String supplierCacheKey = RedisKeyUtil.getSupplierInfoKey(supplierDo.getId());
        log.info("updateCache,supplierCacheKey:{}", supplierCacheKey);
        RBucket<SupplierDo> bucket = redissonClient.getBucket(supplierCacheKey);
        bucket.set(supplierDo);
        RMap<Long, SupplierDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_SUPPLIER);
        map.put(supplierDo.getId(), supplierDo);
    }

    /**
     * 生成LTP调用参数（编辑功能使用）
     * @param supplier  supplier对象
     * @param settings  supplier的bank设置列表
     * @param add       新增的vendorCode
     * @param remove    移除的vendorCode
     * @return 生成的参数
     */
    private List<SupplierActivationParam> getSupplierActivationParams(SupplierDo supplier, List<SupplierBankSettingDo> settings, List<SupplierUniqueIdDo> add, Map<String, String> remove) {
        log.info("getSupplierActivationParams,supplier:{},add:{},remove:{}", JsonUtil.toJSON(supplier), JsonUtil.toJSON(add), JsonUtil.toJSON(remove));
        if (CollectionUtils.isEmpty(settings)) {
            log.warn("getSupplierActivationParams,settings is empty,return");
            return new ArrayList<>();
        }
        List<String> bankIds = settings.parallelStream().map(SupplierBankSettingDo::getBankId).collect(Collectors.toList());
        log.info("getSupplierActivationParams,settings:{}", JsonUtil.toJSON(settings));
        Example bankCondition = new Example(BankDo.class);
        bankCondition.createCriteria().andIn("id", bankIds);
        List<BankDo> bankDos = bankMapper.selectByExample(bankCondition);
        if (CollectionUtils.isEmpty(bankDos)) {
            log.info("getSupplierActivationParams,bankDos is empty,bank ids:{}", JsonUtil.toJSON(bankIds));
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        Set<String> addVendorCodeSet = new HashSet<>();
        if (!CollectionUtils.isEmpty(add)) {
            addVendorCodeSet = add.stream().map(SupplierUniqueIdDo::getSupplierCode).collect(Collectors.toSet());
        }
        // bank列表转为id和vendorCode的map
        Map<String, String> bankVendorMap = bankDos.stream().collect(Collectors.toMap(BankDo::getId, BankDo::getErpVendorId, (k1, k2) -> k2));
        log.info("getSupplierActivationParams,bankVendorMap:{}", JsonUtil.toJSON(bankVendorMap));
        // 需要新增的数据
        List<SupplierActivationParam> result = new ArrayList<>();
        for (SupplierBankSettingDo setting : settings) {
            String bankVendor = bankVendorMap.get(setting.getBankId());
            if (!CollectionUtils.isEmpty(add)) {
                for (SupplierUniqueIdDo uniqueIdDo : add) {
                    String companyCodeStr = uniqueIdDo.getCommonCompanyCode();
                    if (StringUtils.isEmpty(companyCodeStr)) {
                        log.info("getSupplierActivationParams,companyCodeStr is empty in add loop,continue");
                        continue;
                    }
                    String[] companyCodes = companyCodeStr.split(StringConstants.PROP_SEPARATOR);
                    for (String companyCode : companyCodes) {
                        SupplierActivationParam param = createParam(supplier, uniqueIdDo.getSupplierCode(), companyCode, bankVendor);
                        param.setStatus(SupplierConstants.REMOTE_STATUS_ACTIVE);
                        result.add(param);
                    }
                }
            }
            // 需要删除的数据
            if (!CollectionUtils.isEmpty(remove)) {
                for (Map.Entry<String, String> entry : remove.entrySet()) {
                    String companyCodeStr = entry.getValue();
                    if (StringUtils.isEmpty(companyCodeStr)) {
                        log.info("getSupplierActivationParams,companyCodeStr is empty in remove loop,continue");
                        continue;
                    }
                    String vendorCode = entry.getKey();
                    if (addVendorCodeSet.contains(vendorCode)) {
                        log.info("getSupplierActivationParams,vendorCode contains in addVendorCodeSet,vendorCode:{}", vendorCode);
                        continue;
                    }
                    String[] companyCodes = companyCodeStr.split(StringConstants.PROP_SEPARATOR);
                    for (String companyCode : companyCodes) {
                        SupplierActivationParam param = createParam(supplier, vendorCode, companyCode, bankVendor);
                        param.setStatus(SupplierConstants.REMOTE_STATUS_SUSPEND);
                        result.add(param);
                    }
                }
            }
        }
        log.info("getSupplierActivationParams,result:{}", JsonUtil.toJSON(result));
        return result;
    }

    private SupplierActivationParam createParam(SupplierDo supplier, String entry, String companyCode, String bankVendor) {
        SupplierActivationParam param = new SupplierActivationParam();
        param.setVendorName(supplier.getSupplierName());
        param.setSupplierCode(entry);
        param.setValidFrom(DateUtils.dateTime(calStartDate(supplier.getActivationDate(), supplier.getTraceBackHistoryInvoiceDays())));
        param.setCompanyCode(companyCode);
        param.setBankVendor(bankVendor);
        param.setSupplierModel(SupplierConstants.SUPPLIER_MODEL_BOE.equals(supplier.getSupplierModel())
                ? SupplierConstants.REMOTE_SUPPLIER_MODEL_BOE
                : SupplierConstants.REMOTE_SUPPLIER_MODEL_AP);
        return param;
    }

    @Override
    public boolean onHold(OnHoldParam param) {
        log.info("onHold,param:{}", JsonUtil.toJSON(param));
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria()
                .andEqualTo("id", param.getId())
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        SupplierDo supplierDo = supplierMapper.selectOneByExample(condition);
        if (supplierDo == null) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
        try {
            Date startTime = DateUtils.dateParse(param.getStartTime(), SupplierDo.ON_HOLD_TIME_PATTERN);
//            supplierDo.setOnHoldStartTime(startTime);
            supplierDo.setOnHoldStartTime(dateToLocalDateTime(startTime));
            Date endTime = DateUtils.dateParse(param.getEndTime(), SupplierDo.ON_HOLD_TIME_PATTERN);
//            supplierDo.setOnHoldEndTime(endTime);
            supplierDo.setOnHoldEndTime(dateToLocalDateTime(endTime));
        } catch (ParseException e) {
            log.error("onHold,parse time error,startTime:{},endTime:{}", param.getStartTime(), param.getEndTime());
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        supplierDo.setStatus(SupplierDo.STATUS_ON_HOLD);
        supplierDo.setUpdateBy(param.getUpdateBy());
//        supplierDo.setUpdateTime(new Date());
        supplierDo.setUpdateTime(LocalDateTime.now());
        log.info("onHold,supplierDo:{}", JsonUtil.toJSON(supplierDo));
        supplierMapper.updateByPrimaryKey(supplierDo);
        return true;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean cancelOnHold(IdParam param) {
        log.info("cancelOnHold,param:{}", JsonUtil.toJSON(param));
        Long id = param.getId();
        String updateBy = param.getUpdateBy() == null ? "admin" : param.getUpdateBy();
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria()
                .andEqualTo("id", id)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        SupplierDo supplierDo = supplierMapper.selectOneByExample(condition);
        if (supplierDo == null) {
            throw throwEx(DefaultErrorMessage.SUPPLIER_NOT_FOUND);
        }
//        if(SupplierDo.ON_HOLD_TRUE.equals(supplierDo.getOnHoldFlag())){
//            log.info("cancelOnHold,onHoldFlag is wrong,ohHoldFlag:{}",supplierDo.getOnHoldFlag());
//            throw throwEx(DefaultErrorMessage.STATUS_IS_WRONG);
//        }
        supplierDo.setStatus(SupplierDo.STATUS_ACTIVE);
        supplierDo.setOnHoldFlag(SupplierDo.ON_HOLD_FALSE);
        supplierDo.setOnHoldStartTime(null);
        supplierDo.setOnHoldEndTime(null);
        supplierDo.setUpdateBy(updateBy);
        supplierDo.setUpdateTime(LocalDateTime.now());
        supplierMapper.updateByPrimaryKey(supplierDo);
        // 调用LTP
        List<SupplierDo> activationParam = Collections.singletonList(supplierDo);
        List<SupplierActivationParam> supplierParams = getSupplierActivationParams("cancelOnHold", activationParam, SupplierConstants.REMOTE_STATUS_HOLD_CANCEL);
        ResultDTO<Object> execResult = remoteSupplierDataService.callBackBankSuppliers(supplierParams);
        if (!checkExecResult(execResult, false)) {
            log.error("cancelOnHold,cancelOnHold remote execute error,supplierId:{},msg:{}", supplierDo.getId(), execResult.getMsg());
            throw throwEx(DefaultErrorMessage.SUPPLIER_REMOTE_CANCEL_HOLD_FAILED);
        }
        return true;
    }

    @Override
    public void doOnHold() {
        LocalDateTime now = LocalDateTime.now();
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria()
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andEqualTo("status", SupplierDo.STATUS_ON_HOLD)
                .andLessThanOrEqualTo("onHoldStartTime", now)
                .andGreaterThan("onHoldEndTime", now)
                .andEqualTo("onHoldFlag", SupplierDo.ON_HOLD_FALSE);
        List<SupplierDo> supplierDos = supplierMapper.selectByCondition(condition);
        log.info("doOnHold,supplierDos:{}", JsonUtil.toJSON(supplierDos));
        if (CollectionUtils.isEmpty(supplierDos)) {
            log.info("doOnHold,supplierDos is empty,return");
            return;
        }
        List<SupplierActivationParam> request = getSupplierActivationParams("onHold", supplierDos, SupplierConstants.REMOTE_STATUS_ON_HOLD);
        log.info("doOnHold,request.size:{},detail:{}", request.size(), JsonUtil.toJSON(request));
        ResultDTO<Object> remoteResult = remoteSupplierDataService.callBackBankSuppliers(request);
        if (!checkExecResult(remoteResult, false)) {
            log.error("doOnHold error,msg:{},request:{}", remoteResult.getMsg(), JsonUtil.toJSON(request));
        }
        Set<Long> supplierIds = supplierDos.stream().map(SupplierDo::getId).collect(Collectors.toSet());
        supplierMapper.updateToOnHold(supplierIds);
        log.info("doOnHold success");
    }

    /**
     * 生成调用LTP接口的参数（激活Supplier时使用）
     * @param supplierDos supplier列表
     * @param status    状态,SupplierConstants.REMOTE_STATUS_*
     * @param vendorCompanyCodeMap vendorCode - companyCode的map关系
     * @return 接口需要的参数
     */
    private List<SupplierActivationParam> getSupplierActivationParams(List<SupplierDo> supplierDos,
                                                                      String status,
                                                                      Map<String, String> vendorCompanyCodeMap) {
        if (CollectionUtils.isEmpty(vendorCompanyCodeMap)) {
            log.info("getSupplierActivationParams,vendorCompanyCodeMap is empty");
            throw throwEx(DefaultErrorMessage.SERVER_ERROR);
        }
        // supplierId集合
        List<Long> supplierIds = supplierDos.parallelStream().map(SupplierDo::getId).collect(Collectors.toList());
        // 查询BankSetting
        Example bankSettingCondition = new Example(SupplierBankSettingDo.class);
        bankSettingCondition.createCriteria().andIn("supplierId", supplierIds);
        // bankSetting集合
        List<SupplierBankSettingDo> supplierBankSettingDos = supplierBankSettingMapper.selectByExample(bankSettingCondition);
        if (CollectionUtils.isEmpty(supplierBankSettingDos)) {
            log.info("getSupplierActivationParams,supplierBankSettingDos is empty,supplier ids:{}", JsonUtil.toJSON(supplierIds));
            throw throwEx(DefaultErrorMessage.SUPPLIER_BANK_SETTING_IS_NULL);
        }
        // bankIds
        List<String> bankIds = supplierBankSettingDos.parallelStream().map(SupplierBankSettingDo::getBankId).collect(Collectors.toList());
        Example bankCondition = new Example(BankDo.class);
        bankCondition.createCriteria().andIn("id", bankIds);
        List<BankDo> bankDos = bankMapper.selectByExample(bankCondition);
        if (CollectionUtils.isEmpty(bankDos)) {
            log.info("getSupplierActivationParams,bankDos is empty,bank ids:{}", JsonUtil.toJSON(bankIds));
            throw throwEx(DefaultErrorMessage.BANK_NOT_FOUND);
        }
        // bankSetting根据supplierId分组
        Map<Long, List<SupplierBankSettingDo>> bankSettingMap = supplierBankSettingDos.parallelStream().collect(Collectors.groupingBy(SupplierBankSettingDo::getSupplierId));
        // bank列表转为id和vendorCode的map
        Map<String, String> bankVendorMap = bankDos.stream().collect(Collectors.toMap(BankDo::getId, BankDo::getErpVendorId));
        List<SupplierActivationParam> request = new ArrayList<>();
        Example supplierCompanyCodeCondition = new Example(SupplierCompanyCodeDo.class);
        supplierCompanyCodeCondition.createCriteria().andIn("supplierId", supplierIds);
        List<SupplierCompanyCodeDo> supplierCompanyCodeDos = supplierCompanyCodeMapper.selectByExample(supplierCompanyCodeCondition);
        Map<Long, List<SupplierCompanyCodeDo>> supplierCompanyCodeMap = supplierCompanyCodeDos.parallelStream().collect(Collectors.groupingBy(SupplierCompanyCodeDo::getSupplierId));

        for (SupplierDo supplier : supplierDos) {
            List<SupplierCompanyCodeDo> supplierCompanyCodes = supplierCompanyCodeMap.get(supplier.getId());
            if (CollectionUtils.isEmpty(supplierCompanyCodes)) {
                log.warn("getSupplierActivationParams,supplierCompanyCodes is empty,supplierId:{}", supplier.getId());
                continue;
            }

            for (SupplierBankSettingDo setting : supplierBankSettingDos) {
                String bankVendorCode = bankVendorMap.get(setting.getBankId());
                if (StringUtils.isEmpty(bankVendorCode)) {
                    log.warn("getSupplierActivationParams,bankVendorCode is empty,id:{}", setting.getBankId());
                    continue;
                }
                List<SupplierBankSettingDo> settingList = bankSettingMap.get(setting.getSupplierId());
                if (CollectionUtils.isEmpty(settingList)) {
                    log.warn("getSupplierActivationParams,settingList is empty,supplierId:{}", setting.getSupplierId());
                    continue;
                }
                for (SupplierCompanyCodeDo supplierCompanyCodeDo : supplierCompanyCodes) {
                    String vendorCode = supplierCompanyCodeDo.getSupplierCode();
                    String codes = vendorCompanyCodeMap.get(vendorCode);
                    if (StringUtils.isEmpty(codes)) {
                        log.warn("getSupplierActivationParams,codes is empty,supplierId:{},vendorCode:{}", supplier.getId(), vendorCode);
                        continue;
                    }
                    List<String> codeList = new ArrayList<>(Arrays.asList(codes.split(StringConstants.PROP_SEPARATOR)));
                    log.info("getSupplierActivationParams,codeList:{}", JsonUtil.toJSON(codeList));
                    String companyCode = supplierCompanyCodeDo.getCompanyCode();
                    if (!codeList.contains(companyCode)) {
                        log.warn("getSupplierActivationParams,codeList does not contains code:{}", companyCode);
                        continue;
                    }
                    SupplierActivationParam param = new SupplierActivationParam();
                    param.setSupplierCode(supplierCompanyCodeDo.getSupplierCode());
                    param.setCompanyCode(companyCode);
//                        param.setStatus(SupplierConstants.REMOTE_STATUS_ON_HOLD);
                    param.setStatus(status);
                    param.setSupplierModel(SupplierConstants.SUPPLIER_MODEL_BOE.equals(supplier.getSupplierModel())
                            ? SupplierConstants.REMOTE_SUPPLIER_MODEL_BOE
                            : SupplierConstants.REMOTE_SUPPLIER_MODEL_AP);
                    param.setBankVendor(bankVendorCode);
                    param.setValidFrom(DateUtils.dateTime(calStartDate(supplier.getActivationDate(), supplier.getTraceBackHistoryInvoiceDays())));
                    param.setVendorName(supplier.getSupplierName());
                    request.add(param);
                }
            }
        }
        log.info("getSupplierActivationParams,request:{}", JsonUtil.toJSON(request));
        return request;
    }

    /**
     * 生成调用LTP接口的参数（定时任务/禁用/启用中使用）
     * @param invokeMethod 调用方法（任意字符即可）
     * @param supplierDos supplier列表
     * @param status 状态,SupplierConstants.REMOTE_STATUS_*
     * @return 接口的参数
     */
    private List<SupplierActivationParam> getSupplierActivationParams(String invokeMethod, List<SupplierDo> supplierDos, String status) {
        if (CollectionUtils.isEmpty(supplierDos)) {
            log.info("getSupplierActivationParams,supplierDos is empty,invokeMethod:{}", invokeMethod);
            return new ArrayList<>();
        }
        log.info("getSupplierActivationParams without vendorCompanyCodeMap,invokeMethod:{},supplierDos:{},status:{}", invokeMethod, JsonUtil.toJSON(supplierDos), status);
        List<Long> supplierIds = supplierDos.parallelStream().map(SupplierDo::getId).collect(Collectors.toList());
        Example supplierUniqueCondition = new Example(SupplierUniqueIdDo.class);
        supplierUniqueCondition.createCriteria()
                .andIn("supplierId", supplierIds)
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR);
        List<SupplierUniqueIdDo> supplierUniqueIdDos = supplierUniqueMapper.selectByExample(supplierUniqueCondition);
        log.info("getSupplierActivationParams without vendorCompanyCodeMap,invokeMethod:{},supplierUniqueIdDos:{}", invokeMethod, JsonUtil.toJSON(supplierUniqueIdDos));
        Map<String, String> vendorCompanyCodeMap = supplierUniqueIdDos.parallelStream().collect(Collectors.toMap(SupplierUniqueIdDo::getSupplierCode, t -> t.getCommonCompanyCode() == null ? "" : t.getCommonCompanyCode(), (k1, k2) -> k2));
        log.info("getSupplierActivationParams without vendorCompanyCodeMap,invokeMethod:{},vendorCompanyCodeMap:{}", invokeMethod, JsonUtil.toJSON(vendorCompanyCodeMap));
        List<SupplierActivationParam> result = getSupplierActivationParams(supplierDos, status, vendorCompanyCodeMap);
        log.info("getSupplierActivationParams without vendorCompanyCodeMap,invokeMethod:{},result:{}", invokeMethod, JsonUtil.toJSON(result));
        return result;
    }

    private Date calStartDate(Date baseTime, int interval) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(baseTime);
        calendar.add(Calendar.DAY_OF_MONTH, -interval);
        return calendar.getTime();
    }

    @Override
    public void doCancelOnHold() {
        LocalDateTime now = LocalDateTime.now();
        Example condition = new Example(SupplierDo.class);
        condition.createCriteria()
                .andEqualTo("deleteFlag", StateConstants.FLAG_NORMAL_STR)
                .andEqualTo("onHoldFlag", SupplierDo.ON_HOLD_TRUE)
                .andLessThan("onHoldEndTime", now);
        List<SupplierDo> supplierDos = supplierMapper.selectByCondition(condition);
        log.info("doCancelOnHold,supplierDos:{}", JsonUtil.toJSON(supplierDos));
        if (CollectionUtils.isEmpty(supplierDos)) {
            return;
        }

        List<SupplierDo> paramList = new ArrayList<>();
        List<Long> supplierIds = new ArrayList<>();
        for (SupplierDo supplier : supplierDos) {
            supplierIds.add(supplier.getId());
            Boolean update = updateStatus(supplier.getId(), SupplierDo.STATUS_ACTIVE);
            if (!update) {
                continue;
            }
            paramList.add(supplier);
        }
        log.info("doCancelOnHold,supplierIds:{}", JsonUtil.toJSON(supplierIds));
        supplierMapper.updateCancelOnHold(supplierIds);
        log.info("doCancelOnHold,paramList:{}", JsonUtil.toJSON(paramList));
        List<SupplierActivationParam> request = getSupplierActivationParams("doCancelOnHold", paramList, SupplierConstants.REMOTE_STATUS_HOLD_CANCEL);
        log.info("doCancelOnHold,request.size:{},detail:{}", request.size(), JsonUtil.toJSON(request));
        ResultDTO<Object> remoteResult = remoteSupplierDataService.callBackBankSuppliers(request);
        if (!checkExecResult(remoteResult, false)) {
            log.error("doCancelOnHold error,msg:{},request:{}", remoteResult.getMsg(), JsonUtil.toJSON(request));
        }
        log.info("doCancelOnHold,success");
    }

    @Override
    public List<EntityBankPairVo> entityBankRelation() {
        List<EntityBankRelationDo> relations = entityBankSettingMapper.selectEntityBankRelation();
        if (CollectionUtils.isEmpty(relations)) {
            log.info("entityBankRelation,relations is empty");
            throw throwEx(DefaultErrorMessage.ENTITY_BANK_SETTING_NOT_FOUND);
        }
        List<EntityBankPairVo> result = new ArrayList<>();
        for (EntityBankRelationDo relation : relations) {
            String entityCurrency = relation.getEntityCurrency();
            String bankCurrency = relation.getBankCurrency();
            if (StringUtils.isEmpty(entityCurrency) || StringUtils.isEmpty(bankCurrency)) {
                continue;
            }
            List<String> entityCurrencyList = new ArrayList<>(Arrays.asList(entityCurrency.split(StringConstants.PROP_SEPARATOR)));
            List<String> bankCurrencyList = new ArrayList<>(Arrays.asList(bankCurrency.split(StringConstants.PROP_SEPARATOR)));
            List<String> commonCurrency = new ArrayList<>(entityCurrencyList);
            commonCurrency.retainAll(bankCurrencyList);
            if (commonCurrency.isEmpty()) {
                continue;
            }
            EntityBankPairVo item = new EntityBankPairVo();
            item.setId(String.valueOf(relation.getId()));
            item.setEntityId(String.valueOf(relation.getEntityId()));
            item.setEntityName(relation.getEntityName());
            item.setBankId(relation.getBankId());
            item.setBankName(relation.getBankName());
            item.setCommonCurrency(commonCurrency);
            result.add(item);
        }
        log.info("entityBankRelation,result:{}", JsonUtil.toJSON(result));
        return result;
    }
}
