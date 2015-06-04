package com.tianyi.bph.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tianyi.bph.dao.report.CaseReportMapper;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.WarningCfgService;

public class CaseReportServiceTest extends BaseTest{
	
	@Autowired
	private WarningCfgService warningCfgService;
	
	@Test
	public void saveWarning(){
		
		WarningColor color =new WarningColor();
		color.setWarningId(0);
		color.setId(0);
		color.setLevel(2);
		color.setGe(21f);
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
