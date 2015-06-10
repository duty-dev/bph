package com.tianyi.bph.query.report;

import java.util.List;

public class ResultList {
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
	private List<CaseInfo> caseList;
	/**
	 * 子节点信息
	 **/
	private List<ResultList> resultList;
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
	public List<CaseInfo> getCaseList() {
		return caseList;
	}
	public void setCaseList(List<CaseInfo> caseList) {
		this.caseList = caseList;
	}
	public List<ResultList> getResultList() {
		return resultList;
	}
	public void setResultList(List<ResultList> resultList) {
		this.resultList = resultList;
	}
}
