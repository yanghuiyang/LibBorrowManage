package com.yang.bishe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.BookLocation;
import com.yang.bishe.entity.BookType;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBookLocationService;

@Controller
@RequestMapping("/bookLocation")
public class BookLocationController extends BaseController {
	@Autowired IBookLocationService bookLocationService;
	
	/**
	 * 用于添加sn时候
	 * @param request
	 * @param response
	 * @return 返回所有馆藏地
	 * @throws Exception
	 */
	   @RequestMapping("/getBookLocation")
		 public  void getBookLocation(HttpServletRequest request,
					HttpServletResponse response) throws Exception{
			List<BookLocation> bookLocations=(List<BookLocation> )bookLocationService.find("from BookLocation");	
		   writeJson(bookLocations,response);
	   }
	
	   @RequestMapping("/goBookLocation")
	   public ModelAndView goBookLocation(){
		  return new ModelAndView("book/bookLocation");
	    }

	   @RequestMapping("/addBookLocation")
		 public void addBookLocation(BookLocation bookLocation,HttpServletRequest request,
					HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			//判断是否存在
				bookLocationService.save(bookLocation);
				json.setMsg("添加馆藏地成功！");
				json.setSuccess(true);
		//	}
			writeJson(json,response);
			}
	   @RequestMapping("/updateBookLocation")
		 public void updateBookLocation(BookLocation bookLocation,HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			try{
					bookLocationService.update(bookLocation);
					json.setMsg("更新成功！");
					json.setSuccess(true);
			}catch(Exception e){
				json.setMsg("更新失败！"+e.getMessage());
			
			}
			writeJson(json,response);
		}

}
