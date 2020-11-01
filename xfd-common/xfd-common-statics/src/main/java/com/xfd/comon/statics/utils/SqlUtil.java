package com.xfd.comon.statics.utils;

/**
 * sql工具类, 防止模糊查询的时候特殊字符
 * @author xfd
 * 
 * 2020年4月15日
 *
 */
public class SqlUtil
{
	private SqlUtil() { }
	
	/**
	 * 对字符进行转义
	 * @param str
	 * @return
	 */
	public static String escapeStr(String str)
	{
		if (CommonUtil.isEmpty(str)) 
		{
			return str;
		}
		// 测试转义
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < str.length(); i++)
		{
			char ch = str.charAt(i);
			if (ch == '%' || ch == '_' || ch == '/' || ch == '\\') 
			{
				buf.append("\\");
			} 
			buf.append(ch);
		}
		return buf.toString();
	}
}
