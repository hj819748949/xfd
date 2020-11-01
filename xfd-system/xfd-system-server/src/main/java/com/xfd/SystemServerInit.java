package com.xfd;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统服务端启动类
 * @author xfd
 *
 * 2020年10月30日
 */
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.xfd.**.dao")
public class SystemServerInit
{
	public static void main(String[] args)
	{
		SpringApplication.run(SystemServerInit.class, args);
	}
}
