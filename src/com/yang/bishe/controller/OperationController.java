package com.yang.bishe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Operation;
import com.yang.bishe.entity.Role;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Tree;
import com.yang.bishe.service.interfaces.IOperationService;
import com.yang.bishe.util.BeanUtils;

@Controller
@RequestMapping("/operation")
public class OperationController extends BaseController {
	@Autowired
	private IOperationService operationService;
//	@	RequestMapping("/go")
//	private ModelAndView go(HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//	
//		return new ModelAndView("home2");
//	}
	/**
	 * 显示左边导航菜单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@	RequestMapping("/getHomeMenu")
	private  void getHomeMenu(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Tree> tree=new ArrayList<Tree>();
		User sessionInfo =  (User)request.getSession().getAttribute("sessionInfo");
		Role role=sessionInfo.getRole();
		List<Operation> op=operationService.findRoleMenu(role);
		//List<Operation> op =(List<Operation>) operationService.find("from Operation where menuOrFunction="+"'menu'");
		for(Operation oper:op){
			Tree node = new Tree();
			BeanUtils.copyNotNullProperties(oper, node);
			Map<String, String> attributes = new HashMap<String, String>();
			attributes.put("url", oper.getUrl());
			attributes.put("target", oper.getTarget());
			node.setAttributes(attributes);
			tree.add(node);
		}
		writeJson(tree,response);
	}
	
	/**
	 * 用于显示点击授权按钮 显示资源树
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@	RequestMapping("/getOperationTree")
	private  void getOperationTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	//	User sessionInfo =  (User)request.getSession().getAttribute("sessionInfo");
	//	Role role=sessionInfo.getRole();
	//	List<Operation> op=operationService.findRoleMenuAndFunction(role);
		List<Operation> op=operationService.find("from Operation");

		writeJson(op,response);
	}
	
	@	RequestMapping("/getRoleOperation")
	private  void getRoleOperation(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int roleId =  Integer.valueOf(request.getParameter("roleId")) ;
		List<Operation> op=operationService.findRoleOperation(roleId);
		writeJson(op,response);
	}
	
}
