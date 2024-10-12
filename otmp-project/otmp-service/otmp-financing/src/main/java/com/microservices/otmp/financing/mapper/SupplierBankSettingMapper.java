package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.BankNameDo;
import com.microservices.otmp.financing.domain.entity.OnBoardingSupplierByBankDo;
import com.microservices.otmp.financing.domain.entity.SupplierBankSettingDo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface SupplierBankSettingMapper extends BaseMapper<SupplierBankSettingDo> {

    void insertListWithId(@Param("records") List<SupplierBankSettingDo> records);

    List<BankNameDo> selectEntityAndBankName(@Param("supplierIds") List<Long> supplierIds, @Param("deleteFlag") String delete);

    List<OnBoardingSupplierByBankDo> onboardingByBank(@Param("bankIds") List<String> bankIds);

    void removeSettingRelation(@Param("settingIds") List<Long> settingIds);

    List<String> selectBankCompanyCodes(@Param("supplierId") Long supplierId, @Param("bankId") String bankId);

    SupplierBankSettingDo selectOneByEntityId(@Param("entityId") Long entityId);

    void removeBySupplierId(@Param("supplierId") Long supplierId);

    void deleteBySupplierId(@Param("supplierId") Long supplierId);

    void activeBySupplierId(@Param("supplierId") Long supplierId);

    List<SupplierBankSettingDo> selectBySupplierIdAndBankId(@Param("supplierId") Long supplierId, @Param("bankId") String bankId);

    List<SupplierBankSettingDo> selectBySupplierBankEntityIds(@Param("supplierId") Long supplierId,
                                                              @Param("bankId") String bankId,
                                                              @Param("entityIds") Collection<Long> entityIds);

    List<SupplierBankSettingDo> selectBySupplierId(@Param("supplierId") Long id, @Param("delete") String deleteFlag);
}
