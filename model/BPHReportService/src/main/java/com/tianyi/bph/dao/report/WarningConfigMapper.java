package com.tianyi.bph.dao.report;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.WarningConfig;

@MyBatisRepository
public interface WarningConfigMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningConfig record);

    int insertSelective(WarningConfig record);

    WarningConfig selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningConfig record);

    int updateByPrimaryKey(WarningConfig record);
}