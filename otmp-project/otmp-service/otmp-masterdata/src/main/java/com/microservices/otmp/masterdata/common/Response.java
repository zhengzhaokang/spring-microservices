package com.microservices.otmp.masterdata.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 表格分页数据对象
 *
 * @author lovefamily
 */
@Data
public class Response<T extends Serializable> implements Serializable {

    /**
     * 消息状态码
     */
    private String code;

    private transient T data;

    private transient T result;

    private String msg;

    private String message;

}
