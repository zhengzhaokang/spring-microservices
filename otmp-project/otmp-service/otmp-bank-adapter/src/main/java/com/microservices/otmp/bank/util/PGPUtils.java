package com.microservices.otmp.bank.util;

import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

import static org.slf4j.LoggerFactory.getLogger;


public class PGPUtils {
	private static Logger logger = getLogger(PGPUtils.class);


//	public static void genKeyPair() throws Exception {
//
//		RSAKeyPairGenerator rkpg = new RSAKeyPairGenerator();
//
//		Security.addProvider(new BouncyCastleProvider());
//
//		KeyPairGenerator    kpg = KeyPairGenerator.getInstance("RSA", "BC");
//
//		kpg.initialize(1024);
//
//		KeyPair kp = kpg.generateKeyPair();
//		FileOutputStream    out1 = new FileOutputStream("/Users/blueearth/Downloads/sftp/priKey.asc");
//		FileOutputStream    out2 = new FileOutputStream("/Users/blueearth/Downloads/sftp/pubKey.asc");
//		rkpg.exportKeyPair(out1, out2, kp.getPublic(), kp.getPrivate(), null, "".toCharArray(), false);
//	}

	public static void encrypt(String plainTextFile, String pubKeyFile, String decPlainTextFile) throws Exception{
		FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
		FileOutputStream cipheredFileIs = new FileOutputStream(decPlainTextFile);
		PgpHelper.getInstance().encryptFile(cipheredFileIs, plainTextFile, PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
		cipheredFileIs.close();
		pubKeyIs.close();
	}
	public static void encrypt(InputStream in,String fileName, long length, Date modificationTime, String pubKeyFile, String decPlainTextFile) throws Exception{
		FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
		FileOutputStream cipheredFileIs = new FileOutputStream(decPlainTextFile);
		PgpHelper.getInstance().encryptFile(cipheredFileIs, in, fileName, length, modificationTime,
				PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
		cipheredFileIs.close();
		pubKeyIs.close();
	}

	public static void decrypt(File cipheredFile, String privKeyFile, String decPlainTextFile, String passwd) throws Exception{
    	FileInputStream cipheredFileIs = new FileInputStream(cipheredFile);
		FileInputStream privKeyIn = new FileInputStream(privKeyFile);
		FileOutputStream plainTextFileIs = new FileOutputStream(decPlainTextFile);
		PgpHelper.getInstance().decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd == null ? null : passwd.toCharArray());
		cipheredFileIs.close();
		plainTextFileIs.close();
		privKeyIn.close();
		logger.info("解密文件成功...");
	}

	public static void encryptAutoClose(String plainTextFile, String pubKeyFile, String decPlainTextFile) throws Exception{
		logger.info("开始加密文件...");
		try(FileInputStream pubKeyIs = new FileInputStream(pubKeyFile);
			FileOutputStream cipheredFileIs = new FileOutputStream(decPlainTextFile)){
			PgpHelper.getInstance().encryptFile(cipheredFileIs, plainTextFile, PgpHelper.getInstance().readPublicKey(pubKeyIs), false, false);
		}
		logger.info("加密文件成功...");
	}

	public static void decryptAutoClose(File cipheredFile, String privKeyFile, String decPlainTextFile, String passwd) throws Exception{
		logger.info("开始解密文件...");
		try(FileInputStream cipheredFileIs = new FileInputStream(cipheredFile);
			FileInputStream privKeyIn = new FileInputStream(privKeyFile);
			FileOutputStream plainTextFileIs = new FileOutputStream(decPlainTextFile)){
			PgpHelper.getInstance().decryptFile(cipheredFileIs, plainTextFileIs, privKeyIn, passwd == null ? null : passwd.toCharArray());
		}
		logger.info("解密文件成功...");
	}
	
	/**
	 * Decrypt the PGP file
	 * @Title: decrypt
	 * @param @param cipheredFile Source file path
	 * @param @param privKeyFile The private key path
	 * @param @param fileList Cache in-memory data sets
	 * @param @param passwd password
	 * @param @throws Exception 参数
	 * @throws
	 */
	public static void decrypt(File cipheredFile, String privKey,List<String> fileDataList, String passwd) throws Exception{
		FileInputStream cipheredFileIs = null;
		FileInputStream privKeyIn = null;
		try {
			//Generate a file input stream
			cipheredFileIs = new FileInputStream(cipheredFile);
	    	//Generate a private key input stream
			privKeyIn = new FileInputStream(privKey);
			//The decryption of the PGP file starts
			PgpHelper.getInstance().decryptFile(cipheredFileIs, privKeyIn, passwd == null ? null : passwd.toCharArray(), fileDataList);
		}catch (Exception e) {
			logger.error("Decryption exception occurs:{}", e.getMessage());
			throw e;
		}finally {
			//Close the input stream
			if(cipheredFileIs != null) {
				cipheredFileIs.close();
				cipheredFileIs = null;
			}
			if(privKeyIn != null) {
				privKeyIn.close();
				privKeyIn = null;
			}
		}
	}



	/**
	 * Analytical data
	 * @Title: readFile
	 * @param @param fileList Source file path
	 * @param @return 参数
	 * @return List<Map<String,Object>> The return type
	 * @throws Exception
	 * @throws
	 */
	public static List<Map<String, Object>> readFile2List(List<String> fileList) throws Exception {
		//用来保存数据
		List<Map<String, Object>> datas = new ArrayList<>();
		//获取表头
		String[] header = fileList.get(0).split("\",\"");
		//遍历数据
		for (int row = 1; row < fileList.size(); row++) {
	    	Map<String, Object> map = new HashMap<>();
    	    // 取得第row行第0列的数据
    	    String[] rows = fileList.get(row).split("\",\"");
	        for (int cell = 0; cell < rows.length; cell++) {
	        	map.put(header[cell].replaceAll("\"", "").replaceAll(" ", "").toLowerCase(), rows[cell].replaceAll("\"", ""));
	        }
	        datas.add(map);
	    }
		return datas;
	}
	
	public static List<Map<String, Object>> readFile2List2(List<String> fileList) throws Exception {
		//用来保存数据
		List<Map<String, Object>> datas = new ArrayList<>();
		//获取表头
		String[] header = fileList.get(0).split(",");
		//遍历数据
		for (int row = 1; row < fileList.size(); row++) {
	    	Map<String, Object> map = new HashMap<>();
    	    // 取得第row行第0列的数据
    	    String[] rows = fileList.get(row).split("\",\"");
	        for (int cell = 0; cell < rows.length; cell++) {
	        	map.put(header[cell].replaceAll("\"", "").replaceAll(" ", "").toLowerCase(), rows[cell].replaceAll("\"", ""));
	        }
	        datas.add(map);
	    }
		return datas;
	}
}
