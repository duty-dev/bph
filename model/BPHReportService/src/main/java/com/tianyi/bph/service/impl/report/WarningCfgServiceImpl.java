package com.tianyi.bph.service.impl.report;

import java.util.List;
import java.util.Map;

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
	public void saveWarningCfg(WarningCfgVM vm)  {
		try{
			if(vm.getId()==0){
				warningConfigMapper.insert(vm);
			}else{
				warningConfigMapper.updateByPrimaryKey(vm);
			}
			List<WarningColor> colors=vm.getColors();
			for(WarningColor color : colors){
				color.setWarningId(vm.getId());
				if(color.getId() ==0){
					warningColorMapper.insert(color);
				}else{
					warningColorMapper.updateByPrimaryKey(color);
				}
			}
			List<WarningCaseType> caseTypes =vm.getCaseTypes();
			for(WarningCaseType caseType : caseTypes){
				caseType.setWarningId(vm.getId());
				if(caseType.getId()==0){
					warningCaseTypeMapper.insert(caseType);	
				}else{
					warningCaseTypeMapper.updateByPrimaryKey(caseType);	
				}
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
