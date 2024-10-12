package com.microservices.otmp.bank.util;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.*;

/**
 * * @author lovefamily
 * *  
 * * @SFTP操作工具类
 */
@Component
@Slf4j
public class SftpUtil {

    private Session session;
    private ChannelSftp channel;

    // 使用账号密码连接SFTP
    public void connectWithPassword(String host, int port, String username, String password) throws JSchException {
        JSch jsch = new JSch();

        session = jsch.getSession(username, host, port);
        session.setPassword(password);
        session.setConfig("StrictHostKeyChecking", "no");

        session.connect();

        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
    }

    // 使用PublicKey连接SFTP
    public void connectWithPublicKey(String host, int port, String username, String password, String privateKeyPath, String passphrase) throws JSchException {
        log.info("host:{}", host);
        System.out.println("host:" + host);
        JSch jsch = new JSch();
        if (!Objects.isNull(passphrase) && !"".equals(passphrase)) {
            jsch.addIdentity(privateKeyPath, passphrase);

        } else {
            jsch.addIdentity(privateKeyPath);
        }

        session = jsch.getSession(username, host, port);
        session.setConfig("StrictHostKeyChecking", "no");

        if (!Objects.isNull(privateKeyPath) && !"".equals(privateKeyPath)) {
            session.setUserInfo(new SftpAuthKeyUserInfo(privateKeyPath));
        }
        if (!Objects.isNull(password) && !"".equals(password)) {
            session.setPassword(password);
        }
        session.connect();
        log.info("session connected success.");
        System.out.println("session connected success:" + host);

        channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        log.info("channel connected success.");
        System.out.println("channel connected success:" + host);

    }

    // 上传文件
    public void uploadFile(String localFilePath, String remoteFilePath) throws SftpException {
        channel.put(localFilePath, remoteFilePath);
    }

    // 下载文件
    public void downloadFile(String remoteFilePath, String localFilePath) throws SftpException {
        channel.get(remoteFilePath, localFilePath);
        System.out.println("downloadFile  success:" + localFilePath);

    }

    // 断开连接
    public void disconnect() {
        if (channel != null) {
            channel.disconnect();
        }
        if (session != null) {
            session.disconnect();
        }
    }

    /**
     * 批量下载文件
     *
     * @return
     */
    public List<File> batchDownLoadFileReturnFile(String remotePath, String localPath) {
        List<File> filenames = new ArrayList<File>();
        try {
            Vector<String> v = listFiles(remotePath);
            if (v.size() > 0) {
                log.info(new Date() + "共获取到文件：" + v.size());
                // 判断路径是否存在
                if (isDirExist(localPath)) {
                    mkdirs(localPath);
                }

                Iterator it = v.iterator();
                while (it.hasNext()) {
                    ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        // 三种情况
                        File file = findFile(remotePath, filename, localPath, filename);
                        if (file != null) {
                            filenames.add(file);
                        }

                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("download file is success:remotePath=" + remotePath + "and localPath="
                        + localPath
                        + ",file size is" + v.size());
            }
        } catch (SftpException e) {
            log.error("文件批量下载失败SftpException：", e);
        } finally {
            // this.disconnect();
        }
        return filenames;
    }

    public File findFile(String remotePath, String remoteFileName, String localPath,
                         String localFileName) {
        File file = null;
        InputStream inputStream = null;
        try {
            file = new File(localPath + localFileName);
            downloadFile(remotePath, remoteFileName, localPath, localFileName);
            //将文件上传到archive文件夹
            //inputStream=new FileInputStream(file);
            //channel.put(inputStream,remotePath+"Archive/"+localFileName);
            //删除原先路径下的文件
            // deleteSFTP(remotePath, localFileName);
        } catch (Exception e) {
            log.error("findFile method error :", e);
            return file;
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                log.error("inputStream close error :", e);
            }
        }
        return file;
    }

