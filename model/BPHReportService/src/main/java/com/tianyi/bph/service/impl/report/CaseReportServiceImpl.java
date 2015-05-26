package com.tianyi.bph.service.impl.report;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.tianyi.bph.dao.report.CaseReportMapper;
import com.tianyi.bph.service.report.CaseReportService;

@Service
public class CaseReportServiceImpl implements  CaseReportService {

	@Resource(name = "caseReportMapper")
	private CaseReportMapper caseReportMapper;
	
	@Override
	public int loadMaxYMD() {
		int maxymd=caseReportMapper.loadMaxYMD();
		return maxymd;
	}

	@Override
	public Date loadMaxDate() {
		Date maxDate=caseReportMapper.loadMaxDate();
		return maxDate;
	}
	
	@Override
	public void insertCaseInfo(Date beginTime, Date endTime) {
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("beginTime", beginTime);
		map.put("endTime", endTime);
		
		caseReportMapper.insertCaseInfo(map);
	}



}
