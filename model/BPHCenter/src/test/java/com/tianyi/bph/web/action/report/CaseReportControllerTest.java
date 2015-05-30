package com.tianyi.bph.web.action.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.BaseTest;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.domain.system.Organ;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.query.system.OrganQuery;
import com.tianyi.bph.service.report.WarningCfgService;
import com.tianyi.bph.service.system.OrganService;
import com.tianyi.bph.web.controller.reportdata.CaseReportController;

public class CaseReportControllerTest extends BaseTest{
	
//	@Autowired
//	private CaseReportController   caseReportControll;
//	
	@Autowired
	private WarningCfgService warningCfgService;
	
	@Autowired
	private OrganService  organService;
	
	@Test
	public void test(){
		//this.saveWarning();
		this.loadWarningByOrgId();
	}
//	@Test
//	public  void  loadCasePeriodReport(){
//		caseReportControll.loadCasePeriodReport(null, null);
//	}
	
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
		
		WarningColor color =new WarningColor();
		color.setWarningId(0);
		color.setId(0);
		color.setLevel(2);
		color.setGe(22F);
		color.setLt(33f);
		color.setDefaultColor("");
		List<WarningColor> cs=new ArrayList<WarningColor>();
		cs.add(color);
		
		WarningCaseType caseType =new WarningCaseType();
		caseType.setId(0);
		caseType.setWarningId(0);
		caseType.setCaseTypeCode("caseType110010200");
		caseType.setCaseTypeLevel(2);
		List<WarningCaseType> cts=new ArrayList<WarningCaseType>();
		cts.add(caseType);
		
		WarningCfgVM vm=new WarningCfgVM();
		vm.setId(0);
		vm.setCaseTypes(cts);
		vm.setColors(cs);
		vm.setName("frist four color warning settings");
		vm.setOrgId(72);
		vm.setShare(false);
		
		warningCfgService.saveWarningCfg(vm);
		
	}
	
}
