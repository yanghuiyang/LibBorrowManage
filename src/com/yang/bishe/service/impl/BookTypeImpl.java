package com.yang.bishe.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.BookType;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBookTypeService;
@Service
public class BookTypeImpl extends BaseServiceImpl<BookType>implements IBookTypeService {
	@Autowired
	private IBaseDao<BookType> bookTypeDao;
	
}