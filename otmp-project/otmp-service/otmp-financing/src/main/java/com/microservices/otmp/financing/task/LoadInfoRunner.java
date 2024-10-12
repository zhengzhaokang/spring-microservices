package com.microservices.otmp.financing.task;

import com.microservices.otmp.financing.domain.entity.BankDo;
import com.microservices.otmp.financing.domain.entity.EntityDo;
import com.microservices.otmp.financing.domain.entity.SupplierDo;
import com.microservices.otmp.financing.mapper.BankMapper;
import com.microservices.otmp.financing.mapper.EntityMapper;
import com.microservices.otmp.financing.mapper.SupplierMapper;
import com.microservices.otmp.financing.util.RedisKeyUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LoadInfoRunner implements ApplicationRunner {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private SupplierMapper supplierMapper;
    @Autowired
    private BankMapper bankMapper;
    @Autowired
    private EntityMapper entityMapper;

    /**
     * 读取Bank,Entity,Supplier的对象基础信息到Cache
     * 仅执行一次,不设置过期时间
     * 内容新增/更新在各自对象的add/update操作中进行(supplier为active和edit)
     */
    @Override
    public void run(ApplicationArguments args) {
        List<BankDo> bankDos = bankMapper.selectAll();
        if(CollectionUtils.isNotEmpty(bankDos)){
            Map<String,BankDo> bankDoMap = new HashMap<>(bankDos.size());
            for (BankDo bankDo : bankDos) {
                String bankCacheKey = RedisKeyUtil.getBankInfoKey(bankDo.getId());
                RBucket<BankDo> bucket = redissonClient.getBucket(bankCacheKey);
                bucket.set(bankDo);
                bankDoMap.put(bankDo.getId(),bankDo);
            }
            RMap<String, BankDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_BANK);
            map.putAll(bankDoMap);
        }
        List<EntityDo> entityDos = entityMapper.selectAll();
        if(CollectionUtils.isNotEmpty(entityDos)){
            Map<Long,EntityDo> entityDoMap = new HashMap<>(entityDos.size());
            for (EntityDo entityDo : entityDos) {
                String entityCacheKey = RedisKeyUtil.getEntityInfoKey(entityDo.getId());
                RBucket<EntityDo> bucket = redissonClient.getBucket(entityCacheKey);
                bucket.set(entityDo);
                entityDoMap.put(entityDo.getId(),entityDo);
            }
            RMap<Long, EntityDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_ENTITY);
            map.putAll(entityDoMap);
        }
        List<SupplierDo> supplierDos = supplierMapper.selectAll();
        if(CollectionUtils.isNotEmpty(supplierDos)){
            Map<Long,SupplierDo> supplierDoMap = new HashMap<>(supplierDos.size());
            for (SupplierDo supplierDo : supplierDos) {
                String supplierCacheKey = RedisKeyUtil.getSupplierInfoKey(supplierDo.getId());
                RBucket<SupplierDo> bucket = redissonClient.getBucket(supplierCacheKey);
                bucket.set(supplierDo);
                supplierDoMap.put(supplierDo.getId(),supplierDo);
            }
            RMap<Long, SupplierDo> map = redissonClient.getMap(RedisKeyUtil.INFO_ALL_SUPPLIER);
            map.putAll(supplierDoMap);
        }
    }
}
