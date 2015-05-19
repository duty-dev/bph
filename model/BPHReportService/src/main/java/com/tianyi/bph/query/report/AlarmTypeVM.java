package com.tianyi.bph.query.report;

import java.util.List;

import com.tianyi.bph.domain.alarm.AlarmType;

public class AlarmTypeVM {
	
	private AlarmType alarmType;
	
	private List<AlarmType> alarmTypeList;

	public AlarmType getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(AlarmType alarmType) {
		this.alarmType = alarmType;
	}

	public List<AlarmType> getAlarmTypeList() {
		return alarmTypeList;
	}

	public void setAlarmTypeList(List<AlarmType> alarmTypeList) {
		this.alarmTypeList = alarmTypeList;
	}
}