    /**
     * 下载单个文件
     *  
     *
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public File downloadFileToFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileOutputStream fieloutput = null;
        File file_out = null;
        try {
            file_out = new File(localPath + localFileName);

            fieloutput = new FileOutputStream(file_out);
            channel.get(remotePath + remoteFileName, fieloutput);
            if (log.isInfoEnabled()) {
                log.info("DownloadFile:" + remoteFileName + " success from sftp.");
            }
        } catch (FileNotFoundException e) {
            log.error("文件下载失败FileNotFoundException：", e);
        } catch (SftpException e) {
            log.error("文件下载失败SftpException：", e);
        } catch (IOException e) {
            log.error("文件下载失败IOException：", e);
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    log.error("文件下载失败IOException：", e);
                }
            }
        }
        return file_out;
    }


    /**
     * 下载单个文件
     *  
     *
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        FileOutputStream fieloutput = null;
        File file = null;
        try {
            file = new File(localPath + localFileName);

            fieloutput = new FileOutputStream(file);
            channel.get(remotePath + remoteFileName, fieloutput);
            if (log.isInfoEnabled()) {
                log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
            }
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件下载失败FileNotFoundException：", e);
        } catch (SftpException e) {
            log.error("文件下载失败SftpException：", e);
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    log.error("文件下载失败IOException：", e);
                }
            }
        }
        return false;
    }

    /**
     * 下载单个文件
     *  
     *
     * @param remoteFileName：下载文件名
     * @param localPath：本地保存目录(以路径符号结束)
     * @param localFileName：保存文件名
     * @return
     */
    public boolean downloadFile(String remotePath, String remoteFileName, String localPath, String localFileName,
                                String charset) {
        FileOutputStream fieloutput = null;
        File file = null;
        try {
            file = new File(localPath + localFileName);

            fieloutput = new FileOutputStream(file);
            channel.get(remotePath + remoteFileName, fieloutput);
            if (log.isInfoEnabled()) {
                log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
            }
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件下载失败FileNotFoundException：", e);
        } catch (SftpException e) {
            log.error("文件下载失败SftpException：", e);
        } finally {
            if (null != fieloutput) {
                try {
                    fieloutput.close();
                } catch (IOException e) {
                    log.error("文件下载失败IOException：", e);
                }
            }
        }
        return false;
    }


    /**
     * 上传单个文件
     *  
     *
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param file：本地文件
     * @return
     */

