package com.xfd.attach.server.attach.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xfd.common.dynamic.model.AbstractDO;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;

/**
 * 附件信息DO
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
@EqualsAndHashCode(callSuper=false)
@TableName("xfd_attachinfo")
public class AttachInfoDO extends AbstractDO
{
	private static final long serialVersionUID = -6250783269810467651L;
	
	/** 文件真实名称 */
	@TableField("file_name")
	private String fileName;
	
	/** 文件的mime类型 */
	@TableField("mime_type")
	private String mimeType;
	
	/** 文件大小 */
	@TableField("file_size")
	private Long fileSize; 
	
	/** 组id, 一次传多个, 组id会一样 */
	@TableField("gp_id")
	private String groupId;
	
	/** 存储的路径 */
	@TableField("storage_path")
	private String storagePath;
	
	/** 服务器id */
	@TableField("server_id")
	private Long serverId;
	
	
}
