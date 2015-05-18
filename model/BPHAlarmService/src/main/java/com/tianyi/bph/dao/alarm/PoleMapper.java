package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.Pole;

import java.util.List;

@MyBatisRepository
public interface PoleMapper {
    int deleteByPrimaryKey(Integer objectid);

    int insert(Pole record);

    int insertSelective(Pole record);

    List<Pole> selectByExample(Pole record);

    Pole selectByPrimaryKey(Integer objectid);

    int updateByExampleSelective(Pole record);

    int updateByExample(Pole record);

    int updateByPrimaryKeySelective(Pole record);

    int updateByPrimaryKey(Pole record);
    
    Pole selectByBm(String gtbm);
}