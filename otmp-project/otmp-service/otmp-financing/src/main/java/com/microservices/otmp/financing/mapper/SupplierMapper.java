package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.SupplierDo;
import com.microservices.otmp.financing.domain.entity.SupplierWithVendorCode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Mapper
public interface SupplierMapper extends BaseMapper<SupplierDo> {

    int updateStatus(@Param("id") Long id, @Param("newStatus") Integer newStatus, @Param("oldStatus") Integer oldStatus);

    List<SupplierWithVendorCode> selectSupplierByVendorCodes(@Param("vendorCodes") Set<String> vendorCodeSet);

    void updateToOnHold(@Param("supplierIds") Collection<Long> supplierIds);

    void updateCancelOnHold(@Param("supplierIds") Collection<Long> supplierIds);

    void updateSupplierNeedRefresh(@Param("entityId") Long entityId);
}
