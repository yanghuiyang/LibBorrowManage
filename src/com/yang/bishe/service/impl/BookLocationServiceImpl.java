package com.yang.bishe.service.impl;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Book;
import com.yang.bishe.entity.BookLocation;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IBookLocationService;
@Service
public class BookLocationServiceImpl  extends BaseServiceImpl<BookLocation> implements IBookLocationService{
	@Autowired
	private IBaseDao<BookLocation> BookLocation;

}
