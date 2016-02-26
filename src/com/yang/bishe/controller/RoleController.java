package com.yang.bishe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Role;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IRoleService;
import com.yang.bishe.util.BeanUtils;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	@Autowired
	private IRoleService roleService;
	
	 @RequestMapping("/goRoleManage")
	 public ModelAndView goList(){
		 return new ModelAndView("sys/roleManage");
	    }
	
	  @RequestMapping("/getRole")
	  public void getRole(HttpServletRequest request,HttpServletResponse response){
			List <Role>roles=roleService.find("from Role");
			writeJson(roles,response);
		}
	  @RequestMapping("/goRoleForm")
	  public ModelAndView goRoleForm(HttpServletRequest request,HttpServletResponse response){
	//		ModelAndView  mav=new ModelAndView("sys/roleGrant");
	//		request.setAttribute("roleId", request.getQueryString());
			ModelMap mmap = new ModelMap();  
			mmap.addAttribute("roleId", request.getQueryString());
			return  new ModelAndView("sys/roleForm", mmap);
		}
	  @RequestMapping("/goRoleGrant")
	  public ModelAndView goRoleGrant(HttpServletRequest request,HttpServletResponse response){
	//		ModelAndView  mav=new ModelAndView("sys/roleGrant");
	//		request.setAttribute("roleId", request.getQueryString());
			ModelMap mmap = new ModelMap();  
			mmap.addAttribute("roleId", request.getQueryString());
			return  new ModelAndView("sys/roleGrant", mmap);
		}
	  
	  @RequestMapping("/grantRole")
	  public void grantRole(HttpServletRequest request,HttpServletResponse response){
		  int roleId =  Integer.valueOf(request.getParameter("roleId")) ;
		  String ids=request.getParameter("ids");
		  System.out.println(ids);
		  roleService.grant(roleId,ids);
		  Json json = new Json();
		  json.setSuccess(true);
		  writeJson(json,response);
		}
		
		  @RequestMapping("/getRoleById")
		  public void getRoleById(HttpServletRequest request,HttpServletResponse response){
			  int roleId =  Integer.valueOf(request.getParameter("roleId")) ;
			  Role role=roleService.getById(roleId);
			  writeJson(role,response);
			}
			
			@RequestMapping("/savaRole")
			 public void addUser(Role role,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
				Json json = new Json();//用于向前端发送消息
				try{
					roleService.save(role);
					json.setMsg("新建角色成功！");
					json.setSuccess(true);
				}catch (Exception e){
					json.setMsg("新建角色失败！"+e.getMessage());
				}
				writeJson(json,response);  
			}
			
			@RequestMapping("/updateRole")
			 public void updateRole(Role role,HttpServletResponse response) throws Exception{
				Json json = new Json();//用于向前端发送消息
				Role dbRole=roleService.getById(role.getRoleId());
				BeanUtils.copyNotNullProperties(role, dbRole); 
				try{
					roleService.update(dbRole);
						json.setMsg("更新成功！");
						json.setSuccess(true);
				}catch(Exception e){
					json.setMsg("更新失败！"+e.getMessage());
				
				}
				writeJson(json,response);	
			}		
			
	@RequestMapping("/deleteRole")
	 public void deleteRole(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		String roleId=request.getParameter("roleId");
		try{
			roleService.delete((Role)roleService.getById(roleId));
			json.setMsg("删除成功！");
			json.setSuccess(true);
		}catch (Exception e){
			json.setMsg("删除失败！"+e.getMessage());
		}
		writeJson(json,response);
		}
}
