package com.yang.bishe.service.interfaces;

import java.util.List;

import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.Role;
import com.yang.bishe.service.base.IBaseService;

public interface IOperationService extends IBaseService<Operation> {
	/**
	 * 
	 * @param roleId
	 * @return 角色拥有的菜单（注意根据操作id排序，防止每次加载时菜单顺序不同）
	 */
	List<Operation> findRoleMenu(Role role);

	List<Operation> findRoleMenuAndFunction(Role role);

	List<Operation> findRoleOperation(int rowId);

}