    public boolean uploadFile(String remotePath, String remoteFileName, File file) {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            in = new FileInputStream(file);
            channel.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件上传失败FileNotFoundException：{}", e);
        } catch (SftpException e) {
            log.error("文件上传失败SftpException：{}", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("文件上传失败IOException：{}", e);
                }
            }
        }
        return false;
    }

    /**
     * 上传单个文件
     *  
     *
     * @param remotePath：远程保存目录
     * @param remoteFileName：保存文件名
     * @param localPath：本地上传目录(以路径符号结束)
     * @param localFileName：上传的文件名
     * @return
     */
    public boolean uploadFile(String remotePath, String remoteFileName, String localPath, String localFileName) {
        File file = new File(localPath + localFileName);
        return uploadFile(remotePath, remoteFileName, file);
    }

    /**
     * 批量上传文件
     *  
     *
     * @param remotePath：远程保存目录
     * @param localPath：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, String localPath, boolean del) {
        try {
            File file = new File(localPath);
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getName().indexOf("bak") == -1) {
                    if (this.uploadFile(remotePath, files[i].getName(), localPath, files[i].getName()) && del) {
                        deleteFile(localPath + files[i].getName());
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("upload file is success:remotePath=" + remotePath + "and localPath=" + localPath
                        + ",file size is " + files.length);
            }
            return true;
        } catch (Exception e) {
            log.error("文件批量上传失败：", e);
        } finally {
            this.disconnect();
        }
        return false;

    }


    /**
     * 删除本地文件
     *  
     *
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        if (!file.isFile()) {
            return false;
        }
        boolean rs = file.delete();
        if (rs && log.isInfoEnabled()) {
            log.info("delete file success from local.");
        }
        return rs;
    }

    /**
     * 创建目录
     *  
     *
     * @param createpath
     * @return
     */
    public boolean createDir(String createpath) {
        try {
            if (isDirExist(createpath)) {
                channel.cd(createpath);
                return true;
            }
            String pathArry[] = createpath.split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                if (isDirExist(filePath.toString())) {
                    channel.cd(filePath.toString());
                } else {
                    // 建立目录
                    channel.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    channel.cd(filePath.toString());
                }

            }
            channel.cd(createpath);
            return true;
        } catch (SftpException e) {
            log.error("创建目录失败SftpException：", e);
        }
        return false;
    }

    /**
     * 判断目录是否存在
     *  
     *
     * @param directory
     * @return
     */
    public boolean isDirExist(String directory) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channel.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            if (StringUtils.equalsIgnoreCase(e.getMessage(), "no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

    /**
     * 删除stfp文件
     *  
     *
     * @param directory：要删除文件所在目录
     * @param deleteFile：要删除的文件
     */
    public void deleteSFTP(String directory, String deleteFile) {
        try {
            channel.cd(directory);
            channel.rm(directory + deleteFile);
            if (log.isInfoEnabled()) {
                log.info("delete file success from sftp.");
            }
        } catch (Exception e) {
            log.error("删除SFTP失败：", e);
        }
    }

    /**
     * 如果目录不存在就创建目录
     *  
     *
     * @param path
     */
    public void mkdirs(String path) {
        File f = new File(path);

        String fs = f.getParent();

        f = new File(fs);

        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 列出目录下的文件
     *  
     *
     * @param directory：要列出的目录
     * @return
     * @throws SftpException
     */
    public Vector listFiles(String directory) throws SftpException {
        return channel.ls(directory);
    }

    public static void main(String[] args) {
        SftpUtil sftpUtil = new SftpUtil();

        try {
            // 使用账号密码连接SFTP
            // sftpUtil.connectWithPassword("203.116.104.211", 2222, "HKLNV", "Q8Secure$6@");
            // 使用PublicKey连接SFTP
            sftpUtil.connectWithPublicKey("203.116.104.211", 2222, "HKLNV", "Q8Secure$6@", "C:\\Users\\T14\\Desktop\\otfp\\.ssh\\id_rsa", "Q8Secure$6@");

            // 上传文件

            sftpUtil.uploadFile("/PAYABLE_INVOICE", "PFLE002.ISCFH.HKLNVHK.D20230912003.csv.pgp", "D:\\test\\encipher\\", "PFLE002.ISCFH.HKLNVHK.D20230912003.csv.pgp");
            // 下载文件
            //  sftpUtil.downloadFile("remoteFilePath", "localFilePath");
            // sftpUtil.deleteSFTP("PAYABLE_INVOICE/", "PFLE002.ISCFH.HKLNVHK.D20230912003.csv.pgp");

            Vector v = sftpUtil.listFiles("/PAYABLE_INVOICE");
            log.info(v.toString());
            // 断开连接
            sftpUtil.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * UserInfo是jsch包的一个接口
     */
    static class SftpAuthKeyUserInfo implements UserInfo {
        /**
         * ssh private key passphrase
         */
        private String passphrase;

        /**
         * 构造函数需要传秘钥的路径，是秘钥的文件路径，不是流也不是秘钥内容
         */
        public SftpAuthKeyUserInfo(String passphrase) {
            this.passphrase = passphrase;
        }

        @Override
        public String getPassphrase() {
            return passphrase;
        }

        @Override
        public String getPassword() {
            return null;
        }

        @Override
        public boolean promptPassword(String message) {
            return false;
        }

        @Override
        public boolean promptPassphrase(String message) {
            return true;
        }

        @Override
        public boolean promptYesNo(String message) {
            return true;
        }

        @Override
        public void showMessage(String message) {
            log.info("SSH Message:{}", message);
        }
    }

}