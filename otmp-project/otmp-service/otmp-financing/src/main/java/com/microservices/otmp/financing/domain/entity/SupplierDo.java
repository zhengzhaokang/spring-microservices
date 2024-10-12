package com.microservices.otmp.financing.domain.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

@Table(name = "supplier")
@Data
public class SupplierDo {

    public static final Integer STATUS_AWAITING = 0;
    public static final Integer STATUS_PENDING = 1;
    public static final Integer STATUS_ACTIVE = 2;
    public static final Integer STATUS_SUSPEND = 3;
    public static final Integer STATUS_FAILED = 4;
    public static final Integer STATUS_ON_HOLD = 5;

    public static final Integer ON_SHORE_FALSE = 0;
    public static final Integer ON_SHORE_TRUE = 1;

    public static final Integer ON_HOLD_FALSE = 0;
    public static final Integer ON_HOLD_TRUE = 1;

    public static final String ON_HOLD_TIME_PATTERN = "yyyy-MM-dd HH:mm";

    public static final String CAL_TYPE_PAY = "1";
    public static final String CAL_TYPE_DUE = "2";
    public static final String CAL_TYPE_PAY_STR = "invoicePayDate";
    public static final String CAL_TYPE_DUE_STR = "invoiceDueDate";

    public static String convertBasisDateToNum(String typeStr){
        return CAL_TYPE_PAY_STR.equals(typeStr) ? CAL_TYPE_PAY : CAL_TYPE_DUE;
    }

    public static String convertBasisDateToStr(String type){
        return CAL_TYPE_PAY.equals(type) ? CAL_TYPE_PAY_STR : CAL_TYPE_DUE_STR;
    }

    @Id
    private Long id;
    /**
     * 供应商名称
     */
    private String supplierName;
    /**
     * 邀请日期
     */
    private Date invitationDate;
    /**
     * 激活日期
     */
    private Date activationDate;
    /**
     * 禁用日期
     */
    private Date suspendDate;
    /**
     * 供应商状态，0：awaiting，1：pending，2：激活，3：禁用，4：失败
     */
    private Integer status;
    /**
     * 供应商类型
     */
    private String supplierModel;
    /**
     * 融资类型
     */
    private String financingModel;
    private String currency;
    private String contactFirstName;
    private String contactLastName;
    private String contactDestination;
    private String contactEmail;
    private String contactPhone;
    private String phoneRegion;
    /**
     * onShore标识，0:false,1:true
     */
    private Integer onShoreFlag;

    private String microservicesEntityName;
    private String preferredFinancingModel;
    private String primarySupplierCode;

    private Double minimumNetFinancingAmount;
    private Double toUpPricing;
    private Integer invalidDaysBeforeMaturityDate;
    private String basisOfMaturityDateCalculation;
    private String paymentCycle;
    private String weekOfTheMonth;
    private Integer traceBackHistoryInvoiceDays;
    private LocalDateTime onHoldStartTime;
    private LocalDateTime onHoldEndTime;
    /**
     * onHold任务标识，已完成API调用的会被标识为1（ON_HOLD_TRUE）
     */
    private Integer onHoldFlag;

    /**
     * 数据状态，0：正常，1：禁用
     */
    private String deleteFlag;

    private LocalDateTime createTime;
    private String createBy;
    private LocalDateTime updateTime;
    private String updateBy;
    private Boolean maturityDateChangeable;
    private String fileIds;
    private Boolean needUpdate;
}
