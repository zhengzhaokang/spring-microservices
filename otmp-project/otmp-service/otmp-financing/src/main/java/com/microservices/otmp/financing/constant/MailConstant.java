package com.microservices.otmp.financing.constant;

public class MailConstant {

    /**
     * 发送人
     */
    public static final String MAIL_CREATOR_ANCHOR = "otfp_admin";
    /**
     * 发送邀请邮件
     */
    public static final String MAIL_TYPE_INVITE_SUPPLIER = "anchor_invite";
    /**
     * Anchor激活Supplier
     */
    public static final String MAIL_TYPE_ACTIVE_SUPPLIER = "anchor_confirm_active";
    public static final String MAIL_TYPE_ACTIVE_SUPPLIER_LINK = "anchor_confirm_active_new";
    /**
     * 给supplier的注册提醒
     */
    public static final String MAIL_TYPE_REG_SUPPLIER = "supplier_register_supplier";
    /**
     * 给anchor的注册提醒
     */
    public static final String MAIL_TYPE_REG_ANCHOR = "supplier_register_anchor";
    /**
     * 提交融资
     */
    public static final String MAIL_TYPE_FINANCING = "submit_financing_application";
    /**
     * 模块名称
     */
    public static final String MAIL_MODULE_AP_FINANCING = "AP Financing";
    /**
     * 业务类型：Anchor
     */
    public static final String BUSINESS_TYPE_ANCHOR = "Anchor";
    /**
     * 业务类型：Supplier
     */
    public static final String BUSINESS_TYPE_SUPPLIER = "Supplier";
    public static final String GEO_CODE_ALL = "ALL";
    public static final String BUSINESS_GROUP_ALL = "ALL";

}
