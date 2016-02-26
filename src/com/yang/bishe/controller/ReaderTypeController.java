package com.yang.bishe.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.ReaderType;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IReaderTypeService;

@Controller
@RequestMapping("/readerType")
public class ReaderTypeController extends BaseController{
	@Autowired
	private IReaderTypeService readerTypeService;
/**
 * 用于读者设置
 * @param request
 * @param response
 */
	@RequestMapping("/getReaderType")
	public void getReaderType(HttpServletRequest request,HttpServletResponse response){
		List <ReaderType>readerTypes=readerTypeService.find("from ReaderType");
		writeJson(readerTypes,response);
	}
	  
	 @RequestMapping("/listReaderType")
	 public String listReaderType(HttpServletRequest request,
			   HttpServletResponse response)throws Exception{
		    page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
			rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
			String queryReaderTypeName=request.getParameter("queryReaderTypeName");//获取要查询的用户账号
		//	System.out.println(queryBookName);
			Grid grid = new Grid();
			String hql=null;
			long totalNum;
			List<ReaderType>readerTypes;
			if(queryReaderTypeName!=null)
			{
				hql="from ReaderType as t where t.ReaderTypeName like  '%"+queryReaderTypeName+"%'";
			}else{
				hql="from ReaderType";
			}
				readerTypes=(List<ReaderType>)readerTypeService.find(hql, page, rows);
				totalNum=readerTypeService.count("select count(*)"+hql);
				grid.setTotal(totalNum);
				grid.setRows(readerTypes);
				writeJson(grid,response);
				return null;
	   }
	
	 @RequestMapping("/addReaderType")
	 public String addReaderType(ReaderType readerType,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		//判断是否存在
			readerTypeService.save(readerType);
			json.setMsg("添加类型成功！");
			json.setSuccess(true);
	//	}
		writeJson(json,response);
		return null;
		}
   @RequestMapping("/updateReaderType")
	 public String updateReaderType(ReaderType readerType,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		try{
			readerTypeService.update(readerType);
				json.setMsg("更新成功！");
				json.setSuccess(true);
		}catch(Exception e){
			json.setMsg("更新失败！"+e.getMessage());
		
		}
		writeJson(json,response);
		return null;
   }
		@RequestMapping("/goReaderType")
		 public ModelAndView goAddBook(){
				return new ModelAndView("reader/readerType");
		    }
		@RequestMapping("/goSysParameter")
		 public ModelAndView goSysParameter(){
				return new ModelAndView("sys/sysParameter");
		    }
	/**	
	 * 读者分类统计
	 * @param request
	 * @param response
	 * @throws Exception
	 */
		@RequestMapping("/statistics")
		  public void statistics(HttpServletRequest request,
					HttpServletResponse response) throws Exception {
			
			if(request.getParameter("readerTypeId")!=""){
				int readerTypeId=Integer.parseInt(request.getParameter("readerTypeId"));
				Map<String, String> dateGrid = new HashMap<String, String>();
				dateGrid=readerTypeService.getStatistics(readerTypeId);
				List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
				temp.add(dateGrid);
				writeJson(temp,response);
				}
		}
		
}
