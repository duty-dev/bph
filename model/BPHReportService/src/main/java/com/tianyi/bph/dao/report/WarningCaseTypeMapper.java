package com.tianyi.bph.dao.report;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.WarningCaseType;

@MyBatisRepository
public interface WarningCaseTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningCaseType record);

    int insertSelective(WarningCaseType record);

    WarningCaseType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningCaseType record);

    int updateByPrimaryKey(WarningCaseType record);
    
    void deleteByWarningId(Integer warningId);
}