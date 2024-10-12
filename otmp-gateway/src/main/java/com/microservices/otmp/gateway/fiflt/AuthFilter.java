package com.microservices.otmp.gateway.fiflt;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.microservices.otmp.gateway.domain.Constants;
import com.microservices.otmp.gateway.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.client.codec.StringCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 网关鉴权
 */
@Slf4j
@Component
public class AuthFilter implements GlobalFilter, Ordered {
    // 排除过滤的 uri 地址
    // swagger排除自行添加
    private static final String[] whiteList = {"/auth/login","/system/login","/system/login/slide", "/user/register",
            "/system/v2/api-docs", "/auth/captcha/check", "/auth/captcha/get", "/auth/login/slide", "/auth/get/token",
            "/system/adfs/acs","/system/index","/system/adfs/user","/system/adfs/token","/system/checkItCode",
            "/financing/supplier/onboarding/register","/financing/supplier/onboarding/company/name","/system/microservices/id/index",
            "/system/microservices/id/validateWebauthnSt","/system/microservices/id/logout"};

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 12小时后过期
     */
    private static final long   EXPIRE        = 12L * 60L * 60L;

    private static final String ACCESS_TOKEN  = Constants.ACCESS_TOKEN;

    private static final String ACCESS_USERID = Constants.ACCESS_USERID;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request =exchange.getRequest();
        Object traceId=request.getHeaders().get(Constants.TRACE_ID);
        if(ObjectUtils.isEmpty(traceId)){
            traceId=getNextRandomId();
            request=exchange.getRequest().mutate().header(Constants.TRACE_ID, traceId.toString()).build();
        }

        String url = request.getURI().getPath();
        log.info("url:{}", url);
        // 跳过不需要验证的路径
        if (Arrays.asList(whiteList).contains(url)) {
            return chain.filter(exchange);
        }
        if (url.contains("/xxljob/")) {
            return chain.filter(exchange);
        }
        String token = request.getHeaders().getFirst(Constants.TOKEN);
        // token为空
        if (StringUtils.isBlank(token)) {
            return setUnauthorizedResponse(exchange, "token can't null or empty string");
        }
        RBucket<String> rBucket = redissonClient.getBucket(Constants.ACCESS_TOKEN + token, StringCodec.INSTANCE);
        if (rBucket == null || StringUtils.isBlank(rBucket.get())) {
            return setUnauthorizedResponse(exchange, "token verify error");
        }
        JSONObject jo = JSONObject.parseObject(rBucket.get(), Feature.IgnoreAutoType);
        String userId = jo.getString("userId");
        // 查询token信息
        if (StringUtils.isBlank(userId)) {
            return setUnauthorizedResponse(exchange, "token verify error");
        }
        //登录续期
        refreshToken(token, userId);
        // 设置userId到request里，后续根据userId，获取用户信息
        request.mutate().header(Constants.CURRENT_ID, userId)
                .header(Constants.CURRENT_USERNAME, jo.getString("loginName")).build();

        ServerHttpResponse response= exchange.getResponse();
        response.getHeaders().add(Constants.TRACE_ID, traceId.toString());
        ServerWebExchange mutableExchange = exchange.mutate().request(request).response(response).build();
        return chain.filter(mutableExchange);
    }

    private void refreshToken(String token, String userId) {
        log.info("### AuthHandlerInterceptor refreshToken token");
        redisTemplate.expire(ACCESS_TOKEN + token, EXPIRE, TimeUnit.SECONDS);
        redisTemplate.expire(ACCESS_USERID + userId, EXPIRE, TimeUnit.SECONDS);
    }

    private Mono<Void> setUnauthorizedResponse(ServerWebExchange exchange, String msg) {
        ServerHttpResponse originalResponse = exchange.getResponse();
        originalResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
        originalResponse.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
        byte[] response = null;
        response = JSON.toJSONString(R.error(401, msg)).getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = originalResponse.bufferFactory().wrap(response);
        return originalResponse.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -200;
    }


    private  String getNextRandomId()
    {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        return uuid;
    }
}