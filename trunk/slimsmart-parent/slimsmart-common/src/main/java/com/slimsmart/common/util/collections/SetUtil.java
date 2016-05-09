package com.slimsmart.common.util.collections;

import java.util.Set;

import org.apache.commons.collections.SetUtils;

public class SetUtil extends SetUtils{
	
	public static boolean isEmpty(Set<?> set) {
		return set == null || set.isEmpty();
	}

	public static boolean isNotEmpty(Set<?> set) {
		return !isEmpty(set);
	}
}
