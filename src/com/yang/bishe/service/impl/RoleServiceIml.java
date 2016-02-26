package com.yang.bishe.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.Role;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IRoleService;
@Service
public class RoleServiceIml  extends BaseServiceImpl<Role> implements IRoleService {
	@Autowired
	private IBaseDao<Role> roleDao;
	@Autowired
	private IBaseDao<Operation> operationDao;
	@Override
	public void grant(int roleId, String ids) {
		// TODO Auto-generated method stub
		Role role=roleDao.getById(Role.class, roleId);
		if(role!=null){
			role.setOpeations(new HashSet<Operation>());
			for(String operationId: ids.split(",")){
				if (!StringUtils.isBlank(operationId)){
					Operation o=operationDao.getById(Operation.class, operationId);
					if(o!=null){
						role.getOpeations().add(o);
					}
				}
			}
		}
	}
	
}
