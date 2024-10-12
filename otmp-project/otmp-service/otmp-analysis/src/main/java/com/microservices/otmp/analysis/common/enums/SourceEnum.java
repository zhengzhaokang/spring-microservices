package com.microservices.otmp.analysis.common.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservices.otmp.analysis.common.domain.DebeziumDataDTO;
import com.microservices.otmp.analysis.common.domain.SourceObject;
import com.microservices.otmp.analysis.common.util.JacksonUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Function;

/**
 * 原始数据
 *
 * @author db117
 * @since 2023/4/20
 */
@Slf4j
@Getter
public enum SourceEnum {
    debezium(SourceEnum::debezium);

    private final static ObjectMapper om = JacksonUtil.base();
    /**
     * 转换成对象
     */
    private final Function<String, SourceObject> convert;

    SourceEnum(Function<String, SourceObject> convert) {
        this.convert = convert;
    }

    public SourceObject convert(String s) {
        return convert.apply(s);
    }

    private static SourceObject debezium(String s) {
        try {
            DebeziumDataDTO dto = om.readValue(s, DebeziumDataDTO.class);
            return SourceObject.of(dto);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
