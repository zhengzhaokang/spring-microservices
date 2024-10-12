package com.microservices.otmp.filestorage.schedule;

import com.microservices.otmp.filestorage.domain.impl.SysFileServiceImpl;
import com.microservices.otmp.filestorage.util.SysDateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * 定时同步文件到目标服务器，每天23点同步一次
 *
 * scp -r source_dir username@ip address:/home/username/target_dir
 */
@Component
public class UploadFileSyncScheduleTask {

    @Value("${file.data.path}")
    private String serverFilePath;

    @Value("${file.data.destInfo}")
    private String destInfo;

    @Value("${file.data.isSwitch}")
    private String isSwitch;

    private static final Logger logger = LoggerFactory.getLogger(UploadFileSyncScheduleTask.class);
    @Scheduled(cron = "0 0/5 * * * ?")
    public void syncUploadFile(){
        logger.info("UploadFileSyncScheduleTask 根据cron表达式的定时执行规则，执行同步文件任务~ isSwitch is " + isSwitch);
        int switchNum = Integer.parseInt(isSwitch);
        if (switchNum != 1) {
            logger.info("UploadFileSyncScheduleTask switchNum != 1 ");
            return;
        }
        String yyyyMMdd = SysDateUtils.getDateYYYYMMDD() + File.separator;
        String sourcePath = serverFilePath + yyyyMMdd;

        String curOrder = "scp -r " + sourcePath + " " + destInfo;
        InputStream in = null;
        BufferedReader br = null;
        Process process = null;
        try{
            process = Runtime.getRuntime().exec(curOrder);
            in = process.getInputStream();
            br = new BufferedReader(new InputStreamReader(in));
            while (in.read() != -1) {
                String result = br.readLine();
                logger.info(result);
            }

            logger.info("UploadFileSyncScheduleTask 复制文件 " + curOrder +" 成功");
        }catch (Exception e){
            logger.info("UploadFileSyncScheduleTask 复制文件 "+ curOrder + " 失败");
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                logger.info("UploadFileSyncScheduleTask br.close() IOException");
            }
            try {
                in.close();
            } catch (IOException e) {
                logger.info("UploadFileSyncScheduleTask in.close() IOException");
            }
            process.destroy();
        }
    }

}
