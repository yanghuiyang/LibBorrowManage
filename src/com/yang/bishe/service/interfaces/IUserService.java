package com.yang.bishe.service.interfaces;

import com.yang.bishe.entity.User;
import com.yang.bishe.service.base.IBaseService;

public interface IUserService extends IBaseService<User>{

	void grantRole(String userId, String roleId);

}
