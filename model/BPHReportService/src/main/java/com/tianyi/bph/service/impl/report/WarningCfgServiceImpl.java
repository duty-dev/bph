package com.tianyi.bph.service.impl.report;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tianyi.bph.dao.report.WarningCaseLevelMapper;
import com.tianyi.bph.dao.report.WarningCaseTypeMapper;
import com.tianyi.bph.dao.report.WarningColorMapper;
import com.tianyi.bph.dao.report.WarningConfigMapper;
import com.tianyi.bph.domain.report.WarningCaseLevel;
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
	@Autowired
	private WarningCaseLevelMapper warningCaseLevelMapper;
	
	@Override
	@Transactional
	public void saveWarningCfg(WarningCfgVM vm)  {
		try{
			if(vm.getId()==0){
				warningConfigMapper.insert(vm);
			}else{
				warningConfigMapper.updateByPrimaryKey(vm);
			}
			
			Integer warningId=vm.getId();
			
			warningCaseLevelMapper.deleteByWarningId(warningId);
			List<WarningCaseLevel> caseLevels=vm.getCaseLevels();
			for(WarningCaseLevel caseLevel : caseLevels){
				caseLevel.setWarningId(vm.getId());
				warningCaseLevelMapper.insert(caseLevel);
			}
			
			warningCaseTypeMapper.deleteByWarningId(warningId);
			List<WarningCaseType> caseTypes =vm.getCaseTypes();
			for(WarningCaseType caseType : caseTypes){
				caseType.setWarningId(vm.getId());
				warningCaseTypeMapper.insert(caseType);	
			}
			
			List<WarningColor> colors=vm.getColors();
			warningColorMapper.deleteByWarningId(warningId);
			for(WarningColor color : colors){
				color.setWarningId(vm.getId());
				warningColorMapper.insert(color);
			}
			
		}catch(Exception ex){
			int x=100;
		}
		
	}

	@Override
	public List<WarningCfgVM> loadWarningCfgVMByOrgId(Map<String, Object> map) {
		List<WarningCfgVM> ls=warningConfigMapper.loadWarningCfgVMByOrgId(map);
		return ls;
	}
	
}
