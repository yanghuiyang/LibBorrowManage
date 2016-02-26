package com.yang.bishe.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_Book")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Book implements java.io.Serializable  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BookId")
	private int BookId;
	//虚拟属性，每次查询时，还要查询有多少本书，可借多少本，然后赋值,这样book就能添加这两个参数了
	@Transient
	private Integer totalNum;//馆藏数
	@Transient
	private Integer canBorrowNum;//可借数
	
	@Transient 
	private Integer borrowNum;//借阅次数 统计要用 注意类型设置为String

	/**
	 * 用于业务逻辑的字段，注解@Transient代表不需要持久化到数据库中
	 */

	
	@ManyToOne
    @JoinColumn(name="BookTypeId")
	private BookType BookType;
	
	//抓取策略为EAGER，要不然bookService统计有多少本书时错误。
	@OneToMany(mappedBy="Book",cascade=(CascadeType.ALL), fetch=FetchType.EAGER)
	 private Set<BookSN> BookSNs;
	
	@Column(name="BookName")
	private String BookName;
	@Column(name="Author")
	private String Author;
	@Column(name="Series") //����
	private String Series;
	@Column(name="Publisher")
	private String Publisher;
	@Column(name="ISBN")
	private String ISBN;
	@Column(name="PublishYear")
	private Integer PublishYear;
	@Column(name="Page")
	private Integer Page;
	@Column(name="Price")
	private Float Price;
	@Column(name="Language")
	private String Language;
	@Column(name="Picture")
	private String Picture;
	@Column(name="CallNumber")
	private String CallNumber;
	public int getBookId() {
		return BookId;
	}
	public void setBookId(int bookId) {
		BookId = bookId;
	}
	public Integer getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}
	public Integer getCanBorrowNum() {
		return canBorrowNum;
	}
	public void setCanBorrowNum(Integer canBorrowNum) {
		this.canBorrowNum = canBorrowNum;
	}
	public BookType getBookType() {
		return BookType;
	}
	public void setBookType(BookType bookType) {
		BookType = bookType;
	}
	public Set<BookSN> getBookSNs() {
		return BookSNs;
	}
	public void setBookSNs(Set<BookSN> bookSNs) {
		BookSNs = bookSNs;
	}
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}
	public String getSeries() {
		return Series;
	}
	public void setSeries(String series) {
		Series = series;
	}
	public String getPublisher() {
		return Publisher;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public Integer getPublishYear() {
		return PublishYear;
	}
	public void setPublishYear(Integer publishYear) {
		PublishYear = publishYear;
	}
	public Integer getPage() {
		return Page;
	}
	public void setPage(Integer page) {
		Page = page;
	}
	public Float getPrice() {
		return Price;
	}
	public void setPrice(Float price) {
		Price = price;
	}
	public String getLanguage() {
		return Language;
	}
	public void setLanguage(String language) {
		Language = language;
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public String getCallNumber() {
		return CallNumber;
	}
	public void setCallNumber(String callNumber) {
		CallNumber = callNumber;
	}
	public Integer getBorrowNum() {
		return borrowNum;
	}
	public void setBorrowNum(Integer borrowNum) {
		this.borrowNum = borrowNum;
	}
}
