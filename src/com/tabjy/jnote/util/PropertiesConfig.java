package com.tabjy.jnote.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesConfig {

	/**
	 * 根据KEY，读取文件对应的值
	 * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties
	 * @param key 键
	 * @return key对应的值
	 */
	public static String readData(String key) {
		String filePath = System.getProperty("java.io.tmpdir")+"/com.tabjy.jnote/config.properties";
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			in.close();
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 修改或添加键值对 如果key存在，修改, 反之，添加。
	 * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties
	 * @param key 键
	 * @param value 键对应的值
	 */
	public static void writeData(String key, String value) {
		String filePath = System.getProperty("java.io.tmpdir")+"/com.tabjy.jnote/config.properties";
		Properties prop = new Properties();
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			//一定要在修改值之前关闭fis
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(key, value);
			//保存，并加入注释
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + value + " value error");
		}
	}
}