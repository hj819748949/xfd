package com.xfd.attach.client.rpc;

import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.client.vo.AttachInfoVO;

/**
 * 附件的rpc接口, 供rpc操作, 考虑附件迁移
 * @author xfd
 *
 * 2020年10月31日
 */
public interface AttachClient
{
	/**
	 * 上传附件
	 * @param req
	 * @return
	 * @throws Exception
	 */
	AttachInfoVO upload(AttachUploadReq req) throws Exception;

	/**
	 * 根据附件ID查询附件信息
	 * @param attachId
	 * @return
	 */
	AttachInfoVO findAttachInfoById(Long attachId);

	/**
	 * 查询附件文件内容
	 * @param attachId
	 * @return
	 * @throws Exception 
	 */
	byte[] findAttachData(Long attachId) throws Exception;
}
