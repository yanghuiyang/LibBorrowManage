package com.yang.bishe.service.interfaces;
import com.yang.bishe.entity.Reader;
import com.yang.bishe.service.base.IBaseService;

public  interface IReaderService  extends IBaseService<Reader>{

	boolean isEmptyById(String readerId);
}
