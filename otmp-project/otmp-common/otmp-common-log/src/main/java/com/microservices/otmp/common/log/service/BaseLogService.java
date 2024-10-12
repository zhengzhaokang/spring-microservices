package com.microservices.otmp.common.log.service;

import com.alibaba.fastjson.JSON;
import com.microservices.otmp.common.kafka.factory.DefaultKafkaFactory;
import com.microservices.otmp.common.kafka.listener.KafkaCallback;
import com.microservices.otmp.common.kafka.util.KafkaSend;
import com.microservices.otmp.common.utils.DateUtils;
import com.microservices.otmp.system.domain.dto.DimensionDTO;
import com.microservices.otmp.system.domain.dto.SysBusinessMassUploadLogDTO;
import com.microservices.otmp.system.domain.dto.SysBusinessOperatorLogDTO;
import com.microservices.otmp.system.domain.dto.SystemLogDTO;
import com.microservices.otmp.system.feign.RemoteBusinessMassUploadLogService;
import com.microservices.otmp.system.feign.RemoteBusinessOperatorLogService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_BUSINESS_MASSUPLOAD_LOG;
import static com.microservices.otmp.system.enums.SystemLogTypeEnum.SYS_BUSINESS_OPERATOR_LOG;

@Service
@Slf4j
public class BaseLogService {
    protected final Logger loggerBase = LoggerFactory.getLogger(BaseLogService.class);

    @Autowired
    private KafkaSend kafkaSend;

    @Value("${kafka.topic.system-log}")
    private String systemLogTopic;

    @Autowired
    private RemoteBusinessMassUploadLogService remoteBusinessMassUploadLogService;
    @Autowired
    private RemoteBusinessOperatorLogService remoteBusinessOperatorLogService;

    public void addUploadLog(String loginName, String fileName, Integer total, String success, int i, String url, String moduleType) {
        SysBusinessMassUploadLogDTO dto = new SysBusinessMassUploadLogDTO();
        dto.setFileName(fileName);
        dto.setStatus(success);
        dto.setRecordCount(total);
        dto.setErrorCount(i);
        dto.setOperator(loginName);
        dto.setOperatorDate(DateUtils.getNowDate());
        dto.setUrl(url);
        dto.setModuleType(moduleType);
        dto.setCreateBy("sys");
        dto.setCreateTime(DateUtils.getNowDate());
        dto.setUpdateBy("sys");
        dto.setUpdateTime(DateUtils.getNowDate());
        dto.setStartDateTime(DateUtils.getNowDate());
        dto.setEndDateTime(DateUtils.getNowDate());
//        remoteBusinessMassUploadLogService.add(dto);
        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setLogType(SYS_BUSINESS_MASSUPLOAD_LOG.getCode());
        systemLogDTO.setSysBusinessMassUploadLog(dto);
        kafkaSend.asynSend(systemLogTopic, String.valueOf(moduleType), JSON.toJSONString(systemLogDTO), DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY, new KafkaCallback(systemLogTopic), false);

    }

    public void addMassUploadLog(String loginName, String fileName, Integer total, String success, Integer errorCount, String url, String moduleType) {
        try {
            SysBusinessMassUploadLogDTO dto = new SysBusinessMassUploadLogDTO();
            dto.setFileName(fileName);
            dto.setStatus(success);
            dto.setRecordCount(total);
            dto.setErrorCount(errorCount);
            dto.setOperator(loginName);
            dto.setOperatorDate(DateUtils.getNowDate());
            dto.setUrl(url);
            dto.setModuleType(moduleType);
            dto.setCreateBy(loginName);
            dto.setCreateTime(DateUtils.getNowDate());
            dto.setUpdateBy(loginName);
            dto.setUpdateTime(DateUtils.getNowDate());
//            remoteBusinessMassUploadLogService.add(dto);
            SystemLogDTO systemLogDTO = new SystemLogDTO();
            systemLogDTO.setLogType(SYS_BUSINESS_MASSUPLOAD_LOG.getCode());
            systemLogDTO.setSysBusinessMassUploadLog(dto);
            kafkaSend.asynSend(systemLogTopic, String.valueOf(moduleType), JSON.toJSONString(systemLogDTO), DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY, new KafkaCallback(systemLogTopic), false);

        }catch (Exception e){
            loggerBase.error("操作日志添加报错:", e);
        }
    }


