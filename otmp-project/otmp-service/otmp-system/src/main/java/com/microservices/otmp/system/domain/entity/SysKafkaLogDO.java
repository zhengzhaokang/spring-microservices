package com.microservices.otmp.system.domain.entity;

import com.microservices.otmp.common.annotation.Excel;
import com.microservices.otmp.common.core.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * 操作日志记录表 oper_log
 * 
 * @author lovefamily
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysKafkaLogDO extends BaseDO
{
    //
    private static final long serialVersionUID = -5556121284445360558L;

    private Boolean     isError;
    private Long      offset;
    private Integer      partition;
    private String      key;

    /** 日志主键 */
    @Excel(name = "操作序号")
    private Long              operId;

    private List<Long> operIds;

    /** 操作模块 */
    @Excel(name = "topic")
    private String            topic;

    /** 业务类型（0其它 1新增 2修改 3删除） */
    @Excel(name = "业务类型", readConverterExp = "0=其它,1=新增,2=修改,3=删除,4=授权,5=导出,6=导入,7=强退,8=生成代码,9=清空数据")
    private Integer           businessType;

    /** 请求方法 */
    @Excel(name = "请求方法")
    private String            method;

    /** 操作类别（0其它 1后台用户 2手机端用户） */
    @Excel(name = "操作类别", readConverterExp = "0=其它,1=后台用户,2=手机端用户")
    private Integer           operatorType;

    /** 请求url */
    @Excel(name = "请求地址")
    private Boolean isSend;

    /** 操作地址 */
    @Excel(name = "操作地址")
    private String            receiveIp;

    private String            sendIp;

    /** 请求参数 */
    @Excel(name = "消息内容")
    private String            kafkaMsg;

    /** 操作状态（0正常 1异常） */
    @Excel(name = "状态", readConverterExp = "0=正常,1=异常")
    private Integer           status;

    /** 错误消息 */
    @Excel(name = "错误消息")
    private String            errorMsg;

    //失败报错会记录topic等信息到redis,用于记录失败重试次数,为了追踪和清理对应key,需要记录此rediskey到日志里
    private String            redisLogKey;

    /** 操作时间 */
    @Excel(name = "操作时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date              operTime;
}
