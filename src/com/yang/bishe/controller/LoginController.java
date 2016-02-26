package com.yang.bishe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IUserService;

@Controller
public class LoginController extends BaseController{
	@Autowired
	private IUserService userService;
		@RequestMapping(value="/index")
	  	public ModelAndView goIndex(HttpServletRequest request){
			 Object obj = request.getSession().getAttribute("sessionInfo");
				if(obj!=null)
					return new ModelAndView("home2");
			return new ModelAndView("index");
	  	}
		@RequestMapping(value="/home")
		public ModelAndView login(User user,HttpServletRequest request){
			 Object obj = request.getSession().getAttribute("sessionInfo");
			if(obj!=null)
				return new ModelAndView("home2");
			if(user.getUserId()==null)
				return new ModelAndView("index");
			User dbUser= userService.getById(user.getUserId());
			ModelAndView  mav=new ModelAndView("index");
			if(dbUser==null){
				mav.addObject("errorMsg","用户名错误");
			}else if(!dbUser.getPassWord().equals(user.getPassWord())){
				mav.addObject("errorMsg","密码错误");
			}else if(dbUser.getEnable()==0){
				mav.addObject("errorMsg","账号停用");
			}else{
				request.getSession(true).setAttribute("sessionInfo", dbUser);
				mav.setViewName("home2");
			}
			return mav;

		}
		@RequestMapping(value="/loginOut")
		public void loginOut(HttpServletRequest request,
				HttpServletResponse response){
			if (request.getSession() != null) {
				request.getSession().invalidate();
			}
			Json j = new Json();
			j.setSuccess(true);
			writeJson(j,response);
		}
		@RequestMapping(value="/test")
		public ModelAndView test(HttpServletRequest request,
				HttpServletResponse response){
			
			  return new ModelAndView("home2");
		}
}
		
		

