package com.microservices.otmp.bank.mapper;

import com.microservices.otmp.bank.domain.entity.SupplierBankSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/22
 * time: 10:43
 * Description:
 */
@Mapper
public interface SupplierBankSettingMapper {
    /**
     * 根据供应商编号 银行信息 获取到数据信息
     *
     * @param supplierCode 供应商编号
     * @param bankId     银行信息
     *  @param entityId     银行信息
     * @return SupplierBankSettingMapper
     */
    String getSupplierBankSettingMapper(@Param("supplierCode") String supplierCode, @Param("bankId") String bankId, @Param("entityId") Long entityId);
}
