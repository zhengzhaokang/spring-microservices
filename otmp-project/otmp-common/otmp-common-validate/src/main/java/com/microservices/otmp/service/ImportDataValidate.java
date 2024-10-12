package com.microservices.otmp.service;

import com.microservices.otmp.common.BaseImportDTO;
import com.microservices.otmp.domain.ImportResult;
import com.microservices.otmp.common.BaseImportDTO;
import com.microservices.otmp.domain.ImportResult;

import java.util.List;

/**
 * 验证数据接口 效验数据的核心接口
 *
 * @author daihuaicai
 */
public interface ImportDataValidate {

    /**
     *
     * @param dataList 数据
     * @param customerValidate 自定义的验证逻辑
     * @return
     */
     ImportResult validateData(List<? extends BaseImportDTO> dataList, CustomerValidate customerValidate);
}
