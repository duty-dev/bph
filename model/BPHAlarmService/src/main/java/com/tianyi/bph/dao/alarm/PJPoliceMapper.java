package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.PJPolice;
import com.tianyi.bph.domain.alarm.PJPoliceKey;

import java.util.List;

@MyBatisRepository
public interface PJPoliceMapper {
    int deleteByPrimaryKey(PJPolice record);

    int insert(PJPolice record);

    int insertSelective(PJPolice record);

    List<PJPolice> selectByExample(PJPolice record);

    PJPolice selectByPrimaryKey(PJPoliceKey key);

    int updateByExampleSelective( PJPolice record);

    int updateByExample(PJPolice record);

    int updateByPrimaryKeySelective(PJPolice record);

    int updateByPrimaryKey(PJPolice record);
}