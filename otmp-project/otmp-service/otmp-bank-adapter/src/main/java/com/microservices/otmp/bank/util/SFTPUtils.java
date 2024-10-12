package com.microservices.otmp.bank.util;

import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author lovefamily
 *  
 * @SFTP操作工具类
 */

@Component
public class SFTPUtils {

    public static Log log = LogFactory.getLog(SFTPUtils.class);

    private String host;// 服务器连接ip
    private String username;// 用户名
    private String password;// 密码
    private int port = 22;// 端口号
    private ChannelSftp sftp = null;
    private Session sshSession = null;
    private String keyFilePath;

    public SFTPUtils() {
    }

    public SFTPUtils(String host, int port, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public SFTPUtils(String host, String username, String password) {
        this.host = host;
        this.username = username;
        this.password = password;
    }

    public SFTPUtils(String host, int port, String username, String password, String keyFilePath) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.keyFilePath = keyFilePath;
    }

    /**
     * 通过SFTP连接服务器
     */
    public void connect(String username, String password, String host, Integer port) {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            if (log.isInfoEnabled()) {
                log.info("Session created.");
            }
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            if (log.isInfoEnabled()) {
                log.info("Session connected.");
            }
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            if (log.isInfoEnabled()) {
                log.info("Opening Channel.");
            }
            sftp = (ChannelSftp) channel;
            if (log.isInfoEnabled()) {
                log.info("Connected to " + host + ".");
            }
        } catch (Exception e) {
            log.error("SFTP连接服务器失败：", e);
        }
    }

    /**
     * 通过SFTP连接服务器-增加指定编码格式
     */
    public void connectCharset(String charset) {
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            if (log.isInfoEnabled()) {
                log.info("Session created.");
            }
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            if (log.isInfoEnabled()) {
                log.info("Session connected.");
            }

            sftp = (ChannelSftp) sshSession.openChannel("sftp");
            sftp.connect();

            // 使用反射修改server_version的默认值,用于指定编码格式
            Class cla = sftp.getClass();
            Field f1 = cla.getDeclaredField("server_version");
            f1.setAccessible(true);
            f1.set(sftp, 2);
            sftp.setFilenameEncoding(charset);

        } catch (Exception e) {
            log.error("SFTP连接服务器失败：", e);
        }
    }

    /**
     * 通过SFTP连接服务器,使用秘钥连接
     */
    public void connectKey() {
        try {
            JSch jsch = new JSch();
            // 判断秘钥是否有秘钥
            if (keyFilePath != null) {
                // 判断是否有秘钥密码
                if (password != null && !"".equals(password)) {
                    jsch.addIdentity(keyFilePath, password);
                } else {
                    jsch.addIdentity(keyFilePath);
                }
            }

            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            if (log.isInfoEnabled()) {
                log.info("Session created.");
            }
            // 判断是否有密码
            if (password != null) {
                sshSession.setPassword(password);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            if (log.isInfoEnabled()) {
                log.info("Session connected.");
            }
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            if (log.isInfoEnabled()) {
                log.info("Opening Channel.");
            }
            sftp = (ChannelSftp) channel;
            if (log.isInfoEnabled()) {
                log.info("Connected to " + host + ".");
            }
        } catch (Exception e) {
            log.error("SFTP连接服务器失败：", e);
        }
    }

    /**
     * 通过SFTP连接服务器-使用秘钥连接，增加指定编码格式
     */
    public void connectKeyCharset(String charset) {
        try {
            JSch jsch = new JSch();
            // 判断秘钥是否有秘钥
            if (keyFilePath != null) {
                // 处理不同系统所用的文件
                String systemKeyFilePath = VoaPathUtil.getKeyFilePath(keyFilePath);
                // 判断是否有秘钥密码
                if (password != null && !"".equals(password)) {
                    jsch.addIdentity(systemKeyFilePath, password);
                } else {
                    jsch.addIdentity(systemKeyFilePath);
                }
            }

            jsch.getSession(username, host, port);
            sshSession = jsch.getSession(username, host, port);
            if (log.isInfoEnabled()) {
                log.info("Session created.");
            }
            // 判断是否有密码
            if (password != null) {
                sshSession.setPassword(password);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            if (log.isInfoEnabled()) {
                log.info("Session connected.");
            }

            sftp = (ChannelSftp) sshSession.openChannel("sftp");
            sftp.connect();

            // 使用反射修改server_version的默认值,用于指定编码格式
            Class cla = sftp.getClass();
            Field f1 = cla.getDeclaredField("server_version");
            f1.setAccessible(true);
            f1.set(sftp, 2);
            sftp.setFilenameEncoding(charset);

        } catch (Exception e) {
            log.error("SFTP连接服务器失败：", e);
        }
    }

    /**
     * 关闭连接
     */
    public void disconnect() {
        if (this.sftp != null) {
            if (this.sftp.isConnected()) {
                this.sftp.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("sftp is closed already");
                }
            }
        }
        if (this.sshSession != null) {
            if (this.sshSession.isConnected()) {
                this.sshSession.disconnect();
                if (log.isInfoEnabled()) {
                    log.info("sshSession is closed already");
                }
            }
        }
        log.info("关闭SFTP连接");

    }

    /**
     * 批量下载文件
     *  
     *
     * @param remotePath：远程下载目录(以路径符号结束,可以为相对路径eg:/Outbox/sftp/)
     * @param localPath：本地保存目录(以路径符号结束,D:\lovafamily\sftp\)
     * @param charset
     * @return
     */
    public List<File> batchDownLoadFileToList(String remotePath, String localPath, String charset) {
        List<File> fileList = new ArrayList<File>();
        try {
            Vector<String> v = listFiles(remotePath);
            if (v.size() > 0) {
                log.info(new Date() + "共获取到文件：" + v.size() + ",远程路径：" + remotePath);
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        // 三种情况
                        File file = downloadFileToFile(remotePath, filename, localPath, filename);
                        fileList.add(file);
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("download file is success:remotePath=" + remotePath + "and localPath=" + localPath
                        + ",file size is" + v.size());
            }
        } catch (SftpException e) {
            log.error("文件批量下载失败SftpException：", e);
        }
        return fileList;
    }

    /**
     * 批量上传文件
     *
     * @param remotePath：远程保存目录
     * @param file：本地上传目录(以路径符号结束)
     * @param del：上传后是否删除本地文件
     * @return
     */
    public boolean bacthUploadFile(String remotePath, File file, boolean del) {
        try {
            String localPath = file.getPath();
            if (this.uploadFile(remotePath, file.getName(), file) && del) {
                deleteFile(localPath + file.getName());
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
                    LsEntry entry = (LsEntry) it.next();
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
            inputStream = new FileInputStream(file);
            sftp.put(inputStream, remotePath + "Archive/" + localFileName);
            //删除原先路径下的文件
            deleteSFTP(remotePath, localFileName);
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
     * 批量下载文件
     *  
     *
     * @param localPath：本地保存目录(以路径符号结束,D:\lovafamily\sftp\)
     * @return
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath) {
        List<String> filenames = new ArrayList<String>();
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
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        // 三种情况
                        flag = downloadFile(remotePath, filename, localPath, filename);
                        if (flag) {
                            filenames.add(localFileName);
                        }

                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("download file is success:remotePath=" + remotePath + "and localPath=" + localPath
                        + ",file size is" + v.size());
            }
        } catch (SftpException e) {
            log.error("文件批量下载失败SftpException：", e);
        } finally {
            // this.disconnect();
        }
        return filenames;
    }

    /**
     * 批量下载文件
     *  
     *
     * @param localPath：本地保存目录(以路径符号结束,D:\lovafamily\sftp\)
     * @param fileFormat：下载文件格式(以特定字符开头,为空不做检验)
     * @param fileEndFormat：下载文件格式(文件格式)
     * @param del：下载后是否删除sftp文件
     * @return
     */
    public List<String> batchDownLoadFile(String remotePath, String localPath, String fileFormat, String fileEndFormat,
                                          boolean del) {
        List<String> filenames = new ArrayList<String>();
        try {
            // connect();
            Vector<String> v = listFiles(remotePath);
            // sftp.cd(remotePath);
            if (v.size() > 0) {
                System.out.println("本次处理文件个数不为零,开始下载...fileSize=" + v.size());
                Iterator it = v.iterator();
                while (it.hasNext()) {
                    LsEntry entry = (LsEntry) it.next();
                    String filename = entry.getFilename();
                    SftpATTRS attrs = entry.getAttrs();
                    if (!attrs.isDir()) {
                        boolean flag = false;
                        String localFileName = localPath + filename;
                        fileFormat = fileFormat == null ? "" : fileFormat.trim();
                        fileEndFormat = fileEndFormat == null ? "" : fileEndFormat.trim();
                        // 三种情况
                        if (fileFormat.length() > 0 && fileEndFormat.length() > 0) {
                            if (filename.startsWith(fileFormat) && filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileFormat.length() > 0 && "".equals(fileEndFormat)) {
                            if (filename.startsWith(fileFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else if (fileEndFormat.length() > 0 && "".equals(fileFormat)) {
                            if (filename.endsWith(fileEndFormat)) {
                                flag = downloadFile(remotePath, filename, localPath, filename);
                                if (flag) {
                                    filenames.add(localFileName);
                                    if (flag && del) {
                                        deleteSFTP(remotePath, filename);
                                    }
                                }
                            }
                        } else {
                            flag = downloadFile(remotePath, filename, localPath, filename);
                            if (flag) {
                                filenames.add(localFileName);
                                if (flag && del) {
                                    deleteSFTP(remotePath, filename);
                                }
                            }
                        }
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("download file is success:remotePath=" + remotePath + "and localPath=" + localPath
                        + ",file size is" + v.size());
            }
        } catch (SftpException e) {
            log.error("文件批量下载失败SftpException：", e);
        } finally {
            // this.disconnect();
        }
        return filenames;
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
            sftp.get(remotePath + remoteFileName, fieloutput);
            if (log.isInfoEnabled()) {
                log.info("===DownloadFile:" + remoteFileName + " success from sftp.");
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
            sftp.get(remotePath + remoteFileName, fieloutput);
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
            sftp.get(remotePath + remoteFileName, fieloutput);
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

    public boolean uploadFile(String remotePath, String remoteFileName, File file) {
        FileInputStream in = null;
        try {
            createDir(remotePath);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件上传失败FileNotFoundException：", e);
        } catch (SftpException e) {
            log.error("文件上传失败SftpException：", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("文件上传失败IOException：", e);
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
        FileInputStream in = null;
        try {
            createDir(remotePath);
            File file = new File(localPath + localFileName);
            in = new FileInputStream(file);
            sftp.put(in, remoteFileName);
            return true;
        } catch (FileNotFoundException e) {
            log.error("文件上传失败FileNotFoundException：", e);
        } catch (SftpException e) {
            log.error("文件上传失败SftpException：", e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("文件上传失败IOException：", e);
                }
            }
        }
        return false;
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
                this.sftp.cd(createpath);
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
                    sftp.cd(filePath.toString());
                } else {
                    // 建立目录
                    sftp.mkdir(filePath.toString());
                    // 进入并设置为当前目录
                    sftp.cd(filePath.toString());
                }

            }
            this.sftp.cd(createpath);
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
            SftpATTRS sftpATTRS = sftp.lstat(directory);
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
            // sftp.cd(directory);
            sftp.rm(directory + deleteFile);
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
        return sftp.ls(directory);
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public ChannelSftp getSftp() {
        return sftp;
    }

    public void setSftp(ChannelSftp sftp) {
        this.sftp = sftp;
    }

    public String getKeyFilePath() {
        return keyFilePath;
    }

    public void setKeyFilePath(String keyFilePath) {
        this.keyFilePath = keyFilePath;
    }
}