package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.JJDBState;

import java.util.List;
import org.apache.ibatis.annotations.Param;

@MyBatisRepository
public interface JJDBStateMapper {
    int deleteByPrimaryKey(Integer state);

    int insert(JJDBState record);

    int insertSelective(JJDBState record);

    List<JJDBState> selectJJDBStateList();

    JJDBState selectByPrimaryKey(Integer state);

    int updateByExampleSelective(JJDBState record);

    int updateByExample(JJDBState record);

    int updateByPrimaryKeySelective(JJDBState record);

    int updateByPrimaryKey(JJDBState record);
}