package com.yang.bishe.service.impl;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BookSN;
import com.yang.bishe.entity.BorrowInfo;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.BorrowInfoForm;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBorrowInfoService;
import com.yang.bishe.util.BeanUtils;
import com.yang.bishe.util.DateUtil;
@Service
public class BorrowInfoServiceImpl extends BaseServiceImpl<BorrowInfo> implements IBorrowInfoService{
	@Autowired
	private IBaseDao<BorrowInfo> borrowInfoDao;
	@Autowired
	private IBaseDao<Reader> readerDao;
	@Autowired
	private IBaseDao<BookSN> bookSNDao;
	@Autowired
	private IBaseDao<Book>bookDao;
//图书借阅,设置借阅表值
	@Override
	public String saveBorrowInfo(String readerId, String booksBarCode,
			User user) {
		// TODO Auto-generated method stub
		Reader reader=readerDao.getById(Reader.class, readerId);
		BookSN bookSN=bookSNDao.getById(BookSN.class, booksBarCode);
		if(bookSN.getBookState()==1){
			//计算读者现在借阅书的数量和是否超出总罚金。
			Set<BorrowInfo>infoes=reader.getBorrowInfoes();
			int nowBorrowNum=0;
			int totalFine=0;
			for(BorrowInfo in:infoes){
				if(in.getReturnDate()==null){
					nowBorrowNum+=1;
				}	
				if(in.getFine()!=null){
					totalFine+=in.getFine(); //借阅表的罚金应该是归还时计算
				}
			}
			if(reader.getReaderType().getMaxFine()==null)
			{	
				return "未设置读者可欠款最大额，不能借阅";
			}
			if(totalFine>reader.getReaderType().getMaxFine())
				{	
					reader.setEnable(2);
					return "罚金超过最大额，不能借阅";
				}
			if(nowBorrowNum<reader.getReaderType().getMaxBorrowNums()){
				BorrowInfo borrowInfo=new BorrowInfo();
				Date today=new Date();
				bookSN.setBookState(3);
				borrowInfo.setBookSN(bookSN);
				borrowInfo.setBorrowDate(today);
				borrowInfo.setDueDate(DateUtil.addDay(today, reader.getReaderType().getMaxBorrowDays()));
				borrowInfo.setOperationDate(today);
				borrowInfo.setReader(reader);
				borrowInfo.setRenewNum(0);
				borrowInfo.setUser(user);
				borrowInfoDao.save(borrowInfo);
				return "借阅成功";
			}else return"借阅失败,已超过最大借阅数量";
		}else
			return "书籍已借出或者不存在"	;
	}
	


