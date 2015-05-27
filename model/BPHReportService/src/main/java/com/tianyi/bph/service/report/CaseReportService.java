package com.tianyi.bph.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianyi.bph.domain.report.CaseTypeAGGR;

public interface CaseReportService {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Date beginTime,Date endTime);
	
	List<CaseTypeAGGR> loadCaseTypeReport(Map<String,Object> map);
}
