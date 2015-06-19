package com.tianyi.bph.domain.report;

import java.util.List;

import com.tianyi.bph.query.report.JJCaseInfo;

public class CaseGpsInfo implements Comparable<CaseGpsInfo>{
	private String typeCode;//警情分类编号
	private String typeName;//警情分类名称
	private Integer count;//警情分类统计数量
	private List<JJCaseInfo> caseInfo;//案情信息：包括：点位信息  mapPoint，接警单编号：jjCode
	private List<CaseGpsInfo> infos;//警情分类子节点信息
	
	
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
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	} 
	public List<CaseGpsInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<CaseGpsInfo> infos) {
		this.infos = infos;
	} 
	@Override
	public int compareTo(CaseGpsInfo cg) {
		// TODO Auto-generated method stub
		return this.getTypeCode().compareTo(cg.getTypeCode());
	}
	public List<JJCaseInfo> getCaseInfo() {
		return caseInfo;
	}
	public void setCaseInfo(List<JJCaseInfo> caseInfo) {
		this.caseInfo = caseInfo;
	}
	
}
