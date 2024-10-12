package com.microservices.otmp.bank.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class FTPDownloader {
    private static final String FTP_SERVER = "ftp.example.com";
    private static final int FTP_PORT = 21;
    private static final String FTP_USERNAME = "username";
    private static final String FTP_PASSWORD = "password";



    private static void downloadFolder(FTPClient ftpClient, String remoteFolder, String localFolder) throws IOException {
        ftpClient.changeWorkingDirectory(remoteFolder);
        String[] subDirectories = ftpClient.listNames();

        if (subDirectories != null && subDirectories.length > 0) {
            for (String subDirectory : subDirectories) {
                String remoteFilePath = remoteFolder + "/" + subDirectory;
                String localFilePath = localFolder + "/" + subDirectory;

                if (ftpClient.listFiles(remoteFilePath).length > 0) {
                    // 如果是文件，则下载文件
                    downloadFile(ftpClient, remoteFilePath, localFilePath);
                } else {
                    // 如果是文件夹，则递归下载文件夹
                    downloadFolder(ftpClient, remoteFilePath, localFilePath);
                }
            }
        }
    }

    private static void downloadFile(FTPClient ftpClient, String remoteFilePath, String localFilePath) throws IOException {
        OutputStream outputStream = new FileOutputStream(localFilePath);
        ftpClient.retrieveFile(remoteFilePath, outputStream);
        outputStream.close();
    }

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(FTP_SERVER, FTP_PORT);
            ftpClient.login(FTP_USERNAME, FTP_PASSWORD);

            // 设置为被动模式
            ftpClient.enterLocalPassiveMode();

            // 设置文件类型为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            // 递归下载文件夹
            downloadFolder(ftpClient, "/remote/folder", "/local/folder");

            ftpClient.logout();
            ftpClient.disconnect();
            System.out.println("文件下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}