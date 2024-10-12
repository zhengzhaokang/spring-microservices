package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.EntityCompanyCodeDo;
import com.microservices.otmp.financing.domain.entity.EntityNameCompanyCodeDo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface EntityCompanyCodeMapper extends BaseMapper<EntityCompanyCodeDo> {

    void insertListWithId(@Param("records") List<EntityCompanyCodeDo> records);

    void removeEntityCompanyCodeRelation(@Param("entityId") Long id);

    List<EntityNameCompanyCodeDo> entityNameCompanyCodes(@Param("codes") Collection<String> codes);

    EntityCompanyCodeDo checkRelation(@Param("companyCodes") Collection<String> companyCodes, @Param("entityId") Long entityId);

    List<EntityCompanyCodeDo> getEntityCompanyCode(@Param("companyCodes") Collection<String> companyCodeSet);

    void removeByEntityId(@Param("entityId") Long id);

    void deleteByEntityId(@Param("entityId") Long id);

    void activeByEntityId(@Param("entityId") Long id);

    EntityCompanyCodeDo selectOneForReactive(@Param("entityId")Long id);

    List<EntityCompanyCodeDo> selectByEntityIds(@Param("entityIds") Collection<Long> entityIds);
}
