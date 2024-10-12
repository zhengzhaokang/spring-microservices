package com.microservices.otmp.service;

import com.microservices.otmp.common.BaseImportDTO;

import java.util.List;

/**
 * 支持自定义效验逻辑
 * 可以是bean形式 也可以是实现了此接口的任意对象
 * 最终会执行自定义的效验逻辑
 * @author daihuaicai
 */
public interface CustomerValidate<T extends BaseImportDTO> {
    /**
     * 自定义前置效验逻辑
     */
    void beforeCustomValidate(T baseImportDTO);


    /**
     * 自定义后置效验逻辑
     * @param baseImportEntity
     */
    void afterCustomValidate(T baseImportEntity);

    /**
     * * 用于所有数据集合的前置效验
     * @param list
     */
    void beforeCollectionValidate(List<T> list);

    /**
     * * 用于所有数据的后置效验
     * @param list
     */
    void afterCollectionValidate(List<T> list,int errorCount);

}
