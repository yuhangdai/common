package com.aotain.common.utils.tools;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 文件基础操作工具
 * 
 * @author liuz@aotian.com
 * @date 2017年11月10日 上午11:05:43
 */
public class FileUtils {

	/**
	 * 将文件读取成为数据流
	 */
	public static InputStream read2Stream(String path) throws FileNotFoundException {
		if (StringUtils.isBlank(path)) {
			return null;
		}

		File f = new File(path);
		if (!f.exists()) {
			return null;
		}

		return new FileInputStream(f);
	}

	/**
	 * 将文件读取成为byte[]
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	public static byte[] read2Bytes(String path) throws IOException {
		InputStream is = read2Stream(path);
		if (is == null) {
			return null;
		}
		org.apache.commons.io.FileUtils.readFileToByteArray(new File(path));
//		return StreamUtils.getBytes(is);
		return null;
	}

	/**
	 * 文件重命名
	 * 
	 * @param path1
	 * @param path2
	 */
	public static boolean renameFile(String path1, String path2) throws Exception {
		File oldFile = new File(path1);
		File newFile = new File(path2);
		if (oldFile.exists() && !newFile.exists()) {
			return oldFile.renameTo(newFile);
		}
		return false;
	}

	/**
	 * 将数据流写入文件
	 * 
	 * @param data
	 * @param path
	 * @throws Exception
	 */
	public static void writeToFile(byte[] data, String path) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(path);
			fos.write(data);
			fos.flush();
		} finally {
			org.springframework.util.StreamUtils.nonClosing(fos);
		}

	}

	/**
	 * 安静的删除指定的文件路径列表指向的文件
	 * 
	 * @param copiedTmpFilePaths
	 */
	public static void deleteFilesQuietly(List<String> copiedTmpFilePaths) {
		if (copiedTmpFilePaths == null || copiedTmpFilePaths.size() == 0) {
			return;
		}
		try {
			for (String path : copiedTmpFilePaths) {
				File file = new File(path);
				if (file.exists() && file.isFile()) {
					file.delete();
				}
			}
		} catch (Exception e) {

		}
	}

	/**
	 * 从环境中加载配置文件
	 * 
	 * @param dir
	 * @param name
	 */
	public static Properties loadPropertiesFromConfig(String dir, String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		name = name.trim();
		if (!name.endsWith(".properties")) {
			name += ".properties";
		}
		String path = null;
		path = name;
		if (!StringUtils.isBlank(dir)) {
			path = dir.trim() + File.separator + path;
		}

		System.out.println("try to load properties from path : " + path);
		Properties p = new Properties();
		File file = new File(path);
		if (file.exists()) {
			try {
				FileInputStream fis = new FileInputStream(file);
				p.load(fis);
				return p;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// 从class path中加载使用绝对路径加载
		path = name;
		if (path.contains("\\")) {
			path = path.replace("\\", "/");
		}
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		System.out.println("load properties from classpath : " + path);
		try {
			InputStream is = FileUtils.class.getResourceAsStream(path);
			if (is != null) {
				p.load(is);
			}
			return p;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("fail to load properties: " + path);
		return null;
	}
}
