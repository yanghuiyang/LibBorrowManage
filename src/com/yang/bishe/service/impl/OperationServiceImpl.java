package com.yang.bishe.service.impl;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.Role;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IOperationService;
@Service
public class OperationServiceImpl extends BaseServiceImpl<Operation> implements IOperationService {
	@Autowired
	private IBaseDao<Operation> operationDao;
	@Autowired
	private IBaseDao<Role> roleDao;
	@Override
	public List<Operation> findRoleMenu(Role role) {
		// TODO Auto-generated method stub
		Set<Operation> operation = role.getOpeations();
		List<Operation> menu = new ArrayList<Operation>() ;
		for(Operation o:operation){
			if(o.getMenuOrFunction().equals("menu")){
				menu.add(o);
			}
		}
		 Collections.sort(menu,new Comparator<Operation>(){  
	            public int compare(Operation arg0, Operation arg1) {  
	                return arg0.getId().compareTo(arg1.getId());  
	            }  
	        });  
		return menu;
	}

	@Override
	public List<Operation> findRoleMenuAndFunction(Role role) {
		// TODO Auto-generated method stub
		Set<Operation> operation = role.getOpeations();
		List<Operation> all = new ArrayList<Operation>() ;
		all.addAll(operation);
		 Collections.sort(all,new Comparator<Operation>(){  
	            public int compare(Operation arg0, Operation arg1) {  
	                return arg0.getId().compareTo(arg1.getId());  
	            }  
	        });  
		return all;
	}

	@Override
	public List<Operation> findRoleOperation(int roleId) {
		// TODO Auto-generated method stub
		Role role=roleDao.getById(Role.class, roleId);
		return findRoleMenuAndFunction(role);
		}
}
