package com.yang.bishe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IUserService;
import com.yang.bishe.util.BeanUtils;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private IUserService userService;
	@RequestMapping("/list")
	   public ModelAndView goList(){
		  return new ModelAndView("user/list");
	    }
	@RequestMapping("/listUsers")
	  public void listUser(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
		String queryUserId=request.getParameter("queryUserId");//获取要查询的用户账号
		Grid grid = new Grid();
		String hql;
		List<User>users;
		if(queryUserId!=null)
		{
			hql="from User as user where user.UserId like  '%"+queryUserId+"%'";
		}else{
			hql="from User";
		}
		users=(List<User>)userService.find(hql, page, rows);
		grid.setTotal(userService.count("select count(*)"+hql));
		grid.setRows(users);
		writeJson(grid,response);
	    }
	
	@RequestMapping("/addUser")
	 public void addUser(User user,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		if(userService.getById(user.getUserId())!=null){
			json.setMsg("新建用户失败，用户已存在！");
		}else{
			userService.save(user);
			json.setMsg("新建用户成功！");
			json.setSuccess(true);
		}
		writeJson(json,response);
		}
	@RequestMapping("/removeUser")
	 public void removeUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		String userId=request.getParameter("id");
		try{
			userService.delete((User)userService.getById(userId));//不懂为啥要加上（User）才对。。
			json.setMsg("删除成功！");
			json.setSuccess(true);
			
		}catch (Exception e){
			json.setMsg("删除失败！"+e.getMessage());
		}
		writeJson(json,response);
	
		}
	@RequestMapping("/updateUser")
	 public void updateUser(User user,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		User dbUser=userService.getById(user.getUserId());
		BeanUtils.copyNotNullProperties(user, dbUser); //这样前台不用传完整的user属性了。如角色id
		try{
				userService.update(dbUser);
				json.setMsg("更新成功！");
				json.setSuccess(true);
		}catch(Exception e){
			json.setMsg("更新失败！"+e.getMessage());
		
		}
		writeJson(json,response);
	}
	@RequestMapping("/updatePwd")
	 public void updatePwd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		String PassWord=request.getParameter("passWord");
		User user=(User)request.getSession(false).getAttribute("sessionInfo");
		user.setPassWord(PassWord);
		try{
				userService.update(user);
				json.setMsg("修改成功！");
				json.setSuccess(true);
		}catch(Exception e){
			json.setMsg("修改失败！"+e.getMessage());
		
		}
		writeJson(json,response);
	}
	
	@RequestMapping("/grantRole")
	 public void grantRole(HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		String userId=request.getParameter("userId");
		String roleId=request.getParameter("role.roleId");
		userService.grantRole(userId,roleId);
		json.setSuccess(true);
		json.setMsg("角色设置成功");
		writeJson(json,response);
	}
	
}
