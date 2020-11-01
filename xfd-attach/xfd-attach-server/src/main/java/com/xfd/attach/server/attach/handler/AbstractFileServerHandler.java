package com.xfd.attach.server.attach.handler;

import org.springframework.beans.BeanUtils;
import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.server.attach.model.AttachInfoDO;
import com.xfd.attach.server.server.model.AttachServerDO;
import com.xfd.common.dynamic.model.enums.DelFlagEnum;

/**
 * 抽象的文件server
 * @author xfd
 *
 * 2020年10月31日
 */
public abstract class AbstractFileServerHandler implements FileServerHandler
{
	// 这里可以考虑加密, 解密相关的处理
	
	@Override
	public AttachInfoDO upload(AttachServerDO attachServer, AttachUploadReq req)
		throws Exception
	{
		AttachInfoDO attachInfo = new AttachInfoDO();
		BeanUtils.copyProperties(req, attachInfo);
		attachInfo.setServerId(attachServer.getId());
		attachInfo.setDelFlag(DelFlagEnum.NORMAL.getFlag());
		// 执行上传
		doUpload(attachServer, req, attachInfo);
		return attachInfo;
	}
	@Override
	public byte[] download(AttachInfoDO attachInfo, AttachServerDO localAttachServer, AttachServerDO curAttachServer)
			throws Exception
	{
		return doDownload(attachInfo, localAttachServer, curAttachServer);
	}
	
	/**
	 * 执行上传
	 * @param attachServer
	 * @param req
	 * @param attachInfoDO
	 * @throws Exception
	 */
	protected abstract void doUpload(AttachServerDO attachServer, AttachUploadReq req, AttachInfoDO attachInfoDO) throws Exception;
	
	/**
	 * 执行下载
	 * @param attachInfo
	 * @param localAttachServer
	 * @param curAttachServer
	 * @throws Exception
	 */
	protected abstract byte[] doDownload(AttachInfoDO attachInfo, AttachServerDO localAttachServer, AttachServerDO curAttachServer) throws Exception;
}
