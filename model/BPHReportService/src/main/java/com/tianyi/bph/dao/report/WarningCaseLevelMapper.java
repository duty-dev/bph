package com.tianyi.bph.dao.report;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.WarningCaseLevel;

@MyBatisRepository
public interface WarningCaseLevelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningCaseLevel record);

    int insertSelective(WarningCaseLevel record);

    WarningCaseLevel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningCaseLevel record);

    int updateByPrimaryKey(WarningCaseLevel record);
    
    void deleteByWarningId(Integer warningId);
}