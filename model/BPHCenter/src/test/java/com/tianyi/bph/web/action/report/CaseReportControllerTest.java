package com.tianyi.bph.web.action.report;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.web.controller.reportdata.CaseReportController;

public class CaseReportControllerTest extends BaseTest{
	
	@Autowired
	private CaseReportController   caseReportControll;
	
//	@Test
//	public  void  loadCasePeriodReport(){
//		caseReportControll.loadCasePeriodReport(null, null);
//	}
	
	@Test
	public  void  loadCaseHourReport(){
		caseReportControll.loadCaseHourReport(null, null);
	}
}
