package com.xfd.attach.server.server.service;

import com.xfd.attach.server.server.model.AttachServerDO;

/**
 * 附件服务器业务
 * @author xfd
 *
 * 2020年10月31日
 */
public interface AttachServerService
{
	/**
	 * 查询本机server
	 * @return
	 */
	AttachServerDO findLocalServer();

	/**
	 * 根据id查询server
	 * @param serverId
	 * @return
	 */
	AttachServerDO findById(Long serverId);
}
