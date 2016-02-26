package com.yang.bishe.entity;

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

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name="T_User")
@DynamicInsert(true)
@DynamicUpdate(true)
public class User implements java.io.Serializable {
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="UserId")
	private String UserId; //Id���˺� ����int
	@Column(name="UserName")//�û�����
	private String UserName;
	@Column(name="PassWord")
	private String PassWord;
	@Column(name="Enable")
	private Integer Enable;
	
	@OneToMany(mappedBy="User")
	 private Set<BorrowInfo> BorrowInfoes;
	 
	 @ManyToOne
	 @JoinColumn(name="RoleId")
	 private Role Role;
	
	 
	 public Role getRole() {
		return Role;
	}
	public void setRole(Role role) {
		Role = role;
	}
	public String getUserId() {
		return UserId;
	}
	public void setUserId(String userId) {
		UserId = userId;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getPassWord() {
		return PassWord;
	}
	public void setPassWord(String passWord) {
		PassWord = passWord;
	}
	public Integer getEnable() {
		return Enable;
	}
	public void setEnable(Integer enable) {
		Enable = enable;
	}
	public Set<BorrowInfo> getBorrowInfoes() {
		return BorrowInfoes;
	}
	public void setBorrowInfoes(Set<BorrowInfo> borrowInfoes) {
		BorrowInfoes = borrowInfoes;
	}

}
