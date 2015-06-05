package com.tianyi.bph.web.action.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.domain.report.ReportPeriod;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.web.controller.reportdata.CaseReportController;

public class CaseReportControllerTest extends BaseTest{
	
	@Autowired
	private CaseReportController   caseReportControll;
	
	@Autowired
	private WarningCfgService warningCfgService;
	
	@Autowired
	private OrganService  organService;
	
	@Test
	public void test(){
		//caseReportControll.loadWarningReport(null, null);
		//this.saveWarning();
		//this.loadWarningByOrgId();
		//int x =testCalendar("2014-12-01","2015-02-01");
		this.testReportPeriod();
	}

	private void  testReportPeriod(){
		try {
			ReportPeriod rp=new ReportPeriod(20141201,20150201,3);
			
			Integer momb=rp.getMOMBeginYmd();
			Integer mome=rp.getMOMEndYmd();
			
			Integer x=rp.getBeginYmd();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private int testCalendar(String date1, String date2){
		int result = 0;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		
		try {
			c1.setTime(sdf.parse(date1));
			c2.setTime(sdf.parse(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);
	}
	
//	@Test
//	public  void  loadCaseHourReport(){
//		caseReportControll.loadCaseHourReport(null, null);
//	}
	
	private void loadWarningByOrgId(){
		Map<String,Object> map=new HashMap<String,Object>();
		OrganQuery orgq =new OrganQuery();
		orgq.setId(72);
		Organ org=organService.getOrganByPrimaryKey(72);
		
		map.put("orgId", 72);
		map.put("orgParentId", org.getParentId());
		
		List<WarningCfgVM> ls= warningCfgService.loadWarningCfgVMByOrgId(map);
		
		WarningCfgVM wcv=ls.get(0);
		double ge=wcv.getColors().get(0).getGe();
	}

	private void saveWarning(){
		
		Integer orgId=1;
		
		WarningColor color =new WarningColor();
		color.setWarningId(0);
		color.setId(0);
		color.setLevel(2);
		color.setGe(15F);
		color.setLt(23.5f);
		color.setDefaultColor("");
		List<WarningColor> cs=new ArrayList<WarningColor>();
		cs.add(color);
		
		List<WarningCaseType> cts=new ArrayList<WarningCaseType>();
		WarningCaseType caseType =new WarningCaseType();
		caseType.setId(0);
		caseType.setWarningId(0);
		caseType.setCaseTypeCode("caseType110010200");
		caseType.setCaseTypeLevel(2);
		cts.add(caseType);
		
		WarningCaseType caseType2 =new WarningCaseType();
		caseType2.setId(0);
		caseType2.setWarningId(0);
		caseType2.setCaseTypeCode("caseType110056000");
		caseType2.setCaseTypeLevel(2);
		cts.add(caseType2);
		
		WarningCfgVM vm=new WarningCfgVM();
		vm.setId(0);
		vm.setCaseTypes(cts);
		vm.setColors(cs);
		vm.setName("owen");
		vm.setOrgId(orgId);
		vm.setShare(false);
		
		warningCfgService.saveWarningCfg(vm);
		
	}
	
}
