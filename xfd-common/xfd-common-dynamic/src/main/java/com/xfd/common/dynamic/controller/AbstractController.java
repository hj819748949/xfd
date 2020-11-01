package com.xfd.common.dynamic.controller;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.xfd.comon.statics.utils.CommonUtil;
import com.xfd.comon.statics.utils.StreamUtil;

/**
 * 抽象Controller
 * @author xfd
 *
 * 2020年10月31日
 */
public abstract class AbstractController
{
	/** request 对象 */
	private HttpServletRequest req;
	
	/** response 对象 */
	private HttpServletResponse resp;
	
	
	public HttpServletRequest getRequest()
	{
		return req;
	}
	public HttpServletResponse getResponse()
	{
		return resp;
	}
	/**
	 * 获取基础路径
	 * @return
	 */
	public StringBuffer getBasePath()
	{
		return new StringBuffer().append(getRequest().getScheme())
					.append("://").append(getRequest().getServerName())
					.append(":").append(getRequest().getServerPort());
	}
	/**
	 * 获取重定向路径
	 * @return
	 */
	public StringBuffer getRedirectPath()
	{
		return new StringBuffer().append("redirect:");
	}
	/**
	 * 下载文件
	 * @param in
	 * @param fileName
	 * @throws Exception
	 */
	public void download(InputStream in, String fileName) 
			throws Exception
	{
		OutputStream out = null;
        try
        {
        	getResponse().reset();
        	getResponse().setHeader("Content-disposition", "attachment; filename=" + CommonUtil.encodeDownFileName(fileName, getRequest().getHeader("User-Agent")));
            // 设置 默认编码
        	getResponse().setCharacterEncoding("UTF-8");
        	getResponse().setContentType("application/x-download");
            out = getResponse().getOutputStream();
            byte[] buf = new byte[1024 * 10];
            int len = 0;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.flush();
        } finally
        {
            StreamUtil.closeStream(in, out);
        }
	}
	/**
	 * 下载, 大文件下载慎用
	 * @param fileData
	 * @param fileName
	 */
	public void download(byte[] fileData, String fileName) throws Exception
	{
		download(new ByteArrayInputStream(fileData), fileName);
	}
	/**
	 * 设置request, response
	 * @param req
	 * @param resp
	 */
	@ModelAttribute
	public void setReqAndResp(HttpServletRequest req, HttpServletResponse resp) 
	{
		this.req = req;
		this.resp = resp;
	}
}
