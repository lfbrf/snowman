package br.com.snowman.util;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import javax.sql.rowset.serial.SerialBlob;

/**
 * @author luiz
 * Essa classe tem métodos genéricos que são podem ser usados em qualquer parte do código
 */
public class MainUtil {
	public static byte[] convertStringToByteFile(String file) {
		byte[] decodedByte = Base64.getDecoder().decode(file);
		return decodedByte;
	}
	
	public static String convertByteToStringFile(byte[] file) {
		try {
			Blob blob = new SerialBlob(file);
			String encoded = Base64.getEncoder().encodeToString(blob.getBytes(1l, (int)blob.length()));
			return encoded;
		} catch (SQLException e) {
			System.err.println("Errr converting byte to 64 " + e.getMessage());
			return e.getMessage();
		}
	}
	
	public static String getBase64fFile(String file) {
		String separator =",";
		String[] result = file.split(separator, 2);
		return result[1];
	}

	
	public static String getNameOfFile(String file) {
		String separator =",";
		String[] result = file.split(separator, 2);
		return result[0];
	}

}
