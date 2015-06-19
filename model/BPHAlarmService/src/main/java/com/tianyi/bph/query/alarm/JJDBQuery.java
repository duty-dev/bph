package com.tianyi.bph.query.alarm;

import java.util.Date;

public class JJDBQuery {
	private int[] ajzt; //警情状态
	private	String	jjdbh; //接警单编号
	private String[] organCodes; //机构编号
	private String bjdh;	//报警电话
	private Integer caseLevel; //警情级别
	private String[] bjlb; //报警类别（一级类型）
	private String[] bjlx; //报警类型（二级类型）
	private String[] bjxl; //报警细类
	private String sfdz; //事发地址
	private Date startTime; //开始时间
	private Date endTime; //结束时间
	private String keyWord; //关键字
	private Integer alarmLocation; //是否警情定位

	public String getJjdbh() {
		return jjdbh;
	}
	public void setJjdbh(String jjdbh) {
		this.jjdbh = jjdbh;
	}
	public String getBjdh() {
		return bjdh;
	}
	public void setBjdh(String bjdh) {
		this.bjdh = bjdh;
	}
	public Integer getCaseLevel() {
		return caseLevel;
	}
	public void setCaseLevel(Integer caseLevel) {
		this.caseLevel = caseLevel;
	}
	public int[] getAjzt() {
		return ajzt;
	}
	public void setAjzt(int[] ajzt) {
		this.ajzt = ajzt;
	}
	public String[] getOrganCodes() {
		return organCodes;
	}
	public void setOrganCodes(String[] organCodes) {
		this.organCodes = organCodes;
	}
	public String[] getBjlb() {
		return bjlb;
	}
	public void setBjlb(String[] bjlb) {
		this.bjlb = bjlb;
	}
	public String[] getBjlx() {
		return bjlx;
	}
	public void setBjlx(String[] bjlx) {
		this.bjlx = bjlx;
	}
	public String[] getBjxl() {
		return bjxl;
	}
	public void setBjxl(String[] bjxl) {
		this.bjxl = bjxl;
	}
	public String getSfdz() {
		return sfdz;
	}
	public void setSfdz(String sfdz) {
		this.sfdz = sfdz;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
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



}
