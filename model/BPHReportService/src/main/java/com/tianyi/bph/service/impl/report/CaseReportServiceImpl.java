package com.tianyi.bph.service.impl.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tianyi.bph.dao.report.CaseReportMapper;
import com.tianyi.bph.dao.report.WarningConfigMapper;
import com.tianyi.bph.domain.report.CaseHourAGGR;
import com.tianyi.bph.domain.report.CaseOrgAGGR;
import com.tianyi.bph.domain.report.CasePeriodAGGR;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.domain.report.WarningAGGR;
import com.tianyi.bph.domain.report.WarningOrgAGGR;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.CaseReportService;
import com.tianyi.bph.service.system.OrganService;

@Service
public class CaseReportServiceImpl implements  CaseReportService {

	@Autowired
	private CaseReportMapper caseReportMapper;
	@Autowired
	private WarningConfigMapper warningCfgMapper;
	@Autowired
	private OrganService orgService;
	
	@Override
	public int loadMaxYMD() {
		int maxymd=caseReportMapper.loadMaxYMD();
		return maxymd;
	}

	@Override
	public Date loadMaxDate() {
		try{
			Date maxDate=caseReportMapper.loadMaxDate();
			return maxDate;
		}catch(Exception ex){
			return null;
		}
	}
	
	@Override
	public void insertCaseInfo(Date beginTime, Date endTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		caseReportMapper.insertCaseInfo(map);
	}

	@Override
	public List<CaseTypeAGGR> loadCaseTypeReport(Map<String, Object> map) {
		List<CaseTypeAGGR> ls=caseReportMapper.loadCaseTypeReport(map);
		return ls;
	}

	@Override
	public List<CasePeriodAGGR> loadCasePeriodReport(Map<String, Object> map) {
		List<CasePeriodAGGR> ls=caseReportMapper.loadCasePeriodReport(map);
		return ls;
	}

	@Override
	public List<CaseHourAGGR> loadCaseHourReport(Map<String, Object> map) {
		List<CaseHourAGGR> ls=caseReportMapper.loadCaseHourReport(map);
		return ls;
	}

	@Override
	public List<CaseOrgAGGR> loadCaseOrgReport(Map<String, Object> map) {
		List<CaseOrgAGGR> ls=caseReportMapper.loadCaseOrgReport(map);
		return ls;
	}

	@Override
	public List<WarningOrgAGGR> loadWarningReport(Map<String, Object> map) {
		
		List<WarningOrgAGGR> w=caseReportMapper.loadWarningReport(map);
		return w;
	}



}
