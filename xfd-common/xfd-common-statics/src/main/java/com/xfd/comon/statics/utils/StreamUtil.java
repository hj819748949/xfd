package com.xfd.comon.statics.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;

/**
 * 流工具类
 * 
 * @author xfd
 *
 * 2019年3月23日
 */
public class StreamUtil
{
	private StreamUtil() { }
	
	/**
	 * 关闭流
	 * @param streams
	 */
	public static void closeStream(AutoCloseable ...streams)
	{
		if (null != streams && streams.length != 0)
		{
			for (AutoCloseable stream : streams)
			{
				if (stream != null)
				{
					try
					{
						stream.close();
					} catch (Exception e)
					{
						
					}
				}
			}
		}
	}
	/**
	 * 拷贝流
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copyStream(InputStream in, OutputStream out) throws IOException
	{
		int len = 0;
		byte[] buf = new byte[1024 * 10];
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		// 关闭流
		closeStream(in, out);
	}
	/**
	 * 获取流的字节数组
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] getStreamBytes(InputStream in) throws IOException
	{
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int len = 0;
		byte[] buf = new byte[1024 * 10];
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		closeStream(in);
		return out.toByteArray();
	}
	/**
	 * 流转换成字符换
	 * @param in
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String getStreamTxt(InputStream in, String charset) throws IOException
	{
		byte[] buf = getStreamBytes(in);
		return new String(buf, charset);
	}
	/**
	 * 流转换成字符换
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getStreamTxt(InputStream in) throws IOException
	{
		return getStreamTxt(in, "utf-8");
	}
	/**
	 * 读取每一行数据
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static List<String> getStreamLines(InputStream in)
		throws Exception
	{
		InputStreamReader reader = null;
		BufferedReader br = null;
		try 
		{
			reader = new InputStreamReader(in, Charset.forName("UTF-8"));
			br = new BufferedReader(reader);
			List<String> result = new ArrayList<String>();
			String line = null;
			while ((line = br.readLine()) != null)
			{
				if (CommonUtil.isEmpty(line))
				{
					continue;
				}
				result.add(line.trim());
			}
			return result;
		} finally 
		{
			StreamUtil.closeStream(br, reader);
		}
	}
}
