package com.yang.bishe.service.impl;

import java.util.Date;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.BookSN;
import com.yang.bishe.entity.BorrowInfo;
import com.yang.bishe.entity.LoseBookInfo;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.User;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBorrowInfoService;
import com.yang.bishe.service.interfaces.ILoseBookInfoService;
import com.yang.bishe.util.DateUtil;
@Service
public class LoseBookInfoImpl extends BaseServiceImpl<LoseBookInfo>  implements ILoseBookInfoService {
	@Autowired
	private IBaseDao<LoseBookInfo> loseBookInfoDao;
	@Autowired
	private IBaseDao<BorrowInfo> borrowInfoDao;
	@Autowired
	private IBaseDao<BookSN> bookSNDao;
//	@Autowired 
//	private IBorrowInfoService borrowInfoService;
	
	@Override
	public void setLoseBook(Float fine, String bookBarCode, User user, String readerId) {
		// 丢失，先做归还操作，然后设置丢失表
		saveReturnInfo(bookBarCode, user);
		BookSN sn=bookSNDao.getById(BookSN.class, bookBarCode);
		sn.setBookState(4); //报损
		LoseBookInfo info=new LoseBookInfo();
		info.setBookBarCode(bookBarCode);
		info.setBookName(sn.getBook().getBookName());
		info.setCreateDate(new Date());
		//info.setLoseDesc(loseDesc);
		info.setLoseFine(fine);
		info.setOperatorId(user.getUserId());
		info.setReaderId(readerId);
		loseBookInfoDao.save(info);
	}
private void saveReturnInfo(String bookBarCode, User user) {
	// TODO Auto-generated method stub
		BookSN sn=bookSNDao.getById(BookSN.class, bookBarCode);
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
		
	}
}
