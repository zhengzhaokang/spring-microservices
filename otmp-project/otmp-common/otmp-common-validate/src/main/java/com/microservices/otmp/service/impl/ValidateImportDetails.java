package com.microservices.otmp.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.microservices.otmp.common.*;
import com.microservices.otmp.common.redis.util.RedisUtils;
import com.microservices.otmp.domain.ImportResult;
import com.microservices.otmp.masterdata.feign.RemoteMasterDataService;
import com.microservices.otmp.service.CustomerValidate;
import com.microservices.otmp.service.ImportDataValidate;
import com.microservices.otmp.system.domain.SysDictData;
import com.microservices.otmp.system.feign.RemoteDictService;
import com.microservices.otmp.system.feign.RemoteViewFieldService;
import com.microservices.otmp.task.ValidateTask;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 导入效验
 *
 * @author dhc
 * @date 2022-08-01
 */
@Component
@Data
public class ValidateImportDetails implements ImportDataValidate {


    RemoteDictService remoteDictService;

    @Autowired
    @Lazy
    public void setRemoteDictService(RemoteDictService remoteDictService) {
        this.remoteDictService = remoteDictService;
    }


    RemoteViewFieldService remoteViewFieldService;

    @Autowired
    @Lazy
    public void setRemoteViewFieldService(RemoteViewFieldService remoteViewFieldService) {
        this.remoteViewFieldService = remoteViewFieldService;
    }

    @Autowired
    ValidImportFields validImportFields;


    RemoteMasterDataService remoteMasterDataService;

    @Autowired
    @Lazy
    public void setRemoteMasterDataService(RemoteMasterDataService remoteMasterDataService) {
        this.remoteMasterDataService = remoteMasterDataService;
    }

    @Resource(name = "ImportTaskExecutor")
    ThreadPoolTaskExecutor poolExecutor;

    @Autowired
    RedisUtils redisUtils;

    private String pageKey;

    private String divType;

    private List<String> viewFieldDimensions;

    private boolean autoClearCache = true;

    /**
     * @param dataList         数据
     * @param customerValidate 自定义的验证逻辑
     * @return
     */
    @Override
    public ImportResult validateData(List<? extends BaseImportDTO> dataList, CustomerValidate customerValidate) {
        if (CollUtil.isEmpty(dataList)) {
            return ImportResult.importFail("no Data");
        }
        //去除空值的list
        List<? extends BaseImportDTO> newList = RemoveImportEmptyRow.removeNullValue(dataList);
        if (CollectionUtils.isEmpty(newList)) {
            return ImportResult.importFail("no Data");
        }
        //初始化字典表
        Map<String, List<SysDictData>> dicMap = initDicMap(newList);
        if (null != customerValidate) {
            customerValidate.beforeCollectionValidate(newList);
        }
        synchronized (this) {
            //存返回结果
            List<Future<ImportResult<BaseImportDTO>>> futures = new ArrayList<>();
            for (BaseImportDTO data : dataList) {
                //统一去除前后空格
                TrimUtil.trim(data);
                //提交效验任务
                Future<ImportResult<BaseImportDTO>> importResult = poolExecutor.submit(new ValidateTask(validImportFields,
                        dicMap, data, remoteViewFieldService, customerValidate, pageKey,viewFieldDimensions));
                futures.add(importResult);
            }
            int errorCount = getErrorCount(futures);
            if (null != customerValidate) {
                //优化点 对于必须要补的数据 考虑百分百效验通过了再补
                customerValidate.afterCollectionValidate(newList,errorCount);
                errorCount = getAfterErrorCount(newList);
            }
            //清除本地缓存
            if (autoClearCache) {
                clearCache();
            }
            //如果有验证失败的大于0 生成文件上传ftp 留给业务实现
            return errorCount > 0 ? ImportResult.importFail("", newList,errorCount) : ImportResult.importSuccess("上传成功", newList);
        }
    }

    public static void clearCache() {
        MasterDataCache.clearCache();
        DictDataCache.clearCache();
        ViewConfigCache.clearCache();
    }

    public void manualClearCache() {
        this.setAutoClearCache(true);
        clearCache();
    }

    public int getAfterErrorCount(List<? extends BaseImportDTO> list) {
       return (int)list.stream().filter(item -> StrUtil.isNotBlank(item.getErrorMsg())).count();
    }

    /**
     * 统计验证失败数量
     *
     * @param futures
     * @return
     */
    private int getErrorCount(List<Future<ImportResult<BaseImportDTO>>> futures) {
        AtomicInteger errorCount = new AtomicInteger(0);
        for (Future<ImportResult<BaseImportDTO>> future : futures) {
            ImportResult<BaseImportDTO> importResult;
            try {
                importResult = future.get();
                if (null != importResult && null != importResult.getData() && StrUtil.isNotBlank(importResult.getData().getErrorMsg())) {
                    errorCount.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return errorCount.get();
    }

    /**
     * 初始化字典表
     *
     * @return
     */
    private Map<String, List<SysDictData>> initDicMap(List<? extends BaseImportDTO> dtos) {
        BaseImportDTO baseImportDTO = dtos.get(0);
        Set<String> types = GetDictTypes.getDictTypes(baseImportDTO);
        return getDicMap(types);
    }

    /**
     * * 优化成按需加载
     *
     * @param types
     * @return
     */
    private Map<String, List<SysDictData>> getDicMap(Set<String> types) {
        Map<String, List<SysDictData>> dictMap = DictDataCache.getMap();
        if (dictMap == null) {
            dictMap = new ConcurrentHashMap<>();
        }
        for (String type : types) {
            if (StrUtil.isNotBlank(type) && !dictMap.containsKey(type)) {
                List<SysDictData> dictList = DictDataCache.get(type);
                if (CollUtil.isEmpty(dictList)) {
                    dictList = remoteDictService.getDictSelect(type);
                }
                if (CollUtil.isNotEmpty(dictList)) {
                    dictMap.put(type, dictList);
                    DictDataCache.put(type, dictList);
                }
            }
        }
        return dictMap;
    }

}
