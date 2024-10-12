package com.microservices.otmp.bank.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  * 文件路径:模板上传下载路径，获取常量路径
 *  * 
 *  * @author lovefamily
 *  *
 *  
 */
public class VoaPathUtil {
    public static Log log = LogFactory.getLog(SFTPUtils.class);

    /*
     * 获取秘钥文件路径，根据系统处理文件后缀
     * 
     * @return 秘钥路径
     */
    public static String getKeyFilePath(String keyFilePath) {
        String osName = System.getProperty("os.name"); // 操作系统名称
        if (StringUtils.contains(osName.toLowerCase(), "windows"))
            return keyFilePath.replace(".ppk", ".pem");
        else
            return keyFilePath;
    }

}
