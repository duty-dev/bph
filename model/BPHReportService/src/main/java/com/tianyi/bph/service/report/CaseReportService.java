package com.tianyi.bph.service.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianyi.bph.domain.report.CaseGps;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.domain.report.WarningAGGR;
import com.tianyi.bph.domain.report.WarningOrgAGGR;
import com.tianyi.bph.query.report.ColorWarningResultList;

public interface CaseReportService {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Date beginTime,Date endTime);
	
	List<CaseTypeAGGR> loadCaseTypeReport(Map<String,Object> map);
	
	List<CasePeriodAGGR> loadCasePeriodReport(Map<String,Object> map);
	
	List<CaseHourAGGR> loadCaseHourReport(Map<String, Object>  map);
	
	List<CaseOrgAGGR> loadCaseOrgReport(Map<String, Object>  map);
	
	List<WarningOrgAGGR> loadWarningReport(Map<String, Object>  map);
	
	List<CaseGps> loadCaseGps(Map<String, Object>  map);

	List<ColorWarningResultList> getWarningReport(String query);
}
