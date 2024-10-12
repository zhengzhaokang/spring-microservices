package com.microservices.otmp.system.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;

@Slf4j
public class EntityUtil {

    private EntityUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 统一处理空格，把小写统一转成大写
     *
     * @param obj
     */
    public static void objectToTrim(Object obj) {

        //子集赋值
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        fieldValue(obj, declaredFields);
        //父集赋值
        Class<?> superclass = obj.getClass().getSuperclass();
        superClass(obj, superclass);

    }

    private static void superClass(Object obj, Class<?> superclass) {
        Field[] declaredFields1 = superclass.getDeclaredFields();
        fieldValue(obj, declaredFields1);
    }
@SuppressWarnings({"java:S3776","java:S3011"})
    private static void fieldValue(Object obj, Field[] declaredFields1) {
        for (Field field : declaredFields1) {
            String type = field.getType().getCanonicalName();
            if (StringUtils.equals("java.lang.String", type)) {
                field.setAccessible(true);
                Object object = null;
                try {
                    object = field.get(obj);
                } catch (IllegalAccessException e) {
                    log.error("IllegalAccessException {}", e);
                }
                if (null != object) {
                    StringBuilder trim = new StringBuilder();
                    String s = object.toString();
                    String[] split = s.split(ConstantSystem.COMMA);
                    //对多选的值进行空格去除
                    for (String s1 : split) {
                        trim.append(s1.trim()).append(ConstantSystem.COMMA);
                    }
                    //去除字符串最后的逗号
                    String str = trim.substring(0, trim.length() - 1);
                    try {
                        field.set(obj, str);
                    } catch (IllegalAccessException e) {
                        log.error("IllegalAccessException {}", e);
                    }
                }
            }
        }
    }

}
