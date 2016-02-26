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
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
@Entity
@Table(name="T_Operation")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Operation  implements java.io.Serializable {
	@Id
	@Column(name="id")
	private String id;
	@Column(name="text")
	private String text;
	@Column(name="state")
	private String state;
	@Column(name="parentId")
	private String parentId;
	@Column(name="url")
	private String url;
	@Column(name="description")
	private String description;
	@Column(name="target")
	private String target;
	@Column(name="menuOrFunction")
	private String menuOrFunction;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name ="Role_Operation",
	joinColumns ={@JoinColumn(name = "id", referencedColumnName ="id")},
	inverseJoinColumns= {@JoinColumn(name = "RoleId", referencedColumnName ="RoleId")})
	private Set<Role> Roles = new HashSet<Role>(0);
	
	
	public Set<Role> getRoles() {
		return Roles;
	}
	public void setRoles(Set<Role> roles) {
		Roles = roles;
	}
	public String getMenuOrFunction() {
		return menuOrFunction;
	}
	public void setMenuOrFunction(String menuOrFunction) {
		this.menuOrFunction = menuOrFunction;
	}
	@Column(name="iconCls", columnDefinition = "String default icon-shape_square_link")
	private String iconCls;
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
}
