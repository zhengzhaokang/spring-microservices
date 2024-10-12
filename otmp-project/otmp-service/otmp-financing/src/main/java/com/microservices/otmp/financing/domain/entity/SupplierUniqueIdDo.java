package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name="supplier_unique")
@Data
public class SupplierUniqueIdDo {

    @Id
    private Long id;
    private Long supplierId;
    private String supplierCode;
    private String bankId;
    private String sellerUniqueId;
    private String commonCompanyCode;
    private Long entityId;

    /**
     * 数据状态，0：禁用，1：正常
     */
    private String deleteFlag;
    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;

}
