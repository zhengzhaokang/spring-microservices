package com.microservices.otmp.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.microservices.otmp.common.json.JSON;
import com.microservices.otmp.common.json.JSONObject;
import com.microservices.otmp.common.utils.http.HttpUtils;

/**
 * 获取地址类
 * 
 * @author lovefamily
 */
public class AddressUtils
{
    private static final Logger log    = LoggerFactory.getLogger(AddressUtils.class);

    public static final String  IP_URL = "http://region.otmp.com";

    public static String getRealAddressByIP(String ip)
    {
        String address = "XX XX";
        // 内网不查询
        if (IpUtils.internalIp(ip))
        {
            return "内网IP";
        }
        String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr))
        {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj;
        try
        {
            obj = JSON.unmarshal(rspStr, JSONObject.class);
            JSONObject data = obj.getObj("data");
            address = data.getStr("address");
        }
        catch (Exception e)
        {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