    public void addOperatorLog(String operation, String poolCode, String detail, String module, String updateBy) {
        try {
            SysBusinessOperatorLogDTO operatorDto = getOperatorDto(operation, poolCode, detail, module, updateBy);
            remoteBusinessOperatorLogService.add(operatorDto);
        } catch (Exception e) {
            loggerBase.error("记录操作日志报错:", e);
        }
    }

    public void addOperatorLogAndDimension(String operation, String poolCode, String detail, String module, String updateBy, Object dimension) {
        SysBusinessOperatorLogDTO operatorDto = getOperatorDto(operation, poolCode, detail, module, updateBy);
        if (null != dimension) {
            DimensionDTO dto = (DimensionDTO) dimension;
            operatorDto.setBusinessGroup(dto.getBusinessGroup());
            operatorDto.setSegmentCode(dto.getSegmentCode());
            operatorDto.setGtnTypeCode(dto.getGtnTypeCode());
            operatorDto.setSalesOfficeCode(dto.getSalesOfficeCode());
            operatorDto.setGeoCode(dto.getGeoCode());
            operatorDto.setRegionMarketCode(dto.getRegionMarketCode());
            operatorDto.setAccrualVersion(dto.getAccrualVersion());
            operatorDto.setSalesOrgCode(dto.getSalesOrgCode());
        }

        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setLogType(SYS_BUSINESS_OPERATOR_LOG.getCode());
        systemLogDTO.setSysBusinessOperatorLog(operatorDto);
        kafkaSend.asynSend(systemLogTopic, String.valueOf(operatorDto.getModuleType()), JSON.toJSONString(systemLogDTO), DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY, new KafkaCallback(systemLogTopic),false);

//        remoteBusinessOperatorLogService.add(operatorDto);
    }

    public void addOperatorLog(String operation, String poolCode, String detail, String module, String updateBy, Object operationData) {
        SysBusinessOperatorLogDTO operatorDto = getOperatorDto(operation, poolCode, detail, module, updateBy);
        DimensionService.setDimension(operatorDto, operationData);

        SystemLogDTO systemLogDTO = new SystemLogDTO();
        systemLogDTO.setLogType(SYS_BUSINESS_OPERATOR_LOG.getCode());
        systemLogDTO.setSysBusinessOperatorLog(operatorDto);
        kafkaSend.asynSend(systemLogTopic, String.valueOf(operatorDto.getModuleType()), JSON.toJSONString(systemLogDTO), DefaultKafkaFactory.SYS_LOG_KAFKA_FACTORY, new KafkaCallback(systemLogTopic),false);
//        remoteBusinessOperatorLogService.add(operatorDto);
    }


    public SysBusinessOperatorLogDTO getOperatorDto(String operation, String poolCode, String detail, String module, String updateBy) {
        SysBusinessOperatorLogDTO operatorDto = new SysBusinessOperatorLogDTO();
        operatorDto.setOperation(operation);
        operatorDto.setOperator(updateBy);
        operatorDto.setOperationDate(DateUtils.getNowDate());
        operatorDto.setTarget(poolCode);
        operatorDto.setDetail(detail);
        operatorDto.setModuleType(module);
        operatorDto.setCreateBy(updateBy);
        operatorDto.setCreateTime(DateUtils.getNowDate());
        operatorDto.setUpdateBy(updateBy);
        operatorDto.setUpdateTime(DateUtils.getNowDate());
        return operatorDto;
    }
}
