package com.yang.bishe.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.BookSN;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBookSNService;
@Service
public class BookSNServieImpl extends BaseServiceImpl<BookSN> implements IBookSNService{
	@Autowired
	private IBaseDao<BookSN> booksnDao;
}
