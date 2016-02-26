package com.yang.bishe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Role;
import com.yang.bishe.entity.User;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IUserService;
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {
	@Autowired
	private IBaseDao<User> userDao;
	@Autowired
	private IBaseDao<Role> roleDao;
	@Override
	public void grantRole(String userId, String roleId) {
		User user=userDao.getById(User.class,userId);
		user.setRole(roleDao.getById(Role.class, Integer.valueOf(roleId)));
		}
}
