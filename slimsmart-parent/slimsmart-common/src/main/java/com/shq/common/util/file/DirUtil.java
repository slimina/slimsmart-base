package com.shq.common.util.file;

import java.io.File;
import java.io.FilenameFilter;

public class DirUtil {

	/**
	 * 列出特定路径下的特定后缀名的文件
	 * 
	 * @param directoryName
	 *            路径名
	 */
	public static File[] list(String directoryName, final String suffix) {
		File dir = new File(directoryName);
		File[] files = null;
		// 确定该路径指向一个目录
		if (dir.exists() && dir.isDirectory()) {
			// 列出所有结尾为suffix的文件
			files = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.endsWith(suffix);
				}
			});
		}
		return files;
	}
}
