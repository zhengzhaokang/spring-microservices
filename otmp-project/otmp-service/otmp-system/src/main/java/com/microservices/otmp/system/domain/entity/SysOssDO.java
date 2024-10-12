package com.microservices.otmp.system.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传
 */
@Data
@Table(name = "sys_oss")
public class SysOssDO implements Serializable
{
    //
    private static final long serialVersionUID = 1356257283938225230L;

    @Id
    private Long              id;

    /** 文件名 */
    private String            fileName;

    /** 文件后缀 */
    private String            fileSuffix;

    /** URL地址 */
    private String            url;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date              createTime;

    /** 上传者 */
    private String            createBy;

    /** 服务商 */
    private String           service;

    /** 文件原名 */
    private String originalName;
    
    /** 用于表格行内编辑*/
    @Transient 
    private Boolean editable;

    
    
}
