package com.yang.bishe.entity.easyui;
/**
 * 复杂查询表单对象，用于接收查询表单。
 * //这里要注意啊，为了模糊查询，我set函数返回的是加了通配符的！！！！
 * @author yang
 *
 */
public class ComplexSearchForm {
//	private String BookName;
	private String Name;
	private String Logic1;
	private String Author;
	private String Logic2;
	private String Series;
	public String getName() {
		return Name;
	}
	public void setName(String Name) {
		if(Name!=null){
			this.Name ="%"+ Name+"%";
		}else this.Name=null;
	//	this.Name = Name;
	}
	public String getLogic1() {
		return Logic1;
	}
	public void setLogic1(String logic1) {
		Logic1 = logic1;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		if(author!=null){
			this.Author ="%"+ author+"%";
		}else this.Author=null;
	//	Author = author;
	}
	public String getLogic2() {
		return Logic2;
	}
	public void setLogic2(String logic2) {
		Logic2 = logic2;
	}
	public String getSeries() {
		return Series;
	}
	public void setSeries(String series) {
	//	Series = series;
		if(series!=null){
			this.Series="%"+ series+"%";
		}
	}

}
