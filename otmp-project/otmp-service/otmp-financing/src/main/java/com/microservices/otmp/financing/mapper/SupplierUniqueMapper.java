package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.SupplierUniqueIdDo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface SupplierUniqueMapper extends BaseMapper<SupplierUniqueIdDo> {

    void insertWithId(@Param("uniques") List<SupplierUniqueIdDo> uniqueIdDoList);

    void removeSupplierUniqueRelation(@Param("ids") List<Long> supplierUniqueIds);

    List<SupplierUniqueIdDo> selectByVendorCode(@Param("supplierId") Long supplierId, @Param("vendorCodes") Collection<String> vendorCodes);

    SupplierUniqueIdDo selectOneByVendorCode(@Param("supplierId") Long supplierId, @Param("vendorCodes") Collection<String> vendorCodes);

    SupplierUniqueIdDo selectOneForReactiveSupplier(@Param("supplierId") Long supplierId);

    SupplierUniqueIdDo checkVendorCodeBound(@Param("vendorCode") String vendorCode);

    List<SupplierUniqueIdDo> selectUniqueId(@Param("supplierId") Long supplierId, @Param("bankIds") List<String> bankIds);

    List<String> selectDistinctSupplierCodes(@Param("supplierId") Long supplierId);

    void removeBySupplierId(@Param("supplierId") Long supplierId);

    void deleteBySupplierId(@Param("supplierId") Long supplierId);

    void activeBySupplierId(@Param("supplierId") Long supplierId);

    List<Long> selectDistinctSupplierId(@Param("entityId") Long entityId);
}
