package com.yang.bishe.service.interfaces;

import com.yang.bishe.entity.Role;
import com.yang.bishe.service.base.IBaseService;

public interface IRoleService extends IBaseService<Role>{

	void grant(int roleId, String ids);

}
