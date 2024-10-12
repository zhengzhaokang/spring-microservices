/*
 * @(#)Donate.java 2019年12月20日 下午2:04:15
 * Copyright 2019 zmr, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.microservices.otmp.system.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.experimental.Accessors;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * <p>File：Donate.java</p>
 * <p>Title: 捐赠</p>
 * <p>Description:</p>
 * <p>Copyright: Copyright (c) 2019 2019年12月20日 下午2:04:15</p>
 * <p>
 * @Author lovefamily
 * @version 1.0
 */
@Data
@Accessors(chain = true)
public class DonateDO
{
    @Id
    @KeySql(useGeneratedKeys = true)
    private Integer id;

    private String  nick;

    private Double  amount;

    private Integer canal;

    private String  remark;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date    createTime;

    @Transient
    private String  beginTime;

    @Transient
    private String  endTime;
}
