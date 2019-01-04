package com.lanxi.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static Logger log= LoggerFactory.getLogger(MD5Util.class);

	/**
	 * md5加盐形式
	 * @param value
	 * @return
	 */
	public static String md5Encrpt(String value) {
		StringBuilder sb=new StringBuilder();
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(value.getBytes());
			byte[] target=md5.digest();
			for (int i=0;i<target.length;i++){
				int x=(int) target[i]&0xff;
				//加盐
				x=x+1;
				if(x<16){
					sb.append(0);
				}else {
					sb.append(Integer.toHexString(x));
				}

			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	/**
	 * 32位小写MD5
	 *
	 * @param str
	 * @return
	 */
	public static String parseStrToMd5L32(String str) {

		if (null == str) {
			log.info(".parseStrToMd5L32() str is null.");
			return str;
		}

		String reStr = null;
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (byte b: bytes) {
				int bt = b & 0xff;
				if (bt < 16) {
					sb.append(0);
				}
				sb.append(Integer.toHexString(bt));
			}

			reStr = sb.toString();

			return reStr;

		} catch (Exception e) {
			log.error(".parseStrToMd5L32() Exception={},param={}", e, str);
			throw new RuntimeException(e);
		}
	}
	/**
	 * 32位大写MD5
	 *
	 * @param str
	 * @return
	 */
	public static String parseStrToMd5U32(String str) {
		String reStr = parseStrToMd5L32(str);
		if (null != reStr) {
			reStr = reStr.toUpperCase();
		}
		return reStr;
	}
}
