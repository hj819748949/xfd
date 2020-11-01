package com.xfd.attach.server.server.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xfd.common.dynamic.model.AbstractDO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 附件服务端实体, 数据库里面可能是缓存数据, 可能存在下限的可能性
 * @author xfd
 * 
 * 2020年10月31日
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("xfd_attachserver")
public class AttachServerDO extends AbstractDO
{
	private static final long serialVersionUID = -8483759405719165958L;
	
	/** 服务器ip, ${spring.cloud.client.ip-address} */
	@TableField("server_ip")
	private String serverIp;
	
	/** 服务器端口, ${server.port} */
	@TableField("server_port")
	private Integer serverPort;
	
	/** 上下文路径 */
	@TableField("ctxpath")
	private String ctxPath;
	
	/** 存储路径 */
	@TableField("storagedir")
	private String storageDir;
	
	/** 服务器类型, 本地类型, 其他类型, com.xfd.attach.server.server.model.enums.ServerTypeEnum */
	@TableField("server_type")
	private Integer serverType;
	
	/** 后续扩展使用, json格式 */
	@TableField("props")
	private String props;
	
	
}
