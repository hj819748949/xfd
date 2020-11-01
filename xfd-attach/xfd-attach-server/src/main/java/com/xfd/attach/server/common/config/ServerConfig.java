package com.xfd.attach.server.common.config;

import lombok.Data;
import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * 本机的服务器配置
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
@Component
@RefreshScope
public class ServerConfig implements Serializable
{
	private static final long serialVersionUID = -3746657582343675649L;
	
	/** 本机的ip */
	@Value("${spring.cloud.client.ip-address}")
	private String localIp;
	
	/** 本机端口 */
	@Value("${server.port}")
	private Integer localPort;
	
	/** 本机上下文路径 */
	@Value("${server.servlet.context-path:}")
	private String localCtxPath;
	
	/** 本机服务器类型, 服务器类型, com.xfd.attach.server.server.model.enums.ServerTypeEnum */
	@Value("${attach.servertype:0}")
	private Integer localServerType;
	
	/** 本机服务器类型, 默认本地存储路径, 非绝对路径 */
	@Value("${attach.storagedir:../attachs}")
	private String storageDir;
	
	
}
