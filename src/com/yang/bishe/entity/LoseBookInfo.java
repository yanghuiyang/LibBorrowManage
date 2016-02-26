package com.yang.bishe.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_LoseBookInfo")
@DynamicInsert(true)
@DynamicUpdate(true)
public class LoseBookInfo implements java.io.Serializable {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="LoseInfoId")
	private int LoseInfoId;
	
	@Column(name="BookBarCode")
	private String BookBarCode;
	@Column(name="BookName")
	private String BookName;
	@Column(name="LoseFine")
	private  Float LoseFine;
	@Column(name="Reader")
	private String ReaderId;
	@Column(name="OperatorId")
	private String OperatorId;
	@Temporal(TemporalType.DATE)
	@Column(name="CreateDate")
	private Date CreateDate;
	@Column(name="LoseDesc")
	private String LoseDesc;
	public int getLoseInfoId() {
		return LoseInfoId;
	}
	public void setLoseInfoId(int loseInfoId) {
		LoseInfoId = loseInfoId;
	}
	public String getBookBarCode() {
		return BookBarCode;
	}
	public void setBookBarCode(String bookBarCode) {
		BookBarCode = bookBarCode;
	}
	public String getBookName() {
		return BookName;
	}
	public void setBookName(String bookName) {
		BookName = bookName;
	}
	public Float getLoseFine() {
		return LoseFine;
	}
	public void setLoseFine(Float loseFine) {
		LoseFine = loseFine;
	}
	public String getReaderId() {
		return ReaderId;
	}
	public void setReaderId(String readerId) {
		ReaderId = readerId;
	}
	public String getOperatorId() {
		return OperatorId;
	}
	public void setOperatorId(String operatorId) {
		OperatorId = operatorId;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getLoseDesc() {
		return LoseDesc;
	}
	public void setLoseDesc(String loseDesc) {
		LoseDesc = loseDesc;
	}
	
	
}
