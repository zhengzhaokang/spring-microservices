package com.microservices.otmp.financing.util;

import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.utils.DateUtils;

public class RedisKeyUtil {

    public static final String FINANCE_OPERATE_USER_NAME = "userName";
    public static final String FINANCE_OPERATE_USER_ID = "userId";
    public static final String FINANCE_LOCK_PAGE_KEY = "otfp::financing::page::lock::supplier::";
    public static final String FINANCE_DATA_CACHE_KEY = "otfp::financing::invoice::data::supplier::";
    public static final String FINANCE_DATA_TAIL_CACHE_KEY = "otfp::financing::invoice::data::tail::";
    public static final String FINANCE_CHECKED_DATA_KEY = "otfp::financing::invoice::data::checked::";
    public static final String FINANCE_CHECKED_ID_KEY = "otfp::financing::invoice::id::checked::";
    public static final String DECREASE_AMOUNT_LOCK_KEY = "otfp::financing::invoice::decrease::lock";
    public static final String CONFIRM_TIME_MARK = "otfp::financing::supplier::submit::mark::";
    //    public static final String SUPPLIER_SUBMIT_LOCK = "otfp::financing::supplier::finance::submit::";
    public static final String SUPPLIER_SUBMIT_SERIAL_NO = "otfp::financing::supplier::finance::submit::no::";
    public static final String INVITATION_LOCK_PREFIX = "otfp::financing::supplier::invitation::mail::";
    public static final String SUPPLIER_REGISTER_LOCK_PREFIX = "otfp::financing::suppler::register::code::";
    public static final String SUPPLIER_ACTIVE_LOCK_PREFIX = "otfp::financing::supplier::active::lock::id::";
    public static final String SUPPLIER_EDIT_LOCK_PREFIX = "otfp::financing::supplier::edit::lock::id::";
    public static final String AVAILABLE_AMOUNT_PREFIX = "otfp::financing::amount::";
    public static final String INFO_BANK_PREFIX = RedisConstants.INFO_BANK_PREFIX;
    public static final String INFO_ALL_BANK = RedisConstants.INFO_ALL_BANK;
    public static final String INFO_ENTITY_PREFIX = RedisConstants.INFO_ENTITY_PREFIX;
    public static final String INFO_ALL_ENTITY = RedisConstants.INFO_ALL_ENTITY;
    public static final String INFO_SUPPLIER_PREFIX = RedisConstants.INFO_SUPPLIER_PREFIX;
    public static final String INFO_ALL_SUPPLIER = RedisConstants.INFO_ALL_SUPPLIER;

    public static String getAvailableAmountKey(String bankId, Long entityId) {
        return AVAILABLE_AMOUNT_PREFIX + bankId + "::" + entityId;
    }

    /**
     * 发票融资时的锁
     */
    public static String getDecreaseAmountLockKey() {
        return DECREASE_AMOUNT_LOCK_KEY;
    }

    /**
     * 发票融资页面锁
     */
    public static String pageLockKey(Long supplierId) {
        return FINANCE_LOCK_PAGE_KEY + supplierId;
    }

    /**
     * debit列表的缓存key
     */
    public static String dataCacheKey(Long supplierId) {
        return FINANCE_DATA_CACHE_KEY + supplierId;
    }

    public static String dataTailCacheKey(Long supplierId){
        return FINANCE_DATA_TAIL_CACHE_KEY + supplierId;
    }

    /**
     * 勾先的debit id的缓存key
     */
    public static String idCheckedKey(Long supplierId){
        return FINANCE_CHECKED_ID_KEY + supplierId;
    }

    /**
     * 勾先的debit的缓存key
     */
    public static String dataCheckedKey(Long supplierId) {
        return FINANCE_CHECKED_DATA_KEY + supplierId;
    }

    /**
     * 提交融资倒计时的key
     */
    public static String getMarkTimeKey(String supplierId, Long userId) {
        return CONFIRM_TIME_MARK + supplierId + "::userId::" + userId;
    }

//    public static String submitOperateLockKey(String supplierId, Long userId) {
//        return SUPPLIER_SUBMIT_LOCK + supplierId + "::userId::" + userId;
//    }

    /**
     * 提交发票时生成批次号的流水号部分
     */
    public static String submitSerialNum(String bankId) {
        return SUPPLIER_SUBMIT_SERIAL_NO + DateUtils.dateTime() + "::bankId::" + bankId;
    }

    /**
     * 邀请supplier操作的锁
     */
    public static String getSupplierInvLockKey(String name) {
        return INVITATION_LOCK_PREFIX + name;
    }

    /**
     * supplier注册操作的锁
     */
    public static String getSupplierRegLockKey(String code) {
        return SUPPLIER_REGISTER_LOCK_PREFIX + code;
    }

    /**
     * anchor激活supplier时的锁
     */
    public static String getSupplierActiveLockKey(Long id) {
        return SUPPLIER_ACTIVE_LOCK_PREFIX + id;
    }

    /**
     * 编辑supplier时的锁
     */
    public static String getSupplierEditLockKey(Long id) {
        return SUPPLIER_EDIT_LOCK_PREFIX + id;
    }

    /**
     * bank信息缓存key
     */
    public static String getBankInfoKey(String bankId) {
        return RedisConstants.getBankInfoKey(bankId);
    }

    /**
     * entity信息缓存key
     */
    public static String getEntityInfoKey(Long entityId) {
        return RedisConstants.getEntityInfoKey(entityId);
    }

    /**
     * supplier信息缓存key
     */
    public static String getSupplierInfoKey(Long supplierId) {
        return RedisConstants.getSupplierInfoKey(supplierId);
    }
}
