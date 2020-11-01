package com.xfd.attach.server.attach.handler;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.server.attach.model.AttachInfoDO;
import com.xfd.attach.server.common.config.ServerConfig;
import com.xfd.attach.server.server.model.AttachServerDO;
import com.xfd.attach.server.server.model.enums.ServerTypeEnum;
import com.xfd.comon.statics.utils.CommonUtil;
import com.xfd.comon.statics.utils.DateUtil;
import com.xfd.comon.statics.utils.FileUtil;
import com.xfd.comon.statics.utils.PathUtil;
import com.xfd.comon.statics.utils.StreamUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 本地文件适配器
 * @author xfd
 *
 * 2020年10月31日
 */
@Slf4j
@Component
public class LocalFileServerHandler extends AbstractFileServerHandler
{
	/** 路径格式化 , /相对路径/文件名 */
	private static final String PATH_FORMAT = "%s" + File.separator + "%s";
	
	/** 本机配置 */
	@Autowired
	private ServerConfig serverConfig;
	

	@Override
	public int getFileServerType()
	{
		return ServerTypeEnum.LOCAL.getType();
	}
	@Override
	protected void doUpload(AttachServerDO attachServer, AttachUploadReq req, AttachInfoDO attachInfoDO)
			throws Exception
	{
		// 执行上传, 
		File rootDir = getRootDir();
		String storePath = String.format(PATH_FORMAT, DateUtil.formatDate(DateUtil.SECTION_FORMAT), FileUtil.newUidName(req.getFileName()));
		attachInfoDO.setStoragePath(storePath);
		// 判断文件是否存在, 不存在创建文件夹
		File attachFile = new File(rootDir, storePath);
		if (attachFile.getParentFile() != null)
		{
			if (!attachFile.getParentFile().exists())
			{
				if (!attachFile.getParentFile().mkdirs())
				{
					throw new Exception("创建[" + attachFile.getAbsolutePath() + "]文件夹失败..");
				}
			}
		}
		// 执行上传
		StreamUtil.copyStream(new ByteArrayInputStream(req.getFileData()), new FileOutputStream(attachFile));
	}
	@Override
	protected byte[] doDownload(AttachInfoDO attachInfo, AttachServerDO localAttachServer,
			AttachServerDO curAttachServer) throws Exception
	{
		if (CommonUtil.isNotEmpty(localAttachServer.getServerIp())
				&& localAttachServer.getServerIp().equals(curAttachServer.getServerIp())
				&& CommonUtil.isNotEmpty(localAttachServer.getServerPort())
				&& localAttachServer.getServerPort().equals(curAttachServer.getServerPort())) 
		{
			// 文件就在本地
			File rootDir = getRootDir();
			File file = new File(rootDir, PathUtil.eraseOsSeparator(attachInfo.getStoragePath()));
			return StreamUtil.getStreamBytes(new FileInputStream(file));
		} else 
		{
			// 判断下是是本地, 避免死循环.
			
			// 远程文件, 执行http请求下载下来
			StringBuffer urlBuf = new StringBuffer();
			urlBuf.append("http://")
				.append(curAttachServer.getServerIp()).append(":")
				.append(curAttachServer.getServerPort())
				.append("/attach/download?id=").append(attachInfo.getId());
			
			CloseableHttpClient httpClient = null;
			HttpPost httpPost = null;
			CloseableHttpResponse httpResponse = null;
			HttpEntity httpEntity = null;
			try 
			{
				httpClient = HttpClients.createDefault();
				httpPost = new HttpPost(urlBuf.toString());
				httpResponse = httpClient.execute(httpPost);
				httpEntity = httpResponse.getEntity();
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) 
				{
					return StreamUtil.getStreamBytes(httpEntity.getContent());
				} else 
				{
					log.error("远程下载[{}], 结果[{}], 请求失败.", urlBuf.toString(), EntityUtils.toString(httpEntity, "UTF-8"));
					return null;
				}
			} finally 
			{
				StreamUtil.closeStream(httpResponse, httpClient);
			}
		}
	}
	/**
	 * 获取根节点路径
	 * @return
	 */
	protected File getRootDir() 
	{
		File rootDir = new File(PathUtil.getCurDirPath(), serverConfig.getStorageDir());
		if (!rootDir.exists()) 
		{
			if (!rootDir.mkdirs()) 
			{
				log.error("创建文件夹[{}]失败.", rootDir.getAbsolutePath());
				throw new RuntimeException("创建文件件[" + rootDir.getAbsolutePath() + "]失败.");
			}
		}
		log.debug("附件本地存储的路径[{}].", rootDir.getAbsolutePath());
		return rootDir;
	}
}