	@Override
	public List<BorrowInfoForm> findReaderNowBorrow(String readerId) {
		// TODO Auto-generated method stub
		List<BorrowInfoForm> borrowInfoForm=new ArrayList();
		Reader reader=readerDao.getById(Reader.class, readerId);
		Set<BorrowInfo>borrowInfoes=reader.getBorrowInfoes();
		if(borrowInfoes.size()==0)
			return null;
		for(BorrowInfo borrowInfo:borrowInfoes){
			//注意过滤掉已经归还的书籍（return day不为空）
			if(borrowInfo.getReturnDate()==null){
					BorrowInfoForm temp=new BorrowInfoForm();
					temp.setBookName(borrowInfo.getBookSN().getBook().getBookName());
					temp.setBooksBarCode(borrowInfo.getBookSN().getBooksBarCode());
					temp.setBookState(borrowInfo.getBookSN().getBookState());
					temp.setBorrowDate(DateUtil.getDate(borrowInfo.getBorrowDate()));
					temp.setCallNumber(borrowInfo.getBookSN().getBook().getCallNumber());
					temp.setDueDate(DateUtil.getDate(borrowInfo.getDueDate()));
					temp.setPrice(borrowInfo.getBookSN().getBook().getPrice());
					temp.setBookType(borrowInfo.getBookSN().getBook().getBookType());
					temp.setOverDueDays(borrowInfo.getOverDueDays());
					temp.setRenewNum(borrowInfo.getRenewNum());
					temp.setReturnDate(DateUtil.getDate(borrowInfo.getReturnDate()));
					borrowInfoForm.add(temp);
				}
			}
		return borrowInfoForm;
	}
	@Override
	public String saveReturnInfo(String booksBarCode, User user) {
		//注意多个借阅表的情况（因为续借会生成多个借阅表）
		//你该用什么找到最新的那个借阅表。应该是还书日期为空的那张表。
		BookSN sn=bookSNDao.getById(BookSN.class, booksBarCode);
		String temp=null;
		if(sn.getBookState()==3){
			Set<BorrowInfo> infoes=sn.getBorrowInfoes();
			Date today=new Date();
			for(BorrowInfo in:infoes){
				if(in.getReturnDate()==null){
					in.setReturnDate(today);
					//超期 设置罚金
					if(today.after(in.getDueDate())){
						in.setOverDueDays(DateUtil.getIntervalDays(in.getDueDate(), today));
						in.setFine(DateUtil.getIntervalDays(in.getDueDate(), today)*in.getReader().getReaderType().getFinePerDay());
						
					}else{
						in.setFine((float) 0);
					}
					in.setUser(user);
					in.setOperationDate(today);
					sn.setBookState(1);
					in.setBookSN(sn);
					//传一个readerId回去用来归还页面查询当前读者的借阅记录
					temp=in.getReader().getReaderId();
					break;
				}
			}
		}else if(sn.getBookState()==1){
			temp="归还失败，书已经归还";
		}
		if(temp==null){
			temp="归还失败";
		}
		return temp;
	}

	@Override
	public String saveReNewInfo(String booksBarCode, User user) {
		// TODO Auto-generated method stub
		BookSN sn=bookSNDao.getById(BookSN.class, booksBarCode);
		String temp=null;
		if(sn.getBookState()==3){ 
			Set<BorrowInfo> infoes=sn.getBorrowInfoes();
			Date today=new Date();
			for(BorrowInfo info:infoes){
				if(info.getReturnDate()==null){
					if(info.getDueDate().after(today)&&info.getRenewNum()<info.getReader().getReaderType().getMaxRenewNum()){ //判断是否超期，超期不能续借,超过续借次数不能续借
						info.setReturnDate(today);//相当于归还
						info.setOperationDate(today);
						info.setUser(user);  
						//设置新的借阅表
						BorrowInfo borrowInfo=new BorrowInfo();
						borrowInfo.setBookSN(sn);
						borrowInfo.setBorrowDate(today);
						borrowInfo.setDueDate(DateUtil.addDay(today, info.getReader().getReaderType().getMaxBorrowDays()));
						borrowInfo.setOperationDate(today);
						borrowInfo.setReader(info.getReader());
						borrowInfo.setRenewNum(info.getRenewNum()+1);
						borrowInfo.setUser(user);
						borrowInfoDao.save(borrowInfo);
						temp=info.getReader().getReaderId();
					}else if(!info.getDueDate().after(today)) {
						temp="A 图书超期，不能续借";
						break;
					}else if(info.getRenewNum()>=info.getReader().getReaderType().getMaxRenewNum()){
						temp="B 图书续借次数超过最多续借次数";
						break;
					}
				}
			}
		}
		
		return temp;
	}



	@Override
	public List<BorrowInfoForm> findReaderNowAllFine(String readerId) {
		// TODO Auto-generated method stub
		List<BorrowInfoForm> borrowInfoForm=new ArrayList();
		Reader reader=readerDao.getById(Reader.class, readerId);
		Set<BorrowInfo>borrowInfoes=reader.getBorrowInfoes();
//		List<BorrowInfo>borrowInfoes=borrowInfoDao.find("From BorrowInfo as t where t.Reader="+reader+" and t.Fine is not null");
		if(borrowInfoes.size()==0)
			return null;
		for(BorrowInfo borrowInfo:borrowInfoes){
			//注意过滤掉未归还的书籍
			if(borrowInfo.getReturnDate()!=null&&borrowInfo.getFine()!=null){
					BorrowInfoForm temp=new BorrowInfoForm();
					temp.setBookName(borrowInfo.getBookSN().getBook().getBookName());
					temp.setBooksBarCode(borrowInfo.getBookSN().getBooksBarCode());
					temp.setCallNumber(borrowInfo.getBookSN().getBook().getCallNumber());
					temp.setFine(borrowInfo.getFine());
					temp.setReturnDate(DateUtil.getDate(borrowInfo.getReturnDate()));
					borrowInfoForm.add(temp);
				}
			}
		return borrowInfoForm;
	}



