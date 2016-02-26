package com.yang.bishe.entity;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.yang.bishe.util.DateUtil;


@Entity
@Table(name="T_Reader")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Reader implements java.io.Serializable {
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ReaderId")
	private String ReaderId;
	
	@ManyToOne
    @JoinColumn(name="ReaderTypeId")
	private ReaderType ReaderType;
	
	@OneToMany(mappedBy="Reader")
	 private Set<BorrowInfo> BorrowInfoes;
	
	@Column(name="ReaderName")
	private String ReaderName;
	@Column(name="Tel")
	private String Tel;
	@Column(name="Email")
	private String Email;
	@Column(name="Sex")
	private String Sex;
	@Temporal(TemporalType.DATE)
	@Column(name="Birthday")
	private Date Birthday;
	//1 可用 2 暂停使用（罚款未缴纳） 3 不可用
	@Column(name="Enable")
	private Integer Enable;
	public String getReaderId() {
		return ReaderId;
	}
	public void setReaderId(String readerId) {
		ReaderId = readerId;
	}
	public ReaderType getReaderType() {
		return ReaderType;
	}
	public void setReaderType(ReaderType readerType) {
		ReaderType = readerType;
	}
	public Set<BorrowInfo> getBorrowInfoes() {
		return BorrowInfoes;
	}
	public void setBorrowInfoes(Set<BorrowInfo> borrowInfoes) {
		BorrowInfoes = borrowInfoes;
	}
	public String getReaderName() {
		return ReaderName;
	}
	public void setReaderName(String readerName) {
		ReaderName = readerName;
	}
	public String getTel() {
		return Tel;
	}
	public void setTel(String tel) {
		Tel = tel;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getSex() {
		return Sex;
	}
	public void setSex(String sex) {
		Sex = sex;
	}
	public Date getBirthday() {
		return Birthday;
	}
	public void setBirthday(Date birthday) {
		Birthday = birthday;
	}
	public Integer getEnable() {
		return Enable;
	}
	public void setEnable(Integer enable) {
		Enable = enable;
	}

	
}
