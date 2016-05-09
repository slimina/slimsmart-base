package com.slimsmart.common.util.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.ListUtils;

public class ListUtil extends ListUtils {

	public static String splitToString(List<String> list) {
		String splitString = "";
		StringBuilder sb = new StringBuilder();
		if (list != null) {
			for (String str : list) {
				sb.append(str).append(",");
			}
			splitString = sb.toString();
			if (splitString.lastIndexOf(",") > 0) {
				splitString = splitString.substring(0,
						splitString.lastIndexOf(",")).trim();
			}
		}
		return splitString;
	}

	/**
	 * 过滤重复的字符串
	 *
	 * @param list
	 * @return
	 */
	public static List<String> filterRepeat(List<String> list) {
		List<String> listFilter = new ArrayList<String>();
		Set<String> setFilter = new HashSet<String>();
		setFilter.addAll(list);
		listFilter.addAll(setFilter);
		return listFilter;
	}

	public static boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}

	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}
}
