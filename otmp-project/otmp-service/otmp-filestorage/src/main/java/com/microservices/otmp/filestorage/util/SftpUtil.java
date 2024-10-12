package com.microservices.otmp.filestorage.util;

import com.jcraft.jsch.*;
import com.microservices.otmp.filestorage.config.SftpConfig;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Component
public class SftpUtil {
    private long count;
    /**
     * 已经连接次数
     */
    private long count1 = 0;

    private long sleepTime;

    private static final Logger logger = LoggerFactory.getLogger(SftpUtil.class);

    /**
     * 连接sftp服务器
     *
     * @return
     */
    public ChannelSftp connect(SftpConfig sftpConfig) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHostname(), sftpConfig.getPort());
            Session sshSession = jsch.getSession(sftpConfig.getUsername(), sftpConfig.getHostname(), sftpConfig.getPort());
            logger.info("Session created ... UserName=" + sftpConfig.getUsername() + ";host=" + sftpConfig.getHostname() + ";port=" + sftpConfig.getPort());
            sshSession.setPassword(sftpConfig.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            logger.info("Session connected ...");
            logger.info("Opening Channel ...");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            logger.info("登录成功");
        } catch (Exception e) {
            try {
                count1 += 1;
                if (count == count1) {
                    throw new RuntimeException(e);
                }
                Thread.sleep(sleepTime);
                logger.info("重新连接....");
                connect(sftpConfig);
            } catch (InterruptedException e1) {
                throw new RuntimeException(e1);
            }
        }
        return sftp;
    }

    /**
     * 上传文件
     *
     * @param directory  上传的目录
     * @param input      要上传的文件流
     * @param sftpConfig
     */
    public boolean upload(String directory, InputStream input, SftpConfig sftpConfig, String fileName) {
        ChannelSftp sftp = connect(sftpConfig);
        logger.info("SFTP path is : " + directory);
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            try {
                sftp.mkdir(directory);
                sftp.cd(directory);
            } catch (SftpException e1) {
                throw new RuntimeException("ftp创建文件路径失败" + directory);
            }
        }
        logger.info("sftp config is :" + sftpConfig.toString());
        try {
            sftp.put(input, fileName);
        } catch (Exception e) {
            throw new RuntimeException("sftp异常" + e);
        } finally {
            disConnect(sftp);
            closeStream(input, null);
        }
        return true;
    }

    public boolean download(SftpConfig sftpConfig, String localPath, String fileName) {
        boolean flag = false;
        ChannelSftp sftp = connect(sftpConfig);
        logger.info("sftp config is :" + sftpConfig.toString());
        OutputStream os = null;
        List<String> ftpFiles = null;
        try {
            sftp.cd(sftpConfig.getRemoteRootPath());
            if (logger.isInfoEnabled()) {
                logger.info("打开远程文件夹: " + sftpConfig.getRemoteRootPath());
            }
            ftpFiles = listFiles(sftpConfig.getRemoteRootPath(), sftpConfig);
            logger.info("ftpFile List is : " + ftpFiles.toString());
            checkDirectory(localPath);
            for (String file : ftpFiles) {
                if (file.contains(fileName)) {
                    File localFile = new File(localPath + "\\" + file);
                    os = new FileOutputStream(localFile);
                    sftp.get(file, os);
                    os.close();
                }
            }
            flag = true;
            disConnect(sftp);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public boolean containsNullFiles(List fileList, SftpConfig sftpConfig){
        ChannelSftp sftpChannel = connect(sftpConfig);
        List<String> ftpFiles =null;
        try {
            sftpChannel.cd(sftpConfig.getRemoteRootPath());
            if (logger.isInfoEnabled()) {
                logger.info("打开远程文件夹: " + sftpConfig.getRemoteRootPath());
            }
            ftpFiles = listFiles(sftpConfig.getRemoteRootPath(), sftpConfig);
            logger.info("ftpFile List is : " + ftpFiles.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        int count = 0;
        for(int i = 0 ;i < fileList.size();i++){
            for(String file: ftpFiles){
                if(file.contains(fileList.get(i).toString())) {
                    count++;
                }
                try{
                    InputStream in = sftpChannel.get( sftpConfig.getRemoteRootPath()+fileList.get(i).toString() );
                    byte[] bytes = IOUtils.toByteArray(in);
                    if(bytes.length<10){
                        return true;
                    }
                }catch (Exception e){
                    logger.info(e.getStackTrace().toString());
                }
            }
        }
        if(count==0) {
        	return true;
        }
        return false;
    }

    public boolean download(List fileList, SftpConfig sftpConfig, HttpServletResponse response) {
        ChannelSftp sftp = connect(sftpConfig);

        logger.info("fileList are : " + fileList.toString());
        logger.info("sftp config is :" + sftpConfig.toString());
        CompressedFileUtil compressedFileUtil = new CompressedFileUtil();
        OutputStream os = null;
        File zipDir = new File("zip");
        String UUid = UUID.randomUUID().toString().replaceAll("-", "");

        response.setContentType("application/octet-stream");
        response.addHeader("content-disposition", "attachment;filename=\"" + UUid + ".zip\"");
        File archiveDir = new File(UUid);
        List<String> ftpFiles = null;
        try {
            sftp.cd(sftpConfig.getRemoteRootPath());
            if (logger.isInfoEnabled()) {
                logger.info("打开远程文件夹: " + sftpConfig.getRemoteRootPath());
            }
            ftpFiles = listFiles(sftpConfig.getRemoteRootPath(), sftpConfig);
            logger.info("ftpFile List is : " + ftpFiles.toString());
            if (!archiveDir.exists()) {
                FileUtils.forceMkdir(archiveDir);
            }
            if (!zipDir.exists()) {
                FileUtils.forceMkdir(zipDir);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < fileList.size(); i++) {
                for (String file : ftpFiles) {
                    if (file.contains(fileList.get(i).toString())) {
                        //fileList.get(i).toString().subSequence(0, 12)
                        File localFile = new File(UUid + "/" + fileList.get(i).toString());
                        os = new FileOutputStream(localFile);
                        sftp.get(file, os);
                        os.close();
                    }
                }
            }
            compressedFileUtil.compressedFile(UUid, "zip");
            delFolder(archiveDir.getPath());
            disConnect(sftp);
        } catch (Exception e) {
            if (logger.isInfoEnabled()) {
                logger.info("文件下载出现异常，[{}]", e);
            }
            throw new RuntimeException("文件下载出现异常，[{}]", e);
        } finally {
            closeStream(null, os);
        }

        File temp = new File("zip/" + UUid + ".zip");
        try {
            InputStream input = new FileInputStream(temp);

            byte[] buffer = new byte[1024];
            int len = -1;

            OutputStream output = response.getOutputStream();
            {
                while ((len = input.read(buffer)) != -1) {
                    output.write(buffer, 0, len);
                }
            }
            output.close();
            input.close();
            delFolder(zipDir.getPath());
        } catch (Exception e) {
            delFolder(zipDir.getPath());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 下载远程文件夹下的所有文件
     *
     * @param remoteFilePath
     * @param localDirPath
     * @throws Exception
     */
    public void getFileDir(String remoteFilePath, String localDirPath, SftpConfig sftpConfig) throws Exception {
        File localDirFile = new File(localDirPath);
        // 判断本地目录是否存在，不存在需要新建各级目录
        if (!localDirFile.exists()) {
            localDirFile.mkdirs();
        }
        if (logger.isInfoEnabled()) {
            logger.info("sftp文件服务器文件夹[{}],下载到本地目录[{}]", new Object[]{remoteFilePath, localDirFile});
        }
        ChannelSftp channelSftp = connect(sftpConfig);
        Vector<ChannelSftp.LsEntry> lsEntries = channelSftp.ls(remoteFilePath);
        if (logger.isInfoEnabled()) {
            logger.info("远程目录下的文件为[{}]", lsEntries);
        }
        for (ChannelSftp.LsEntry entry : lsEntries) {
            String fileName = entry.getFilename();
            if (checkFileName(fileName)) {
                continue;
            }
            String remoteFileName = getRemoteFilePath(remoteFilePath, fileName);
            channelSftp.get(remoteFileName, localDirPath);
        }
        disConnect(channelSftp);
    }

    /**
     * 关闭流
     *
     * @param outputStream
     */
    public void closeStream(InputStream inputStream, OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkFileName(String fileName) {
        if (".".equals(fileName) || "..".equals(fileName)) {
            return true;
        }
        return false;
    }

    public String getRemoteFilePath(String remoteFilePath, String fileName) {
        if (remoteFilePath.endsWith("/")) {
            return remoteFilePath.concat(fileName);
        } else {
            return remoteFilePath.concat("/").concat(fileName);
        }
    }

    /**
     * 删除文件
     *
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 列出目录下的文件
     *
     * @param directory  要列出的目录
     * @param sftpConfig
     * @return
     * @throws SftpException
     */
    public List<String> listFiles(String directory, SftpConfig sftpConfig) throws SftpException {
        ChannelSftp sftp = connect(sftpConfig);
        List fileNameList = new ArrayList();
        try {
            sftp.cd(directory);
        } catch (SftpException e) {
            return fileNameList;
        }
        Vector vector = sftp.ls(directory);
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) instanceof ChannelSftp.LsEntry) {
                ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) vector.get(i);
                String fileName = lsEntry.getFilename();
                if (".".equals(fileName) || "..".equals(fileName)) {
                    continue;
                }
                fileNameList.add(fileName);
            }
        }
        disConnect(sftp);
        return fileNameList;
    }

    /**
     * 断掉连接
     */
    public void disConnect(ChannelSftp sftp) {
        try {
            sftp.disconnect();
            sftp.getSession().disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SftpUtil(long count, long sleepTime) {
        this.count = count;
        this.sleepTime = sleepTime;
    }

    public SftpUtil() {

    }

    public void delFolder(String folderPath) {
        try {
            delAllFile(folderPath); //删除完里面所有内容
            String filePath = folderPath;
            filePath = filePath.toString();
            File myFilePath = new File(filePath);
            myFilePath.delete(); //删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean delAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return flag;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
                delFolder(path + "/" + tempList[i]);//再删除空文件夹
                flag = true;
            }
        }
        return flag;
    }

    private void checkDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);

        if (!dir.exists()) {
            FileUtils.forceMkdir(dir);
        }
    }
}