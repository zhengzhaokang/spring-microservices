package com.microservices.otmp.analysis.common.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.SneakyThrows;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.function.BiFunction;

/**
 * excel下载util
 *
 * @author zhangdb3
 * @date 2022/12/29
 */
public class ExcelDownloadUtil {

    /**
     * 下载响应设置
     *
     * @param response 响应
     * @param fileName 文件名
     */
    @SneakyThrows
    public static void settingResponse(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    @SneakyThrows
    public static void settingResponseNew(HttpServletResponse response, String fileName) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=" + fileName + ".xlsx");
    }


    /**
     * 单表下载
     * 执行无上限导出,只要愿意等
     * 并行数量不大不会OOM
     *
     * @param response   响应
     * @param outClass   导出的实体类
     * @param fileName   文件名
     * @param pageSelect 翻页查询 入参 当前页,页大小
     */
    @SneakyThrows
    public static <T> void singleTableDownload(HttpServletResponse response,
                                               Class<T> outClass,
                                               String fileName,
                                               BiFunction<Integer, Integer, Page<T>> pageSelect) {
        ExcelDownloadUtil.settingResponse(response, fileName);
        WriteSheet writeSheet = new WriteSheet();
        try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), outClass).build()) {
            for (int i = 1; i < 10000; i++) {
                // 一个 Sheet 最大 104W
                int curMaxRow = i * 5000;
                int curSheetNo = curMaxRow / 1000000;
                writeSheet.setSheetNo(curSheetNo);
                writeSheet.setSheetName("sheet" + curSheetNo);

                // 分页去数据库查询数据
                // 一页 5000 查到查询不出来为止
                Page<T> pageData = pageSelect.apply(i, 5000);

                excelWriter.write(pageData.getRecords(), writeSheet);

                if (!pageData.hasNext()) {
                    // 后面没有数据了
                    break;
                }
            }
        }
    }
}
