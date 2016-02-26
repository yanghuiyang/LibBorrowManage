package com.yang.bishe.entity.easyui;

import java.util.Date;

import com.yang.bishe.entity.BookType;

/**
 * 拼凑显示读者当前借阅情况的属性表单
 * 
*/
public class BorrowInfoForm implements java.io.Serializable{
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private Integer BookState;
		private String BookName;
		private String BooksBarCode;//条形码
	    private String CallNumber;//索书号
	   // private Date BorrowDate;
		//private Date DueDate;//应还时间
		//private Date ReturnDate;//归还时间
	    private String BorrowDate;
	 	private String DueDate;//应还时间
	 	private String ReturnDate;//归还时间
		private  Integer RenewNum;//续借次数
		private  Integer OverDueDays;//超期天数
		private  Float Fine;
		private Float Price;
		private BookType bookType;
		public BookType getBookType() {
			return bookType;
		}
		public void setBookType(BookType bookType) {
			this.bookType = bookType;
		}
		public Float getPrice() {
			return Price;
		}
		public void setPrice(Float price) {
			Price = price;
		}
		public Float getFine() {
			return Fine;
		}
		public void setFine(Float fine) {
			Fine = fine;
		}
		public Integer getBookState() {
			return BookState;
		}
		public void setBookState(Integer bookState) {
			BookState = bookState;
		}
		public String getBookName() {
			return BookName;
		}
		public void setBookName(String bookName) {
			BookName = bookName;
		}
		public String getBooksBarCode() {
			return BooksBarCode;
		}
		public void setBooksBarCode(String booksBarCode) {
			BooksBarCode = booksBarCode;
		}
		public String getCallNumber() {
			return CallNumber;
		}
		public void setCallNumber(String callNumber) {
			CallNumber = callNumber;
		}
		public String getBorrowDate() {
			return BorrowDate;
		}
		public void setBorrowDate(String borrowDate) {
			BorrowDate = borrowDate;
		}
		public String getDueDate() {
			return DueDate;
		}
		public void setDueDate(String dueDate) {
			DueDate = dueDate;
		}
		public String getReturnDate() {
			return ReturnDate;
		}
		public void setReturnDate(String returnDate) {
			ReturnDate = returnDate;
		}
		public Integer getRenewNum() {
			return RenewNum;
		}
		public void setRenewNum(Integer renewNum) {
			RenewNum = renewNum;
		}
		public Integer getOverDueDays() {
			return OverDueDays;
		}
		public void setOverDueDays(Integer overDueDays) {
			OverDueDays = overDueDays;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
	
}
