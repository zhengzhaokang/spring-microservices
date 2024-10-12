package com.microservices.otmp.system.service.impl;

import com.microservices.otmp.common.asynctask.constant.AsyncBusinessType;
import com.microservices.otmp.common.constant.RedisConstants;
import com.microservices.otmp.common.enums.AsyncTaskStatusEnum;
import com.microservices.otmp.common.enums.ModuleType;
import com.microservices.otmp.common.exception.OtmpException;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.common.utils.bean.BeanUtils;
import com.microservices.otmp.system.domain.BizAsyncTaskLogDTO;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.domain.entity.BizAsyncTaskLogDO;
import com.microservices.otmp.system.manager.IBizAsyncTaskLogManager;
import com.microservices.otmp.system.service.IBizAsyncTaskLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 记录文件上传下载信息Service业务层处理
 *
 * @author lovefamily
 * @date 2022-09-29
 */
@Service
@Slf4j
public class BizAsyncTaskLogServiceImpl implements IBizAsyncTaskLogService {
    @Autowired
    private IBizAsyncTaskLogManager bizAsyncTaskLogManager;
    @Autowired
    private RedisUtils redisUtils;

    /**
     * 查询记录文件上传下载信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 记录文件上传下载信息DTO
     */
    @Override
    public BizAsyncTaskLogDTO selectBizAsyncTaskLogById(Long id) {
        BizAsyncTaskLogDO bizAsyncTaskLogDO = bizAsyncTaskLogManager.selectBizAsyncTaskLogById(id);
        BizAsyncTaskLogDTO bizAsyncTaskLogDTO = new BizAsyncTaskLogDTO();
        org.springframework.beans.BeanUtils.copyProperties(bizAsyncTaskLogDO, bizAsyncTaskLogDTO);
        return bizAsyncTaskLogDTO;
    }

    /**
     * 查询记录文件上传下载信息列表
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 记录文件上传下载信息DTO
     */
    @Override
    public List<BizAsyncTaskLogDTO> selectBizAsyncTaskLogList(BizAsyncTaskLogDTO bizAsyncTaskLog) {
        List<BizAsyncTaskLogDO> bizAsyncTaskLogDOS = bizAsyncTaskLogManager.selectBizAsyncTaskLogList(bizAsyncTaskLog);
        List<BizAsyncTaskLogDTO> bizAsyncTaskLogList = new ArrayList<>(bizAsyncTaskLogDOS.size());
        BeanUtils.copyListProperties(bizAsyncTaskLogDOS, bizAsyncTaskLogList, BizAsyncTaskLogDTO.class);
        for (BizAsyncTaskLogDTO bizAsyncTaskLogDTO : bizAsyncTaskLogList) {
            Long id = bizAsyncTaskLogDTO.getId();
            if (0 == Double.compare(0, bizAsyncTaskLogDTO.getProgress())) {
                Object o = redisUtils.get(RedisConstants.ASYNC_PREFIX, RedisConstants.ASYNC_ROW + ":" + id);
                if (null == o) {
                    bizAsyncTaskLogDTO.setProgress(0.0);
                } else {
                    bizAsyncTaskLogDTO.setProgress(Double.parseDouble(o.toString()));
                }
            }
        }

        return bizAsyncTaskLogList;

    }

