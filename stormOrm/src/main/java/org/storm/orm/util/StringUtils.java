package org.storm.orm.util;
/**
 * 字符串处理工具累
 * @author Administrator
 *
 */
public class StringUtils {
	
	/**
	 * 将目标字符串首字母大写
	 * @param str 目标字符串
	 * @return
	 */
	public static String firstChar2UpperCase(String str){
		//abcd --?Abcd
		
		return str.toUpperCase().substring(0,1)+str.substring(1);
	}
	

}
