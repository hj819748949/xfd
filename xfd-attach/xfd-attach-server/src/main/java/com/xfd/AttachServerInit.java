package com.xfd;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 附件server初始化
 * @author xfd
 *
 * 2020年10月31日
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xfd.**.dao")
public class AttachServerInit
{
	public static void main(String[] args)
	{
		SpringApplication.run(AttachServerInit.class, args);
	}
}
