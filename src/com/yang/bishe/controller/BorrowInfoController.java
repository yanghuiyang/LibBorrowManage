package com.yang.bishe.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BorrowInfo;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.BorrowInfoForm;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBookSNService;
import com.yang.bishe.service.interfaces.IBookService;
import com.yang.bishe.service.interfaces.IBorrowInfoService;
import com.yang.bishe.service.interfaces.ILoseBookInfoService;
import com.yang.bishe.service.interfaces.IReaderService;
@Controller
@RequestMapping("/borrowInfo")
public class BorrowInfoController extends BaseController {
	@Autowired 
	private IBorrowInfoService borrowInfoService;
	@Autowired 
	private IReaderService readerService;
	/**
	 * 读者当前借阅书列表
	 */
	@RequestMapping("/listReaderNowBorrow")
	  public String listReaderNowBorrow(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		//这里分页没弄。可以吧borrowInfoForm当成总集合，然后用for输出指定数量的结果。（利用List有序的）
		page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
		String readerId=request.getParameter("readerId");//获取要查询的读者账号
		if(readerId!=null){
			if(!readerService.isEmptyById(readerId)){
				List<BorrowInfoForm> borrowInfoForm=borrowInfoService.findReaderNowBorrow(readerId);//拼凑表单
				Grid grid = new Grid();
				grid.setTotal((long)borrowInfoForm.size());
				grid.setRows(borrowInfoForm);
				writeJson(grid,response);
			}
		}
		return null;
	}
	/**
	 *借书  
	 */
	@RequestMapping("/borrow")
	  public void borrow(HttpServletRequest request,HttpServletResponse response) throws Exception {
			Json json=new Json();
			try{
					String readerId=request.getParameter("readerId");//获取要查询的读者账号
					String booksBarCode=request.getParameter("booksBarCode");//图书条码
					User user=(User)request.getSession(false).getAttribute("sessionInfo");//从session中获取操作员信息
					String temp = borrowInfoService.saveBorrowInfo(readerId,booksBarCode,user);//设置借阅表属性
					if(temp.equals("借阅成功")){
						json.setMsg(temp);
						json.setSuccess(true);
					}else{
						json.setMsg(temp);
					}
			}catch (Exception e){
				e.printStackTrace();
				json.setMsg("借阅失败！"+e.getMessage());
			}
			writeJson(json,response);
	}
	@RequestMapping("/return")
	  public String returnBook(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Json json=new Json();
			try{
				String booksBarCode=request.getParameter("booksBarCode");//图书条码
				User user=(User)request.getSession(false).getAttribute("sessionInfo");//从session中获取操作员信息
				String temp = borrowInfoService.saveReturnInfo(booksBarCode,user);//设置借阅表属性
				if(temp.equals("归还失败，书已经归还")){
					json.setMsg(temp);//这里回传读者账号；
				}else if(temp.equals("归还失败")){
					json.setMsg(temp);
				}else{
					json.setMsg(temp);//这里回传读者账号；
						json.setSuccess(true);
				}
			}catch (Exception e){
				json.setMsg("归还失败！"+e.getMessage());
			}
			writeJson(json,response);
			return null;
	}
	@RequestMapping("/renew")
	  public String renewBook(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Json json=new Json();
			try{
				String booksBarCode=request.getParameter("booksBarCode");//图书条码
				User user=(User)request.getSession(false).getAttribute("sessionInfo");//从session中获取操作员信息
				String temp = borrowInfoService.saveReNewInfo(booksBarCode,user);//设置借阅表属性
				if(temp.startsWith("A")){
					json.setMsg("续借失败！！图书超期，不能续借");
				}else if(temp.startsWith("B")){
					json.setMsg("续借失败！！图书续借次数超过最多续借次数");
				}else{
					json.setMsg(temp);//这里回传读者账号；
					json.setSuccess(true);
				}
			}catch (Exception e){
				json.setMsg("续借失败！"+e.getMessage());
			}
			writeJson(json,response);
			return null;
	}
	/**
	 * 列出读者还未缴纳罚款的书及罚金
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/listReaderAllFine")
	  public void listReaderAllFine(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		//这里不要分页，欠费的书一般数目不会很多
		String readerId=request.getParameter("readerId");//获取要查询的读者账号
		if(readerId!=null){
			//Reader reader=(Reader)readerService.getById(readerId);
			if(!readerService.isEmptyById(readerId)){
				List<BorrowInfoForm> borrowInfoForm=borrowInfoService.findReaderNowAllFine(readerId);//拼凑表单
				Grid grid = new Grid();
				grid.setTotal((long)borrowInfoForm.size());
				grid.setRows(borrowInfoForm);
				writeJson(grid,response);
			}
		}
	}
	/**
	 * 读者缴纳罚款，清空该读者借阅信息表中所有的fine
	 */
	@RequestMapping("/payFine")
	  public void payFine(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		String readerId=request.getParameter("readerId");//获取要查询的读者账号
		String temp=borrowInfoService.setPayFine(readerId);
		Json json=new Json();
		if(temp.equals("sucess")){
			json.setSuccess(true);
			json.setMsg("缴纳成功！");
		}else{
			json.setMsg("缴纳失败！"+temp);
		}
	}
	/**
	 * 统计,分类统计和总排行
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/statistics")
	  public void statistics(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		String bookTypeId=request.getParameter("bookTypeId");
		String overall=request.getParameter("overall");
		String startDate=request.getParameter("startDate");//service再判断是否有没有选择了日期区间吧
		String endDate=request.getParameter("endDate");
		List<Book>books=null;
		if(overall!=null){
			books=borrowInfoService.getOverall(startDate,endDate);
		}
		if(bookTypeId!=null){
			books=borrowInfoService.getBookTypeOverall(bookTypeId,startDate,endDate);
		}
		writeJson(books,response);
	}
}
