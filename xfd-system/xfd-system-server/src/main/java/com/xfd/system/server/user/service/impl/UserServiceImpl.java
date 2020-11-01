package com.xfd.system.server.user.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.xfd.system.server.user.dao.UserDao;
import com.xfd.system.server.user.model.UserDO;
import com.xfd.system.server.user.service.UserService;

/**
 * 用户业务实现
 * @author xfd
 *
 * 2020年10月31日
 */
@Service
@Transactional
public class UserServiceImpl implements UserService
{
	@Resource
	private UserDao userDao;
	
	@PostConstruct
	public void init() 
	{
		UserDO userDO = new UserDO();
		userDO.setDelFlag(1);
		this.userDao.insert(userDO);
		System.out.println(this.userDao);
		System.out.println(this.userDao);
		
	}
}
