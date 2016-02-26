package com.yang.bishe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.LoseBookInfo;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBorrowInfoService;
import com.yang.bishe.service.interfaces.ILoseBookInfoService;
import com.yang.bishe.service.interfaces.IReaderService;

@Controller
@RequestMapping("/loseInfo")
public class LoseBookInfoController extends BaseController{
	@Autowired 
	private ILoseBookInfoService loseBookInfoService;
	@Autowired 
	private IBorrowInfoService borrowInfoService;
	@Autowired 
	private IReaderService readerService;
	
	@RequestMapping("/goListAllLose")
	  public ModelAndView goListAllLose(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		return new ModelAndView("book/loseBookList");
	}
	@RequestMapping("/lose")
	  public void loseBook(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		Json json=new Json();
		try{
				Float fine=Float.valueOf(request.getParameter("fine"));
				String bookBarCode=request.getParameter("booksBarCode");
				String readerId=request.getParameter("readerId");
				User user=(User)request.getSession(false).getAttribute("sessionInfo");//从session中获取操作员信息
				loseBookInfoService.setLoseBook(fine,bookBarCode,user,readerId);
				json.setMsg("赔偿成功");
				json.setSuccess(true);
			}catch (Exception e){
				json.setMsg("赔偿失败"+e.getMessage());
			}
			writeJson(json,response);
		}
	
	  @RequestMapping("listAll")
	   public void listReader(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
//		System.out.println(queryBookName);
		Grid grid = new Grid();
		String hql="from LoseBookInfo";
		long totalNum;
		List<LoseBookInfo>loseBookInfos;
		loseBookInfos=(List<LoseBookInfo>)loseBookInfoService.find(hql, page, rows);
		totalNum=readerService.count("select count(*)"+hql);
		grid.setTotal(totalNum);
		grid.setRows(loseBookInfos);
		writeJson(grid,response);
	    }
}
