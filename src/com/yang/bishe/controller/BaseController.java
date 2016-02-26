package com.yang.bishe.controller;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.yang.bishe.util.FastjsonFilter;

/**
 * 基础Controller,其他Controller继承此Controller来获得writeJson和ActionSupport的功能
 * 
 * 基本的CRUD已实现，子类继承BaseController的时候，提供setService方法即可
 * @author yang
 * 
 */
@Controller
public class BaseController {
	protected int page;// 当前页
	protected int rows ;// 每页显示记录数
	//protected String sort;// 排序字段
	//protected String order = "asc";// asc/desc
	//protected String q;// easyui的combo和其子类过滤时使用
	//protected T data;// 数据模型(与前台表单name相同，name="data.xxx")
    protected String id;// 主键
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @param includesProperties
	 *            需要转换的属性
	 * @param excludesProperties
	 *            不需要转换的属性
	 */
	public void writeJsonByFilter(Object object, HttpServletResponse response,String[] includesProperties, String[] excludesProperties) {
		try {
			FastjsonFilter filter = new FastjsonFilter();// excludes优先于includes
			if (excludesProperties != null && excludesProperties.length > 0) {
				filter.getExcludes().addAll(Arrays.<String> asList(excludesProperties));
			}
			if (includesProperties != null && includesProperties.length > 0) {
				filter.getIncludes().addAll(Arrays.<String> asList(includesProperties));
			}
		//	logger.info("对象转JSON：要排除的属性[" + excludesProperties + "]要包含的属性[" + includesProperties + "]");
			String json;
			String User_Agent = getRequest().getHeader("User-Agent");
			if (StringUtils.indexOfIgnoreCase(User_Agent, "MSIE 6") > -1) {
				// 使用SerializerFeature.BrowserCompatible特性会把所有的中文都会序列化为\\uXXXX这种格式，字节数会多一些，但是能兼容IE6
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.BrowserCompatible);
			} else {
				// 使用SerializerFeature.WriteDateUseDateFormat特性来序列化日期格式的类型为yyyy-MM-dd hh24:mi:ss
				// 使用SerializerFeature.DisableCircularReferenceDetect特性关闭引用检测和生成
				json = JSON.toJSONString(object, filter, SerializerFeature.WriteDateUseDateFormat, SerializerFeature.DisableCircularReferenceDetect);
			}
		//	logger.info("转换后的JSON字符串：" + json);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HttpServletRequest getRequest() {
		// TODO Auto-generated method stub
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public void writeJson(Object object ,HttpServletResponse response) {
		writeJsonByFilter(object, response,null, null);
	}

//	/**
//	 * 将对象转换成JSON字符串，并响应回前台
//	 * 
//	 * @param object
//	 * @param includesProperties
//	 *            需要转换的属性
//	 */
//	public void writeJsonByIncludesProperties(Object object, String[] includesProperties) {
//		writeJsonByFilter(object, includesProperties, null);
//	}
//
//	/**
//	 * 将对象转换成JSON字符串，并响应回前台
//	 * 
//	 * @param object
//	 * @param excludesProperties
//	 *            不需要转换的属性
//	 */
//	public void writeJsonByExcludesProperties(Object object, String[] excludesProperties) {
//		writeJsonByFilter(object, null, excludesProperties);
//	}

}
