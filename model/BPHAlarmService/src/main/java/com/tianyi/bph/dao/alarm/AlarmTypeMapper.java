package com.tianyi.bph.dao.alarm;

import java.util.List;

import com.tianyi.bph.dao.MyBatisRepository;
import com.tianyi.bph.domain.alarm.AlarmType;

@MyBatisRepository
public interface AlarmTypeMapper {
	 List<AlarmType>  getAlarmTypeList(AlarmType alarmType);

}
