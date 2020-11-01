package com.xfd.attach.server.attach.handler.ctx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import com.xfd.attach.server.attach.handler.FileServerHandler;

/**
 * 附件上下文
 * @author xfd
 *
 * 2020年10月31日
 */
@Component
public class FileServerHandlerContext implements BeanPostProcessor
{
	/** 文件适配器 */
	private final Map<Integer, FileServerHandler> serverHandlers = new ConcurrentHashMap<Integer, FileServerHandler>();
	
	/**
	 * 根据类型查找
	 * @param type
	 * @return
	 */
	public FileServerHandler findFileServerHandlerByType(Integer type) 
	{
		return this.serverHandlers.get(type);
	}
	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException
	{
		if (bean instanceof FileServerHandler) 
		{
			FileServerHandler fileServerHandler = (FileServerHandler)bean;
			serverHandlers.put(Integer.valueOf(fileServerHandler.getFileServerType()), fileServerHandler);
		}
		return bean;
	}
}
