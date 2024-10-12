package com.microservices.otmp.common.exception.feign;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;

/**
 * 重新封装 feign异常类，确保报错信息抛出
 */
@Configuration
public class FeignErrorDecoder implements ErrorDecoder {

    @SneakyThrows
    @Override
    public Exception decode(String methodKey, Response response) {

        // 这里直接拿到咱们抛出的异常信息
        Response.Body body = response.body();
        if(body == null ){
            throw new HystrixBadRequestException("null body");
        }else{
            String message = Util.toString(body.asReader(StandardCharsets.UTF_8));
            JSONObject jsonObject = JSONObject.parseObject(message);
            throw new HystrixBadRequestException(jsonObject.getString("msg"));
        }
    }
}
