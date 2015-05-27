package com.tianyi.bph.service.impl.report;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tianyi.bph.dao.report.CaseReportMapper;
import com.tianyi.bph.domain.report.CaseTypeAGGR;
import com.tianyi.bph.service.report.CaseReportService;

@Service
public class CaseReportServiceImpl implements  CaseReportService {

	@Autowired
	private CaseReportMapper caseReportMapper;
	
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



}
