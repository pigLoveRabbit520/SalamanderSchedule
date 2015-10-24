package com.attraction.schedule.tool;

/**
 * 工具类
 * @author MH
 *
 */
public class CommonUtil {
	/**
	 * 判断是否为空串
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str) {
		if(str == null || str.length() <= 0) {
			return true;
		}
		else {
			return false;
		}
	}
}
