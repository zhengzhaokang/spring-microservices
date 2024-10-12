package com.microservices.otmp.bank.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FtpUtil {
    private static final Logger logger = LoggerFactory.getLogger(FtpUtil.class);
    // ftp服务器ip地址
    private String FTP_ADDRESS;
    // 端口号
    private int FTP_PORT;
    // 用户名
    private String FTP_USERNAME;
    // 密码
    private String FTP_PASSWORD;

    private String FTP_BASEPATH;

    public static final String FILE_PART_SUFFIX = ".filepart";

    private boolean uploadFile(String originFileName, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        ftp.setControlEncoding("UTF-8");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            logger.info("FTP Connection IP :" + FTP_ADDRESS + " connection is" + String.valueOf(ftp.isConnected()));
            if (!ftp.isConnected()) {
                return false;
            }
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                logger.info("FTP reply is : " + reply);
                ftp.disconnect();
                return success;
            }
            ftp.setControlEncoding("UTF-8");
            logger.info("FTP Connection IP :" + FTP_ADDRESS + " available is" + String.valueOf(ftp.isAvailable()));
            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.info("changeWorkingDirectory to FTP_BASEPATH failed");
                input.close();
                ftp.logout();
                return success;
            }
            ftp.enterLocalPassiveMode();
            success = ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            if (!success) {
                logger.info("setFileType failed");
                input.close();
                ftp.logout();
                return success;
            }
            success = ftp.storeFile(originFileName, input);
            if (!success) {
                logger.info("file upload error");
                input.close();
                ftp.logout();
                return success;
            }
            input.close();
            ftp.logout();
            success = true;
        } catch (Exception e) {
            logger.info(e.toString());
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    logger.info(ioe.toString());
                }
            }
        }
        return success;
    }


    public Boolean uploadFile(String path, InputStream inputStream, String ftpAddress, int ftpPort, String ftpName,
                              String ftpPassWord, String ftpBasePath) {
        Boolean upload = false;
        FTP_ADDRESS = ftpAddress;
        FTP_PORT = ftpPort;
        FTP_USERNAME = ftpName;
        FTP_PASSWORD = ftpPassWord;
        FTP_BASEPATH = ftpBasePath;
        upload = uploadFile(path, inputStream);
        return upload;
    }

    public boolean downloadFile(List fileName, Map<String, String> ftpInfo, HttpServletResponse response) {
        FTP_ADDRESS = ftpInfo.get("FTP_ADDRESS");
        FTP_PORT = Integer.parseInt(ftpInfo.get("FTP_PORT"));
        FTP_USERNAME = ftpInfo.get("FTP_USERNAME");
        FTP_PASSWORD = ftpInfo.get("FTP_PASSWORD");
        FTP_BASEPATH = ftpInfo.get("FTP_BASEPATH");
        return (downloadFile(fileName, FTP_BASEPATH, response));
    }

    public Boolean downloadFile(String ftpAddress, int ftpPort, String ftpName, String ftpPassWord, String ftpBasePath,
                                String localpath, String fileName) {
        FTP_ADDRESS = ftpAddress;
        FTP_PORT = ftpPort;
        FTP_USERNAME = ftpName;
        FTP_PASSWORD = ftpPassWord;
        FTP_BASEPATH = ftpBasePath;
        logger.info("FTP:" + ftpBasePath);
        return downloadFile(fileName, localpath);
    }

    public boolean downloadFile(List fileList, String pathname, HttpServletResponse response) {
        CompressedFileUtil compressedFileUtil = new CompressedFileUtil();
        boolean flag = false;
        boolean success = false;
        OutputStream os = null;
        FTPClient ftp = new FTPClient();

        File zipDir = new File("zip");
        String UUid = UUID.randomUUID().toString().replaceAll("-", "");
        response.setContentType("application/octet-stream");
        //response.setCharacterEncoding("UTF-8");
        response.addHeader("content-disposition", "attachment;filename=\"" + UUid + ".zip\"");
        File archiveDir = new File(UUid);
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();


            if (!archiveDir.exists()) {
                FileUtils.forceMkdir(archiveDir);
            }
            if (!zipDir.exists()) {
                FileUtils.forceMkdir(zipDir);
            }
            for (int i = 0; i < fileList.size(); i++) {
                for (FTPFile file : ftpFiles) {
                    if (file.getName().contains(fileList.get(i).toString())) {
                        //fileList.get(i).toString().subSequence(0, 12)
                        File localFile = new File(UUid + "/" + fileList.get(i).toString());
                        os = new FileOutputStream(localFile);
                        ftp.retrieveFile(file.getName(), os);
                        os.close();
                    }
                }
            }
            compressedFileUtil.compressedFile(UUid, "zip");
            delFolder(archiveDir.getPath());
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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

    public boolean downloadFile(String fileName, String localPath) {
        boolean flag = false;
        boolean success = false;
        OutputStream os = null;
        FTPClient ftp = new FTPClient();
        //File zipDir = new File(localPath);
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();

            checkDirectory(localPath);
            for (FTPFile file : ftpFiles) {
                if (file.getName().contains(fileName)) {
                    File localFile = new File(localPath + "\\" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftp.retrieveFile(file.getName(), os);
                    os.flush();
                    os.close();
                }

            }
            ftp.logout();
            flag = true;
            logger.info("Download success, file name : " + fileName);
        } catch (Exception e) {
            logger.error("Download fail, file name: " + fileName);
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }


    public boolean deleteFile(String path, String fileName) {
        boolean flag = false;
        boolean success = false;
        FTPClient ftp = new FTPClient();
        FTP_BASEPATH = path;
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return false;
            }
            flag = ftp.deleteFile(fileName);
            ftp.logout();
            if (flag) {
                logger.info("Delete success, file name : {}", fileName);
            } else {
                logger.info("Delete fail, file name: {}", fileName);
            }
        } catch (Exception e) {
            logger.error("Delete error, file name:{},{} ", fileName, e.getMessage(), e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    logger.error("Close ftp fail, file name. ");
                }
            }
        }
        return flag;
    }

    public boolean checkFile(String path, String fileName) {
        boolean flag = false;
        boolean success = false;
        FTPClient ftp = new FTPClient();
        FTP_BASEPATH = path;
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile file : ftpFiles) {
                if (StringUtils.equals(file.getName(), fileName)) {
                    flag = true;
                    break;
                }
            }
            ftp.logout();
        } catch (Exception e) {
            logger.error("Delete error, file name:{},{} ", fileName, e.getMessage(), e);
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    logger.error("Close ftp fail, file name. ");
                }
            }
        }
        return flag;
    }

    public byte[] downloadFileByteData(String ftpAddress, int ftpPort, String ftpName, String ftpPassWord, String ftpBasePath, String filename) {
        FTP_ADDRESS = ftpAddress;
        FTP_PORT = ftpPort;
        FTP_USERNAME = ftpName;
        FTP_PASSWORD = ftpPassWord;
        FTP_BASEPATH = ftpBasePath;
        boolean success = false;
        byte[] resultData = null;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return resultData;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return resultData;
            }
            InputStream inStream = ftp.retrieveFileStream(filename);
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();
                resultData = outStream.toByteArray();
            }
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultData;
    }

    private void checkDirectory(String directoryPath) throws IOException {
        File dir = new File(directoryPath);

        if (!dir.exists()) {
            FileUtils.forceMkdir(dir);
        }
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

    public boolean downloadFile(String fileName, Map<String, String> ftpInfo, HttpServletResponse response) {
        FTP_ADDRESS = ftpInfo.get("FTP_ADDRESS");
        FTP_PORT = Integer.parseInt(ftpInfo.get("FTP_PORT"));
        FTP_USERNAME = ftpInfo.get("FTP_USERNAME");
        FTP_PASSWORD = ftpInfo.get("FTP_PASSWORD");
        FTP_BASEPATH = ftpInfo.get("FTP_BASEPATH");
        CompressedFileUtil compressedFileUtil = new CompressedFileUtil();
        boolean flag = false;
        boolean success = false;
        OutputStream os = null;
        FTPClient ftp = new FTPClient();

        File zipDir = new File("download");

        response.setContentType("application/octet-stream");
//		response.setCharacterEncoding("UTF-8");
        response.addHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            //ftp.enterRemotePassiveMode();
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH failed");
                ftp.logout();
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();
            if (!zipDir.exists()) {
                FileUtils.forceMkdir(zipDir);
            }

            for (FTPFile file : ftpFiles) {
                if (file.getName().contains(fileName)) {
                    File localFile = new File(fileName);
                    //os = new FileOutputStream(localFile);
                    OutputStream os1 = response.getOutputStream();
                    ftp.retrieveFile(file.getName(), os1);
                    os1.flush();
                    os1.close();
                }
            }
            ftp.logout();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//		try {
//			InputStream input = new FileInputStream(fileName);
//
//			byte[] buffer = new byte[1024];
//			int len = -1;
//
//			OutputStream output = response.getOutputStream();
//			{
//				while((len = input.read(buffer)) != -1) {
//					output.write(buffer, 0, len);
//				}
//			}
//			output.close();
//			input.close();
//			delFolder(zipDir.getPath());
//		} catch (Exception e) {
//			delFolder(zipDir.getPath());
//			e.printStackTrace();
//			return false;
//		}

        return true;
    }

    /**
     * 下载远程文件夹下的所有文件
     *
     * @param remoteFilePath
     * @param localDirPath
     * @throws Exception
     */
    public boolean getFileDir(String localDirPath, String ftpAddress, int ftpPort, String ftpName,
                              String ftpPassWord, String ftpBasePath) throws Exception {
        File localDirFile = new File(localDirPath);
        // 判断本地目录是否存在，不存在需要新建各级目录
        if (!localDirFile.exists()) {
            localDirFile.mkdirs();
        }
        if (logger.isInfoEnabled()) {
            logger.info("ftp文件服务器文件夹[{}],下载到本地目录[{}]", new Object[]{ftpBasePath, localDirFile});
        }
        FTP_ADDRESS = ftpAddress;
        FTP_PORT = ftpPort;
        FTP_USERNAME = ftpName;
        FTP_PASSWORD = ftpPassWord;
        FTP_BASEPATH = ftpBasePath;
        boolean flag = false;
        boolean success = false;
        OutputStream os = null;
        FTPClient ftp = new FTPClient();
        String fileName = null;
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);// 连接FTP服务器
            ftp.login(FTP_USERNAME, FTP_PASSWORD);// 登录
            ftp.enterLocalPassiveMode();//设置被动模式
            ftp.setFileTransferMode(FTP.BINARY_FILE_TYPE);
            ftp.setBufferSize(1024);
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setControlEncoding("UTF-8");
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }

            success = ftp.changeWorkingDirectory(FTP_BASEPATH);
            if (!success) {
                logger.error("changeWorkingDirectory to FTP_BASEPATH:{} failed", FTP_BASEPATH);
                ftp.logout();
                return false;
            }
            FTPFile[] ftpFiles = ftp.listFiles();
            for (FTPFile file : ftpFiles) {
                if (!file.isFile() || file.getName().endsWith(FILE_PART_SUFFIX)) {
                    continue;
                }
                fileName = file.getName();
                File localFile = new File(localDirPath + "/" + file.getName());
                os = new FileOutputStream(localFile);
                ftp.retrieveFile(file.getName(), os);
                os.flush();
                os.close();
            }
            ftp.logout();
            flag = true;
            logger.info("Download success, file name : " + fileName);
        } catch (Exception e) {
            logger.error("Download error, file name: " + fileName);
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

}