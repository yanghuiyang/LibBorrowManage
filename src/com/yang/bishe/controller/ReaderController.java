package com.yang.bishe.controller;
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
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IReaderService;
@Controller
@RequestMapping("/reader")
public class ReaderController extends BaseController {
	@Autowired
	private IReaderService readerService;
	@RequestMapping("/goManageReader")
	public ModelAndView goManageReader(){
		return new ModelAndView("reader/manageReader");
	}
	
	@RequestMapping("/goPayFine")
	public ModelAndView goPayFine(){
		return new ModelAndView("reader/payFine");
	}
	@RequestMapping("/goReaderStatistics")
	public ModelAndView goReaderStatistics(){
		return new ModelAndView("reader/readerStatistics");
	}
	
	
	/**
	 * 用于借阅时，查找设置读者。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSingleReader")
	  public String findSingleReader(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		String queryReaderId=request.getParameter("queryReaderId");
		Map<String, Object> m = new HashMap<String, Object>();
	    Reader reader=readerService.getById(queryReaderId);
	//    System.out.println(reader.getReaderName());
		if(reader!=null){	  
			m.put("reader", reader);
			m.put("success", true);
		}else{
			m.put("success", false);
			m.put("msg", "读者不存在！");
		}
		writeJson(m,response);
		return null;
	    }

	/**
	 * 用于管理员查询和添加读者
	 * @param request
	 * @param response
	 * @return  读者基本信息列表
	 * @throws Exception
	 */
  @RequestMapping("listReader")
   public String listReader(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
	rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
	String queryReaderId=request.getParameter("queryReaderId");//获取要查询的用户账号
//	System.out.println(queryBookName);
	Grid grid = new Grid();
	String hql=null;
	long totalNum;
	List<Reader>readers;
	if(queryReaderId!=null)
	{
		hql="from Reader as t where t.ReaderId="+queryReaderId;
	}else{
		hql="from Reader";
	}
		readers=(List<Reader>)readerService.find(hql, page, rows);
	//	grid.setTotal(bookService.count("select count(*) from Book"));
		//还得遍历每一本书，查询馆藏数和可借数
	//	books=bookService.caculateBookNum(books);
		totalNum=readerService.count("select count(*)"+hql);
		grid.setTotal(totalNum);
		grid.setRows(readers);
		writeJson(grid,response);
		return null;
    }
	/**
	 * 管理员添加读者
	 * @param book
	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
	 */
   @RequestMapping("/addReader")
	 public String addReader(Reader reader,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			if(readerService.isEmptyById(reader.getReaderId()))
			{
				readerService.save(reader);
				json.setMsg("添加读者成功！");
				json.setSuccess(true);
			}else{
				json.setMsg("读者已存在");
			}
		writeJson(json,response);
		return null;
		}
   @RequestMapping("/updateReader")
	 public String updateReader(Reader reader,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		try{
				readerService.update(reader);
				json.setMsg("更新成功！");
				json.setSuccess(true);
		}catch(Exception e){
			json.setMsg("更新失败！"+e.getMessage());
		
		}
		writeJson(json,response);
		return null;
	}
   @RequestMapping("/removeReader")
	 public String removeReader(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Json json = new Json();//用于向前端发送消息
		String readerId=request.getParameter("id");
		try{
			readerService.delete((Reader)readerService.getById(readerId));
			json.setMsg("删除成功！");
			json.setSuccess(true);
			
		}catch (Exception e){
			json.setMsg("删除失败！"+e.getMessage());
			System.out.println(e.getMessage());
		}
		writeJson(json,response);
		return null;
	
		}
	
}
