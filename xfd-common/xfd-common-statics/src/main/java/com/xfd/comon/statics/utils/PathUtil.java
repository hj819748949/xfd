package com.xfd.comon.statics.utils;

import java.io.File;

/**
 * 路径工具类
 * @author xfd
 *
 * 2020年10月31日
 */
public class PathUtil
{
	private PathUtil() { }
	
	/**
	 * 获取当前文件夹路径
	 * @return
	 */
	public static String getCurDirPath() 
	{
		String userDir = System.getProperty("user.dir");
		if (CommonUtil.isNotEmpty(userDir)) 
		{
			return userDir;
		}
		// 这种获取的办法可能会有点问题
		String path = PathUtil.class.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		String osName = System.getProperty("os.name");
		if(osName.contains("dows"))
		{
			path = path.substring(1, path.length());
		}
		if(path.contains("jar"))
		{
			path = path.substring(0, path.lastIndexOf("."));
			return path.substring(0, path.lastIndexOf("/"));
		}
		return path.replace("target/classes/", "");
	}
	/**
	 * 去除路径
	 * @param realPath
	 * @return
	 */
	public static String eraseOsSeparator(String realPath)
    {
    	if (CommonUtil.isEmpty(realPath))
    	{
    		return realPath;
    	}
    	StringBuffer buf = new StringBuffer();
    	for (int i = 0; i < realPath.length(); i++)
    	{
    		String ch = realPath.charAt(i) + "";
    		if (ch.equals("/") || ch.equals("\\"))
    		{
    			ch = File.separator;
    		}
    		buf.append(ch);
    	}
    	return buf.toString();
    }
	/**
	 * 转换成http的请求路径
	 * @param realPath
	 * @return
	 */
	public static String eraseHttpSeparator(String realPath)
	{
		if (CommonUtil.isEmpty(realPath))
    	{
    		return realPath;
    	}
    	StringBuffer buf = new StringBuffer();
    	for (int i = 0; i < realPath.length(); i++)
    	{
    		String ch = realPath.charAt(i) + "";
    		if (ch.equals("\\"))
    		{
    			ch = "/";
    		}
    		buf.append(ch);
    	}
    	return buf.toString();
	}
}
