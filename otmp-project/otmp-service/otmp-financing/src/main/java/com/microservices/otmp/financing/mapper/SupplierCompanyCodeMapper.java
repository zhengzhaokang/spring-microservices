package com.microservices.otmp.financing.mapper;

import com.microservices.otmp.common.core.dao.BaseMapper;
import com.microservices.otmp.financing.domain.entity.SupplierCompanyCodeDo;
import com.microservices.otmp.financing.domain.entity.SupplierCompanyCodeWithEntityDo;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

public interface SupplierCompanyCodeMapper extends BaseMapper<SupplierCompanyCodeDo> {

    void insertListWithId(@Param("records") Collection<SupplierCompanyCodeDo> records);

    List<SupplierCompanyCodeWithEntityDo> selectCompanyCodeBySupplier(@Param("supplierIds") List<Long> supplierIds);

    List<SupplierCompanyCodeWithEntityDo> selectAllCompanyCodeBySupplier(@Param("supplierIds") List<Long> supplierIds,@Param("deleteFlag") String delete);

    void removeSupplierCodeRelation(@Param("codeIds") List<Long> codeIds);

    void removeBySupplierId(@Param("supplierId") Long supplierId);
    void deleteBySupplierId(@Param("supplierId") Long supplierId);
    void activeBySupplierId(@Param("supplierId") Long supplierId);
}
