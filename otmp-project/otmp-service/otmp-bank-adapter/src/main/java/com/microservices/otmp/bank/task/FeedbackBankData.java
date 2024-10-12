package com.microservices.otmp.bank.task;

import com.microservices.otmp.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * created with IntelliJ IDEA.
 * user: licf
 * Date: 2023/8/17
 * time: 15:16
 * Description:
 */
@Service
@Transactional
@EnableScheduling
public class FeedbackBankData {

    @Autowired
    private BankService bankService;

    //    @Scheduled(cron = "0 0/5 * * * ?")
    public void getFeedbackBankData() {
        /*
         * 1.搜索数据库中应该拉取的文件
         * 2.拉取服务器中指定位置的文件
         * 3.解密并转换文件
         * 4.保存数据入库
         */
//        bankService.download("D:\\test\\decrypt\\");
    }

    public static void main(String[] args) {
        try {
            List<List<String>> data = new ArrayList<>();//list of lists to store data
            String file = "C:\\Users\\Admin\\Desktop\\test23.csv";//file path
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            //Reading until we run out of lines
            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"));//splitting lines
                data.add(lineData);
                line = br.readLine();
            }



            br.close();
        } catch (Exception e) {
            System.out.print(e);
        }
    }





}
