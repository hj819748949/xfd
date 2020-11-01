package com.xfd.attach.server.attach.handler;

import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.server.attach.model.AttachInfoDO;
import com.xfd.attach.server.server.model.AttachServerDO;

/**
 * 文件适配器
 * @author xfd
 *
 * 2020年10月31日
 */
public interface FileServerHandler
{
	/**
	 * 获取文件server类型
	 * @return
	 */
	int getFileServerType();
	
	/**
	 * 执行文件上传
	 * @param attachServer
	 * @param req
	 * @return
	 */
	AttachInfoDO upload(AttachServerDO attachServer, AttachUploadReq req) throws Exception;

	/**
	 * 下载文件
	 * @param attachInfo
	 * @param localAttachServer
	 * @param curAttachServer
	 * @return
	 * @throws Exception
	 */
	byte[] download(AttachInfoDO attachInfo, AttachServerDO localAttachServer, AttachServerDO curAttachServer) throws Exception;
}
