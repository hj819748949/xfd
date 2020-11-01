package com.xfd.test;

import org.junit.Test;

/**
 * jar路径测试
 * @author xfd
 *
 * 2020年10月31日
 */
public class JarPathTest
{
	@Test
	public void testPath() 
	{
		System.out.println(getPath());
	}
	
	/**
	 * 获取路径
	 * @return
	 */
	public String getPath()
	{
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		if(System.getProperty("os.name").contains("dows"))
		{
			path = path.substring(1,path.length());
		}
		if(path.contains("jar"))
		{
			path = path.substring(0,path.lastIndexOf("."));
			return path.substring(0,path.lastIndexOf("/"));
		}
		return path.replace("target/classes/", "");
	}
}
