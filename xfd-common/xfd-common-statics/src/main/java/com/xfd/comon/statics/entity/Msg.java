package com.xfd.comon.statics.entity;

import java.io.Serializable;

/**
 * 返回值实体
 * @author xfd
 *
 * 2020年10月31日
 */
public class Msg implements Serializable
{
	private static final long serialVersionUID = -7750382441427041368L;
	
	/** 0表示成成功, 其他表示失败. */
	private int code;
	
	/** 数据 */
	private Object data;
	
	public Msg()
	{
		
	}
	public Msg(int code, Object data)
	{
		this.code = code;
		this.data = data;
	}
	public static Msg getSuccess(Object data)
	{
		return new Msg(0, data);
	}
	public static Msg getError(Object error)
	{
		return new Msg(-1, error);
	}
	public int getCode()
	{
		return code;
	}
	public Object getData()
	{
		return data;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public void setData(Object data)
	{
		this.data = data;
	}
}
