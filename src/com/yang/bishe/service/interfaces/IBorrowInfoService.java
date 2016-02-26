package com.yang.bishe.service.interfaces;
import java.util.List;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BorrowInfo;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.User;
import com.yang.bishe.entity.easyui.BorrowInfoForm;
import com.yang.bishe.service.base.IBaseService;

public interface IBorrowInfoService extends IBaseService< BorrowInfo> {
	//这里要注意函数名要save开头，要不然不能保存。看xml注解事务那。
	String saveBorrowInfo(String readerId, String booksBarCode, User user);

	List<BorrowInfoForm> findReaderNowBorrow(String readerId);

	String saveReturnInfo(String booksBarCode, User user);

	String saveReNewInfo(String booksBarCode, User user);
	//列出读者还未缴纳罚款的全部罚款信息
	List<BorrowInfoForm> findReaderNowAllFine(String readerId);
/**
 * 清空读者罚金（缴纳罚金）
 */
	String setPayFine(String readerId) throws Exception;
	/**
	 * 
	 * @return 书籍借阅总排行 前30
	 */
	List<Book> getOverall(String startDate,String endDate);

	/**
	 * 书籍排名分类型查询
	 * @param bookTypeId
	 * @return 排名情况 前30
	 */
	List<Book> getBookTypeOverall(String bookTypeId,String startDate,String endDate);
}
