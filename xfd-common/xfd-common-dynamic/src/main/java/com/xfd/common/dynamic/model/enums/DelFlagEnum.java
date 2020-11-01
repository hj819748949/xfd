package com.xfd.common.dynamic.model.enums;

/**
 * 删除枚举
 * @author xfd
 *
 * 2020年10月31日
 */
public enum DelFlagEnum
{
	NORMAL(0, "正常"),
	
	DELETED(1, "已删除"),
	
	;
	
	/** 标识 */
	private int flag;
	
	/** 描述 */
	private String description;

    DelFlagEnum(int flag, String description)
	{
		this.flag = flag;
		this.description = description;
	}
	public int getFlag()
	{
		return flag;
	}
	public String getDescription()
	{
		return description;
	}
}
