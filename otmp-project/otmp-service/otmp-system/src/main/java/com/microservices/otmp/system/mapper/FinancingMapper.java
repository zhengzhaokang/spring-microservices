package com.microservices.otmp.system.mapper;

import com.microservices.otmp.system.domain.entity.SupplierBaseInfoDto;

import java.util.List;

public interface FinancingMapper {
    List<SupplierBaseInfoDto> selectSupplierBaseInfo();
}
