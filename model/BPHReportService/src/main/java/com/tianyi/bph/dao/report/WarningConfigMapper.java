package com.tianyi.bph.dao.report;

import java.util.List;
import java.util.Map;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.WarningConfig;
import com.tianyi.bph.query.report.WarningCfgVM;

@MyBatisRepository
public interface WarningConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningConfig record);

    int insertSelective(WarningConfig record);

    WarningConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningConfig record);

    int updateByPrimaryKey(WarningConfig record);
    
    List<WarningCfgVM> loadWarningCfgVMByOrgId(Map<String,Object> map);

	int loadWarningCfgVMCountByOrgId(Map<String, Object> map);

	void deleteCaseTypeItemByCaseId(Integer caseId);

	void deleteColorItemByCaseId(Integer caseId);

	WarningCfgVM loadWarningCfgVMInfoById(Integer caseId);
}