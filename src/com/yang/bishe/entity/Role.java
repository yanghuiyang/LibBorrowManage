package com.yang.bishe.entity;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "T_Role")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Role  implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RoleId")
	private int RoleId;
	
	@OneToMany(mappedBy="Role")
	private Set<User> Users;
	
	@Column(name="RoleName")
	private String RoleName;
	@Column(name="RoleDescription")
	private String RoleDescription;
	
	@ManyToMany(fetch =FetchType.EAGER)
	@JoinTable(name ="Role_Operation",
		joinColumns ={@JoinColumn(name = "RoleId", referencedColumnName ="RoleId")},
		inverseJoinColumns= {@JoinColumn(name = "id", referencedColumnName ="id")})
	private Set<Operation> Opeations = new HashSet<Operation>(0);
	
	
	public int getRoleId() {
		return RoleId;
	}
	public void setRoleId(int roleId) {
		RoleId = roleId;
	}
	public Set<User> getUsers() {
		return Users;
	}
	public void setUsers(Set<User> users) {
		Users = users;
	}
	public Set<Operation> getOpeations() {
		return Opeations;
	}
	public void setOpeations(Set<Operation> opeations) {
		Opeations = opeations;
	}
	public String getRoleName() {
		return RoleName;
	}
	public void setRoleName(String roleName) {
		RoleName = roleName;
	}
	public String getRoleDescription() {
		return RoleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		RoleDescription = roleDescription;
	}


}
