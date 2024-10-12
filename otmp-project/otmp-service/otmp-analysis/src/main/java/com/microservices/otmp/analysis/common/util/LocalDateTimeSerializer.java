package com.microservices.otmp.analysis.common.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 时间戳转LocalDateTime
 *
 * @author db117
 * @since 2023/5/26
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        long epochMilli = value.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        gen.writeRawValue(String.valueOf(epochMilli));
    }
}
