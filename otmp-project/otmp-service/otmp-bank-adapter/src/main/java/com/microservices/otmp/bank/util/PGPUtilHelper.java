package com.microservices.otmp.bank.util;

import org.bouncycastle.openpgp.PGPLiteralDataGenerator;
import org.bouncycastle.openpgp.PGPUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

public class PGPUtilHelper extends PGPUtil {

	@SuppressWarnings("unused")
	private static void pipeFileContents(InputStream in, OutputStream pOut, int bufSize) throws IOException {
		byte[] buf = new byte[bufSize];

		int len;
		while ((len = in.read(buf)) > 0) {
			pOut.write(buf, 0, len);
		}

		pOut.close();
		in.close();
	}

	/**
	 * write out the passed in file as a literal data packet.
	 * 
	 * @param out
	 * @param fileType the LiteralData type for the file.
	 * @param file
	 * 
	 * @throws IOException
	 */
	public static void writeFileToLiteralData(OutputStream out, char fileType, InputStream in, String fileName,
			long length, Date modificationTime) throws IOException {
		PGPLiteralDataGenerator lData = new PGPLiteralDataGenerator();
		OutputStream pOut = lData.open(out, fileType, fileName, length, modificationTime);
		pipeFileContents(in, pOut, 4096);
	}

}
