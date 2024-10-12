package com.microservices.otmp.domain;

import lombok.Data;


@Data
public class ImportResult<T>  {

    private boolean result;
    private String msg;
    private T data;
    private String url;
    private Integer errorTotal;


    public Integer getErrorTotal() {
        return errorTotal;
    }

    public void setErrorTotal(Integer errorTotal) {
        this.errorTotal = errorTotal;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public ImportResult(Boolean re, String msg, T data) {
        this.result = re;
        this.msg = msg;
        this.data = data;
    }

    public ImportResult(Boolean result, String msg, T data, Integer errorTotal) {
        this.result = result;
        this.msg = msg;
        this.data = data;
        this.errorTotal = errorTotal;
    }

    public boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 成功
     *
     * @param msg
     * @return
     */
    public static <T> ImportResult<T> importSuccess(String msg, T data) {
        return new  ImportResult<>(true, msg, data);
    }

    /**
     * 失败
     *
     * @param msg
     * @return
     */
    public static <T>ImportResult<T> importFail(String msg) {
        return new ImportResult<>(false, msg, null);
    }

    public static <T>ImportResult<T> importFail(String msg, T obj) {
        return new ImportResult<>(false, msg, obj);
    }

    public static <T> ImportResult<T> importFail(String msg, T obj, Integer errorTotal) {
        return new  ImportResult<>(false, msg, obj, errorTotal);
    }
}
