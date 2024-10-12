package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.*;
import com.microservices.otmp.financing.domain.entity.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface EntityBankSettingMapper extends BaseMapper<EntityBankSettingDo> {

    void insertListWithId(@Param("records") List<EntityBankSettingDo> records);

    List<EntityBankNameGroupDo> entityBankNameGroup();

    int decreaseLimit(@Param("bankId") String bankId, @Param("entityId") Long entityId, @Param("amount") BigDecimal amount);

    List<EntityNameBankSettingDo> selectEntityNamesByBankId(@Param("bankId") String bankId);

    List<EntityBankRelationDo> selectEntityBankRelation();

    void removeByEntityId(@Param("entityId") Long id);

    void deleteByEntityId(@Param("entityId") Long id);

    void deleteByEntityIdAndBankIds(@Param("entityId") Long id,@Param("bankIds") Collection<String> bankIds);

    void activeByEntityId(@Param("entityId") Long id);

    List<EntityBankSettingDo> selectByBankIdAndEntityIds(@Param("bankId") String bankId, @Param("entityIds") Collection<Long> entityIds);

    Double selectTotalBankAmount(@Param("bankId") String bankId, @Param("supplierId") Long supplierId);

    List<EntityBankSettingDo> selectBankAmounts(@Param("bankId") String bankId, @Param("supplierId") Long supplierId);

    List<BankLimitDo> selectGroupTotalBankAmount(@Param("supplierId") Long supplierId, @Param("bankIds") List<String> bankIds);

    List<EntityBankSettingDo> selectAllActive();

    List<EntityBankSettingDo> selectByEntityId(@Param("entityId") Long id,@Param("delete") String deleteFlag);
}
