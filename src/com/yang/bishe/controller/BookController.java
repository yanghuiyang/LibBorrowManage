package com.yang.bishe.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.ComplexSearchForm;
import com.yang.bishe.entity.easyui.Grid;
import com.yang.bishe.entity.easyui.Json;
import com.yang.bishe.service.interfaces.IBookService;
import com.yang.bishe.service.interfaces.IUserService;
import com.yang.bishe.util.BookXMLParser;
import com.yang.bishe.util.RetrieveDocumentByURL;

@Controller
@RequestMapping("/book")
public class BookController extends BaseController {
	@Autowired
	private IBookService bookService;
	@RequestMapping("/borrow")
	   public ModelAndView goBorrow(){
		  return new ModelAndView("book/borrowBook");
	    }
	@RequestMapping("/return")
	   public ModelAndView goReturn(){
		  return new ModelAndView("book/returnBook");
	    }
	@RequestMapping("/renew")
	   public ModelAndView goRenew(){
		  return new ModelAndView("book/renewBook");
	    }
	@RequestMapping("/goLoseBook")
		 public ModelAndView goLoseBook(){
			  return new ModelAndView("book/loseBook");
		   }
	  /*
	   * 书籍高级检索
	   */
	  @RequestMapping("/complexFind")
	   public void complexFind(ComplexSearchForm searchForm,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		  page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		  rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
		  Grid grid = new Grid();
		  List<Book>books;
		  String hql=bookService.getCompleSearchHQL(searchForm);
		  books=bookService.find(hql,searchForm, page, rows);
		  books=bookService.caculateBookNum(books);
		  grid.setTotal(bookService.count(hql,searchForm));
		  grid.setRows(books);
		  writeJson(grid,response);
	  }
	  
	/**
	 * 用于借阅的查询
	 * @param request
	 * @param response
	 * @return  书籍列表，包括馆藏数和可借数
	 * @throws Exception
	 */
	  @RequestMapping("/findBook")
	   public String listBookForReader(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
		String queryBookName=request.getParameter("queryBookName");
	//	System.out.println(queryBookName);
		Grid grid = new Grid();
		String hql=null;
		List<Book>books;
		if(queryBookName!=null)
		{
			hql="from Book as book where book.BookName like  '%"+queryBookName+"%'";
		
			books=(List<Book>)bookService.find(hql, page, rows);
		//	grid.setTotal(bookService.count("select count(*) from Book"));
			//还得遍历每一本书，查询馆藏数和可借数
			books=bookService.caculateBookNum(books);
			grid.setTotal((long)books.size());
			grid.setRows(books);
			writeJson(grid,response);
		}
			return null;
	    }
		/**
		 * 用于管理员查询和添加书籍
		 * @param request
		 * @param response
		 * @return  书籍基本信息列表
		 * @throws Exception
		 */
	  @RequestMapping("listBookForUser")
	   public String listBookForUser(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		page=ServletRequestUtils.getIntParameter(request, "page", 1);//默认值为1
		rows=ServletRequestUtils.getIntParameter(request, "rows", 0);
		String queryBookName=request.getParameter("queryBookName");//

		Grid grid = new Grid();
		String hql=null;
		long totalNum;
		List<Book>books;
		if(queryBookName!=null)
		{
			hql="from Book as book where book.BookName like  '%"+queryBookName+"%'";
		}else{
			hql="from Book";
		}
			books=(List<Book>)bookService.find(hql, page, rows);
		//	grid.setTotal(bookService.count("select count(*) from Book"));
			//还得遍历每一本书，查询馆藏数和可借数
		//	books=bookService.caculateBookNum(books);
			totalNum=bookService.count("select count(*)"+hql);
			grid.setTotal(totalNum);
			grid.setRows(books);
			writeJson(grid,response);
			return null;
	    }
	   @RequestMapping("/goManageBook")
	   public ModelAndView goManageBook(){
		  return new ModelAndView("book/manageBook");
	    }
		/**
		 * 管理员添加书籍
		 * @param book
		 * @param request
		 * @param response
		 * @return 
		 * @throws Exception
		 */
	   @RequestMapping("/addBook")
		 public String addBook(Book book,HttpServletRequest request,
					HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			//这里怎么判断一本书是否存在？ ISBN不同版次可能一样么？
			//if(    !=null){
		//		json.setMsg("添加书籍失败，用户已存在！");
			//}else{
				bookService.save(book);
				json.setMsg("添加书籍成功！");
				json.setSuccess(true);
		//	}
			writeJson(json,response);
			return null;
			}
	   @RequestMapping("/updateBook")
		 public String updateBook(Book book,HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			try{
					bookService.update(book);
					json.setMsg("更新成功！");
					json.setSuccess(true);
			}catch(Exception e){
				json.setMsg("更新失败！"+e.getMessage());
			
			}
			writeJson(json,response);
			return null;
		}
	   @RequestMapping("/removeBook")
		 public String removeBook(HttpServletRequest request,HttpServletResponse response) throws Exception{
			Json json = new Json();//用于向前端发送消息
			int bookId=Integer.parseInt(request.getParameter("id"));
			try{
				bookService.delete((Book)bookService.getById(bookId));//不懂为啥要加上（User）才对。。
				json.setMsg("删除成功！");
				json.setSuccess(true);
				
			}catch (Exception e){
				json.setMsg("删除失败！"+e.getMessage());
				System.out.println(e.getMessage());
			}
			writeJson(json,response);
			return null;
		
			}
	   /**
	    * 用于书籍管理显示详细视图
	    * 返回书籍的所有sn
	    * @param request
	    * @param response
	    * @return 
	    * @throws Exception
	    */
		@RequestMapping("/findSnByBook")
		public String findSnByBook(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
					String bookId= request.getParameter("bookId");
					Book book=(Book)bookService.getById(Integer.parseInt(bookId));
				    writeJson(book.getBookSNs(),response);
					return null;
		}
		/*
		 * 联网获得图书信息
		 */
		@RequestMapping("/getBookInfoByDouBanApi")
		public void getBookInfoByDouBanApi(HttpServletRequest request,
				HttpServletResponse response) throws Exception {
					//Json json = new Json();
					Map<String, Object> m = new HashMap<String, Object>();
					String isbn= request.getParameter("isbn");
					 DefaultHttpClient client = new DefaultHttpClient();
					 HttpGet get = new HttpGet("http://api.douban.com/book/subject/isbn/"+isbn);
				//	 HttpGet get = new HttpGet("http://api.douban.com/book/subject/isbn/9787308083256");
					 HttpResponse response2 = client.execute(get);
					 HttpEntity entity = response2.getEntity();
					 InputStream is = entity.getContent();
					 Book book = new BookXMLParser(is).getBook();
					// json.setSuccess(true);
					 m.put("book", book);
					 m.put("success", true);
					 writeJson(m,response);
		}
}
