package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Table(name = "invite_link")
@Data
public class InviteLinkDo {

    @Id
    private Long id;
    /**
     * 邀请时间
     */
    private Date inviteTime;
    /**
     * 链接唯一id
     */
    private String linkUniqueCode;
    /**
     * 供应商名称
     */
    private String supplierName;
    private Long supplierId;
    /**
     * onshore标识
     */
    @Transient
    private Integer onshore;
    /**
     * 数据状态，0：禁用，1：正常
     */
    private String deleteFlag;

    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
}
