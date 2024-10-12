package com.microservices.otmp.system.common;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

@Slf4j
public class Base64Utils {
    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 密码明文规则 7个随机字符 + 密码明文 + 6个随机字符串
     *
     * @param base64Encode
     * @return
     */
    public static String getCleartextPassword(String base64Encode) {
        String str = Base64.decodeStr(base64Encode, Charset.forName(DEFAULT_CHARSET));
        if (StrUtil.isBlank(str)) {
            return null;
        }
        try {
            str = StrUtil.subSuf(str, 7);
            str = StrUtil.subPre(str, -6);
            return str;
        } catch (IndexOutOfBoundsException e) {
            log.error("密码获取异常 -> 下标越界");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("密码获取异常 -> {}", e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getCleartextPassword("YnoxNHc0dDEyMzQ1NnFhOXk2Yg=="));
    }
}
