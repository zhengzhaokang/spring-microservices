package com.microservices.otmp.gateway.domain;

/**
 * 通用常量信息
 * 
 * @author lovefamily
 */
public class Constants
{
    /**ValidateCodeException
     * UTF-8 字符集
     */
    public static final String UTF8             = "UTF-8";
    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * RMI 远程方法调用
     */
    public static final String LOOKUP_RMI = "rmi:";

    /**
     * LDAP 远程方法调用
     */
    public static final String LOOKUP_LDAP = "ldap:";

    /**
     * LDAPS 远程方法调用
     */
    public static final String LOOKUP_LDAPS = "ldaps:";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";


    /**
     * 通用成功标识
     */
    public static final String SUCCESS          = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL             = "1";

    /**
     * 登录成功
     */
    public static final String LOGIN_SUCCESS    = "Success";

    /**
     * 注销
     */
    public static final String LOGOUT           = "Logout";

    /**
     * 登录失败
     */
    public static final String LOGIN_FAIL       = "Error";

    /**
     * 自动去除表前缀
     */
    public static final String AUTO_REOMVE_PRE  = "true";

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM         = "pageNum";

    /**
     * 每页显示记录数
     */
    public static final String PAGE_SIZE        = "pageSize";

    /**
     * 排序列
     */
    public static final String ORDER_BY_COLUMN  = "sortField";

    /**
     * 排序的方向 "desc" 或者 "asc".
     */
    public static final String IS_ASC           = "sortOrder";

    public static final String CURRENT_ID       = "current_id";

    public static final String CURRENT_USERNAME = "current_username";

    public static final String TOKEN            = "token";

    public static final String DEFAULT_CODE_KEY = "random_code_";

    public final static String ACCESS_TOKEN     = "access_token_";

    public final static String ACCESS_USERID    = "access_userid_";

    public static final String RESOURCE_PREFIX  = "/profile";

    public static final String TRACE_ID  = "trace_id";
}
