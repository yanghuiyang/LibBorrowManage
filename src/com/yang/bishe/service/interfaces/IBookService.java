package com.yang.bishe.service.interfaces;

import java.util.List;

import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.easyui.ComplexSearchForm;
import com.yang.bishe.service.base.IBaseService;

public interface IBookService extends IBaseService<Book>{

	List<Book> caculateBookNum(List<Book> books);

//	List<Book> doCompleSearch(ComplexSearchForm searchForm, int page, int rows);

	String getCompleSearchHQL(ComplexSearchForm searchForm);

}
