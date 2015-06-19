package com.tianyi.bph.query.report;

import java.util.List;

public class AlarmMapResultList {
	/**
	 ** 查询警情类型编码
	 ** 父级节点编码
	 **/
	private String typeCode;
	/**
	 ** 查询警情类型名称
	 ** 父级节点名称
	 **/
	private String typeName;
	/**
	 ** 查询警情类型统计数量
	 **/
	private int totalCount;
	/**
	 ** 查询案情信息对象
	 ** 
	 **/
	private List<JJCaseInfo> caseInfo;
	/**
	 * 子节点信息
	 **/
	private List<AlarmMapResultList> resultList;
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	} 
	public List<AlarmMapResultList> getResultList() {
		return resultList;
	}
	public void setResultList(List<AlarmMapResultList> resultList) {
		this.resultList = resultList;
	}
	public List<JJCaseInfo> getCaseInfo() {
		return caseInfo;
	}
	public void setCaseInfo(List<JJCaseInfo> caseInfo) {
		this.caseInfo = caseInfo;
	}
}
