package com.xfd.attach.server.server.service.impl;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import com.xfd.common.dynamic.model.enums.DelFlagEnum;
import com.xfd.attach.server.common.config.ServerConfig;
import com.xfd.attach.server.server.dao.AttachServerDao;
import com.xfd.attach.server.server.model.AttachServerDO;
import com.xfd.attach.server.server.model.enums.ServerTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.xfd.attach.server.server.service.AttachServerService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * 附件服务器业务实现
 * @author xfd
 *
 * 2020年10月31日
 */
@Slf4j
@Service
@Transactional
public class AttachServerServiceImpl implements AttachServerService
{
	/** 附件服务器DAO */
	@Autowired
	private AttachServerDao attachServerDao;
	
	/** 本地服务器配置 */
	@Autowired
	private ServerConfig serverConfig;
	
	@Override
	public AttachServerDO findLocalServer()
	{
		QueryWrapper<AttachServerDO> queryWrapper = new QueryWrapper<AttachServerDO>();
		queryWrapper.lambda().eq(AttachServerDO::getServerIp, serverConfig.getLocalIp())
						.eq(AttachServerDO::getServerPort, serverConfig.getLocalPort());
		return attachServerDao.selectOne(queryWrapper);
	}
	@Override
	public AttachServerDO findById(Long serverId)
	{
		return attachServerDao.selectById(serverId);
	}
	/**
	 * 初始化汇报
	 */
	@PostConstruct
	public void initReport() 
	{
		log.info("开始准备注册附件服务器, IP地址[{}], 端口[{}], 上下文路径[{}], 服务器类型[{}], 本地存储路径[{}].", 
				serverConfig.getLocalIp(), serverConfig.getLocalPort(), serverConfig.getLocalCtxPath(),
				ServerTypeEnum.findByType(serverConfig.getLocalServerType()) == null ? "" : ServerTypeEnum.findByType(serverConfig.getLocalServerType()).getDescription(), 
				serverConfig.getStorageDir());
		
		// 本地检测查询
		QueryWrapper<AttachServerDO> queryWrapper = new QueryWrapper<AttachServerDO>();
		queryWrapper.lambda().eq(AttachServerDO::getServerIp, serverConfig.getLocalIp())
						.eq(AttachServerDO::getServerPort, serverConfig.getLocalPort());
		AttachServerDO attachServerDO = attachServerDao.selectOne(queryWrapper);
		if (null == attachServerDO) {
			log.info("附件服务器信息不存在, 开始准备注册.");
			// 准备保存
			attachServerDO = new AttachServerDO();
			attachServerDO.setServerIp(serverConfig.getLocalIp());
			attachServerDO.setServerPort(serverConfig.getLocalPort());
			attachServerDO.setServerType(serverConfig.getLocalServerType());
			attachServerDO.setStorageDir(serverConfig.getStorageDir());
			attachServerDO.setDelFlag(DelFlagEnum.NORMAL.getFlag());
			attachServerDO.setCreateDate(new Date());
			attachServerDO.setModifyDate(new Date());
			
			if (!(attachServerDao.insert(attachServerDO) > 0)) 
			{
				log.error("保存附件服务器, IP地址[{}], 端口[{}], 上下文路径[{}], 服务器类型[{}], 本地存储路径[{}]失败.", 
						serverConfig.getLocalIp(), serverConfig.getLocalPort(), serverConfig.getLocalCtxPath(),
						ServerTypeEnum.findByType(serverConfig.getLocalServerType()) == null ? "" : ServerTypeEnum.findByType(serverConfig.getLocalServerType()).getDescription(), 
						serverConfig.getStorageDir());
			} else 
			{
				log.info("附件服务器信息注册成功.");
			}
		} else {
			// 执行更新
			attachServerDO.setServerType(serverConfig.getLocalServerType());
			attachServerDO.setStorageDir(serverConfig.getStorageDir());
			attachServerDO.setDelFlag(DelFlagEnum.NORMAL.getFlag());
			attachServerDO.setModifyDate(new Date());
			QueryWrapper<AttachServerDO> updateWrapper = new QueryWrapper<AttachServerDO>();
			updateWrapper.lambda().eq(AttachServerDO::getId, attachServerDO.getId());
			if (!(attachServerDao.update(attachServerDO, updateWrapper) > 0)) 
			{
				log.error("更新附件服务器, IP地址[{}], 端口[{}], 上下文路径[{}], 服务器类型[{}], 本地存储路径[{}]失败.", 
						serverConfig.getLocalIp(), serverConfig.getLocalPort(), serverConfig.getLocalCtxPath(),
						ServerTypeEnum.findByType(serverConfig.getLocalServerType()) == null ? "" : ServerTypeEnum.findByType(serverConfig.getLocalServerType()).getDescription(), 
						serverConfig.getStorageDir());
			} else 
			{
				log.info("附件服务器信息注册成功.");
			}
		}
	}
}
