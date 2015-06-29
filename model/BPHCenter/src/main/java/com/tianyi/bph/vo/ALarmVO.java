package com.tianyi.bph.vo;

import java.text.ParseException;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.tianyi.bph.common.DateUtil;

import com.tianyi.bph.query.alarm.JJDBQuery;


public class ALarmVO {
    public ALarmVO() {
		
	}
	private int[] alarmState;
	private String[] organCodes;
	private String alarmPhone;
	private Integer alarmLocation;
	private String keyWord;
	private String alarmAddress;
	private Integer alarmLevel;
	private String[] alarmTypeOne;
	private String[] alarmTypeTwo;
	private String startTime;
	private String endTime;
	private String[] alarmFine;
	
	public int[] getAlarmState() {
		return alarmState;
	}
	public void setAlarmState(int[] alarmState) {
		this.alarmState = alarmState;
	}
	public String[] getAlarmTypeTwo() {
		return alarmTypeTwo;
	}
	public void setAlarmTypeTwo(String[] alarmTypeTwo) {
		this.alarmTypeTwo = alarmTypeTwo;
	}
	public String[] getOrganCodes() {
		return organCodes;
	}
	public void setOrganCodes(String[] organCodes) {
		this.organCodes = organCodes;
	}
	public String getAlarmPhone() {
		return alarmPhone;
	}
	public void setAlarmPhone(String alarmPhone) {
		this.alarmPhone = alarmPhone;
	}
	public Integer getAlarmLocation() {
		return alarmLocation;
	}
	public void setAlarmLocation(Integer alarmLocation) {
		if(alarmLocation == -1){
			this.alarmLocation = null;
		}else{
			this.alarmLocation = alarmLocation;
		}
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getAlarmAddress() {
		return alarmAddress;
	}
	public void setAlarmAddress(String alarmAddress) {
		this.alarmAddress = alarmAddress;
	}
	public Integer getAlarmLevel() {
		return alarmLevel;
	}
	public void setAlarmLevel(Integer alarmLevel) {
		if(alarmLevel == -1){
			this.alarmLevel = null;
		}else{
			this.alarmLevel = alarmLevel;
		}
	}
	public String[] getAlarmTypeOne() {
		return alarmTypeOne;
	}
	public void setAlarmTypeOne(String[] alarmTypeOne) {
		this.alarmTypeOne = alarmTypeOne;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String[] getAlarmFine() {
		return alarmFine;
	}
	public void setAlarmFine(String[] alarmFine) {
		this.alarmFine = alarmFine;
	}
	public JJDBQuery ctrate() throws ParseException {
		JJDBQuery query = new JJDBQuery();
		if(this.alarmState != null){
			query.setAjzt(alarmState);
		}
		if(this.alarmLocation != null){query.setAlarmLocation(this.alarmLocation);}
		if(!StringUtils.isEmpty(this.alarmPhone)){query.setBjdh(this.alarmPhone);}
		//if(this.alarmTypeOne != null){query.setBjlb(this.alarmTypeOne);}
		if(this.alarmTypeTwo != null){
			query.setBjlx(alarmTypeTwo);
			}		
		//if(this.alarmFine != null){query.setBjxl(this.alarmFine);}
		if(this.alarmLevel != null){query.setCaseLevel(alarmLevel);}
		if(!StringUtils.isEmpty(this.keyWord)){query.setKeyWord(this.keyWord);}
		if(this.organCodes != null){
		query.setOrganCodes(organCodes);
		}
		if(!StringUtils.isEmpty(this.alarmAddress)){query.setSfdz(this.alarmAddress);}
		if(!StringUtils.isEmpty(this.startTime)){query.setStartTime(DateUtil.parse(this.startTime, "yyyyMMddHHmmss"));}
		if(!StringUtils.isEmpty(this.endTime)){query.setEndTime(DateUtil.parse(this.endTime, "yyyyMMddHHmmss"));}
		return query;
	}

}
