package com.xfd.attach.server.common.rpc;

import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.client.rpc.AttachClient;
import com.xfd.attach.client.vo.AttachInfoVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.xfd.attach.server.attach.service.AttachInfoService;
import com.xfd.attach.server.server.service.AttachServerService;

/**
 * 附件rpc服务实现
 * @author xfd
 *
 * 2020年10月31日
 */
@Service
@SuppressWarnings("all")
public class AttachClientImpl implements AttachClient
{
	/** 附件server服务 */
	@Autowired
	private AttachServerService attachServerService;
	
	/** 附件信息业务 */
	@Autowired
	private AttachInfoService attachInfoService;

	@Override
	public AttachInfoVO upload(AttachUploadReq req) throws Exception
	{
		return attachInfoService.upload(req);
	}
	@Override
	public AttachInfoVO findAttachInfoById(Long attachId)
	{
		return attachInfoService.findById(attachId);
	}
	@Override
	public byte[] findAttachData(Long attachId) throws Exception
	{
		return attachInfoService.findAttachData(attachId);
	}
}
