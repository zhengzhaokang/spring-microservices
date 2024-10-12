package com.microservices.otmp.system.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SupplierBaseInfoDto {

    private String supplierName;

    private String id;

    private String deleteFlag;

    private String status;
}