    /**
     * 新增记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    @Override
    public BizAsyncTaskLogDTO insertBizAsyncTaskLog(BizAsyncTaskLogDTO bizAsyncTaskLog) {
        bizAsyncTaskLog.setCreateTime(DateUtils.getNowDate());
        BizAsyncTaskLogDO bizAsyncTaskLogDO = new BizAsyncTaskLogDO();
        org.springframework.beans.BeanUtils.copyProperties(bizAsyncTaskLog, bizAsyncTaskLogDO);
        bizAsyncTaskLogManager.insertBizAsyncTaskLog(bizAsyncTaskLogDO);
        bizAsyncTaskLog.setId(bizAsyncTaskLogDO.getId());
        return bizAsyncTaskLog;
    }

    /**
     * 修改记录文件上传下载信息
     *
     * @param bizAsyncTaskLog 记录文件上传下载信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBizAsyncTaskLog(BizAsyncTaskLogDTO bizAsyncTaskLog) {
        bizAsyncTaskLog.setUpdateTime(DateUtils.getNowDate());
        BizAsyncTaskLogDO bizAsyncTaskLogDO = new BizAsyncTaskLogDO();
        org.springframework.beans.BeanUtils.copyProperties(bizAsyncTaskLog, bizAsyncTaskLogDO);
        return bizAsyncTaskLogManager.updateBizAsyncTaskLog(bizAsyncTaskLogDO);
    }

    /**
     * 批量删除记录文件上传下载信息
     *
     * @param ids 需要删除的记录文件上传下载信息主键
     * @return 结果
     */
    @Override
    public int deleteBizAsyncTaskLogByIds(Long[] ids) {
        return bizAsyncTaskLogManager.deleteBizAsyncTaskLogByIds(ids);
    }

    /**
     * 删除记录文件上传下载信息信息
     *
     * @param id 记录文件上传下载信息主键
     * @return 结果
     */
    @Override
    public int deleteBizAsyncTaskLogById(Long id) {
        return bizAsyncTaskLogManager.deleteBizAsyncTaskLogById(id);
    }

    @Override
    public List<BizAsyncTaskLogDTO> getProgress(Long[] ids) {
        List<BizAsyncTaskLogDTO> list = new ArrayList<>();
        for (Long id : ids) {
            Object o = redisUtils.get(RedisConstants.ASYNC_PREFIX, RedisConstants.ASYNC_ROW + ":" + id);
            BizAsyncTaskLogDO bizAsyncTaskLogDO = bizAsyncTaskLogManager.selectBizAsyncTaskLogById(id);
            if (null == bizAsyncTaskLogDO) {
                throw new OtmpException("");
            }
            BizAsyncTaskLogDTO bizAsyncTaskLogDTO = new BizAsyncTaskLogDTO();
            if (null == o) {
                bizAsyncTaskLogDTO.setProgress(0.0);
            } else {
                bizAsyncTaskLogDO.setProgress(Double.parseDouble(o.toString()));
            }
            org.springframework.beans.BeanUtils.copyProperties(bizAsyncTaskLogDO, bizAsyncTaskLogDTO);
            list.add(bizAsyncTaskLogDTO);
        }
        return list;
    }

    @Override
    public void deleteAsyncTask(Long id) {
        BizAsyncTaskLogDO bizAsyncTaskLogDO = new BizAsyncTaskLogDO();
        bizAsyncTaskLogDO.setId(id);
        bizAsyncTaskLogDO.setStatus(AsyncTaskStatusEnum.cancelling.getCode());
        bizAsyncTaskLogManager.updateBizAsyncTaskLog(bizAsyncTaskLogDO);
    }

    @Override
    @SuppressWarnings("java:S3011")
    public List<SysDictData> businessTypeSelect() {
        List<SysDictData> list = new ArrayList<>();
        Class<AsyncBusinessType> clazz = AsyncBusinessType.class;
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            if (Modifier.isStatic(field.getModifiers())) {
                try {
                    SysDictData sysDictData = new SysDictData();
                    sysDictData.setDictLabel(field.getName());
                    sysDictData.setDictValue((String) field.get(clazz));
                    list.add(sysDictData);
                } catch (IllegalAccessException e) {
                    log.error("BusinessTypeSelect Error:{}", e);
                }
            }
        }
        return list;
    }

    @Override
    public List<SysDictData> moduleSelect() {
        List<SysDictData> list = new ArrayList<>();
        for (ModuleType value : ModuleType.values()) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictLabel(value.name());
            sysDictData.setDictValue(value.name());
            list.add(sysDictData);
        }
        return list;
    }

    @Override
    public List<SysDictData> statusSelect() {
        List<SysDictData> list = new ArrayList<>();
        for (AsyncTaskStatusEnum value : AsyncTaskStatusEnum.values()) {
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictLabel(value.getName());
            sysDictData.setDictValue(String.valueOf(value.getCode()));
            list.add(sysDictData);
        }
        return list;
    }
}
