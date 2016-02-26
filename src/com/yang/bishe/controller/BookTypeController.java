package com.yang.bishe.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BookType;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBookService;
import com.yang.bishe.service.interfaces.IBookTypeService;

@Controller
@RequestMapping("/bookType")
public class BookTypeController extends BaseController {
	@Autowired
	private IBookTypeService bookTypeService;
	@Autowired
	private IBookService bookService;
	
	/**
	 * 
	 * 用于添加书籍时，选择书类型
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		   @RequestMapping("/getBookType")
			 public String getBookType(HttpServletRequest request,
						HttpServletResponse response) throws Exception{
				List<BookType> bookTypes=(List<BookType> )bookTypeService.find("from BookType");	
			   writeJson(bookTypes,response);
			   return null;
		   }
		   @RequestMapping("/listBookType")
		   public String listBookType(HttpServletRequest request,
				   HttpServletResponse response)throws Exception{
			   page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
				rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
				String queryBookTypeName=request.getParameter("queryBookTypeName");//获取要查询的用户账号
			//	System.out.println(queryBookName);
				Grid grid = new Grid();
				String hql=null;
				long totalNum;
				List<BookType>bookTypes;
				if(queryBookTypeName!=null)
				{
					hql="from BookType as t where t.BookTypeName like  '%"+queryBookTypeName+"%'";
				}else{
					hql="from BookType";
				}
					bookTypes=(List<BookType>)bookTypeService.find(hql, page, rows);
					totalNum=bookTypeService.count("select count(*)"+hql);
					grid.setTotal(totalNum);
					grid.setRows(bookTypes);
					writeJson(grid,response);
					return null;
		   }
		   @RequestMapping("/goBookType")
		   public ModelAndView goAddBook(){
			  return new ModelAndView("book/bookType");
		    }
			/**
			 * 管理员添加书籍类型
			 * @param book
			 * @param request
			 * @param response
			 * @return 
			 * @throws Exception
			 */
		   @RequestMapping("/addBookType")
			 public String addBookType(BookType bookType,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
				Json json = new Json();//用于向前端发送消息
				//判断是否存在
					bookTypeService.save(bookType);
					json.setMsg("添加书籍类型成功！");
					json.setSuccess(true);
			//	}
				writeJson(json,response);
				return null;
				}
		   @RequestMapping("/updateBookType")
			 public String updateBookType(BookType bookType,HttpServletResponse response) throws Exception{
				Json json = new Json();//用于向前端发送消息
				try{
						bookTypeService.update(bookType);
						json.setMsg("更新成功！");
						json.setSuccess(true);
				}catch(Exception e){
					json.setMsg("更新失败！"+e.getMessage());
				
				}
				writeJson(json,response);
				return null;
			}
		   @RequestMapping("/removeBookType")
			 public void removeBookType(HttpServletRequest request,HttpServletResponse response) throws Exception{
				Json json = new Json();//用于向前端发送消息
				String bookTypeId=request.getParameter("bookTypeId");
				BookType b=(BookType)bookTypeService.getById(Integer.valueOf(bookTypeId));
		//		bookService.count("from Book where Book.BookType="+b);
			//	if(b.getBooks().size()==0){
				if(0==bookService.count("select count(*) from Book where BookType='"+b+"'")){
					try{
						bookTypeService.delete(b);
						json.setMsg("删除成功！");
						json.setSuccess(true);
					}catch (Exception e){
						json.setMsg("删除失败！"+e.getMessage());
					}
				}else
					json.setMsg("删除失败！ 请先删除该类型下的书籍");
					
				writeJson(json,response);
			
				}
}
