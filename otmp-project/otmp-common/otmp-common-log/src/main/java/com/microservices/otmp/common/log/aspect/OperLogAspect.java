package com.microservices.otmp.common.log.aspect;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.enums.BusinessStatus;
import com.microservices.otmp.common.kafka.factory.DefaultKafkaFactory;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.log.event.SysOperLogEvent;
import com.microservices.otmp.common.utils.AddressUtils;
import com.microservices.otmp.common.utils.IpUtils;
import com.microservices.otmp.common.utils.ServletUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.spring.SpringContextHolder;
import com.microservices.otmp.system.domain.SysOperLog;
import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.common.constant.Constants;
import com.microservices.otmp.common.enums.BusinessStatus;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.log.annotation.OperLog;
import com.microservices.otmp.common.utils.AddressUtils;
import com.microservices.otmp.common.utils.IpUtils;
import com.microservices.otmp.common.utils.ServletUtils;
import com.microservices.otmp.common.utils.StringUtils;
import com.microservices.otmp.common.utils.spring.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_OPER_LOG;

/**
 * 操作日志记录处理
 */
@Aspect
@Slf4j
@Component
public class OperLogAspect {

    @Autowired
    private KafkaSend kafkaSend;

    @Value("${kafka.topic.system-log}")
    private String systemLogTopic;

    // 配置织入点
    @Pointcut("@annotation(com.microservices.otmp.common.log.annotation.OperLog)")
    public void logPointCut() {
    }

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()")
    public void doAfterReturning(JoinPoint joinPoint) {
        handleLog(joinPoint, null);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e         异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e) {
        try {
            // 获得注解
            OperLog controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // *========数据库日志=========*//
            SysOperLog operLog = new SysOperLog();
            operLog.setStatus(BusinessStatus.SUCCESS.ordinal());
            // 请求的地址
            HttpServletRequest request = ServletUtils.getRequest();
            String ip = IpUtils.getIpAddr(request);
            operLog.setOperIp(ip);
            operLog.setOperUrl(request.getRequestURI());
            operLog.setOperLocation(AddressUtils.getRealAddressByIP(ip));
            String username = request.getHeader(Constants.CURRENT_USERNAME);
            operLog.setOperName(username);
            if (e != null) {
                operLog.setStatus(BusinessStatus.FAIL.ordinal());
                operLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
            }
            // 设置方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            operLog.setMethod(className + "." + methodName + "()");
            // 设置请求方式
            operLog.setRequestMethod(request.getMethod());
            // 处理设置注解上的参数
            Object[] args = joinPoint.getArgs();
            getControllerMethodDescription(controllerLog, operLog, args);
            // 发布事件
            SpringContextHolder.publishEvent(new SysOperLogEvent(operLog));
//            SystemLogDTO systemLogDTO = new SystemLogDTO();
//            systemLogDTO.setLogType(SYS_OPER_LOG.getCode());
//            systemLogDTO.setSysOperLog(operLog);
//            kafkaSend.asynSend(systemLogTopic, operLog.getOperIp(), JSON.toJSONString(systemLogDTO), DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY, new KafkaCallback(systemLogTopic), false);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log     日志
     * @param operLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(OperLog log, SysOperLog operLog, Object[] args) throws Exception {
        // 设置action动作
        operLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        operLog.setTitle(log.title());
        // 设置操作人类别
        operLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(operLog, args);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(SysOperLog operLog, Object[] args) throws Exception {
        List<?> param = new ArrayList<>(Arrays.asList(args)).stream().filter(p -> !(p instanceof ServletResponse))
                .collect(Collectors.toList());
        log.debug("args:{}", param);
        String params = JSONUtil.toJsonStr(param);
        operLog.setOperParam(StringUtils.substring(params, 0, 2000));
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private OperLog getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(OperLog.class);
        }
        return null;
    }
}