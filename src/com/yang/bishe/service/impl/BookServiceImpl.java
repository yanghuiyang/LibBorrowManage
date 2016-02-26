package com.yang.bishe.service.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BookSN;
import com.yang.bishe.entity.easyui.ComplexSearchForm;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBookService;
import com.yang.bishe.service.interfaces.IUserService;
	@Service
	public class BookServiceImpl extends BaseServiceImpl<Book> implements IBookService {
		@Autowired
		private IBaseDao<Book> bookDao;
/*
 *统计一本书有多少馆藏和可借数
 *通过book里面的bookSN Set集合来算
 * @yang
 */
		@Override
		public List<Book> caculateBookNum(List<Book> books) {
			// TODO Auto-generated method stub
			for(Book book : books){
				//book.setTotalNum(bookDao.count("select count(*) from BookSN where ").intValue());
				Set<BookSN>BookSNs = book.getBookSNs();
				book.setTotalNum(BookSNs.size());
			//	System.out.println("BookSNs.size()="+BookSNs.size());
				int temp=0;
				for(BookSN sn:BookSNs){
					if(sn.getBookState()==1) //1为可借
						temp+=1;
				}
				book.setCanBorrowNum(temp);
			//	System.out.println("temp="+temp);
			}
			return books;
		}
		/**
		 *  高级检索
		 */
		@Override
		public String getCompleSearchHQL(ComplexSearchForm searchForm) {
			String hql="from Book as book where 1=1 ";//前台已做验证，不能提交空的查询条件
			//注意前后间空格
			if(searchForm.getName()!=null){
				hql+="and book.BookName like :Name ";
			}
			if(searchForm.getAuthor()!=null){
				if(searchForm.getName()!=null){
					hql+=searchForm.getLogic1()+" book.Author like :Author ";
				}else{
					hql+="and book.Author like :Author ";
				}
			}
			if(searchForm.getSeries()!=null){
				if(searchForm.getAuthor()!=null){
					hql+=searchForm.getLogic2()+" book.Series like :Series ";
				}else{
					if(searchForm.getName()!=null){
					hql+=searchForm.getLogic1()+" book.Series like :Series ";
						}else{
								hql+="and book.Series like :Series ";
							}
					}
			}
			return hql;
		}
	}
