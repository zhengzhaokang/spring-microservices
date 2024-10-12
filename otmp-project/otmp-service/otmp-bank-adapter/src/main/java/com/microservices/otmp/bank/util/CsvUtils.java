package com.microservices.otmp.bank.util;


import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: lovefamily
 * @date: 2023/09/21 11:05
 * @Version 1.0
 */
@Slf4j
public class CsvUtils {


    /**
     * 解析csv文件并转成数组（方法二）
     *
     * @param file csv文件
     * @return 数组
     */
    public static List<String[]> getCsvArrayData(MultipartFile file) {

        List<String[]> list = new ArrayList<String[]>();
        int i = 0;
        try {
            CSVReader csvReader = new CSVReaderBuilder(
                    new BufferedReader(
                            new InputStreamReader(file.getInputStream(), "utf-8"))).build();
            Iterator<String[]> iterator = csvReader.iterator();
            while (iterator.hasNext()) {
                String[] next = iterator.next();
                //去除第一行的表头，从第二行开始
                if (i >= 0) {
                    list.add(next);
                }
                i++;
            }
            return list;
        } catch (Exception e) {
            log.error("CSV文件读取异常");
            return list;
        }
    }


    /**
     * 解析csv文件并转成bean（方法三）
     *
     * @param file  csv文件
     * @param clazz 类
     * @param <T>   泛型
     * @return 泛型bean集合
     */
    public static <T> List<T> getCsvBeanData(MultipartFile file, Class<T> clazz) {
        InputStreamReader in = null;
        CsvToBean<T> csvToBean = null;
        try {
            in = new InputStreamReader(file.getInputStream(), "utf-8");
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            csvToBean = new CsvToBeanBuilder<T>(in).withMappingStrategy(strategy).build();
        } catch (Exception e) {
            log.error("数据转化失败");
            return null;
        }
        return csvToBean.parse();
    }

    private static String splitResult(String once) {
        String result = "";
        for (int i = 0; i < once.length(); i++) {
            if (once.charAt(i) != '"') {
                result += once.charAt(i);
            }
        }
        return result;
    }
}
