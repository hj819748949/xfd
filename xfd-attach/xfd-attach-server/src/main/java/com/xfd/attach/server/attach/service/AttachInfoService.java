package com.xfd.attach.server.attach.service;

import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.client.vo.AttachInfoVO;

/**
 * 附件信息业务
 * @author xfd
 *
 * 2020年10月31日
 */
public interface AttachInfoService
{
	/**
	 * 上传文件
	 * @param req
	 * @return
	 * @throws Exception
	 */
	AttachInfoVO upload(AttachUploadReq req) throws Exception;

	/**
	 * 根据附件id查询附件信息
	 * @param attachId
	 * @return
	 */
	AttachInfoVO findById(Long attachId);

	/**
	 * 查询文件内容
	 * @param attachId
	 * @return
	 * @throws Exception
	 */
	byte[] findAttachData(Long attachId) throws Exception;
}
