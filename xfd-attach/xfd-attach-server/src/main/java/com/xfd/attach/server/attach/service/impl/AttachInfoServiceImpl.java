package com.xfd.attach.server.attach.service.impl;

import javax.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xfd.attach.client.req.AttachUploadReq;
import com.xfd.attach.client.vo.AttachInfoVO;
import com.xfd.attach.server.attach.dao.AttachInfoDao;
import com.xfd.attach.server.attach.handler.FileServerHandler;
import com.xfd.attach.server.attach.handler.ctx.FileServerHandlerContext;
import com.xfd.attach.server.attach.model.AttachInfoDO;
import com.xfd.attach.server.attach.service.AttachInfoService;
import com.xfd.attach.server.server.model.AttachServerDO;
import com.xfd.attach.server.server.service.AttachServerService;
import com.xfd.comon.statics.utils.CommonUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 附件信息业务实现
 * @author xfd
 *
 * 2020年10月31日
 */
@Slf4j
@Service
@Transactional
public class AttachInfoServiceImpl implements AttachInfoService
{
	@Autowired
	private FileServerHandlerContext serverHandlerCtx;
	
	/** 附件服务器 */
	@Resource
	private AttachServerService attachServerService;
	
	/** 附件信息业务 */
	@Resource
	private AttachInfoDao attachInfoDao;
	
	@Override
	public AttachInfoVO upload(AttachUploadReq req) throws Exception
	{
		// 执行基础校验
		if (CommonUtil.isEmpty(req.getFileName())) 
		{
			log.error("文件名称为空.");
			throw new Exception("文件名称不能为空.");
		}
		if (CommonUtil.isEmpty(req.getMimeType())) 
		{
			log.error("mime类型为空.");
			throw new Exception("mime类型为空.");
		}
		if (CommonUtil.isEmpty(req.getFileSize())
				|| req.getFileSize() < 0) 
		{
			log.error("文件大小不能为空, 且必须大于0.");
			throw new Exception("文件大小不能为空, 且必须大于0.");
		}
		if (CommonUtil.isEmpty(req.getGroupId())) 
		{
			req.setGroupId(CommonUtil.getUid());
		}
		if (req.getFileData() == null) 
		{
			log.error("文件数据不能为空.");
			throw new Exception("文件数据不能为空.");
		}
		// 获取本地文件存储对象
		AttachServerDO attachServer = attachServerService.findLocalServer();
		FileServerHandler fileServerHandler = serverHandlerCtx.findFileServerHandlerByType(attachServer.getServerType());
		AttachInfoDO attachInfoDO = fileServerHandler.upload(attachServer, req);
		if (!(attachInfoDao.insert(attachInfoDO) > 0)) 
		{
			log.error("保存附件信息[{}]失败.", attachInfoDO);
			throw new Exception("保存附件信息失败.");
		}
		AttachInfoVO attachInfoVO = new AttachInfoVO();
		BeanUtils.copyProperties(attachInfoDO, attachInfoVO);
		return attachInfoVO;
	}
	@Override
	public AttachInfoVO findById(Long attachId)
	{
		if (CommonUtil.isEmpty(attachId)) 
		{
			log.error("附件id为空.");
			return null;
		}
		AttachInfoDO attachInfoDO = this.attachInfoDao.selectById(attachId);
		if (CommonUtil.isEmpty(attachInfoDO)) 
		{
			log.warn("附件ID[{}], 附件信息不存在.", attachId);
			return null;
		}
		// 判断该文件是否被删除
		/**
		if (Integer.valueOf(DelFlagEnum.DELETED.getFlag()).equals(attachInfoDO.getDelFlag()))
		{
			log.error("附件ID[{}], 附件已经被删除.", attachId);
			return null;
		}
		*/
		AttachInfoVO attachInfoVO = new AttachInfoVO();
		BeanUtils.copyProperties(attachInfoDO, attachInfoVO);
		return attachInfoVO;
	}
	@Override
	public byte[] findAttachData(Long attachId) throws Exception
	{
		if (CommonUtil.isEmpty(attachId)) 
		{
			log.error("附件id为空.");
			return null;
		}
		AttachInfoDO attachInfoDO = this.attachInfoDao.selectById(attachId);
		if (CommonUtil.isEmpty(attachInfoDO)) 
		{
			log.warn("附件ID[{}], 附件信息不存在.", attachId);
			return null;
		}
		AttachServerDO dbAttachServerDO = attachServerService.findById(attachInfoDO.getServerId());
		if (CommonUtil.isEmpty(dbAttachServerDO)) 
		{
			log.error("附件服务器ID[{}]信息不存在.", attachInfoDO.getServerId());
			return null;
		}
		// 获取本地server
		AttachServerDO localAttachServer = attachServerService.findLocalServer();
		FileServerHandler fileServerHandler = serverHandlerCtx.findFileServerHandlerByType(dbAttachServerDO.getServerType());
		return fileServerHandler.download(attachInfoDO, localAttachServer, dbAttachServerDO);
	}
}
