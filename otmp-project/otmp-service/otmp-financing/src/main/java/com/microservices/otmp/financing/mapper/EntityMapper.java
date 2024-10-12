package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.EntityDo;
import com.microservices.otmp.financing.domain.entity.EntityWithCompanyCodeDo;
import com.microservices.otmp.financing.domain.entity.EntityWithSupplierId;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface EntityMapper extends BaseMapper<EntityDo> {

    List<EntityWithCompanyCodeDo> selectEntityByCompanyCodeList(@Param("codes") Collection<String> companyCodeSet);

    List<EntityDo> selectEnitiesBySupplierId(@Param("status") String status, @Param("supplierId") String supplierId);

    List<EntityDo> selectByIdsAndStatus(@Param("entityIds") Collection<Long> suspendEntityIds, @Param("delete") String delete);

    List<EntityWithSupplierId> selectEnitiesBySupplierIds(@Param("supplierIds")List<Long> supplierIds);

}
