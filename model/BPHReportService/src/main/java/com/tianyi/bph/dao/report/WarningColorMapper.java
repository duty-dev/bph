package com.tianyi.bph.dao.report;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.report.WarningColor;

@MyBatisRepository
public interface WarningColorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarningColor record);

    int insertSelective(WarningColor record);

    WarningColor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(WarningColor record);

    int updateByPrimaryKey(WarningColor record);
}