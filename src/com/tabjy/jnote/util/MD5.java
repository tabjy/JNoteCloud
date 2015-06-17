package com.tabjy.jnote.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5(String input) throws NoSuchAlgorithmException {
	         MessageDigest md = MessageDigest.getInstance("MD5");
	         
	         md.update(input.getBytes()); 
	      	 byte[] output = md.digest();
	         
	         return bytesToHex(output).substring(8, 24);	
	   }

	public static String bytesToHex(byte[] b) {
		char hexDigit[] = {'0', '1', '2', '3', '4', '5', '6', '7',
							'8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
		StringBuffer buf = new StringBuffer();
		for (int j=0; j<b.length; j++) {
			buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
			buf.append(hexDigit[b[j] & 0x0f]);
		}
		return buf.toString();
	}
}
