package com.xfd.comon.statics.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 * @author xfd
 *
 * 2020年5月5日
 */
public class ReflectUtil
{
	private ReflectUtil () { }
	
	/**
	 * 查询对象的属性
	 * @param obj
	 * @return
	 */
	public static List<Field> findFields(Object obj) 
		throws Exception
	{
		List<Field> fields = new ArrayList<Field>();
		Class<?> curClass = obj.getClass();
		while (curClass != null) 
		{
			Field[] fieldsArr = curClass.getDeclaredFields();
			for (int i = 0; fieldsArr != null && i < fieldsArr.length; i++)
			{
				Field curField = fieldsArr[i];
				// 静态属性一般不是, 常量一般不会作为属性
				if (Modifier.isStatic(curField.getModifiers())
						|| Modifier.isFinal(curField.getModifiers()))
				{
					continue;
				}
				fields.add(curField);
			}
			curClass = curClass.getSuperclass();
		}
		return fields;
	} 
	/**
	 * 查找指定类型的属性
	 * @param obj
	 * @param types
	 * @return
	 */
	public static List<Field> findFields(Object obj, Class<?> ...types) 
		throws Exception
	{
		List<Field> fields = findFields(obj);
		if (CommonUtil.isEmpty(types))
		{
			return fields;
		}
		List<Field> result = new ArrayList<Field>();
		for (int i = 0; fields != null && i < fields.size(); i++)
		{
			Field curField = fields.get(i);
			boolean isInclude = false;
			for (int j = 0; j < types.length; j++)
			{
				Class<?> type = types[j];
				if (curField.getType() == type
						|| curField.getType().equals(type))
				{
					isInclude = true;
					break;
				}
			}
			if (isInclude)
			{
				result.add(curField);
			}
		}
		return result;
	}
	/**
	 * 查询属性的值
	 * @param obj
	 * @param field
	 * @return
	 */
	public static Object findFieldValue(Object obj, Field field)
		throws Exception
	{
		field.setAccessible(true);
		return field.get(obj);
	}
	/**
	 * 更新属性值
	 * @param obj
	 * @param field
	 * @param newVal
	 * @return
	 * @throws Exception
	 */
	public static void updateFieldValue(Object obj, Field field, Object newVal)
		throws Exception
	{
		field.setAccessible(true);
		field.set(obj, newVal);
	}
	/**
	 * 反射执行指定的方法
	 * @param obj        方法所在对象
	 * @param mthName    方法名称
	 * @param paramTypes 方法类型数组
	 * @param params     方法调用参数数组
	 * @return
	 * @throws Exception
	 */
	public static Object invokeMethod(Object obj, String mthName, Class<?>[] paramTypes, Object[] params) 
			throws Exception
	{
		Class<?> curClass = obj.getClass();
		Method mth = null;
		while (curClass != null)
		{
			try 
			{
				mth = curClass.getDeclaredMethod(mthName, paramTypes);
				break;
			} catch (Exception e)
			{
				curClass = curClass.getSuperclass();
			}
		}
		if (null == mth)
		{
			throw new NoSuchMethodException(mthName + "方法不存在.");
		}
		mth.setAccessible(true);
		return mth.invoke(obj, params);
	}
}
