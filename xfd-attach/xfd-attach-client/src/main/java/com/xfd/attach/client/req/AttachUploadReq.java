package com.xfd.attach.client.req;

import java.io.Serializable;
import lombok.Data;

/**
 * 附件上传请求
 * @author xfd
 *
 * 2020年10月31日
 */
@Data
public class AttachUploadReq implements Serializable
{
	private static final long serialVersionUID = 2498745137242732551L;
	
	/** 文件真实名称 */
	private String fileName;
	
	/** 文件的mime类型 */
	private String mimeType;
	
	/** 文件大小 */
	private Long fileSize; 
	
	/** 组id, 一次传多个, 组id会一样 */
	private String groupId;
	
	/** 文件数据 */
	private byte[] fileData;
	
}
