package com.tianyi.bph.query.report;

import java.util.List;

public class QueryCondition {
	/**
	 ** 开始日期格式如下：
	 ** 2015-06-03
	 **/
	private String startDate;
	/**
	 ** 开始日期格式如下：
	 ** 2015-06-03
	 **/
	private String endDate;
	/**
	 ** 查询日期类型
	 ** 1：天
	 ** 2：周
	 ** 3：月
	 ** 4：年
	 **/
	private int periodType;
	/**
	 ** 组织机构Id
	 **/
	private int organId;
	/**
	 ** 预警规则Id
	 ** 用于提供四色预警查询计算
	 **/
	private int warningCfgId;
	
	/**
	 ** 警情类型
	 **/
	private List<String> caseType;
	/**
	 ** 时间区间
	 ** [0,1,2,3,4,5,6,……23]
	 **/
	private List<Integer> caseTimaSpan;
	/**
	 ** 警情级别
	 ** [1,2,3]
	 **/
	private List<Integer> caseLevels;
	public int getWarningCfgId() {
		return warningCfgId;
	}
	public void setWarningCfgId(int warningCfgId) {
		this.warningCfgId = warningCfgId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) { 
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getPeriodType() {
		return periodType;
	}
	public void setPeriodType(int periodType) {
		this.periodType = periodType;
	}
	public int getOrganId() {
		return organId;
	}
	public void setOrganId(int organId) {
		this.organId = organId;
	}
	public List<String> getCaseType() {
		return caseType;
	}
	public void setCaseType(List<String> caseType) {
		this.caseType = caseType;
	}
	public List<Integer> getCaseTimaSpan() {
		return caseTimaSpan;
	}
	public void setCaseTimaSpan(List<Integer> caseTimaSpan) {
		this.caseTimaSpan = caseTimaSpan;
	}
	public List<Integer> getCaseLevels() {
		return caseLevels;
	}
	public void setCaseLevels(List<Integer> caseLevels) {
		this.caseLevels = caseLevels;
	}
}
