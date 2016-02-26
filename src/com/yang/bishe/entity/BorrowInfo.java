package com.yang.bishe.entity;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yang.bishe.util.DateUtil;

@Entity
@Table(name="T_BorrowInfo")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BorrowInfo implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BorrowInfoId")
	private int BorrowInfoId;
	@ManyToOne
    @JoinColumn(name="BooksBarCode")
	private BookSN BookSN;
	@ManyToOne
    @JoinColumn(name="ReaderId")
	private Reader Reader;
	
	@ManyToOne
    @JoinColumn(name="UserId")
	private User User;
	

	@Temporal(TemporalType.DATE)
	@Column(name="BorrowDate")
	private Date BorrowDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DueDate")//Ӧ�ù黹����
	private Date DueDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="ReturnDate")
	private Date ReturnDate;
	
	@Column(name="RenewNum")
	private  Integer RenewNum;/**续借次数 */
	@Column(name="OverDueDays")/**get函数就临时计算  ? */
	private  Integer OverDueDays;

	@Column(name="Fine")
	private  Float Fine;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="OperationDate")
	private Date OperationDate;
//	@Column(name="UpatedBy")
//	private  String UpatedBy;
	
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	public int getBorrowInfoId() {
		return BorrowInfoId;
	}
	public Date getOperationDate() {
		return OperationDate;
	}
	public void setOperationDate(Date operationDate) {
		OperationDate = operationDate;
	}
	public void setBorrowInfoId(int borrowInfoId) {
		BorrowInfoId = borrowInfoId;
	}
	public BookSN getBookSN() {
		return BookSN;
	}
	public void setBookSN(BookSN bookSN) {
		BookSN = bookSN;
	}

	public Reader getReader() {
		return Reader;
	}
	public void setReader(Reader reader) {
		Reader = reader;
	}

	public Date getBorrowDate() {
		return BorrowDate;
	}
	public void setBorrowDate(Date borrowDate) {
		BorrowDate = borrowDate;
	}
	public Date getDueDate() {
		return DueDate;
	}
	public void setDueDate(Date dueDate) {
		DueDate = dueDate;
	}
	public Date getReturnDate() {
		return ReturnDate;
	}
	public void setReturnDate(Date returnDate) {
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
	public Float getFine() {
		return this.Fine;
	}
	public void setFine(Float fine) {
		this.Fine = fine;
	}

}
