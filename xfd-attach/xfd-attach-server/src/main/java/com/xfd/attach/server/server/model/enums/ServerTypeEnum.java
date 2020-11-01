package com.xfd.attach.server.server.model.enums;

/**
 * 服务器类型枚举
 * @author xfd
 *
 * 2020年10月31日
 */
public enum ServerTypeEnum
{
	LOCAL(0, "本地存储"),
	
	;
	
	/** 类型 */
	private int type;
	
	/** 描述 */
	private String description;
	
	ServerTypeEnum(int type, String description) 
	{
		this.type = type;
		this.description = description;
	}
	public int getType()
	{
		return type;
	}
	public String getDescription()
	{
		return description;
	}
	/**
	 * 根据类型查找
	 * @param type
	 * @return
	 */
	public static ServerTypeEnum findByType(int type) 
	{
		for (ServerTypeEnum ste : values()) 
		{
			if (ste.getType() == type) 
			{
				return ste;
			}
		}
		return null;
	}
}
