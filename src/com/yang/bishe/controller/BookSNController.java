package com.yang.bishe.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BookSN;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBookSNService;
import com.yang.bishe.service.interfaces.IBookService;

@Controller
@RequestMapping("/booksn")
public class BookSNController extends BaseController{
	@Autowired
	private IBookSNService booksnService;
	@Autowired
	private IBookService bookService;
	
	@RequestMapping("/goAddBookSN")
	public ModelAndView go(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		return new ModelAndView("book/addBookSN");
	}
	
	@RequestMapping("/addBookSN")
	public void addBookSN(BookSN booksn,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Json json = new Json();//用于向前端发送消息
		if(booksnService.getById(booksn.getBooksBarCode()) != null){
			json.setMsg("添加失败，条码已存在");
		}else{
				booksnService.save(booksn);
				json.setMsg("关联成功！");
				json.setSuccess(true);
			}
		writeJson(json,response);
	}
	
	@RequestMapping("/findBookBySN")
	public String findBookBySN(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				String booksBarCode= request.getParameter("booksBarCode");
				//这里做条形码找不到情况的判断及处理。
				BookSN bookSN=(BookSN)booksnService.getById(booksBarCode);
				if(bookSN!=null){
					Book book=bookSN.getBook();
					request.setAttribute("bookInfo", book);
					return "book/bookForm";
				}else
					return "book/bookNotFound";
	}
	  @RequestMapping("/updateBookSN")
		 public void removeBookSN(BookSN booksn,HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			try{
					booksnService.update(booksn);
					json.setMsg("更新成功！");
					json.setSuccess(true);
			}catch(Exception e){
				json.setMsg("更新失败！"+e.getMessage());
			
			}
			writeJson(json,response);
		}
	  	@RequestMapping("/removeBookSN")
		 public void updateBook(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			String booksBarCode=request.getParameter("booksBarCode");
			BookSN sn=booksnService.getById(booksBarCode);
			if(sn.getBookState()==3){
				json.setMsg("删除失败，书籍借出不能删除！");
			}else{
				try{
						booksnService.delete(sn);
						json.setMsg("删除成功！");
						json.setSuccess(true);
				}catch(Exception e){
					json.setMsg("删除失败！"+e.getMessage());
				}
			}
			writeJson(json,response);
		}

}
