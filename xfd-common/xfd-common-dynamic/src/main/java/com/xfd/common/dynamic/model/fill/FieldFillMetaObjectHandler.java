package com.xfd.common.dynamic.model.fill;

import java.util.Date;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 属性填充
 * @author xfd
 *
 * 2020年10月31日
 */
@Slf4j
@Component
public class FieldFillMetaObjectHandler implements MetaObjectHandler
{
	@Override
	public void insertFill(MetaObject metaObject)
	{
		log.debug("start insertFill ...");
		this.setFieldValByName("createDate", new Date(), metaObject);
		this.setFieldValByName("modifyDate", new Date(), metaObject);
	}
	@Override
	public void updateFill(MetaObject metaObject)
	{
		log.debug("start updateFill ...");
		this.setFieldValByName("modifyDate", new Date(), metaObject);
	}
}
