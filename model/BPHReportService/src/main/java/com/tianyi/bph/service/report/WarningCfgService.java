package com.tianyi.bph.service.report;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.domain.report.WarningCaseType;
import com.tianyi.bph.query.report.WarningCfgVM;

public interface WarningCfgService {
	
	void saveWarningCfg(WarningCfgVM  vm) ;
	
	List<WarningCfgVM> loadWarningCfgVMByOrgId(Map<String,Object> map);
	int loadWarningCfgVMCountByOrgId(Map<String,Object> map);

	void deleteById(Integer caseId);

	WarningCfgVM loadWarningCfgVMInfoById(Integer caseId);

	WarningCaseType getNodechecked(Map<String, Object> map);
}
