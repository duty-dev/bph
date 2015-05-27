package com.tianyi.bph.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.CaseTypeAGGR;


@MyBatisRepository
public interface CaseReportMapper {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Map<String, Object>  map );
	
	List<CaseTypeAGGR> loadCaseTypeReport(Map<String, Object>  map);
	
}
