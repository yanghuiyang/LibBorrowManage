package com.yang.bishe.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_BookType")
@DynamicInsert(true)
@DynamicUpdate(true)
public class BookType implements java.io.Serializable  {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="BookTypeId")
	private int BookTypeId;
	
	@Column(name="BookTypeName")
	private String BookTypeName;
	@Column(name="BookTypeCode")
	private String BookTypeCode;
	 @OneToMany(mappedBy="BookType")
	  private Set<Book> Books;

	public String getBookTypeCode() {
		return BookTypeCode;
	}

	public void setBookTypeCode(String bookTypeCode) {
		BookTypeCode = bookTypeCode;
	}

	public int getBookTypeId() {
		return BookTypeId;
	}

	public void setBookTypeId(int bookTypeId) {
		BookTypeId = bookTypeId;
	}

	public String getBookTypeName() {
		return BookTypeName;
	}

	public void setBookTypeName(String bookTypeName) {
		BookTypeName = bookTypeName;
	}

	public Set<Book> getBooks() {
		return Books;
	}

	public void setBooks(Set<Book> books) {
		Books = books;
	}
}
