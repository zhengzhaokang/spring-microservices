package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "entity")
@Data
public class EntityDo {

    @Id
    private Long id;
    /**
     * 实体名称
     */
    private String entityName;
    /**
     * 公司代码
     */
    private String companyCode;
    /**
     * 实体对象币种类型
     */
    private String currency;
    /**
     * 实体对象所属地区
     */
    private String region;
    /**
     * 实体对象注册地址
     */
    private String registerAddress;
    /**
     * 实体对象类型，0：booth，1：AP financing，2：AR financing
     */
    private String type;
    /**
     * 数据状态，0：正常，1：禁用
     */
    private String deleteFlag;
    /**
     * 附件id，多个用,分隔
     */
    @Deprecated
    private String fileIds;
    private String kycDocumentIds;

    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
}
