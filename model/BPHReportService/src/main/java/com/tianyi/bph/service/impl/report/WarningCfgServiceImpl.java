package com.tianyi.bph.service.impl.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.dao.report.WarningCaseTypeMapper;
import com.tianyi.bph.dao.report.WarningColorMapper;
import com.tianyi.bph.dao.report.WarningConfigMapper;
import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.domain.report.WarningColor;
import com.tianyi.bph.query.report.WarningCfgVM;
import com.tianyi.bph.service.report.WarningCfgService;

@Service
public class WarningCfgServiceImpl implements WarningCfgService{

	@Autowired
	private WarningConfigMapper warningConfigMapper;
	@Autowired
	private WarningColorMapper  warningColorMapper;
	@Autowired
	private WarningCaseTypeMapper warningCaseTypeMapper;
	
	@Override
	@Transactional
	public void saveWarningCfg(WarningCfgVM vm) {
		if(vm.getId()==0){
			warningConfigMapper.insert(vm);
		}else{
			warningConfigMapper.updateByPrimaryKey(vm);
		}
		List<WarningColor> colors=vm.getColors();
		for(WarningColor color : colors){
			if(color.getId() ==0){
				warningColorMapper.insert(color);
			}else{
				warningColorMapper.updateByPrimaryKey(color);
			}
		}
		List<WarningCaseType> caseTypes =vm.getCaseTypes();
		for(WarningCaseType caseType : caseTypes){
			if(caseType.getId()==0){
				warningCaseTypeMapper.insert(caseType);	
			}else{
				warningCaseTypeMapper.updateByPrimaryKey(caseType);	
			}
		}
	}

	public List<WarningCfgVM> loadWarningCfgVMByOrgId(Integer orgId){
		
		
		return null;
	}
	
}
