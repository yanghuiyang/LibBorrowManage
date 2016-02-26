package com.yang.bishe.service.interfaces;

import com.yang.bishe.entity.LoseBookInfo;
import com.yang.bishe.entity.User;
import com.yang.bishe.service.base.IBaseService;

public interface ILoseBookInfoService  extends IBaseService<LoseBookInfo>{

	void setLoseBook(Float fine, String bookBarCode, User user, String readerId);

}
