package cn.sgst.mywebplus.core.util;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * sql注入处理工具类
 * 
 */
@Slf4j
public class SqlInjectionUtil {

	private final static String regex = "'|#|%|;|--| and | and|and | or | or|or | not | not|not " +
			"| use | use|use | insert | insert|insert | delete | delete|delete | update | update|update " +
			"| select | select|select | count | count|count | group | group|group | union | union|union " +
			"| create | create|create | drop | drop|drop | truncate | truncate|truncate | alter | alter|alter " +
			"| grant | grant|grant | execute | execute|execute | exec | exec|exec | xp_cmdshell | xp_cmdshell|xp_cmdshell " +
			"| call | call|call | declare | declare|declare | source | source|source | sql | sql|sql ";

	/**
	 * Sql关键词替换成空字符串
	 * @param value 原始值
	 * @return 替换后的值
	 */
	public  static String filterSql(String value) {
		if(StrUtil.isEmpty(value)) {
			return value;
		}
		return value.replaceAll("(?i)" + regex, ""); // (?i)不区分大小写替换
	}


}
