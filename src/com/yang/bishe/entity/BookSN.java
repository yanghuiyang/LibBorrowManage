package com.yang.bishe.entity;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_BookSN")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BookSN implements java.io.Serializable {
	@Id
//	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="BooksBarCode" ,unique=true)
	private String BooksBarCode;
	
	@ManyToOne
    @JoinColumn(name="BookId")
	private Book Book;
	
	@ManyToOne
    @JoinColumn(name="LocationId")
	private BookLocation BookLocation;
	
	public BookLocation getBookLocation() {
		return BookLocation;
	}
	public void setBookLocation(BookLocation bookLocation) {
		BookLocation = bookLocation;
	}
	@OneToMany(mappedBy="BookSN",cascade=(CascadeType.ALL), fetch=FetchType.EAGER)
	 private Set<BorrowInfo> BorrowInfoes;
	
	/**
	 * 1：待借阅  2：待审批（用户预约） 3：已借出  4.报损  
	 */
	@Column(name="BookState")//
	private Integer BookState;
	public String getBooksBarCode() {
		return BooksBarCode;
	}
	public void setBooksBarCode(String booksBarCode) {
		BooksBarCode = booksBarCode;
	}
	public Book getBook() {
		return Book;
	}
	public void setBook(Book book) {
		Book = book;
	}
	public Set<BorrowInfo> getBorrowInfoes() {
		return BorrowInfoes;
	}
	public void setBorrowInfoes(Set<BorrowInfo> borrowInfoes) {
		BorrowInfoes = borrowInfoes;
	}
	public Integer getBookState() {
		return BookState;
	}
	public void setBookState(Integer bookState) {
		BookState = bookState;
	}

}
