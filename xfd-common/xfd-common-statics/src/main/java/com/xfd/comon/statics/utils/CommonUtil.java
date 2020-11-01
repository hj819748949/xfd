package com.xfd.comon.statics.utils;

import java.util.Map;
import java.util.UUID;
import java.net.URLEncoder;
import java.util.Collection;
import java.lang.reflect.Array;
import java.io.UnsupportedEncodingException;

/**
 * 工具类
 * @author jsb-hujie
 * 
 * 2019年5月9日
 *
 */
public class CommonUtil
{
	private CommonUtil(){ }
	
	/**
	 * 获取UUID字符串
	 * @return
	 */
	public static String getUUID()
	{
		return UUID.randomUUID().toString();
	}
	/**
	 * uuid去除横杠
	 * @return
	 */
	public static String getUid()
	{
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	/**
	 * 判断是否为空
	 * @param obj 需要判断的对象, 空集合也算是空
	 * @return
	 */
	public static boolean isEmpty(Object obj)
	{
		if (null == obj)
		{
			return true;
		} else if (obj instanceof String)
		{
			return obj.toString().trim().length() == 0;
		} else if (obj instanceof Collection)
		{
			Collection<?> coll = (Collection<?>) obj;
			return coll.size() == 0;
		} else if (obj instanceof Map)
		{
			Map<?, ?> map = (Map<?, ?>) obj;
			return map.size() == 0;
		} else if (obj.getClass().isArray())
		{
			return Array.getLength(obj) == 0;
		}
		return false;
	}
	/**
	 * 判断非空
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj)
	{
		return !isEmpty(obj);
	}
	/**
	 * 编码下载文件的文件名称
	 * @param fileName
	 * @param agent
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeDownFileName(String fileName, String agent) throws UnsupportedEncodingException
    {
		String codedfilename = null;
		if (null != agent && -1 != agent.indexOf("MSIE"))
		{
		    String prefix = fileName.lastIndexOf(".") != -1 ? fileName.substring(0, fileName.lastIndexOf("."))
			    : fileName;
		    String extension = fileName.lastIndexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".")) : "";
		    String name = URLEncoder.encode(prefix, "UTF8");
		    if (name.lastIndexOf("%0A") != -1)
		    {
			name = name.substring(0, name.length() - 3);
		    }
		    codedfilename = name + extension;
		} else if (null != agent && -1 != agent.indexOf("Chrome"))
		{
		    //Chrome
		    codedfilename = URLEncoder.encode(fileName, "UTF-8");
	            codedfilename = StringUtil.replace(codedfilename, "+", "%20");    // 处理空格
		    codedfilename = StringUtil.replace(codedfilename, "%28", "(").replace( "%29", ")");//替换空格
		    codedfilename = StringUtil.replace(codedfilename, "%7B", "{").replace( "%7D", "}");//替换{}
		} else if (null != agent && -1 != agent.indexOf("Mozilla"))
		{
		    //IE7+
		    return new String(fileName.getBytes("gbk"), "iso8859-1");
		} else
		{
		    codedfilename = fileName;
		}
		return codedfilename;
    }
}