	@Override
	public String setPayFine(String readerId) throws Exception{
		String s=null;
		try{
				borrowInfoDao.executeHql("Update BorrowInfo b  set b.Fine=null where b.Reader.ReaderId='"+readerId+"'");
				s="sucess";
			}catch (Exception e){
				s=e.getMessage();
			}
			return s;
		}



	@Override
	public List<Book> getOverall(String startDate,String endDate) {
		List<Book> books=new ArrayList();
		String sql=null;
		//注意这的的判断条件，是空字符串而不是null
		if(startDate!=""&&endDate!=""){
			sql="select  temp.BookId as BookId,count(BookId) as borrowNum"
					+" from (select b.BorrowInfoId,b.BooksBarCode,sn.BookId,b.BorrowDate from t_borrowinfo as b "
					+" left join t_booksn as sn on b.BooksBarCode=sn.BooksBarCode) "
					+" as temp "
					+" where BorrowDate"
					+" between '"+startDate+"' and '"+endDate
					+"' group by temp.BookId "
					+" order by borrowNum desc "
					+" limit 30";
			}else{
					sql="select  temp.BookId as BookId,count(BookId) as borrowNum"
									+" from (select b.BorrowInfoId, b.BooksBarCode, sn.BookId from t_borrowinfo as b "
									+" left join t_booksn as sn on b.BooksBarCode=sn.BooksBarCode) "
									+" as temp "
									+" group by temp.BookId "
									+" order by borrowNum desc "
									+" limit 30";
			}
		 List<Map> map=borrowInfoDao.findBySql(sql);
		 for(int i=0;i<map.size();i++){
			 Book book=new Book();
			 book=bookDao.getById(Book.class,(Integer)map.get(i).get("BookId"));
			 book.setBorrowNum(Integer.parseInt(map.get(i).get("borrowNum").toString()));
			 books.add(book);
		 }
		return books;
	}



	@Override
	public List<Book> getBookTypeOverall(String bookTypeId,String startDate,String endDate) {
		List<Book> books=new ArrayList();
		String sql=null;
		//注意这的的判断条件，是空字符串而不是null
		if(startDate!=""&&endDate!=""){
			sql="select temp.BookId,count(temp.BookId) as borrowNum"
					+" from (select b.BorrowDate,b.BooksBarCode,sn.BookId from t_borrowinfo as b" 
					+" left join t_booksn as sn on b.BooksBarCode=sn.BooksBarCode)"
					+" as temp"
					+" left join t_book as book on book.BookId=temp.BookId"
					+" where book.BookTypeId="+bookTypeId 
					+" and BorrowDate between '"+startDate+"' and '"+endDate
					+"' group by book.BookId"
					+" order by borrowNum desc"
					+" limit 30";
		}else{
			sql="select temp.BookId,count(temp.BookId) as borrowNum"
					+" from (select b.BooksBarCode,sn.BookId from t_borrowinfo as b" 
					+" left join t_booksn as sn on b.BooksBarCode=sn.BooksBarCode)"
					+" as temp"
					+" left join t_book as book on book.BookId=temp.BookId"
					+" where book.BookTypeId="+bookTypeId
					+" group by book.BookId"
					+" order by borrowNum desc"
					+" limit 30";
		}
				List<Map> map=borrowInfoDao.findBySql(sql);
				for(int i=0;i<map.size();i++){
					 Book book=new Book();
					 book=bookDao.getById(Book.class,(Integer)map.get(i).get("BookId"));
					 book.setBorrowNum(Integer.parseInt(map.get(i).get("borrowNum").toString()));
					 books.add(book);
				}
				return books;
	}

}
