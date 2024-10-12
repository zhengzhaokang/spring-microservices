package com.microservices.otmp.common.utils;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.lang.reflect.Field;
import java.util.*;


public class MapUtil extends cn.hutool.core.map.MapUtil {
    /**
     * 取Map集合的差集
     */
    public static <S,T> Map<S, T> getDifferenceSet(Map<S, T> leftMap, Map<S, T> rightMap) {
        if (null != leftMap && null != rightMap) {

            Set<S> leftMapKey = leftMap.keySet();
            Set<S> rightMapKey = rightMap.keySet();
            Set<S> differenceSet = Sets.difference(leftMapKey, rightMapKey);
            Map<S, T> result = Maps.newHashMap();
            for (S key : differenceSet) {
                result.put(key, leftMap.get(key));
            }
            return result;

        } else {
            return null;
        }
    }

    /**
     * 取Map集合的差集（key不一致和value不一致）
     */
    public static <S,T> Map<S, T> getDifferenceMap(Map<S, T> leftMap, Map<S, T> rightMap) {
        if (null != leftMap && null != rightMap) {

            Set<S> leftMapKey = leftMap.keySet();
            Set<S> rightMapKey = rightMap.keySet();
            Set<S> differenceSetLeft = Sets.difference(leftMapKey, rightMapKey);
            Set<S> differenceSetRight = Sets.difference(rightMapKey, leftMapKey);
            Map<S, T> result = Maps.newHashMap();
            for (S key : differenceSetLeft) {
                result.put(key, leftMap.get(key));
            }
            for (S key : differenceSetRight) {
                result.put(key, rightMap.get(key));
            }
            if(differenceSetLeft.size()==differenceSetRight.size()&&differenceSetLeft.size()==0){
                for (S key : leftMapKey) {
                    if(!Objects.equals(leftMap.get(key),rightMap.get(key) )){
                        result.put(key, rightMap.get(key));
                    }
                }
            }
            return result;

        } else {
            return null;
        }
    }

    /**
     * 取Map集合的并集
     */
    public static <S,T> Map<S, T> getUnionSet(Map<S, T> leftMap, Map<S, T> rightMap) {
        if (null != leftMap && null != rightMap) {

            Set<S> leftMapKey = leftMap.keySet();
            Set<S> rightMapKey = rightMap.keySet();
            Set<S> differenceSet = Sets.union(leftMapKey, rightMapKey);
            Map<S, T> result = Maps.newHashMap();
            for (S key : differenceSet) {
                if (leftMap.containsKey(key)) {
                    result.put(key, leftMap.get(key));
                } else {
                    result.put(key, rightMap.get(key));
                }
            }
            return result;

        } else {
            return null;
        }
    }

    /**
     * 取Map集合的交集（String,String）
     */
    public static <S,T> Map<S, T> getIntersectionSet(Map<S, T> leftMap, Map<S, T> rightMap) {
        if (null != leftMap && null != rightMap) {

            Set<S> leftMapKey = leftMap.keySet();
            Set<S> rightMapKey = rightMap.keySet();
            Set<S> differenceSet = Sets.intersection(leftMapKey, rightMapKey);
            Map<S, T> result = Maps.newHashMap();
            for (S key : differenceSet) {
                result.put(key, leftMap.get(key));
            }
            return result;

        } else {
            return null;
        }
    }

/*
    public static void main(String[] args) {
        Map<String, Person> map1 = new HashMap<>();
        map1.put("a", new Person(1));
        map1.put("b", new Person(2));
        map1.put("c", new Person(3));

        Map<String, Person> map2 = new HashMap<>();
        map2.put("c", new Person(3));
        map2.put("d", new Person(4));
        map2.put("e", new Person(5));


        Map<String, Person> diffMap1 = getDifferenceSetByGuava(map1, map2);
        System.out.println("-------------差集结果,入参:A,B  出参:A-B后A中剩余的  -----------");
        diffMap1.forEach((k, v) -> System.out.println(k + ":" + v));

        Map<String, Person> diffMap2 = getDifferenceSetByGuava(map2, map1);
        System.out.println("-------------差集结果,入参:B,A  出参:B-A后B中剩余的  -----------");
        diffMap2.forEach((k, v) -> System.out.println(k + ":" + v));

        Map<String, Person> unionMap = getUnionSetByGuava(map1, map2);
        System.out.println("-------------并集结果-----------");
        unionMap.forEach((k, v) -> System.out.println(k + ":" + v));

        Map<String, Person> intersectionMap = getIntersectionSetByGuava(map1, map2);
        System.out.println("-------------交结果-----------");
        intersectionMap.forEach((k, v) -> System.out.println(k + ":" + v));

    }*/


    /**
     * 取Map集合的List
     */
    public static <S,T> List<T> getDifferenceList(Map<S, T> leftMap, Map<S, T> rightMap) {
        if (null != leftMap && null != rightMap) {

            Set<S> leftMapKey = leftMap.keySet();
            Set<S> rightMapKey = rightMap.keySet();
            Set<S> differenceSet = Sets.difference(leftMapKey, rightMapKey);
            List<T> result = new ArrayList<>();
            for (S key : differenceSet) {
                result.add(leftMap.get(key));
            }
            return result;

        } else {
            return Collections.emptyList();
        }

    }

    /**
     * 对象转Map
     * @param object
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String,Object> beanToMap(Object object) throws IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            map.put(field.getName(), field.get(object));
        }
        return map;
    }

}
