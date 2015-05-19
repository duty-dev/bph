package com.tianyi.bph.dao.alarm;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.AlarmCommunication;

import java.util.List;

@MyBatisRepository
public interface AlarmCommunicationMapper {
    //int deleteByPrimaryKey(alarmCommunicationKey key);

    int insert(AlarmCommunication record);

    int insertSelective(AlarmCommunication record);

    List<AlarmCommunication> selectByExample(AlarmCommunication record);

    //alarmCommunication selectByPrimaryKey(alarmCommunicationKey key);

    int updateByExampleSelective(AlarmCommunication record);

    int updateByExample(AlarmCommunication record);

    int updateByPrimaryKeySelective(AlarmCommunication record);

    int updateByPrimaryKey(AlarmCommunication record);
}