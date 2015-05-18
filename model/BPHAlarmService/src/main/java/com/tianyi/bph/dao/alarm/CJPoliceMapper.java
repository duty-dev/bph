package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.CJPolice;
import com.tianyi.bph.domain.alarm.CJPoliceKey;

import java.util.List;

@MyBatisRepository
public interface CJPoliceMapper {
    int deleteByPrimaryKey(CJPoliceKey key);

    int insert(CJPolice record);

    int insertSelective(CJPolice record);

    List<CJPolice> selectByExample(CJPolice record);

    CJPolice selectByPrimaryKey(CJPoliceKey key);

    int updateByExampleSelective(CJPolice record);

    int updateByExample(CJPolice record);

    int updateByPrimaryKeySelective(CJPolice record);

    int updateByPrimaryKey(CJPolice record);
}