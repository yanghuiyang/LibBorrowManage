package com.yang.bishe.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yang.bishe.dao.IBaseDao;
import com.yang.bishe.entity.BorrowInfo;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.entity.ReaderType;
import com.yang.bishe.service.base.BaseServiceImpl;
import com.yang.bishe.service.interfaces.IReaderTypeService;
@Service
public class ReaderTypeServiceImpl extends BaseServiceImpl<ReaderType> implements IReaderTypeService {
	@Autowired
	private IBaseDao<ReaderType> readerTypeDao;
	@Autowired
	private IBaseDao<BorrowInfo> borrowInfoDao;


	@Override
	public Map<String, String> getStatistics(int readerTypeId) {
		String readerTypeName;
		int nowBorrowNum=0;
		int historyBorrowNum=0;
		ReaderType readerType=readerTypeDao.getById(ReaderType.class,readerTypeId);
		readerTypeName=readerType.getReaderTypeName();
		Map<String, String> dateGrid = new HashMap<String, String>();
		dateGrid.put("readerTypeName", readerTypeName);
		dateGrid.put("readerNum", String.valueOf(readerType.getReaders().size()));
		Set<Reader> readers = readerType.getReaders();
		for(Reader reader:readers){
			Set<BorrowInfo> infos = reader.getBorrowInfoes();
				for(BorrowInfo info:infos){
					if(info.getReturnDate()==null){
						nowBorrowNum++;
					}
				}
			historyBorrowNum+=infos.size();
		}
		dateGrid.put("nowBorrowNum", String.valueOf(nowBorrowNum));
		dateGrid.put("historyBorrowNum", String.valueOf(historyBorrowNum));
		return dateGrid;
	}
}
