package com.xfd.attach.client.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.xfd.comon.statics.model.AbstractVO;

/**
 * 结果集返回
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class AttachInfoVO extends AbstractVO
{
	private static final long serialVersionUID = 5515069727352184742L;
	
	/** 文件真实名称 */
	private String fileName;
	
	/** 文件的mime类型 */
	private String mimeType;
	
	/** 文件大小 */
	private Long fileSize; 
	
	/** 组id, 一次传多个, 组id会一样 */
	private String groupId;
	
	/** 存储的路径 */
	private String storagePath;
	
	/** 服务器id */
	private Long serverId;
}
