package com.yang.bishe.service.interfaces;

import java.util.Map;

import com.yang.bishe.entity.ReaderType;
import com.yang.bishe.service.base.IBaseService;

public interface IReaderTypeService extends IBaseService<ReaderType>{
/**
 * 读者分类统计
 * @param readerTypeId
 * @return 前台结果表单属性
 */
	Map<String, String> getStatistics(int readerTypeId);

}
