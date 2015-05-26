package com.tianyi.bph.dao.report;

import java.util.Date;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;


@MyBatisRepository
public interface CaseReportMapper {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Map<String, Object>  map );
	
}
