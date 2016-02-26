package com.yang.bishe.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IReaderService;
import com.yang.bishe.service.interfaces.IUserService;
@Service
public class ReaderServiceImpl extends BaseServiceImpl<Reader> implements IReaderService{
	@Autowired
	private IBaseDao<Reader> readerDao;

	@Override
	public boolean isEmptyById(String readerId) {
		// TODO Auto-generated method stub
		if((Reader)readerDao.getById(Reader.class, readerId)==null)
		{
			return true;
		}else
		return false;
	}
}
