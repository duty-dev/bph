package com.tianyi.bph.dao.report;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.CaseGps;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.domain.report.WarningOrgAGGR;


@MyBatisRepository
public interface CaseReportMapper {

	int loadMaxYMD();
	
	Date loadMaxDate();
	
	void insertCaseInfo(Map<String, Object>  map );
	
	void insertOrganLevel();
	
	List<CaseTypeAGGR> loadCaseTypeReport(Map<String, Object>  map);
	
	List<CasePeriodAGGR> loadCasePeriodReport(Map<String, Object>  map);
	
	List<CaseHourAGGR> loadCaseHourReport(Map<String, Object>  map);
	
	List<CaseOrgAGGR> loadCaseOrgReport(Map<String, Object>  map);
	
	List<WarningOrgAGGR> loadWarningReport(Map<String, Object>  map);
	
	List<CaseGps> loadCaseGps(Map<String, Object>  map);
	
	
}
