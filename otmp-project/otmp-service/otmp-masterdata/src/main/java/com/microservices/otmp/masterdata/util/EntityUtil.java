package com.microservices.otmp.masterdata.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * 试替对象处理
 *
 * @author qiaocan
 */
public class EntityUtil {
    private EntityUtil() {
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

    private static void fieldValue(Object obj, Field[] declaredFields1) {
        for (Field field : declaredFields1) {
            String type = field.getType().getCanonicalName();
            if (StringUtils.equals("java.lang.String", type)) {
                ReflectionUtils.makeAccessible(field);
                Object object = null;
                try {
                    object = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                extracted(obj, field, object);
            }
        }
    }

    private static void extracted(Object obj, Field field, Object object) {
        if (null != object) {
            StringBuilder trim = new StringBuilder();
            String s = object.toString();
            String[] split = s.split(",");
            //对多选的值进行空格去除
            for (String s1 : split) {
                trim.append(s1.trim()).append(",");
            }
            //去除字符串最后的逗号
            String str = trim.substring(0,trim.length()-1);
            try {
                ReflectionUtils.setField(field, obj, str);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
