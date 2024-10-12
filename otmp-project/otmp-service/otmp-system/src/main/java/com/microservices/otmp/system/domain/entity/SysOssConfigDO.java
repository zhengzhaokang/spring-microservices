package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 对象存储配置对象 sys_oss_config
 *
 * @author lovefamily
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name ="sys_oss_config")
public class SysOssConfigDO extends BaseDO {

    /**
     * 主建
     */
    @Id
    private Long ossConfigId;

    /**
     * 配置key
     */
    private String configKey;

    /**
     * accessKey
     */
    private String accessKey;

    /**
     * 秘钥
     */
    private String secretKey;

    /**
     * 桶名称
     */
    private String bucketName;

    /**
     * 前缀
     */
    private String prefix;

    /**
     * 访问站点
     */
    private String endpoint;

    /**
     * 自定义域名
     */
    private String domain;


    /**
     * 是否https（0否 1是）
     */
    private String isHttps;

    /**
     * 域
     */
    private String region;

    /**
     * 状态(0正常 1停用)
     */
    private String status;

    /**
     * 扩展字段
     */
    private String ext1;

    /**
     * 备注
     */
    private String remark;

}
